package renderer.external.Structures;

import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import renderer.external.RenderSystem;
import renderer.external.RenderUtils;
import renderer.external.Scrollable;

public class InputItem implements Scrollable {

    private ToggleButton button;
    private RenderSystem rs;
    private Text keyBind;

    public InputItem(Text desc){
        rs = new RenderSystem();
        initializeButton(desc);
        button.setOnKeyPressed(e -> addKeyBinding(e));

    }

    private void addKeyBinding(KeyEvent e){
        Text keyShower = new Text();
        if(!Character.isLetter(e.getText().charAt(0))){
            RenderUtils.throwErrorAlert("Invalid Input","Only alphabetic characters");
        }else{
            keyShower.setText(e.getText().toUpperCase());
            setNodeGraphic(keyShower,"");
        }

    }

    public void setNodeGraphic(Node key,String text){
        keyBind = (Text) key;
        keyBind.setFill(Color.WHITE);
        keyBind.setFont(rs.getEmphasisFont());
        button.setGraphic(keyBind);
    }

    public Text getKeyBind(){
        return keyBind;
    }

    @Override
    public void initializeButton(Text desc) {
        button = new ToggleButton(desc.getText(),new Text());
        button.setTextFill(Color.WHITE);
        button.wrapTextProperty().setValue(true);
        button.setPrefWidth(150.0);
        button.setPrefHeight(50.0);
        keyBind = new Text(" ");
        button.setGraphic(keyBind);
        rs.applyStyleAndEffect(button);
    }

    @Override
    public ToggleButton getButton() {
        return button;
    }

    @Override
    public ToggleButton getImageButton() {
        return null;
    }

    @Override
    public Image getImage() {
        return null;
    }
}
