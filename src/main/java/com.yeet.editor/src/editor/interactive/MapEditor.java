package editor.interactive;

import editor.EditorManager;
import editor.MapSettings;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import renderer.external.Structures.Level;
import renderer.external.Scrollable;
import renderer.external.Structures.*;
import xml.XMLParser;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

;

/**
 * @author ob29
 * @author rr202
 * @author ak457
 */

public class MapEditor extends EditorSuper {
    private static final String DEFAULT_BACKGROUND_IMAGE = "fd.jpg";
    private static final String DEFAULT_IMAGE_DIR = "/data/tiles";
    private static final String TAG_PATH = "tags";
    private static final String DEFAULT_BGM = "BGM.mp3";
    private static final String[] BUTTONS = {"Save File","Choose Tile","Set Background","Reset Grid","Map Settings"};

    private Image currentTileFile;
    private String myCurrentTileName;
    private ScrollablePaneNew myScrollablePane;
    private Level level;
    private String myBackgroundImage;
    private String myBGMFileName;
    private ResourceBundle myTags;
    private File myStageDirectory;
    private File backgroundFile;
    private HBox myButtonBox;

    /**
     * Constructs the Map Editor object given the root and the editor manager
     * @param em
     */
    public MapEditor(EditorManager em, Scene prev){
        super(new Group(), em, prev);
        myBackgroundImage = DEFAULT_BACKGROUND_IMAGE;
        myBGMFileName = DEFAULT_BGM;
        try {
            initializeLevel(960, 600, 260, 150,
                    this.getClass().getClassLoader().getResource(DEFAULT_BACKGROUND_IMAGE).toURI().toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        initializeScrollPane();
        ScrollItem si = (ScrollItem) myScrollablePane.getItems().get(0);
        currentTileFile = si.getImage();
        myCurrentTileName = si.getButton().getText();
        level.setOnMouseClicked(e -> clickProcessTile(e));
        initializeButtons();
        root.getChildren().add(myButtonBox);
        backgroundFile = Paths.get(myEM.getGameDirectoryString(), "data","background").toFile();
    }

    public MapEditor(EditorManager em, Scene prev, File xmlFile, boolean isEdit) {
        this(em, prev);
        File stageProperties = Paths.get(xmlFile.getPath(), "stageproperties.xml").toFile();
        if(isEdit) {
            loadMapFile(stageProperties);
        }
        isSaved = isEdit;
        myStageDirectory = xmlFile;
    }

    private void initializeScrollPane(){
        File paneFile = new File(myEM.getGameDirectoryString()+DEFAULT_IMAGE_DIR);
        myScrollablePane = new ScrollablePaneNew(paneFile,15,147.0, 240, 600);
        for(Scrollable b: myScrollablePane.getItems()){
            b.getButton().setOnMouseClicked(e -> selectTileFromScroll(b.getImage(), b.getButton().getText()));
        }
        root.getChildren().add(myScrollablePane.getScrollPane());
    }


    private void initializeButtons(){
        myButtonBox = new HBox(30);
        List<Button> buttons = new ArrayList<>();
        for(int i = 0; i < BUTTONS.length; i++){
            Button b = myRS.makeStringButton(BUTTONS[i],Color.BLACK,true,Color.WHITE,20.0,0.0,0.0,200.0,50.0);
            myButtonBox.getChildren().add(b);
            buttons.add(b);
        }
        buttons.get(0).setOnMouseClicked(e -> createSaveFile());
        buttons.get(1).setOnMouseClicked(e -> chooseTileImage());
        buttons.get(2).setOnMouseClicked(e -> chooseBackground());
        buttons.get(3).setOnMouseClicked(e -> level.resetGrid());
        buttons.get(4).setOnMouseClicked(e -> {
            MapSettings s = new MapSettings(this);
            s.setScene();
        });
        myButtonBox.setLayoutX(90.0);
        myButtonBox.setLayoutY(70.0);
    }

    /**
     * initializes the level object given dimensions and the root
     * @param width
     * @param height
     */
    private void initializeLevel(int width, int height, int x, int y, String background){
        level = new Level(width, height, background);
        level.setLayoutX(x);
        level.setLayoutY(y);
        root.getChildren().add(level);
    }

    /**
     * general method for choosing an image
     * @returns file chosen
     */
    private File chooseImage(String message){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("*png only",
                "*.png");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle(message);
        return fileChooser.showOpenDialog(getWindow());
    }

    /**
     * User selects background, and it is applied to level.
     */
    private void chooseBackground(){
        ListView<String> backgroundList = myRS.makeDirectoryFileList(backgroundFile, false);
        Stage edit = new Stage();
        edit.setScene(new Scene(new Group(backgroundList)));
        backgroundList.setOnMouseClicked(e -> {
            myBackgroundImage = backgroundList.getSelectionModel().getSelectedItem();
            level.setBackground(backgroundFile.toURI()+"/"+myBackgroundImage);
            edit.close();
        });
        edit.show();
    }

    private void chooseTileImage(){
        File tileFile = chooseImage("Choose Tile Image");
        try{
            Image image = new Image(tileFile.toURI().toString());
            if (image == null){
                throw new IllegalArgumentException("Image not found");
            }

                currentTileFile = image;


            myScrollablePane.addItem(new ScrollItem(currentTileFile, new Text()));
            int size = myScrollablePane.getItems().size();

            String name = tileFile.getName();
            name = name.substring(0, name.length()-4);
            String finalName = name;
            myScrollablePane.getItems().get(size-1).getButton().setOnMouseClicked(e->selectTileFromScroll(image, finalName));
            updateToUnsaved();
            File destination = Paths.get(myEM.getGameDirectoryString(),"data","tiles",tileFile.getName()).toFile();
            importResource(tileFile, destination);
        }
        catch (Exception e){
            System.out.println("Invalid image");
            e.printStackTrace();
        }
    }

    /**
     * Given mouse event, approximates position in relation to grid and
     * either adds or removes tile currently at that position.
     * @param e
     */
    private void clickProcessTile(MouseEvent e){
        int xindex = (int)e.getX()/level.getTileWidth();
        int yindex = (int)e.getY()/level.getTileHeight();
        level.processTile(xindex, yindex, currentTileFile, myCurrentTileName);
        if(isSaved) {
            isSaved = false;
            root.getChildren().remove(saved);
        }
    }

    private void selectTileFromScroll(Image image, String name){
        currentTileFile = image;
        myCurrentTileName = name;
    }


    public String toString(){
        return "Map Editor";
    }

    @Override
    public String getDirectoryString() {
        return myStageDirectory.getPath();
    }

    private void snapShot(Node node) {
        WritableImage img = node.snapshot(new SnapshotParameters(), null);
        File file = Paths.get(myStageDirectory.getPath(), myStageDirectory.getName()+".png").toFile();
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
        }
    }

    private void createSaveFile() {
        HashMap<String, ArrayList<String>> structure = new HashMap<>();
        ArrayList<String> mapAttributes = new ArrayList<>(List.of("x","y","image"));
        structure.put("map", mapAttributes);
        structure.put("background", new ArrayList<>(List.of("bgFile")));
        structure.put("music", new ArrayList<>(List.of("mFile")));
        structure.put("position", new ArrayList<>(List.of("id","xPos","yPos")));
        HashMap<String, ArrayList<String>> levelMap = level.createLevelMap();
        levelMap.put("bgFile", new ArrayList<>(List.of(myBackgroundImage)));
        levelMap.put("mFile", new ArrayList<>(List.of(myBGMFileName)));
        levelMap.put("id", new ArrayList<>(List.of("0","1","2","3")));
        levelMap.put("xPos", new ArrayList<>(List.of("0","1","2","3")));
        levelMap.put("yPos", new ArrayList<>(List.of("0","0","0","0")));
        try {
            File xmlFile = Paths.get(myStageDirectory.getPath(), "stageproperties.xml").toFile();
            generateSave(structure, levelMap, xmlFile);
            snapShot(level);
            root.getChildren().add(saved);
            isSaved = true;
        } catch (Exception ex) {
            myRS.createErrorAlert("Could not save file","Please check code logic");
        }
    }

    private void loadMapFile(File xmlFile) {
        try {
            XMLParser parser = loadXMLFile(xmlFile);
            HashMap<String, ArrayList<String>> data = parser.parseFileForElement("map");
            ArrayList<String> xPos = data.get("x");
            ArrayList<String> yPos = data.get("y");
            ArrayList<String> image = data.get("image");
            ArrayList<String> background = parser.parseFileForAttribute("background", "bgFile");
            level.setBackground(backgroundFile.toURI()+"/"+background.get(0));
            myBackgroundImage = background.get(0);
            if(xPos.size() != yPos.size()) {
                throw new IOException("Incorrect information contained within xml");
            }
            Path tilePath = Paths.get(myEM.getGameDirectoryString(), DEFAULT_IMAGE_DIR);
            for(int i = 0; i < xPos.size(); i++) {
                Path imagePath = Paths.get(tilePath.toString(), image.get(i)+".png");
                File imageFile = new File(imagePath.toString());
                level.processTile(Integer.parseInt(xPos.get(i)), Integer.parseInt(yPos.get(i)), new Image(imageFile.toURI().toString()), image.get(i));
            }
        } catch (Exception ex) {
            myRS.createErrorAlert("Could not load file","Please check resources folder");
        }
    }
}
