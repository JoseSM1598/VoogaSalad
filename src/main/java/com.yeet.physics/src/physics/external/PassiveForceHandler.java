package physics.external;

import java.util.Map;

import static java.lang.Math.PI;

/**
 * Applies passive force of gravity
 *
 * @author skm44
 * @author jrf36
 */

public class PassiveForceHandler {

    public static final double DEFAULT_GRAVITY_ACCELERATION = 1800;
    public static final double DEFAULT_GRAVITY_DIRECTION = PI / 2;
    public static final double TERMINAL_VELOCITY = 800;


    private Map<Integer, PhysicsObject> myObjects;

    public PassiveForceHandler(Map<Integer, PhysicsObject> objects) {
        this.myObjects = objects;
    }

    public void update() {
        for (PhysicsObject o : myObjects.values()) {
            if (o.isPhysicsBody() && (o.getYVelocity().getMagnitude() < TERMINAL_VELOCITY)) {
                o.addCurrentForce(new PhysicsVector(Math.round(o.getMass() * DEFAULT_GRAVITY_ACCELERATION), DEFAULT_GRAVITY_DIRECTION)); // always add gravity
            }
        }
    }
}
