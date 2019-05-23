package physics.external;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

/**
 * Represents a line between two points (would be needed if extended to 3-D)
 *
 * @author jrf36
 */

public class Line {

    private Point2D.Double first;
    private Point2D.Double second;

    public Line(Point2D.Double first, Point2D.Double second){
        this.first = first;
        this.second = second;
    }

    public double[] getXBounds(){
        double[] bounds = new double[2];
        if(this.getFirst().getX() > this.getSecond().getX()){
            bounds[0] = this.getSecond().getX();
            bounds[1] = this.getFirst().getX();
        }else{
            bounds[1] = this.getSecond().getX();
            bounds[0] = this.getFirst().getX();
        }
        return bounds;
    }

    public double[] getYBounds(){
        double[] bounds = new double[2];
        if(this.getFirst().getY() > this.getSecond().getY()){
            bounds[0] = this.getSecond().getY();
            bounds[1] = this.getFirst().getY();
        }else{
            bounds[1] = this.getSecond().getY();
            bounds[0] = this.getFirst().getY();
        }
        return bounds;
    }

    public Point2D.Double getFirst(){
        return this.first;
    }

    public Point2D.Double getSecond(){
        return this.second;
    }

}
