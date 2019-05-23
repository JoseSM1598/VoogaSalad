package physics.external;

/**
 * Abstract class for a shape (would be needed if extended to 3-D)
 *
 * @author jrf36
 */

import java.util.List;

public abstract class Shape {

    private List<Line> path;

    public Shape(List<Line> shapeLines){
        this.path = shapeLines;
    }

}
