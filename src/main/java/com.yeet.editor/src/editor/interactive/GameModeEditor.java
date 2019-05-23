package editor.interactive;


import editor.EditorManager;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import renderer.external.RenderUtils;
import renderer.external.Structures.SliderBox;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.zip.GZIPOutputStream;


/**
 * @author ob29
 */
public class GameModeEditor extends EditorSuper {
    private static String[] DEFAULT_GAME_MODES = new String[]{"SSBB","Street Fighter"};
    private Image splashScreen;
    private File splashFile;
    private ImageView splashView;
    private File bgMusic;

    private int maxMinutes;
    private int maxStock;
    private Text musicLabel;
    private TextField minutesField;
    private TextField stockField;
    private VBox myBox;
    private Stage popupStage;
    private SliderBox soundVol;
    private SliderBox musicVol;
    private SliderBox voiceVol;


    public GameModeEditor(EditorManager em, Scene prev) {
        super(new Group(),em, prev);
        makeMyBox();
        splashView = new ImageView();
        splashFile = Paths.get(em.getGameDirectoryString(), "data","splash","splash.png").toFile();
        root.getChildren().addAll(myBox,splashView);
    }

    private ListView makeListViewForFile(String directoryType) {
        File directory = Paths.get(myEM.getGameDirectoryString(), "data",directoryType).toFile();
        ListView filesList = myRS.makeDirectoryFileList(directory, false);
        popupStage = new Stage();
        popupStage.setScene(new Scene(new Group(filesList)));
        popupStage.show();
        return filesList;
    }

    private void getSplashFile(){
        ListView splashList = makeListViewForFile("splash");
        splashList.setOnMouseClicked(e -> {
            splashFile = Paths.get(myEM.getGameDirectoryString(), "data", "splash", splashList.getSelectionModel().getSelectedItem().toString()).toFile();
            popupStage.close();
            splashScreen = new Image(splashFile.toURI().toString());
            splashView.setImage(splashScreen);
            splashView.setPreserveRatio(true);
            splashView.setFitHeight(400.0);
            splashView.setLayoutX(500.0);
            splashView.setLayoutY(100.0);
            updateToUnsaved();
        });
    }


    public void makeMyBox(){
        myBox = new VBox(20.0);
        Button saveFile = myRS.makeStringButton("Save File", Color.CRIMSON, true, Color.WHITE,
                20.0,0.0, 0.0, 200.0, 50.0);
        saveFile.setOnMouseClicked(e -> createSaveFile());

        Button pickSplash = myRS.makeStringButton("pick splash",Color.BLACK,true,Color.WHITE,20.0,60.0,100.0,200.0,50.0);
        pickSplash.setOnMouseClicked(e -> getSplashFile());

        HBox musicbox = new HBox(5);
        Button pickMusic = myRS.makeStringButton("pick main music",Color.BLACK,true,Color.WHITE,20.0,60.0,200.0,200.0,50.0);
        musicLabel = myRS.makeText("", false, 30, Color.BLACK, 30.0, 0.0);
        pickMusic.setOnMouseClicked(e -> getMusicFile());
        musicbox.getChildren().addAll(pickMusic,musicLabel);

        HBox minbox = new HBox(5);
        HBox stockbox = new HBox(5);
        Text minutes = new Text();
        Text stock = new Text();
        minutesField = new TextField();
        stockField = new TextField();
        minutesField.setOnKeyPressed(e -> updateMinuteField(e, minutesField, minutes));
        stockField.setOnKeyPressed(e -> updateStockField(e, stockField, stock));
        Text minLabel = new Text("Maximum Minutes");
        Text stockLabel = new Text("Maximum Lives");
        minbox.getChildren().addAll(minLabel,minutesField,minutes);
        stockbox.getChildren().addAll(stockLabel,stockField,stock);
        Consumer consumer = new Consumer() {
            @Override
            public void accept(Object o) {
                o = o;
            }
        };
        soundVol = myRS.makeSlider("Sound",100.0, consumer,0.0,0.0,500.0);
        soundVol.getSlider().setMin(1.0);
        soundVol.getSlider().setMax(100.0);
        musicVol = myRS.makeSlider("Music",100.0,consumer,0.0,0.0,500.0);
        musicVol.getSlider().setMin(0.0);
        musicVol.getSlider().setMax(100.0);
        voiceVol = myRS.makeSlider("Voice",100.0,consumer,0.0,0.0,500.0);
        voiceVol.getSlider().setMin(0.0);
        voiceVol.getSlider().setMax(100.0);
        myBox.getChildren().addAll(saveFile,pickSplash,musicbox,minbox,stockbox, soundVol, musicVol, voiceVol);
        myBox.setLayoutX(50.0);
        myBox.setLayoutY(100.0);
    }

    private void getMusicFile(){
        ListView musicList = makeListViewForFile("bgm");
        musicList.setOnMouseClicked(e -> {
            bgMusic = Paths.get(myEM.getGameDirectoryString(), "data", "bgm", musicList.getSelectionModel().getSelectedItem().toString()).toFile();
            popupStage.close();
            musicLabel.setText(bgMusic.getName());
            updateToUnsaved();
        });
    }


    private void updateMinuteField(KeyEvent e, TextField t,Text text){
        if(e.getCode() == KeyCode.ENTER) {
            maxMinutes = processEnter(t, text);
        }
    }

    private int processEnter(TextField t, Text text) {
        if (!Character.isDigit(t.getText().charAt(0))){
            RenderUtils.throwErrorAlert("Invalid Input", "Only Numbers");
        } else {
            text.setText(t.getText());
            updateToUnsaved();
            return Integer.parseInt(t.getText());
        }
        return 0;
    }

    private void updateStockField(KeyEvent e, TextField t,Text text){
        if(e.getCode() == KeyCode.ENTER) {
            maxStock = processEnter(t, text);
        }
    }


    public String toString(){
        return "Game Settings Editor";
    }

    public void createSaveFile() {
        HashMap<String, ArrayList<String>> structure = new HashMap<>();
        structure.put("volume", new ArrayList<>(List.of("music", "sound", "voice")));
        structure.put("musicFile", new ArrayList<>(List.of("mFile")));
        structure.put("splash", new ArrayList<>(List.of("bgFile")));
        structure.put("stock", new ArrayList<>(List.of("numLives")));
        structure.put("time", new ArrayList<>(List.of("numMinutes")));
        structure.put("volume", new ArrayList<>(List.of("sound","music","voice")));
        HashMap<String, ArrayList<String>> data = new HashMap<>();
        data.put("bgFile", new ArrayList<>(List.of(splashFile.getName())));
        data.put("mFile", new ArrayList<>(List.of(bgMusic.getName())));
        data.put("numLives", new ArrayList<>(List.of(maxMinutes+"")));
        data.put("numMinutes", new ArrayList<>(List.of(maxStock+"")));

        data.put("sound", new ArrayList<>(List.of(soundVol.getValue()+"")));
        data.put("music", new ArrayList<>(List.of(musicVol.getValue()+"")));
        data.put("voice", new ArrayList<>(List.of(voiceVol.getValue()+"")));

        try {
            File xmlFile = Paths.get(myEM.getGameDirectoryString(),"gameproperties.xml").toFile();
            generateSave(structure, data, xmlFile);
            root.getChildren().add(saved);
            isSaved = true;
        } catch (Exception ex) {
            System.out.println("Invalid save");
        }
    }
}
