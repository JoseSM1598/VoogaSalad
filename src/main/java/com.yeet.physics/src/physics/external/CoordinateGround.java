package physics.external;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

public class CoordinateGround extends CoordinateObject {

    private Dimensions dims;
    private Rectangle2D hitBox;

    private List<SubRectangle> subRects;

    private Coordinate pos;
    public CoordinateGround(Coordinate start, Dimensions dims) {
        super(start, dims);
        this.dims = dims;
        this.pos = start;
        this.hitBox = new Rectangle2D.Double(this.pos.getX(), this.pos.getY(), this.dims.getSizeX(), this.dims.getSizeY());
    }

    @Override
    public void update(Coordinate newPos) {
        this.pos = newPos;
        this.hitBox = new Rectangle2D.Double(this.pos.getX(), this.pos.getY(), dims.getSizeX(), dims.getSizeY());
    }

    @Override
    public Intersection intersects(CoordinateObject c) {
        Intersection myInt;
        if(this.getHitBox().intersects(c.getHitBox())){
            myInt = new Intersection(new Side("TOP"));
            return myInt;
        }
        myInt = new Intersection(new Side("NONE"));
        return myInt;
    }
}
