package player.internal.Elements;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.function.Consumer;

import static renderer.external.RenderUtils.toRGBCode;

/** Custom extension of {@code StackPane} that is used to display what character the player has chosen, as well as toggle
 *  whether the selected player should be a human, computer, or none.
 *  @author bpx
 */
public class CharacterChooseDisplay extends StackPane {

    public static final String FORMAT_RECT = "-fx-border-radius: %s;-fx-background-radius: %s; -fx-background-color: %s;";

    public enum State{
        HUMAN, CPU, NONE;
    }

    private DragToken myButton;
    private State myState;
    private Color myColor;
    private Color myCurrentColor;
    private String myDefaultPlayerName;
    private Text myCharacterName;
    private Text myCurrentCharacterName;


    private StackPane bottom;
    private StackPane colorblock;
    private StackPane namepiece;
    private StackPane iconHolder;

    private ImageView portrait;

    private ImageView icon;
    private final Image none = new Image(this.getClass().getClassLoader().getResourceAsStream("none_icon.png"));
    private final Image human = new Image(this.getClass().getClassLoader().getResourceAsStream("human_icon.png"));
    private final Image cpu = new Image(this.getClass().getClassLoader().getResourceAsStream("cpu_icon.png"));



    /** Creates a new {@code CharacterChooseDisplay} using the specified parameters
     *  @param color The {@code Color} the player will be represented by
     *  @param playerText The {@code Text} with the default name of the player
     *  @param characterText The {@code Text} with the default character name
     *  @param button The token for choosing characters
     */
    public CharacterChooseDisplay(Color color, Text playerText, Text characterText, DragToken button, Consumer<State> playerStateConsumer){
        super();
        myButton = button;
        myButton.setDisable(true);
        myButton.setOpacity(0);
        myState = State.NONE;
        myColor = color;
        myCurrentColor = color;
        myDefaultPlayerName = playerText.getText();
        super.setAlignment(Pos.BOTTOM_RIGHT);
        super.setPrefSize(305.0,332.0);
        super.setMaxSize(305.0,332.0);
        super.setMinSize(305.0,332.0);
        super.setStyle(String.format(FORMAT_RECT,"100 0 0 0","100 0 0 0","transparent"));
        VBox portraitHolder = new VBox();
        StackPane portraitInner = new StackPane();
        portraitInner.setAlignment(Pos.BOTTOM_CENTER);
        myCharacterName = characterText;
        myCharacterName.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 3px; -fx-font-size: 60");
        portrait = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("empty.png")));
        portrait.setPreserveRatio(true);
        portrait.setFitWidth(305.0);
        portrait.setOpacity(0);
        Rectangle spacer = new Rectangle(305,107,Color.TRANSPARENT);
        bottom = new StackPane();
        bottom.setAlignment(Pos.TOP_LEFT);
        bottom.setMaxSize(305.0,107.0);
        bottom.setStyle(String.format(FORMAT_RECT,"0 35 0 0", "0 35 0 0", toRGBCode(Color.web("#1F1C1F"))));
        colorblock = new StackPane();
        colorblock.setAlignment(Pos.CENTER);
        colorblock.setMaxSize(296.0,69.0);
        colorblock.setStyle(String.format(FORMAT_RECT, "0 35 0 35", "0 35 0 35","transparent"));
        namepiece = new StackPane();
        namepiece.setAlignment(Pos.CENTER_LEFT);
        namepiece.setMinSize(257.0,33.0);
        namepiece.setPrefSize(257.0,33.0);
        namepiece.setMaxSize(257.0,33.0);
        namepiece.setStyle(String.format(FORMAT_RECT,"17 17 17 17","17 17 17 17",toRGBCode(Color.WHITE)));
        iconHolder = new StackPane();
        HBox nameBox = new HBox(16.5);
        nameBox.setAlignment(Pos.CENTER_LEFT);
        iconHolder.setAlignment(Pos.CENTER);
        iconHolder.setMaxSize(33,33);
        iconHolder.setStyle(String.format(FORMAT_RECT,"17 17 17 17","17 17 17 17",toRGBCode(Color.web("#1F1C1F"))));
        icon = new ImageView(none);
        icon.setFitWidth(33);
        icon.setFitHeight(33);
        myCurrentCharacterName = playerText;
        myCurrentCharacterName.setText("None");
        myCurrentCharacterName.setStyle("-fx-font-size: 30");
        portraitInner.getChildren().addAll(portrait,myCharacterName);
        portraitHolder.getChildren().addAll(portraitInner,spacer);
        super.getChildren().addAll(portraitHolder,bottom);
        bottom.getChildren().addAll(colorblock);
        colorblock.getChildren().addAll(namepiece);
        namepiece.getChildren().addAll(nameBox);
        nameBox.getChildren().addAll(iconHolder, myCurrentCharacterName);
        iconHolder.getChildren().addAll(icon);
        iconHolder.setOnMousePressed(event -> {
            nextState();
            playerStateConsumer.accept(myState);
        });
    }

    /** Advances the {@code CharacterChooseDisplay} to the next state, NONE->HUMAN->CPU, then goes back to NONE*/
    private void nextState(){
        switch(myState){
            case NONE:
                myState = State.HUMAN;
                super.setStyle(String.format(FORMAT_RECT,"100 0 0 0","100 0 0 0",toRGBCode(Color.web("#1F1C1F"))));
                colorblock.setStyle(String.format(FORMAT_RECT, "0 35 0 35", "0 35 0 35",toRGBCode(myColor)));
                myCurrentColor = myColor;
                icon.setImage(human);
                myCurrentCharacterName.setText(myDefaultPlayerName);
                myCharacterName.setOpacity(1);
                portrait.setOpacity(1);
                myButton.resetColor();
                myButton.setOpacity(1);
                myButton.setDisable(false);
                break;
            case HUMAN:
                myState = State.CPU;
                super.setStyle(String.format(FORMAT_RECT, "0 0 0 0", "0 0 0 0","rgba(255,255,255,0.64)"));
                colorblock.setStyle(String.format(FORMAT_RECT, "0 35 0 35", "0 35 0 35",toRGBCode(Color.web("#5B585C"))));
                myCurrentColor = Color.web("#5B585C");
                icon.setImage(cpu);
                myCurrentCharacterName.setText("CPU");
                myButton.setColor(Color.web("#848484"));
                break;
            case CPU:
                myState = State.NONE;
                super.setStyle(String.format(FORMAT_RECT,"100 0 0 0","100 0 0 0","transparent"));
                colorblock.setStyle(String.format(FORMAT_RECT, "0 35 0 35", "0 35 0 35","transparent"));
                myCurrentColor = Color.TRANSPARENT;
                icon.setImage(none);
                myCurrentCharacterName.setText("None");
                myCharacterName.setOpacity(0);
                portrait.setOpacity(0);
                myButton.setOpacity(0);
                myButton.setDisable(true);
                break;
        }

    }

    /** Set the character portrait
     *  @param image The image representing the character chosen
     */
    public void setPortrait(Image image){
        portrait.setOpacity(1);
        portrait.setImage(image);
    }

    /** Set the character name for the {@code CharacterChooseDisplay}
     *  @param text The new name to use
     */
    public void setCharacterName(String text){
        myCharacterName.setText(text);
    }

    /** Returns the current {@code State} of the {@code CharacterChooseDisplay}*/
    public State getState(){
        if(myState.equals(State.HUMAN)){
            return State.HUMAN;
        }
        else if(myState.equals(State.CPU)){
            return State.CPU;
        }
        else{
            return State.NONE;
        }
    }

    /** Returns the current name of the selected character */
    public String getCharacterName(){
        return myCharacterName.getText();
    }

    /** Returns the current {@code Color} of the {@code CharacterChooseDisplay} */
    public Color getCurrentColor(){
        return myCurrentColor;
    }


    /** Creates a clone of the {@code CharacterChooseDisplay} */
    public CharacterChooseDisplay clone(){
        Text charName = new Text(myCurrentCharacterName.getText());
        charName.setFont(myCurrentCharacterName.getFont());
        Text playerName = new Text(myCharacterName.getText());
        playerName.setFont(myCharacterName.getFont());
        CharacterChooseDisplay result = new CharacterChooseDisplay(myColor,charName,playerName,myButton,null);
        result.setPortrait(portrait.getImage());
        while(result.getState()!=myState){
               result.nextState();
        }
        return result;
    }

    /** Switches the {@code CharacterChooseDisplay} to a display mode suitable for showing results */
    public void switchToResultMode(){
        super.setStyle(String.format(FORMAT_RECT,"20 20 20 20","20 20 20 20","rgba(0,0,0,0.5)")+"-fx-border-color: "+toRGBCode(myCurrentColor)+"; -fx-border-width: 10;");
        bottom.setStyle(String.format(FORMAT_RECT,"0 35 0 0", "0 35 0 0", "transparent"));
        myCharacterName.setOpacity(0);
    }

}
