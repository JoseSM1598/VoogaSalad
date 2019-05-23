package editor.interactive;

import com.google.common.eventbus.EventBus;
import editor.EditorConstant;
import editor.EditorManager;
import editor.EditorScreen;
import editor.home.CharacterHome;
import editor.home.MapHome;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import messenger.external.CreateCopyEvent;
import messenger.external.EventBusFactory;
import renderer.external.RenderSystem;
import xml.XMLParser;
import xml.XMLSaveBuilder;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Editor super class
 * @author ob29
 */

public abstract class EditorSuper extends Scene implements EditorScreen {

    protected Group root;
    protected EditorManager myEM;
    protected Scene myPrevScreen;
    protected RenderSystem myRS;
    protected EditorConstant myEC;
    protected boolean isSaved;
    protected Text saved;
    protected EventBus myEB;

    public EditorSuper(Group root, EditorManager em, Scene prev){
        super(root);
        this.root = root;
        myEM = em;
        myRS = new RenderSystem();
        myPrevScreen = prev;
        Text t = createTitle();
        Button back = createBack();
        root.getChildren().addAll(back, t);
        isSaved = true;
        saved = myRS.makeText("Saved", true, 20, Color.BLACK, 640.0, 60.0);
        myEB = EventBusFactory.getEventBus();
    }

    /**
     * Creates back button to the editor landing page
     */
    @Override
    public Button createBack(){
        Button back = myRS.makeStringButton("Back", Color.BLACK,true,Color.WHITE,30.0,myEC.BACKBUTTONXPOSITION.getValue(),0.0,150.0,50.0);
        back.setOnMouseClicked(e -> goBack());
        return back;
    }

    /**
     * Creates a back button to the specified scene, not just a regular back button to editor home scene
     * @param scene, the Scene you want to have a reference to
     */
    public void createBack(Scene scene){
        Button buttonBack = myRS.makeStringButton("Back", Color.BLACK, true, Color.WHITE, 30.0, myEC.BACKBUTTONXPOSITION.getValue(), 0.0, 150.0, 50.0);
        buttonBack.setOnMouseClicked(e -> myEM.changeScene(scene));
        root.getChildren().add(buttonBack);
    }

    public XMLParser loadXMLFile(File xmlFile) {
        try {
            if(xmlFile != null) {
                return new XMLParser(xmlFile);
            } else {
                throw new IOException("Cannot load file");
            }
        } catch (IOException e) {
            myRS.createErrorAlert("Invalid XML File","Please check your resources folder");
            return null;
        }
    }

    public void generateSave(HashMap<String, ArrayList<String>> structure, HashMap<String, ArrayList<String>> data, File xmlFile) {
        try {
            if(xmlFile != null) {
                new XMLSaveBuilder(structure, data, xmlFile);
            } else {
                throw new IOException("Invalid save location");
            }
        } catch (IOException e) {
            myRS.createErrorAlert("Invalid save structures", "Please check your save logic");
        }
    }

    public abstract String toString();

    public Text createTitle() {
        return myRS.makeText(toString(), true, 20, Color.BLACK, 50.0, 50.0);
    }

    @Override
    public String getDirectoryString() {
        return myEM.getGameDirectoryString();
    }

    @Override
    public void goBack() {
        if(isSaved) {
            myEM.changeScene(myPrevScreen);
            if(myPrevScreen instanceof MapHome) {
                MapHome home = (MapHome) myPrevScreen;
                home.updateScroll("stages");
            } else if(myPrevScreen instanceof CharacterHome) {
                CharacterHome home = (CharacterHome) myPrevScreen;
                home.updateScroll("characters");
            }
        } else {
            myRS.createErrorAlert("Not Allowed to Go Back", "Please save your changes first");
        }
    }

    protected void updateToUnsaved() {
        if(isSaved) {
            isSaved = false;
            root.getChildren().remove(saved);
        }
    }

    public Scene getScene(){
        return this;
    }

    public void importResource(File source, File directory) {
        myEB.post(new CreateCopyEvent("Copy File", source, directory));
    }
}
