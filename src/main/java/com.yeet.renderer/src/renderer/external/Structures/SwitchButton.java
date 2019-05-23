package renderer.external.Structures;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

import static renderer.external.RenderUtils.toRGBCode;

/**
 * Creates array of {@code Button} objects of which only one can be active at a time, similarly to radio buttons
 *  @author bpx
 */
public class SwitchButton extends HBox {

    public static final String FORMAT_BUTTON = "-fx-font-family: '%s'; -fx-font-size: %s; -fx-background-color: %s; -fx-text-fill: %s; -fx-border-color: black;";
    public static final double BUTTON_HFACTOR = 0.75;

    private String state;
    private Button[] myButtons;
    private Color myBackgroundColor;
    private Color myTextColor;

    /** Creates a new {@code SwitchButton} with the specified parameters
     * @param options The possible options for the {@code Carousel}
     * @param x X position of the {@code SwitchButton}
     * @param y Y Position of the {@code SwitchButton}
     * @param w The width of the {@code SwitchButton}
     * @param h The height of the {@code SwitchButton}
     * @param spacing Amount of spacing between buttons
     * @param mainColor The main background fill for the {@code SwitchButton}
     * @param secondaryColor The text fill color
     * @param font The font for the text display
     */
    public SwitchButton(List<String> options, Double x, Double y, Double w, Double h, Double spacing, Color mainColor, Color secondaryColor, Font font){
        super(spacing);
        state = options.get(0);
        myButtons = new Button[options.size()];
        myBackgroundColor = mainColor;
        myTextColor = secondaryColor;
        double buttonw = (w/options.size())-(spacing*(options.size()-1));
        double buttonh = h* BUTTON_HFACTOR;
        this.setAlignment(Pos.CENTER);
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setPrefWidth(w);
        this.setPrefHeight(h);
        for(int i=0;i<options.size();i++){
            Button button = new Button(options.get(i));
            if(i==0){
                button.setStyle(String.format(FORMAT_BUTTON,font.getName(),font.getSize(),toRGBCode(secondaryColor),toRGBCode(mainColor)));
            }
            else{
                button.setStyle(String.format(FORMAT_BUTTON,font.getName(),font.getSize(),toRGBCode(mainColor),toRGBCode(secondaryColor)));
            }
            button.setPrefWidth(buttonw);
            button.setPrefHeight(buttonh);
            button.setUserData(options.get(i));
            button.setOnMousePressed(event -> {
                if(!button.getUserData().equals(state)){
                    setState(button, (String) button.getUserData());
                }
            });
            button.setOnMouseEntered(event -> {
                if(!button.getUserData().equals(state)){
                    button.setStyle(String.format(FORMAT_BUTTON, font.getName(), font.getSize(), toRGBCode(mainColor.darker()), toRGBCode(secondaryColor)));
                }
            });
            button.setOnMouseExited(event -> {
                if(!button.getUserData().equals(state)){
                    button.setStyle(String.format(FORMAT_BUTTON, font.getName(), font.getSize(), toRGBCode(mainColor), toRGBCode(secondaryColor)));
                }
            });
            myButtons[i] = button;
            this.getChildren().add(button);
        }
    }

    /** Helper method for when a {@code Button} is pressed to set colors and update state
     *  @param button The {@code Button} that was pressed
     *  @param state The new state to update to
     */
    private void setState(Button button, String state){
        this.state = state;
        for(Button b : myButtons){
            if(!b.equals(button)){
                b.setStyle(String.format(FORMAT_BUTTON,b.getFont().getName(),b.getFont().getSize(),toRGBCode(myBackgroundColor),toRGBCode(myTextColor)));
            }
            else{
                b.setStyle(String.format(FORMAT_BUTTON,b.getFont().getName(),b.getFont().getSize(),toRGBCode(myTextColor),toRGBCode(myBackgroundColor)));
            }
        }
    }

    /** Returns the current state of the {@code SwitchButton}
     */
    public String getState(){
        return state;
    }
}
