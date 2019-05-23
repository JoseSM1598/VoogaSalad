package renderer.external;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/** Specialized {@code ImageView} adapted to more conveniently be used with animations
 *  @author bpx
 */
public class Sprite extends ImageView {

    private Image mySpriteSheet;
    private Rectangle2D myViewport;

    /** Creates a new {@code Sprite} assuming no offset
     *  @param image The spritesheet for the {@code Sprite}
     *  @param width The width of the first frame
     *  @param height The height of the first frame
     */
    public Sprite(Image image,Double width, Double height){
        super(image);
        mySpriteSheet = image;
        if(width>0 && height>0){
            myViewport = new Rectangle2D(0,0,width,height);
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
        if(offsetX>0 && offsetY>0 && width>0 && height>0){
            myViewport = new Rectangle2D(offsetX,offsetY,width,height);
        }
        else{
            System.out.println("Negative values not allowed for width and height! Setting to default values.");
            myViewport = new Rectangle2D(0,0, mySpriteSheet.getWidth(), mySpriteSheet.getHeight());
        }
        this.setViewport(myViewport);
    }

    /** Set the position of the {@code Sprite} Viewport with persistent dimensions
     *  @param offsetX The X offset of the Viewport
     *  @param offsetY the Y offset of the Viewport
     */
    public void setViewport(Double offsetX, Double offsetY){
        if(offsetX>0 && offsetY>0){
            myViewport = new Rectangle2D(offsetX,offsetY,this.getViewport().getWidth(),this.getViewport().getHeight());
        }
        else{
            System.out.println("Negative values not allowed for width and height! Setting to default values.");
            myViewport = new Rectangle2D(0,0, this.getViewport().getWidth(), this.getViewport().getHeight());
        }
        this.setViewport(myViewport);
    }
}
