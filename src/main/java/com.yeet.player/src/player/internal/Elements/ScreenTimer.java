package player.internal.Elements;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.function.Consumer;

/** Visual timer that triggers an event upon conclusion
 * @author bpx
 * */
public class ScreenTimer extends VBox {

    private Text timeText;

    private TimeTransition myTimeTransition;

    public ScreenTimer(int minutes, Text timerText, Consumer<String> timerEndEvent){
        super();
        super.setAlignment(Pos.CENTER);
        timeText = timerText;
        timeText.setText(getTime());
        timeText.setStyle("-fx-fill: black;");
        timeText.setEffect(new DropShadow(3.0, Color.WHITE));
        timeText.setOpacity(0.5);
        myTimeTransition = new TimeTransition(timerText, Duration.minutes(minutes));
        myTimeTransition.setOnFinished(event -> timerEndEvent.accept(getTime()));
        super.getChildren().add(timeText);
    }


    /** Returns the current time as a string */
    public String getTime(){
        return timeText.getText();
    }

    /** Plays the {@code TimeTransition}*/
    public void play(){
        myTimeTransition.play();
    }

    /** Pauses the {@code TimeTransition}*/
    public void pause(){
        myTimeTransition.pause();
    }


}



