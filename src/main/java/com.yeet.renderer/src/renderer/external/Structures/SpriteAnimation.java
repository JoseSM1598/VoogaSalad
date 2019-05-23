package renderer.external.Structures;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.util.Duration;

/** Simple {@code Transition} for linear progression through a Spritesheet based on pre-defined parameters
 *  @author bpx
 */
public class SpriteAnimation extends Transition {

    private final Sprite mySprite;
    private final int count;
    private final int columns;
    private final Double offsetX;
    private final Double offsetY;
    private final Double width;
    private final Double height;
    private Double direction;

    private int lastIndex;

    /** Creates a new {@code SpriteAnimation} based on the given parameters
     * @param sprite The {@code Sprite} that will be animated
     * @param duration The total length of the animation
     * @param count The total number of frames
     * @param columns The number of frames per row
     * @param offsetX The offset of the first frame in the x direction
     * @param offsetY The offset of the first frame in the y direction
     * @param width The width of each animation frame
     * @param height The height of each animation frame
     */
    public SpriteAnimation(Sprite sprite, Duration duration,
                           Integer count, Integer columns,
                           Double offsetX, Double offsetY,
                           Double width, Double height){
        mySprite = sprite;
        this.count = count;
        this.columns = columns;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        this.setCycleDuration(duration);
        this.setInterpolator(Interpolator.LINEAR);
    }



    @Override
    protected void interpolate(double frac) {
        final int index = Math.min((int) Math.floor(frac * count), count - 1);
        mySprite.setScaleX(direction);
        if (index != lastIndex) {
            final Double x = (index % columns) * width  + offsetX;
            final Double y = (index / columns) * height + offsetY;
            mySprite.setViewport(x,y,width,height);
            lastIndex = index;
        }
    }

    @Override
    public void play(){
        direction = mySprite.getScaleX();
        /*System.out.println("Sprite direction at beginning: "+direction);
        this.setOnFinished(event -> {
            mySprite.defaultViewport();
            mySprite.setScaleX(direction);
            System.out.println("Sprite direction at end: "+mySprite.getScaleX());
        });*/
        super.play();
    }

    public int getCount(){
        return count;
    }
    public int getColumns() {
        return columns;
    }

    public Double getOffsetX() {
        return offsetX;
    }

    public Double getOffsetY() {
        return offsetY;
    }

    public Double getWidth() {
        return width;
    }

    public Double getHeight() {
        return height;
    }
}
