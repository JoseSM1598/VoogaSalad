package renderer.external;

import javafx.scene.Node;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.text.Text;

public interface Scrollable {

    public void initializeButton(Text t);

    public void setNodeGraphic(Node key, String text);

    public ToggleButton getButton();

    public ToggleButton getImageButton();

    Image getImage();


}
