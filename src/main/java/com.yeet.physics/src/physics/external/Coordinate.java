package physics.external;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an object's position in space
 *
 * @author skm44
 * @author jrf36
 */

public class Coordinate {

    private List<Double> cord;
    private double x;
    private double y;
    private double z;

    //Dependency: X is at 0, Y is at 1, Z is at 2;
    public Coordinate(double x, double y){
        this.x = x;
        this.y = y;
        cord = new ArrayList<Double>();
        cord.add(x);
        cord.add(y);
    }

    public Coordinate(double x, double y, double z){
        this(x,y);
        this.z = z;
        cord.add(z);
    }

    public Coordinate(List<Double> cords){
        this.cord = cords;
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ(){
        return this.z;
    }

    public List<Double> getCoords(){
        return this.cord;
    }


}
