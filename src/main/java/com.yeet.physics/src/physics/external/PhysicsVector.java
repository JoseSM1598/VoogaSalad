package physics.external;

/**
 * Class that is applied to any vector (velocity, acceleration, force), and has a magnitude and direction
 *
 * @author skm44
 */

public class PhysicsVector {
    protected double magnitude;
    protected double direction; // IN RADIANS

    public PhysicsVector (double magnitude, double direction) {
        this.magnitude = magnitude;
        this.direction = direction;
    }
    public double getMagnitude() {
        return magnitude;
    }
    public double getDirection() {
        return direction;
    }
}