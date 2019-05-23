package renderer.external.Structures;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.function.Consumer;

/** Custom extension of {@code VBox} that contains a {@code TextField} that allows for getting the value of the {@code TextField}
 *  @author bpx
 */
public class TextBox extends VBox {

    public static final double VBOX_SPACING = 5.0;

    private String myText;
    private TextField myTextField;

    public TextBox(Consumer<String> fieldSetter, String text, Double x, Double y, Double w, Double h, Font font){
        super(VBOX_SPACING);
        this.setAlignment(Pos.CENTER_RIGHT);
        myTextField = new TextField(text);
        myTextField.setUserData(text);
        this.setLayoutX(x);
        this.setLayoutY(y);
        myTextField.setPrefSize(w,h);
        myTextField.setFont(font);
        Label textLabel = new Label("");
        textLabel.setFont(font);
        textLabel.setTextFill(Color.RED);
        myTextField.setOnKeyPressed(event -> {
            if(event.getCode()== KeyCode.ENTER){
                // field reverts to previous value if consumer fails
                try{
                    fieldSetter.accept(myTextField.getText());
                    myTextField.setUserData(myTextField.getText());
                    myText = myTextField.getText();
                    textLabel.setText("");
                }
                catch(Exception e){
                    myTextField.setText((String)myTextField.getUserData());
                    textLabel.setText("Invalid input.");
                }

            }
            else if(event.getCode()==KeyCode.ESCAPE){
                myTextField.setText((String)myTextField.getUserData());
            }
        });
        this.getChildren().addAll(myTextField,textLabel);
    }

    /** Returns the current stored value of the {@code TextField}*/
    public String getText(){
        return myText;
    }

    public void setText(String value) {
        myText = value;
        myTextField.setText(value);
    }

}
