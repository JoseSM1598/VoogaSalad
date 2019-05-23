package physics.external;

import com.google.common.eventbus.EventBus;
import messenger.external.AttackIntersectEvent;
import messenger.external.EventBusFactory;
import messenger.external.GroundIntersectEvent;
import messenger.external.PositionsUpdateEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.lang.Math.PI;

/**
 * Main class of Physics that is used to start a physics environment
 *
 * @author skm44
 * @author jrf36
 */

public class PhysicsSystem {

    public static final double DEFAULT_MASS = 50;
    public static final double DEFAULT_STRENGTH = 20;
    public static final double DEFAULT_JUMP_HEIGHT = 50000;
    public static final double DEFAULT_MOVEMENT_SPEED = 5000;
    public static final double DEFAULT_ATTACK_SPACE = 10;
    public static final double TERMINAL_VELOCITY = 200;

    private int playerId;
    private int groundId;
    private int attackId;


    List<PlayerCharacteristics> playerCharacteristics = new ArrayList<>();

    Map<Integer, PhysicsObject> gameObjects = new HashMap<>();

    private EventBus myMessageBus;

    public PhysicsSystem() {
        this.myMessageBus = EventBusFactory.getEventBus();
        this.playerId = 0;
        this.attackId = 100;
        this.groundId = 1000;
    }

    public void update() {
        PassiveForceHandler passHandler = new PassiveForceHandler(gameObjects);
        passHandler.update();
        CollisionDetector detector = new CollisionDetector(gameObjects);
        List<Collision> collisions = new ArrayList<>(detector.detectCollisions(gameObjects));
        CollisionHandler collHandler = new CollisionHandler(collisions);
        collHandler.update();
        List<Integer> groundCollisions = collHandler.getGroundCollisions();
        List<List<Integer>> attackCollisions = collHandler.getAttackCollisions();
        applyForces();
        updatePositions();
        GroundIntersectEvent groundedPlayers = new GroundIntersectEvent(groundCollisions);
        if (groundCollisions.size() > 0) {
            myMessageBus.post(groundedPlayers);
        }
        AttackIntersectEvent attackPlayers = new AttackIntersectEvent(attackCollisions);
        if (attackPlayers.getAttackPlayers().size() > 0) {
            myMessageBus.post(attackCollisions);
        }
        myMessageBus.post(new PositionsUpdateEvent(getPositionsMap(), getDirectionsMap()));
    }

    public void addPhysicsObject(int id, double mass, double XCoordinate, double YCoordinate, double XDimension, double YDimension, int respawnX, int respawnY) { // type 0: player, type 1: attack, type 2: ground
        if (id >= 0 && id < 100) {
            gameObjects.put(id, new PhysicsBody(id, mass, new Coordinate(XCoordinate,YCoordinate), new Dimensions(XDimension,YDimension), new CoordinateBody(new Coordinate(XCoordinate,YCoordinate), new Dimensions(XDimension,YDimension)), respawnX, respawnY));
            playerCharacteristics.add(new PlayerCharacteristics(id, DEFAULT_STRENGTH, DEFAULT_JUMP_HEIGHT, DEFAULT_MOVEMENT_SPEED));
            playerId++;
        } else {
            gameObjects.put(id, new PhysicsGround(id, mass, new Coordinate(XCoordinate,YCoordinate), new Dimensions(XDimension,YDimension), new CoordinateGround(new Coordinate(XCoordinate,YCoordinate), new Dimensions(XDimension,YDimension))));
            groundId++;
        }
    }

    public void applyForces() {
        for (PhysicsObject o : gameObjects.values()) {
            List<PhysicsVector> currentForces = new ArrayList<>(o.getCurrentForces());
            if (!o.isPhysicsGround()) {
                NetVectorCalculator calc = new NetVectorCalculator(currentForces);
                o.applyForce(calc.getNetVector());
                o.clearCurrentForces();
            }
        }

    }

    public void updatePositions() {
        PositionCalculator calc = new PositionCalculator(gameObjects);
        calc.updatePositions();
    }

    public Map<Integer, Point2D> getPositionsMap() {
        Map<Integer, Point2D> out = new HashMap<>();
        for(PhysicsObject obj: gameObjects.values()){
            if (!obj.isPhysicsGround()) {
                Point2D.Double point = new Point2D.Double(obj.getMyCoordinateBody().getPos().getX(), obj.getMyCoordinateBody().getPos().getY());
                out.put(obj.getId(), point);
            }
        }
        return out;
    }

    public Map<Integer, Double> getDirectionsMap() {
        Map<Integer, Double> out = new HashMap<>();
        double direction;
        for(PhysicsObject obj: gameObjects.values()){
            if (obj.isPhysicsBody()) {
                direction = obj.getDirection();
                out.put(obj.getId(), direction);
            }
        }return out;
    }

    public Map<Integer, PhysicsObject> getGameObjects() {
        return this.gameObjects;
    }

    public void jump(int id) {
        PhysicsObject currentBody = gameObjects.get(id);
        currentBody.addCurrentForce(new PhysicsVector(currentBody.getMass() * DEFAULT_JUMP_HEIGHT, -PI/2));
    }

    public void move(int id, double direction) {
        PhysicsObject currentBody = gameObjects.get(id);
        currentBody.setDirection(direction);
        if (Math.abs(currentBody.getXVelocity().getMagnitude()) < TERMINAL_VELOCITY) {
            currentBody.addCurrentForce(new PhysicsVector(currentBody.getMass() * DEFAULT_MOVEMENT_SPEED, direction));
        }
    }

    public void attack(int id) {
        int direction = 1;
        double parentDirection = gameObjects.get(id).getDirection();
        if (parentDirection != 0) {
            direction = -1;
        }
        Coordinate playerLocation = gameObjects.get(id).getMyCoordinateBody().getPos();
        Coordinate attackLocation = new Coordinate(playerLocation.getX() + direction * DEFAULT_ATTACK_SPACE,playerLocation.getY() + DEFAULT_ATTACK_SPACE);
        double XCoordinate = attackLocation.getX();
        double YCoordinate = attackLocation.getY();
        PhysicsAttack attack = new PhysicsMelee(attackId, id, parentDirection, gameObjects.get(id).getMass(), attackLocation, new Dimensions(40, 20), new CoordinateGround(new Coordinate(XCoordinate,YCoordinate), new Dimensions(40,20)));
        gameObjects.put(attackId, attack);
        attackId++;
    }

    public void setHitBox(int type, int id, double XPosition, double YPosition, double XDimension, double YDimension) { // 0: Hitbox, 1: Hurtbox
        Dimensions newDims = new Dimensions(XDimension, YDimension);
        if (type == 0) {
            gameObjects.get(id).getMyCoordinateBody().setDims(newDims);
        } else if (type ==1) {
            gameObjects.get(id).setHurtBoxDimensions(newDims);
        }
    }
}
