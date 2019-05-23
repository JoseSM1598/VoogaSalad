package main;

import com.google.common.eventbus.EventBus;
import console.external.Console;
import dataSystem.DataSystem;
import editor.EditorManager;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import messenger.external.CreateGameEvent;
import messenger.external.EventBusFactory;
import player.external.Player;
import renderer.external.RenderSystem;
import renderer.external.Structures.TextBox;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class Main extends Application {
    private static final String RESOURCE_PATH = "/src/main/java/com.yeet.main/resources";
    private static final String DEFAULT_GAME_DIRECTORY = "/src/main/java/com.yeet.main/resources/game0";

    private Stage newGamePopup;
    private Stage myStage;
    private Stage myPopup;
    private static Console myConsole;
    private RenderSystem myRenderSystem;
    private Player myPlayer;
    private DataSystem myDataSystem;
    private EditorManager em;
    private DirectoryChooser myDirectoryChooser;
    private File myDirectory;
    private ImageView mySplashDisplay;
    private Button playButton;
    private Button editButton;
    private Scene homeScene;
    private MainConstant myMC;
    private EventBus myEB;
    private FilenameFilter filter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return name.startsWith("game");
        }
    };

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        myEB = EventBusFactory.getEventBus();
        myDirectory = new File(System.getProperty("user.dir")+DEFAULT_GAME_DIRECTORY);

        //create window
        myStage = primaryStage;
        primaryStage.setWidth(myMC.STAGEWIDTH.getValue());
        primaryStage.setHeight(myMC.STAGEHEIGHT.getValue());
        primaryStage.setResizable(false);
        primaryStage.setTitle("Yeet Fighter Game Engine");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setOnCloseRequest(event -> System.exit(-1));
        Group root = new Group();
        Scene homeScene = new Scene(root);
        this.homeScene = homeScene;
        primaryStage.setScene(homeScene);
        homeScene.setFill(Color.web("#91C7E8"));
        primaryStage.show();
        //set up systems
        myRenderSystem = new RenderSystem();
        em = new EditorManager(primaryStage,homeScene,new Group(), myDirectory,myRenderSystem);
        myPlayer = new Player(primaryStage, myDirectory, myRenderSystem);
        myPlayer.setEditorLink(em.getInputSceneSwitcher());
        myDataSystem = new DataSystem();
        myConsole = new Console();
        myDirectoryChooser = myRenderSystem.makeDirectoryChooser();
        //register event listeners
        EventBusFactory.getEventBus().register(myPlayer);
        EventBusFactory.getEventBus().register(myConsole);
        //display setup
        myPopup = createErrorPopup();
        ImageView displayFiller = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("home_splash_filler.png")));
        displayFiller.setX(80.0);
        displayFiller.setY(76.0);
        root.getChildren().add(displayFiller);
        mySplashDisplay = createSplashDisplay();
        root.getChildren().add(mySplashDisplay);
        Text titleText = myRenderSystem.makeText("Yeet Fighter Game Engine",true,70,Color.WHITE,851.0,63.0);
        titleText.setWrappingWidth(389.0);
        root.getChildren().add(titleText);
        Button newButton = myRenderSystem.makeStringButton("New Game",Color.web("#4E82D1"),true,Color.WHITE,30.0,891.0,183.36,307.21,94.6);
        newButton.setOnMouseClicked(e -> nameNewObject("Create New Game","Game Name"));
        root.getChildren().add(newButton);
        editButton = myRenderSystem.makeStringButton("Edit Game",Color.web("#4E82D1"),true,Color.WHITE,30.0,891.0,311.68,307.21,94.6);
        editButton.setOnMouseClicked(event -> openGameList(true));
        root.getChildren().add(editButton);
        Button loadButton = myRenderSystem.makeStringButton("Load Game",Color.web("#4E82D1"),true,Color.WHITE,30.0,891.0,440.4,307.21,94.6);
        loadButton.setOnMousePressed(e -> openGameList(false));
        root.getChildren().add(loadButton);
        playButton = myRenderSystem.makeStringButton("Play",Color.RED,true,Color.WHITE,60.0,901.0,578.0,288.0,123.0);
        playButton.setDisable(true);
        playButton.setOnMousePressed(event -> {
            myPlayer.start();
        });
        root.getChildren().add(playButton);
        //program start
        myPlayer.doSomething();
    }

    private void makeGameDirectory(String game){
        newGamePopup.close();
        Path userPath = Paths.get(System.getProperty("user.dir"));
        myDirectory = new File(userPath+RESOURCE_PATH + "/"+game);
        myEB.post(new CreateGameEvent("new game", myDirectory));
        initializeGameEditor(myDirectory);
    }

    private void initializeGameEditor(File gameFile) {
        em.setGameDirectory(gameFile);
        em.setEditorHomeScene();
    }



    /** Create a {@code DirectoryChooser} and set the active game directory if it is valid*/
    private void setDirectory(File directory){
        try {
            if(directory!=null && checkDirectory(directory)){
                Image splash = new Image(String.format("%s%s",myDirectory.toURI(),"data/splash/splash.png"));
                if(!splash.isError()){
                    mySplashDisplay.setImage(splash);
                    myDirectory = directory;
                    myPlayer.setDirectory(directory);
                    playButton.setDisable(false);
                    editButton.setDisable(false);
                }
                else{
                    playButton.setDisable(true);
                    myPopup.show();
                }
            }
        } catch (Exception e) {
            myRenderSystem.createErrorAlert("Invalid Game directory","Please check code logic");
        }
    }

    /** Returns true if the {@code File} is a valid game directory
     *  @param directory The directory to check
     */
    private boolean checkDirectory(File directory){
        for(File f : directory.listFiles()){
            if(f.getName().equalsIgnoreCase("gameproperties.xml")){
                return true;
            }
        }
        return false;
    }

    /** Create the popup that displays upon attempting to load an invalid game directory*/
    private Stage createErrorPopup(){
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setResizable(false);
        stage.setWidth(500);
        stage.setHeight(300);
        stage.setTitle("Warning");
        Group root = new Group();
        Scene scene = new Scene(root);
        root.getChildren().add(myRenderSystem.makeText("Whoops!",true,40,Color.BLACK,146.75,69.16));
        Text message = myRenderSystem.makeText("We couldn't load the splash image for the loaded game.",false,20,Color.BLACK,41.26,129.46);
        message.setWrappingWidth(417.76);
        root.getChildren().add(message);
        Button button = myRenderSystem.makeStringButton("OK",Color.BLACK,false,Color.WHITE,20.9,175.0,200.0,150.0,30.0);
        button.setOnMousePressed(event -> myPopup.close());
        root.getChildren().add(button);
        stage.setScene(scene);
        return stage;
    }

    /** Create the display for the game splash image*/
    private ImageView createSplashDisplay(){
        ImageView display = new ImageView();
        display.setFitHeight(560);
        display.setFitWidth(715);
        display.setLayoutX(80);
        display.setLayoutY(76);
        return display;
    }

    private void openGameList(boolean isEditButton){
        Path userPath = Paths.get(System.getProperty("user.dir"));
        File gameDirectory = new File(userPath+RESOURCE_PATH);
        ListView<String> games = myRenderSystem.makeDirectoryFileList(gameDirectory, true);
        Stage edit = new Stage();
        edit.setScene(new Scene(new Group(games)));
        if(isEditButton) {
            games.setOnMouseClicked(e -> {
                initializeGameEditor(new File(gameDirectory.getPath()+ "/"+games.getSelectionModel().getSelectedItem()));
                edit.close();
            });
        } else {
            games.setOnMouseClicked(e -> {
                setDirectory(new File(gameDirectory.getPath()+ "/"+games.getSelectionModel().getSelectedItem()));
                edit.close();
            });
        }
        edit.show();
    }

    protected void nameNewObject(String title, String label) {
        newGamePopup = new Stage();
        Consumer consumer = new Consumer() {
            @Override
            public void accept(Object o) {
                o = o;
            }
        };
        newGamePopup.setTitle(title);
        TextBox stageName = myRenderSystem.makeTextField(consumer, "", 100.0,20.0,200.0,30.0, myRenderSystem.getPlainFont());
        Text stageLabel = myRenderSystem.makeText(label, false, 12, Color.BLACK, 20.0, 50.0);
        Button create = myRenderSystem.makeStringButton("Create", Color.BLACK, false, Color.WHITE, 12.0,50.0, 100.0, 100.0, 30.0);
        Button cancel = myRenderSystem.makeStringButton("Cancel", Color.BLACK, false, Color.WHITE, 12.0,200.0, 100.0, 100.0, 30.0);
        create.setOnMouseClicked(e -> makeGameDirectory(stageName.getText()));
        cancel.setOnMouseClicked(e -> newGamePopup.close());
        Scene creationScene = new Scene(new Group(stageName, stageLabel, create, cancel), 400, 200);
        newGamePopup.setScene(creationScene);
        newGamePopup.show();
    }
}
