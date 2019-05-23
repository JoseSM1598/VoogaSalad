package renderer.external.Structures;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/** Specialized {@code ImageView} adapted to more conveniently be used with animations
 *  @author bpx
 */
public class Sprite extends ImageView {

    private Image mySpriteSheet;
    private Rectangle2D myDefaultViewport;
    private Rectangle2D myViewport;

    /** Creates a new {@code Sprite} assuming no offset */
    public Sprite(Image image,Double width, Double height){
        this(image, 0.0, 0.0, width, height);
    }


    /** Creates a new {@code Sprite}
     *  @param image The spritesheet for the {@code Sprite}
     *  @param offsetX The X offset of the first frame
     *  @param offsetY The Y offset of the first frame
     *  @param width The width of the first frame
     *  @param height The height of the first frame
     */
    public Sprite(Image image, Double offsetX, Double offsetY, Double width, Double height){
        super(image);
        mySpriteSheet = image;
        if(width>0 && height>0){
            myDefaultViewport =  new Rectangle2D(offsetX,offsetY,width,height);
            myViewport = new Rectangle2D(offsetX,offsetY,width,height);
        }
        else{
            System.out.println("Negative values not allowed for width and height! Setting to default values.");
            myViewport = new Rectangle2D(0,0, mySpriteSheet.getWidth(), mySpriteSheet.getHeight());
        }
        this.setViewport(myViewport);
    }

    /** Set the {@code Sprite} Viewport with all new parameters
     *  @param offsetX The X offset of the Viewport
     *  @param offsetY the Y offset of the Viewport
     *  @param width The width of the Viewport
     *  @param height The height of the Viewport
     */
    public void setViewport(Double offsetX, Double offsetY, Double width, Double height){
        myViewport = new Rectangle2D(offsetX,offsetY,width,height);
        this.setViewport(myViewport);
    }

    /** Set the position of the {@code Sprite} Viewport with persistent dimensions
     *  @param offsetX The X offset of the Viewport
     *  @param offsetY the Y offset of the Viewport
     */
    public void setViewport(Double offsetX, Double offsetY){
        myViewport = new Rectangle2D(offsetX,offsetY,this.getViewport().getWidth(),this.getViewport().getHeight());
        this.setViewport(myViewport);
    }

    /** Resets the {@code Sprite} to the originally defined viewport */
    public void defaultViewport(){
        this.setViewport(myDefaultViewport);
    }

    public Rectangle2D getDefaultViewport() {
        return myDefaultViewport;
    }
}

