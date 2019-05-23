package editor;

import editor.home.CharacterHome;
import editor.home.EditorHome;
import editor.home.MapHome;
import editor.interactive.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import messenger.external.CreateStageEvent;
import renderer.external.RenderSystem;
import renderer.external.Structures.TextBox;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * Controller class for different Editor Scenes
 * @author ob29
 */

public class EditorManager extends Scene {
    private List<EditorScreen> myEditorHomes;
    private Group myRoot;
    private Stage myStage;
    private Scene homeScene;
    private File gameDirectory;
    private RenderSystem rs;

    public EditorManager(Stage stage, Scene scene, Group root, File directory, RenderSystem rs){
        super(root);
        myStage = stage;
        homeScene = scene;
        myRoot = root;
        gameDirectory = directory;
        myEditorHomes = makeEditorHomes();
        this.rs = rs;
        Text title = createTitle();
        myRoot.getChildren().addAll(title, arrangeButtons());
    }

    private VBox arrangeButtons() {
        VBox mainButtons = new VBox(50);
        for (int i = 0; i < myEditorHomes.size()-1; i++) {
            String name = myEditorHomes.get(i).toString();
            final int pos = i;
            Button nextEditor = rs.makeStringButton(name, Color.BLACK, true, Color.WHITE, 30.0, 800.0, 100.0 * i, 350.0, 50.0);
            nextEditor.setOnMouseClicked(e -> changeSceneEditor(myEditorHomes.get(pos)));
            mainButtons.getChildren().add(nextEditor);
        }
        Button back  = createBack();
        back.setOnMouseClicked(e -> goBack());
        mainButtons.getChildren().add(back);
        mainButtons.setLayoutX(500);
        mainButtons.setLayoutY(250);
        return mainButtons;
    }

    public void setEditorHomeScene(){
        myStage.setScene(this);
    }

    public void setGameDirectory(File gameDirectory){
        this.gameDirectory = gameDirectory;
        myEditorHomes = makeEditorHomes();
    }




    private Text createTitle() {
        return rs.makeText(toString(), true, 20, Color.BLACK, 50.0, 50.0);
    }

    private void goBack() {
        myStage.setScene(homeScene);
    }

    private Button createBack() {
        return rs.makeStringButton("Back", Color.BLACK,true,Color.WHITE,30.0,800.0,300.0,350.0,50.0);
    }

    public String getGameDirectoryString(){

       return gameDirectory.toString();
    }

    public void changeScene(Scene scene){
        myStage.setScene(scene);
    }

    public void changeSceneEditor(EditorScreen screen){
        myStage.setScene(screen.getScene());
    }

    private List<EditorScreen> makeEditorHomes(){
        List<EditorScreen> editors = new ArrayList<>();
        Collections.addAll(editors,new MapHome(this), new CharacterHome(this),new GameModeEditor(this,this),new InputEditor(this,this));//;, new GameModeHome(this));

        return editors;
    }

    public Consumer<Scene> getInputSceneSwitcher(){
        return new Consumer<Scene>() {
            @Override
            public void accept(Scene playerScene) {
                //1. switch the stage's scene to the editor input settings
                //2. when back button pressed switch back to playerScene
                    goToInput(playerScene);
            }
        };
    }

    private void goToInput(Scene scene){
        EditorScreen editor = myEditorHomes.get(myEditorHomes.size() - 1);
        EditorSuper inputEditor = (EditorSuper) editor;
        inputEditor.createBack(scene);
        changeScene(inputEditor);
    }

    @Override
    public String toString() {
        return "Game Editor";
    }
}
