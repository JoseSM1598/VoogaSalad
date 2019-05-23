package physics.external;

/**
 * Concrete class of PhysicsObject representing a ground
 *
 * @author skm44
 * @author jrf36
 */

public class PhysicsGround extends PhysicsObject {

    private double frictionCoef;

    PhysicsGround(int id, double mass, Coordinate start, Dimensions dims, CoordinateObject cord) {
        super(id, mass, start, dims, cord);
        this.frictionCoef = .5;
    }

    PhysicsGround(int id, double mass, Coordinate start, Dimensions dims, double frictionCoef, CoordinateObject cord) {
        super(id, mass, start, dims, cord);
        this.frictionCoef = frictionCoef;
    }

    @Override
    public boolean isPhysicsGround(){
        return true;
    }

    @Override
    public PhysicsVector getXVelocity() {
        return null;
    }

    @Override
    public PhysicsVector getYVelocity() {
        return null;
    }

    public double getFrictionCoef() {
        return frictionCoef;
    }

    public void setFrictionCoef(double frictionCoef) {
        this.frictionCoef = frictionCoef;
    }
}
