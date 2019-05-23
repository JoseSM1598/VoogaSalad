package editor;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * This interface defines methods that will be used by any editor in the game editor
 * @author ak457
 * @author ob29
 */
public interface EditorScreen {
    String toString();
    String getDirectoryString();
    void goBack();
    Button createBack();
    Text createTitle();
    Scene getScene();
}
