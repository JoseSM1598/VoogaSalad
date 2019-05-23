package physics.external;

/**
 * Class representing the side of a hit box for determining an intersection
 *
 * @author skm44
 * @author jrf36
 */

public class Side {

    private String mySide;

    public Side(String mySide){
        this.mySide = mySide;
    }

    public String getMySide() {
        return mySide;
    }

    public void setMySide(String mySide) {
        this.mySide = mySide;
    }
}
