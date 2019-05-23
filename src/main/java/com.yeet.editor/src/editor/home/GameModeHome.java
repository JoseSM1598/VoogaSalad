package editor.home;

import editor.EditorManager;
import editor.interactive.GameModeEditor;
import javafx.scene.Group;

public class GameModeHome extends EditorHome {

    public GameModeHome(EditorManager em){
        super(new Group(), em);
        setEditor();
        buttonNew.setOnMouseClicked(e -> nameNewObject("Create Mode", "Mode name:"));
        buttonEdit.setOnMouseClicked(e -> em.changeScene(myEditor));
        myScroll = initializeScroll("modes");
    }

    @Override
    public void setEditor() {
        myEditor = new GameModeEditor(em, this);
    }

    @Override
    public void createNewObject(String name) {
        //nameNewObject();
    }

    public String toString(){
        return "Game Mode Home";
    }

    public String getDir(){
        return "portraits/";
    }
}
