package physics.external;

/**
 * Concrete class of PhysicsAttack representing a moving projectile attack
 *
 * @author skm44
 * @author jrf36
 */

public class PhysicsProjectile extends PhysicsAttack {

    PhysicsProjectile(int id, int parentID, double direction, double mass, Coordinate start, Dimensions dims, CoordinateObject cord) {
        super(id, parentID, direction, mass, start, dims, cord);
    }

    @Override
    public PhysicsVector getYVelocity() {
        double yMag = this.getVelocity().getMagnitude() * Math.sin(this.getVelocity().getDirection());
        PhysicsVector yVel = new PhysicsVector(yMag, -Math.PI / 2);
        return yVel;
    }

    @Override
    public PhysicsVector getXVelocity() {
        double xMag = this.getVelocity().getMagnitude() * Math.cos(this.getVelocity().getDirection());
        PhysicsVector xVel = new PhysicsVector(xMag, 0);
        return xVel;
    }
}
