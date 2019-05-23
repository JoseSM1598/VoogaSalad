package physics.external;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class of an object affected by physics forces
 *
 * @author skm44
 * @author jrf36
 */

public abstract class PhysicsObject {

    protected double myMass;
    protected List<PhysicsVector> currentForces = new ArrayList<>();
    protected CoordinateObject myCoordinateBody;
    protected double myDirection; //0: right, PI: left
    protected PhysicsVector myAcceleration;
    protected PhysicsVector myVelocity;
    protected boolean isGrounded;
    protected Dimensions hurtBoxDimensions;
    protected int id;

    public PhysicsObject(int id, double mass, Coordinate start, Dimensions dims, CoordinateObject cord) {
        this.myMass = mass;
        this.myCoordinateBody = cord;
        this.myMass = mass;
        this.myAcceleration = new PhysicsVector(0, 0);
        this.myVelocity = new PhysicsVector(0, 0);
        this.myCoordinateBody = new CoordinateBody(start, dims);
        this.myDirection = 0; // start facing right
        this.id = id;
        this.isGrounded = false;
        this.hurtBoxDimensions = new Dimensions(40, 20);
    }

    public void applyForce(PhysicsVector force){ // ONLY CALL ONCE PER FRAME
        AccelerationCalculator ACalc = new AccelerationCalculator(force, myVelocity, myMass);
        this.myAcceleration = ACalc.updateAcceleration();
        this.myVelocity = ACalc.updateVelocity();
    }

    public double getMass() {
        return myMass;
    }

    public PhysicsVector getAcceleration(){
        return this.myAcceleration;
    }

    public PhysicsVector getVelocity(){
        return this.myVelocity;
    }

    public void setVelocity(PhysicsVector velocity){
        this.myVelocity = velocity;
    }

    public CoordinateObject getMyCoordinateBody() {
        return myCoordinateBody;
    }
    public boolean isPhysicsAttack() {
        return false;
    }

    public boolean isPhysicsBody() {
        return false;
    }

    public boolean isPhysicsGround(){
        return false;
    }

    public void addCurrentForce(PhysicsVector force) {
        currentForces.add(force);
    }

    public List<PhysicsVector> getCurrentForces() {
        return currentForces;
    }

    public void clearCurrentForces() {
        currentForces.clear();
    }

    public void setDirection(double dir) {
        this.myDirection = dir;
    }

    public double getDirection() {
        return myDirection;
    }

    int getId(){return this.id;}

    public abstract PhysicsVector getXVelocity();

    public abstract PhysicsVector getYVelocity();

    public int getParentID() {
        return 0;
    }

    public Dimensions getHurtBoxDimensions() {
        return this.hurtBoxDimensions;
    }

    public void setHurtBoxDimensions(Dimensions dims) {
        this.hurtBoxDimensions = dims;
    }
}
