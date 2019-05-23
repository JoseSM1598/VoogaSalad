package physics.external;

import java.util.*;

import static java.lang.Math.PI;
import static physics.external.PassiveForceHandler.DEFAULT_GRAVITY_ACCELERATION;

/**
 * Uses output of collisionDetector to filter out significant collisions and apply the correct resulting forces
 *
 * @author skm44
 * @author jrf36
 */

public class CollisionHandler {

    public static double defaultAttackMagnitude = 2000000;
    public static final double timeOfFrame = 0.016666666; // Assume each frame is 1/8 of a sec
    public static final double KINETIC_FRICTION_THRESHOLD = 10;

    private List<Collision> myCollisions;
    private List<Integer> groundCollisions = new ArrayList<>();
    private List<List<Integer>> attackCollisions = new ArrayList<>();

    public CollisionHandler(List<Collision> collisions){
        filterCollisions(collisions);
    }

    private void filterCollisions(List<Collision> collisions) {
        Iterator<Collision> collisionIterator = collisions.iterator();
        Set<PhysicsObject> groundCols = new HashSet<>();
        this.myCollisions = new ArrayList<>();
        //Filter so that only receive single versions of body and ground/attack collisions
        while(collisionIterator.hasNext()){
            Collision col = collisionIterator.next();
            PhysicsObject one = col.getCollider1();
            PhysicsObject two = col.getCollider2();
            if(one.getId() == 1 && two.getId() > 100){
                //System.out.println(col.getSide().getMySide());
            }
            if (one.isPhysicsBody() && two.isPhysicsGround()) {
                filterGroundCollisions(groundCols, one, col);
            } else if (one.isPhysicsBody() && two.isPhysicsAttack()) {
                filterAttackCollisions(one, two, col);
            }
        }
    }

    private void filterGroundCollisions(Set<PhysicsObject> groundCols, PhysicsObject one, Collision col) {
        if (!groundCols.contains(one)) {
            groundCols.add(one);
            this.myCollisions.add(col);
        }
    }

    private void filterAttackCollisions(PhysicsObject one, PhysicsObject two, Collision col) {
        if (!(one.getId() == two.getParentID())) {
            this.myCollisions.add(col);
        }
    }

    public void update() {
        for (Collision c : myCollisions) {
            PhysicsObject one, two;
            one = c.getCollider1();
            two = c.getCollider2();
            if(one.isPhysicsBody() && two.isPhysicsAttack()){ // body+attack
                attackCollisions.add(playerAndAttack(one, two));
            } if(one.isPhysicsBody() && two.isPhysicsGround() && c.getSide().getMySide().equals("BOTTOM")){// body+ground
                PhysicsGround ground = (PhysicsGround)two;
                applyReactiveForce(one);
                if(Math.abs(one.getXVelocity().getMagnitude()) > KINETIC_FRICTION_THRESHOLD) { //Should we apply kinetic friction?
                    one.addCurrentForce(getKineticFriction(one, ground));
                }else{
                    one.addCurrentForce(getStaticFriction(one));
                }
                groundCollisions.add(one.getId());
            } else if(one.isPhysicsBody() && two.isPhysicsGround() && c.getSide().getMySide().equals("TOP")){
                one.addCurrentForce(topCollisionBodyGround(one));
            } else if(one.isPhysicsBody() && two.isPhysicsGround() && c.getSide().getMySide().equals("LEFT")){
                one.addCurrentForce(leftCollisionBodyGround(one));
            } else if(one.isPhysicsBody() && two.isPhysicsGround() && c.getSide().getMySide().equals("RIGHT")){
                one.addCurrentForce(rightCollisionBodyGround(one));
            }
        }
    }

    public PhysicsVector rightCollisionBodyGround(PhysicsObject one){
        double bodyVelocity = Math.abs(one.getXVelocity().getMagnitude());
        double bodyMass = one.getMass();
        PhysicsVector leftwardForce = new PhysicsVector(bodyMass*bodyVelocity/(timeOfFrame), 0);
        return leftwardForce;
    }

    public PhysicsVector leftCollisionBodyGround(PhysicsObject one){
        double bodyVelocity = Math.abs(one.getXVelocity().getMagnitude());
        double bodyMass = one.getMass();
        PhysicsVector rightwardForce = new PhysicsVector(bodyMass*bodyVelocity/(timeOfFrame), PI);
        return rightwardForce;
    }

    public PhysicsVector topCollisionBodyGround(PhysicsObject one){
        double bodyVelocity = one.getYVelocity().getMagnitude();
        double bodyMass = one.getMass();
        double gravityMag = Math.round(one.getMass() * DEFAULT_GRAVITY_ACCELERATION);
        PhysicsVector downwardForce = new PhysicsVector(Math.round(bodyMass*bodyVelocity/(timeOfFrame) - gravityMag), -PI/2);
        return downwardForce;
    }

    public PhysicsVector getKineticFriction(PhysicsObject one, PhysicsGround ground){
        PhysicsVector friction;
        if(one.getXVelocity().getMagnitude() > 0) {
            friction = new PhysicsVector((int) -one.getMass() * DEFAULT_GRAVITY_ACCELERATION * ground.getFrictionCoef(), 0);
        }else{
            friction = new PhysicsVector((int) one.getMass() * DEFAULT_GRAVITY_ACCELERATION * ground.getFrictionCoef(), 0);
        }
        return friction;
    }

    public PhysicsVector getStaticFriction(PhysicsObject one){
        PhysicsVector staticFriction;
        double bodyMass = one.getMass();
        double bodyVelocity = one.getXVelocity().getMagnitude();
        staticFriction = new PhysicsVector(-bodyMass*bodyVelocity/timeOfFrame, 0);
        return staticFriction;
    }

    public List<Integer> playerAndAttack(PhysicsObject one, PhysicsObject two){
        System.out.println("Successful Hit Attack");
        PhysicsVector force = new PhysicsVector(defaultAttackMagnitude, two.getDirection());
        one.addCurrentForce(force);
        System.out.println("CH Current Forces:");
        for (PhysicsVector f : one.getCurrentForces()) {
            System.out.println(f.getMagnitude() + ", " + f.getDirection());
        }
        List<Integer> collisions = new ArrayList<>();
        collisions.add(one.getId());
        collisions.add(two.getId());
        return collisions;
    }

    public void applyReactiveForce(PhysicsObject one){
        double bodyVelocity = one.getYVelocity().getMagnitude();
        double bodyMass = one.getMass();
        double gravityMag = Math.round(one.getMass() * DEFAULT_GRAVITY_ACCELERATION);
        PhysicsVector upwardForce = new PhysicsVector(Math.round(bodyMass*bodyVelocity/(timeOfFrame) + gravityMag), -PI/2);
        one.addCurrentForce(upwardForce);
    }

    public List<Integer> getGroundCollisions() {
        return groundCollisions;
    }

    public List<List<Integer>> getAttackCollisions() {
        return attackCollisions;
    }



}
