package physics.external;

import java.util.ArrayList;
import java.util.List;


/**
 * Determines new acceleration and velocity based on any new forces
 *
 * @author skm44
 * @author jrf36
 */

public class AccelerationCalculator {

    private PhysicsVector newForce;
    private PhysicsVector myAcceleration; // acceleration changes each frame
    private List<PhysicsVector> myVelocities = new ArrayList<>(); // velocity needs to have memory of previous velocity
    private double myMass;
    public static final double timeOfFrame = 0.016666666; // Assume each frame is 1/8 of a sec

    public AccelerationCalculator(PhysicsVector f, PhysicsVector v, double mass) {
        this.newForce = f;
        this.myVelocities.add(v);
        this.myMass = mass;
    }

    public PhysicsVector updateAcceleration(){
        //Acceleration = netforce/mass
        myAcceleration = new PhysicsVector(newForce.getMagnitude() / myMass, newForce.getDirection());
        return myAcceleration;
    }

    public PhysicsVector updateVelocity(){
        //V = V(0) + at
        PhysicsVector newVelocity = new PhysicsVector((myAcceleration.getMagnitude() * timeOfFrame), myAcceleration.getDirection());
        myVelocities.add(newVelocity);
        return condenseVector(myVelocities);
    }

    private PhysicsVector condenseVector(List<PhysicsVector> vectors) {
        //System.out.println("Uncondensed Vectors: ");
        for (PhysicsVector v : vectors) {
            //System.out.println(v.getMagnitude() + ", " + v.getDirection());
        }
        NetVectorCalculator calc = new NetVectorCalculator(vectors);
        //System.out.println("Condensed Vector: " + calc.getNetVector().getMagnitude() + ", " + calc.getNetVector().getDirection());
        return calc.getNetVector();
    }
}
