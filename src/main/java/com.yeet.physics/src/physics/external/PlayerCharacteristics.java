package physics.external;

/**
 * Supplementary class for PhysicsBodies that provide stats of player
 *
 * @author skm44
 * @author jrf36
 */

public class PlayerCharacteristics {

    private int ID;
    private double strength;
    private double jumpHeight;
    private double movementSpeed;


    PlayerCharacteristics (int ID, double strength, double jumpHeight, double movementSpeed) {
        this.ID = ID;
        this.strength = strength;
        this.jumpHeight = jumpHeight;
        this.movementSpeed = movementSpeed;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public double getStrength() {
        return strength;
    }

    public void setJumpHeight(double jumpHeight) {
        this.jumpHeight = jumpHeight;
    }

    public double getJumpHeight() {
        return jumpHeight;
    }

    public void setMovementSpeed(double movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public double getMovementSpeed() {
        return movementSpeed;
    }
}
