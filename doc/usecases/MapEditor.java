package editor;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import renderer.external.*;
import renderer.external.Structures.Level;

public class MapEditor implements Renderer{

    private Level myLevel;
    private Image image;
    private Scene myScene;

    public MapEditor(){
        myLevel = new Level(image);
        Group root = new Group();
        myScene = new Scene(root);
    }

    @Override
    public Button makeImageButton(Image image, Double x, Double y) {
        return null;
    }

}
