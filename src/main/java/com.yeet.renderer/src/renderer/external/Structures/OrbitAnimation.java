package renderer.external.Structures;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

/** Animates a {@code Circle} to move in a circular orbit.
 *  @author bpx
 */
public class OrbitAnimation extends Transition {

    private Circle circle;
    private Double x;
    private Double y;
    private Double radius;
    private Duration duration;

    private Double xadjust;
    private Double yadjust;

    /** Create a new orbital animation in the shape of a circle
     *  @param circle The {@code Circle} to animate
     *  @param x The x center of the circular path
     *  @param y The y center of the circular path
     *  @param radius The radius of the circular path
     *  @param duration The length of one cycle
     */
    public OrbitAnimation(Circle circle, Double x, Double y, Double radius, Duration duration){
        this.circle = circle;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.setCycleDuration(duration);
        this.setInterpolator(Interpolator.LINEAR);
        xadjust = this.circle.getLayoutX()-x;
        yadjust = this.circle.getLayoutY()-y;
    }

    @Override
    protected void interpolate(double frac) {
        circle.setCenterX((x + radius * Math.sin(frac * 2 * Math.PI)));
        circle.setCenterY((y - radius * Math.cos(frac * 2 * Math.PI)));
    }
}
