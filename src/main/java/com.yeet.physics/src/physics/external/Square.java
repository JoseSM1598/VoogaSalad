package physics.external;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a square using four lines (would be needed if extended to 3-D
 *
 * @author jrf36
 */

public class Square extends Shape {

    private List<Line> path;

    public Square(List<Line> shapeLines){
        super(shapeLines);
        this.path = shapeLines;
    }

    private double[] getXBound(){
        double[] bounds = new double[2];
        bounds[0] = getMinX();
        bounds[1] = getMaxX();
        return null;
    }

    private double[] getYBound(){
        double[] bounds = new double[2];
        bounds[0] = getMinY();
        bounds[1] = getMaxY();
        return null;
    }

    public double getMinX(){
        double pos = Double.MAX_VALUE;

        for(int i = 0; i < this.getPath().size(); i++){
            Line thisLine = this.getPath().get(i);
            if(thisLine.getXBounds()[0] < pos){
                pos = thisLine.getXBounds()[0];
            }
        }
        return pos;
    }

    public double getMinY(){
        double pos = Double.MAX_VALUE;

        for(int i = 0; i < this.getPath().size(); i++){
            Line thisLine = this.getPath().get(i);
            if(thisLine.getYBounds()[0] < pos){
                pos = thisLine.getYBounds()[0];
            }
        }
        return pos;
    }

    public double getMaxX(){
        double pos = -Double.MAX_VALUE;

        for(int i = 0; i < this.getPath().size(); i++){
            Line thisLine = this.getPath().get(i);
            if(thisLine.getXBounds()[1] > pos){
                pos = thisLine.getXBounds()[1];
            }
        }
        return pos;
    }

    public double getMaxY(){
        double pos = -Double.MAX_VALUE;

        for(int i = 0; i < this.getPath().size(); i++){
            Line thisLine = this.getPath().get(i);
            if(thisLine.getYBounds()[1] > pos){
                pos = thisLine.getYBounds()[1];
            }
        }
        return pos;
    }

    public List<Line> getPath() {
        return this.path;
    }
}
