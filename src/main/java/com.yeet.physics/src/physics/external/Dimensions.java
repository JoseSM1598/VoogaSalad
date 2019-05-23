package physics.external;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the dimensions of an object
 *
 * @author skm44
 * @author jrf36
 */

public class Dimensions {

    private List<Double> dims;

    public Dimensions(double width, double height){
        dims = new ArrayList<>();
        this.dims.add(width);
        this.dims.add(height);
    }

    public Dimensions(double width, double height, double depth){
        this(width, height);
        this.dims.add(depth);
    }

    public Dimensions(List<Double> dims){
        this.dims = dims;
    }

    public double getSizeX(){
        if(dims.size() >= 1) {
            return dims.get(0);
        }
        return 0.0;
    }

    public double getSizeY(){
        if(dims.size() >= 2) {
            return dims.get(1);
        }
        return 0.0;
    }

    public double getSizeZ(){
        if(dims.size() >= 3) {
            return dims.get(2);
        }else{
            return 0.0;
        }
    }

    public List<Double> getDims(){
        return this.dims;
    }

}
