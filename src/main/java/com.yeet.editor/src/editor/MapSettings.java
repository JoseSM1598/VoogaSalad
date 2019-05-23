package editor;

import editor.interactive.MapEditor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import renderer.external.RenderSystem;
import renderer.external.Structures.SliderBox;
import xml.XMLSaveBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class MapSettings {
    private static final double[] DEFAULT_SETTINGS = {200.0,0.5,400.0};

    private Stage dialog;
    private Scene myScene;
    private Group root;
    private Consumer<Double> consumerG;
    private Consumer<Double> consumerF;
    private Consumer<Double> consumerT;
    private double gravity;
    private double friction;
    private double terminal;
    private RenderSystem rs;
    private VBox v1;
    private File stageMusic;
    private MapEditor prev;
    private SliderBox gravityBox;
    private SliderBox frictionBox;
    private SliderBox terminalBox;

    public MapSettings(MapEditor prevScene){
        prev = prevScene;
        dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        root = new Group();
        myScene = new Scene(root);
        rs = new RenderSystem();
        initPhysicsValues();
        initConsumers();
        buildScene();
        Button save = rs.makeStringButton("Save", Color.BLACK,true, Color.WHITE,15.0,250.0,170.0,150.0,40.0);
        save.setOnMouseClicked(e -> createSaveFile());
        root.getChildren().add(save);
    }

    private void initPhysicsValues(){
        gravity = DEFAULT_SETTINGS[0];
        friction = DEFAULT_SETTINGS[1];
        terminal = DEFAULT_SETTINGS[2];
    }

    public void setScene(){
        dialog.setTitle("Map Settings");
        dialog.setScene(myScene);
        dialog.show();
    }

    private void buildScene(){
        v1 = new VBox(5);
        v1.getChildren().addAll(initSliderBoxes());
        v1.getChildren().add(makeMusicBox());
        v1.setMinWidth(200.0);
        root.getChildren().add(v1);
    }

    private void initConsumers(){
        consumerG = (x) -> gravity = x;
        consumerF = (x) -> friction = x;
        consumerT = (x) -> terminal = x;
    }

    public double[] getPhysicsValues(){
        return new double[]{gravity,friction,terminal};
    }

    public File getStageMusic(){
        return stageMusic;
    }

    private HBox makeMusicBox(){
        HBox musicbox = new HBox(10);
        Button pickMusic = rs.makeStringButton("pick main music", Color.BLACK,true, Color.WHITE,15.0,60.0,200.0,150.0,40.0);
        Text musicLabel = new Text();
        pickMusic.setOnMouseClicked(e -> {
            getMusicFile();
            musicLabel.setText(stageMusic.getName());
        });
        musicbox.getChildren().addAll(pickMusic,musicLabel);
        return musicbox;
    }

    private void getMusicFile(){
        FileChooser fileChooser = rs.makeFileChooser("audio");
        stageMusic = fileChooser.showOpenDialog(myScene.getWindow());
    }
//    private void chooseBGM(){
//        File musicFile = new File(myEM.getGameDirectoryString()+"/data/bgm");
//        ListView<String> musicList = myRS.makeDirectoryFileList(musicFile, false);
//        Stage edit = new Stage();
//        edit.setScene(new Scene(new Group(musicList)));
//        musicList.setOnMouseClicked(e -> {
//            myBGMFileName = musicList.getSelectionModel().getSelectedItem();
//            myBGM.setText(myBGMFileName);
//            edit.close();
//        });
//        edit.show();
//    }

//    private void initButtons() {
//        Button myBGMButton = myRS.makeStringButton("Set Background Music", Color.BLACK, true, Color.WHITE,
//                20.0, 800.0, 650.0, 300.0, 50.0);
//        myBGMButton.setOnMouseClicked(e -> chooseBGM());
//    }

    private List<SliderBox> initSliderBoxes(){
        gravityBox = rs.makeSlider("Gravity",1.0,consumerG,0.0,0.0,500.0);
        gravityBox.getSlider().setMin(-1.0);
        gravityBox.getSlider().setMax(800.0);
        frictionBox = rs.makeSlider("Friction",25.0,consumerF,0.0,0.0,500.0);
        frictionBox.getSlider().setMin(0.0);
        frictionBox.getSlider().setMax(5);
        terminalBox = rs.makeSlider("Terminal Velocity",25.0,consumerT,0.0,0.0,500.0);
        terminalBox.getSlider().setMin(0.0);
        terminalBox.getSlider().setMax(600.0);
        List<SliderBox> s = new ArrayList<>();
        s.add(gravityBox);
        s.add(frictionBox);
        s.add(terminalBox);
        return s;
    }

    public void createSaveFile() {
        HashMap<String, ArrayList<String>> structure = new HashMap<>();
        structure.put("gravity", new ArrayList<>(List.of("gValue")));
        structure.put("friction", new ArrayList<>(List.of("meuValue")));
        structure.put("terminalVelocity", new ArrayList<>(List.of("vtValue")));
        HashMap<String, ArrayList<String>> data = new HashMap<>();
        data.put("gValue", new ArrayList<>(List.of(gravityBox.getValue()+"")));
        data.put("meuValue", new ArrayList<>(List.of(frictionBox.getValue()+"")));
        data.put("vtValue", new ArrayList<>(List.of(terminalBox.getValue()+"")));
        try {
            File xmlFile = Paths.get(prev.getDirectoryString(), "physicsproperties.xml").toFile();
            if (xmlFile != null) {
                new XMLSaveBuilder(structure, data, xmlFile);
            } else {
                throw new IOException("Invalid save location");
            }
        } catch (IOException e) {
            rs.createErrorAlert("Save error occurred","Check code logic");
        }
    }

}
