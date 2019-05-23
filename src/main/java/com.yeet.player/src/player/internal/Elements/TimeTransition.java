package player.internal.Elements;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.text.Text;
import javafx.util.Duration;

/** Custom {@code Transition} used for changing a {@code Text} to reflect a timer's value
 *  @author bpx
 */
public class TimeTransition extends Transition {

    private Text targetText;
    private final double totalTime;
    private double timeLeft;

    public TimeTransition(Text target, Duration duration){
        targetText = target;
        totalTime = duration.toMillis();
        timeLeft = duration.toMillis();
        this.setInterpolator(Interpolator.LINEAR);
        this.setCycleCount(1);
        this.setCycleDuration(duration);
    }

    @Override
    protected void interpolate(double frac) {
        timeLeft = (1-frac)*totalTime;
        targetText.setText(millisToString((int)timeLeft));
    }

    /** Helper method to convert milliseconds into a human-friendly string
     *  @param milliseconds The raw milliseconds time
     */
    private String millisToString(int milliseconds){
        int minutes = milliseconds/60000;
        int seconds = (milliseconds-(minutes*60000))/1000;
        int millis = milliseconds - (minutes*60000) - (seconds*1000);
        String ms = new String(new char[3-String.valueOf(millis).length()]).replace("\0", "0")+String.valueOf(millis);
        String s =  new String(new char[2-String.valueOf(seconds).length()]).replace("\0", "0")+String.valueOf(seconds);
        return String.format("%s:%s %s",minutes,s,ms);
    }
}
