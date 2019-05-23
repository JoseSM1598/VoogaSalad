package renderer.external.Structures;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import java.util.function.Consumer;

/** Extension of HBox with a {@code Slider} that allows for getting of its value from the container
 *  @author bpx
 */
public class SliderBox extends HBox {

    public static final Double SLIDER_DEFAULT = 50.0;
    public static final double SLIDER_HEIGHT = 50.0;

    private double myValue;
    private Slider mySlider;
    private Label mySliderLabel;
    private Consumer myFieldSetter;

    /** Default constructor */
    public SliderBox(String text, Font font, Double startVal, Consumer<Double> fieldSetter, Double x, Double y, Double w){
        super();
        this.setAlignment(Pos.CENTER);
        this.setLayoutX(x);
        this.setLayoutY(y);
        myFieldSetter = fieldSetter;
        mySlider =  new Slider();
        mySlider.setShowTickMarks(false);
        mySlider.setShowTickLabels(false);
        mySlider.setValue(startVal);
        myValue = startVal;
        this.setMinSize(w, SLIDER_HEIGHT);
        //this.setPrefSize(w, SLIDER_HEIGHT);
        this.setAlignment(Pos.CENTER_LEFT);

        this.setMaxSize(w, SLIDER_HEIGHT);

        Label name = new Label(text);
        name.setFont(font);
        name.setLabelFor(mySlider);
        mySliderLabel = new Label(String.valueOf(startVal));
        mySliderLabel.setLabelFor(mySlider);
        mySliderLabel.setFont(font);
        mySlider.setOnMouseDragged(event -> mySliderLabel.setText(String.valueOf(Math.round(mySlider.getValue() * 10.0) / 10.0)));
        mySlider.setOnMouseReleased(event -> setNewValue(mySlider.getValue()));
        this.getChildren().addAll(name,mySlider,mySliderLabel);
    }

    /** Returns the current value of the slider */
    public double getValue(){
        return myValue;
    }

    public void setNewValue(double value) {
        //rounds mySlider value to 1 decimal place
        myFieldSetter.accept(Math.round(value * 10.0) / 10.0);
        mySliderLabel.setText(String.valueOf(Math.round(value * 10.0) / 10.0));
        myValue = Math.round(value * 10.0) / 10.0;
    }

    public Slider getSlider(){
        return mySlider;
    }

    public void setSliderWidth(double width){
        mySlider.setMinWidth(width);
    }
}
