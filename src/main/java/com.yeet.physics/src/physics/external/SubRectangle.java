package physics.external;

import java.awt.geom.Rectangle2D;

public class SubRectangle {

    private double posX, posY, width, height;
    private Rectangle2D myRect;

    public SubRectangle(double posX, double posY, double width, double height){
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.myRect = new Rectangle2D.Double(posX, posY, width, height);
    }

    public Rectangle2D getMyRect() {
        return myRect;
    }

    public void setMyRect(Rectangle2D myRect) {
        this.myRect = myRect;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
        this.myRect = new Rectangle2D.Double(posX, this.posY, this.width, this.height);
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
        this.myRect = new Rectangle2D.Double(this.posX, posY, this.width, this.height);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
