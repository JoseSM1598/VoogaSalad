package physics.external;

import java.awt.geom.Rectangle2D;
import java.util.List;

/**
 * The hit box for each object - made up of a Coordinate and dimensions
 *
 * @author skm44
 * @author jrf36
 */

public class CoordinateBody extends CoordinateObject{

    private Dimensions dims;
    private Rectangle2D hitBox;

    private List<SubRectangle> subRects;

    private Coordinate pos;

    private int xSubRects = 4;
    private int ySubRects = 4;

    public CoordinateBody(Coordinate start, Dimensions dims){
        super(start, dims);
        this.dims = dims;
        this.pos = start;
        this.hitBox = new Rectangle2D.Double(this.pos.getX(), this.pos.getY(), this.dims.getSizeX(), this.dims.getSizeY());
    }

    public void drawSubRects(){
        double subSizeX = this.dims.getSizeX()/xSubRects;
        double subSizeY = this.dims.getSizeY()/ySubRects;
        for(int y = 0; y < ySubRects; y++){
            for(int x = 0; x < xSubRects; x++){
                double locX = this.pos.getX() + (x*(subSizeX));
                double locY = this.pos.getY() + (y*(subSizeY));
                if(!((y >= 1 && (x >= 1 && x <= xSubRects-2)) && (y <= ySubRects-2 && (x >= 1 && x <= xSubRects-2)))){//Messy Logic
                    this.subRects.add(new SubRectangle(locX + (x*subSizeX),locY + (y*subSizeY), subSizeX, subSizeY ));
                }
            }
        }
    }

    public void updateSubRects(){
        for(SubRectangle sub: this.getSubRects()){
        }
    }
    @Override
    public void update(Coordinate newPos){
        this.pos = newPos;
        this.hitBox = new Rectangle2D.Double(this.pos.getX(), this.pos.getY(), dims.getSizeX(), dims.getSizeY());
    }

    public Coordinate getPos(){
        return this.pos;
    }

    public void setPos(double x, double y){
        this.pos.setX(x);
        this.pos.setY(y);
        update(pos);
    }

    /*
    INPUT: This function takes in another coordinate body
    OUTPUT: T or F depending on weather or not this body intersects with the one passed into the parameter
     */
    @Override
    public Intersection intersects(CoordinateObject c){

        Rectangle2D object = c.getHitBox();

        // check which side of the ground body is located
        boolean below = object.getMinY() <= this.getHitBox().getMaxY();
        boolean above = object.getMaxY() >= this.getHitBox().getMinY();
        boolean left = object.getMaxX() >= this.getHitBox().getMinX();
        boolean right = object.getMinX() <= this.getHitBox().getMaxX();

        //
        boolean bottomLeft = (left && below);
        boolean bottomRight = (right && below);
        boolean topLeft = (left && above);
        boolean topRight = (right && above);

        boolean upperLeftShallowIntersect = (object.getMaxY() - this.getHitBox().getMinY()) < (object.getMaxX() - this.getHitBox().getMinX());
        boolean upperRightShallowIntersect = (object.getMaxY() - this.getHitBox().getMinY()) < (this.getHitBox().getMaxX() - object.getMinX());
        boolean upperLeftDeepIntersect = (object.getMaxY() - this.getHitBox().getMinY()) > (object.getMaxX() - this.getHitBox().getMinX());
        boolean upperRightDeepIntersect = (object.getMaxY() - this.getHitBox().getMinY()) > (this.getHitBox().getMaxX() - object.getMinX());

        boolean lowerLeftShallowIntersect = (this.getHitBox().getMaxY() - object.getMinY()) < (object.getMaxX() - this.getHitBox().getMinX());
        boolean lowerRightShallowIntersect = (this.getHitBox().getMaxY() - object.getMinY()) < (this.getHitBox().getMaxX() - object.getMinX());
        boolean lowerLeftDeepIntersect = (this.getHitBox().getMaxY() - object.getMinY()) > (object.getMaxX() - this.getHitBox().getMinX());
        boolean lowerRightDeepIntersect = (this.getHitBox().getMaxY() - object.getMinY()) > (this.getHitBox().getMaxX() - object.getMinX());

        // Find which way the body will be stopped from
        boolean aboveHit = ((topLeft && upperLeftShallowIntersect) || (topRight && upperRightShallowIntersect) || (above && !below && !left && !right));
        boolean belowHit = ((bottomLeft && lowerLeftShallowIntersect) || (bottomRight && lowerRightShallowIntersect) || (!above && below && !left && !right));
        boolean leftHit = ((topLeft && upperLeftDeepIntersect) || (bottomLeft && lowerLeftDeepIntersect) || (!above && !below && left && !right));
        boolean rightHit = ((topRight && upperRightDeepIntersect) || (bottomRight && lowerRightDeepIntersect) || (!above && !below && !left && right));

        if(below && above && left && right) {
            if (belowHit) {
                return new Intersection(new Side("BOTTOM"));
            }
            if (leftHit) {
                return new Intersection(new Side("LEFT"));
            }
            if (rightHit) {
                return new Intersection(new Side("RIGHT"));
            }
            if (aboveHit) {
                return new Intersection(new Side("TOP"));
            }
        }
        return new Intersection(new Side("NONE"));
    }

    public Dimensions getDims(){
        return this.dims;
    }

    public void setDims(Dimensions dims){
        this.dims = dims;
    }

    public Rectangle2D getHitBox() {
        return this.hitBox;
    }

    public List<SubRectangle> getSubRects(){
        return this.subRects;
    }
    public void setSubRects(List<SubRectangle> mySubRects){
        this.subRects = mySubRects;
    }
}