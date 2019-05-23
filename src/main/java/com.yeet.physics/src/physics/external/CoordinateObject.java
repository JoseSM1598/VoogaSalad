package physics.external;

import java.awt.geom.Rectangle2D;
import java.util.List;

    public abstract class CoordinateObject {


        Dimensions dims;
        private Rectangle2D hitBox;

        private List<SubRectangle> subRects;

        private Coordinate pos;

        private int xSubRects = 4;
        private int ySubRects = 4;

        public CoordinateObject(Coordinate start, Dimensions dims){
            this.dims = dims;
            this.pos = start;
            this.hitBox = new Rectangle2D.Double(this.pos.getX(), this.pos.getY(), this.dims.getSizeX(), this.dims.getSizeY());
        }

        public abstract void update(Coordinate newPos);

        public abstract Intersection intersects(CoordinateObject c);

        public Coordinate getPos(){
            return this.pos;
        }

        public void setPos(double x, double y){
            this.pos.setX(x);
            this.pos.setY(y);
            update(pos);
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
