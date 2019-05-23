package player.internal;

import com.google.common.eventbus.EventBus;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import messenger.external.EventBusFactory;
import messenger.external.ExitMenuEvent;
import messenger.external.GameOverEvent;
import messenger.external.MenuStartEvent;
import player.external.CombatScreen;
import player.internal.Elements.MessageBar;
import renderer.external.Renderer;
import replay.external.ReplayPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static player.internal.MainMenuScreen.*;
import static player.internal.SettingsScreen.*;

/** Screen for viewing the replay
 *  @author bpx
 */
public class ReplayViewerScreen extends Screen {

    public static final int VIEWER_WIDTH = 939;
    public static final int VIEWER_HEIGHT = 587;

    private EventBus myEventBus;

    private SceneSwitch backSwitch;

    private File gameDirectory;
    private MessageBar myMessageBar;
    private ReplayPlayer replayPlayer;
    private CombatScreen myCombatScreen;
    private CombatResultsScreen myCombatResultsScreen;

    private StackPane replayViewContainer;

    public ReplayViewerScreen(Group root, Renderer renderer, File gameDirectory, Image background, ReplayPlayer replayPlayer, SceneSwitch backSwitch) {
        super(root, renderer);
        this.backSwitch = backSwitch;
        myEventBus = new EventBus();
        this.gameDirectory = gameDirectory;
        this.replayPlayer = replayPlayer;
        ImageView bg = new ImageView(background);
        bg.setFitHeight(800.0);
        bg.setFitWidth(1280.0);
        myMessageBar = new MessageBar(this.getMyRenderer().makeText("Replay Viewer",true,MESSAGEBAR_TITLE_FONTSIZE, Color.WHITE,0.0,0.0),
                this.getMyRenderer().makeText("Watch your replay!",false,MESSAGEBAR_MSG_FONTSIZE,Color.BLACK,0.0,0.0),
                MESSAGEBAR_X,MESSAGEBAR_Y);
        myMessageBar.hide();
        StackPane topBar = makeTopBar(super.getMyRenderer().makeText("Replay Viewer",true,55, Color.BLACK,0.0,0.0),
                makeButton(new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("back_button.png"))),
                        "Back","Return to the replays screen!",53.74,60.72,0.0,0.0,myMessageBar,
                        event-> handleBack()));
        StackPane mainContainer = new StackPane();
        mainContainer.setPrefSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        mainContainer.setAlignment(Pos.CENTER);
        replayViewContainer = new StackPane();
        //replayViewContainer.setPrefSize(VIEWER_WIDTH, VIEWER_HEIGHT);
        replayViewContainer.setAlignment(Pos.CENTER);


        replayViewContainer.getChildren().addAll();
        mainContainer.getChildren().addAll(replayViewContainer);
        super.getMyRoot().getChildren().addAll(bg, mainContainer,myMessageBar,topBar);
    }

    private void handleBack() {
        EventBusFactory.getEventBus().post(new MenuStartEvent());
        myEventBus.post(new GameOverEvent(0,new ArrayList<>()));
        replayPlayer.stop();
        backSwitch.switchScene();
    }

    public void setUpReplayViewer(HashMap<Integer, String> characterNames, HashMap<Integer, Color> characterColors, String gameMode, Integer typeValue,String stageName) {
        myCombatScreen = new CombatScreen(new Group(),super.getMyRenderer(),gameDirectory,false,()->doNothing(),(i,list)->doNothing());
        myEventBus.register(myCombatScreen);
        myEventBus.post(new ExitMenuEvent());
        myCombatScreen.setupCombatScene(characterNames,characterColors,gameMode,typeValue, new ArrayList<>(),stageName);
        replayViewContainer.getChildren().clear();
        replayViewContainer.getChildren().addAll(myCombatScreen.getMyRoot());
        replayViewContainer.setScaleX(VIEWER_WIDTH/SCREEN_WIDTH);
        replayViewContainer.setScaleY(VIEWER_HEIGHT/SCREEN_HEIGHT);
        replayPlayer.setEventBus(myEventBus);
        replayPlayer.play();
    }

    private void doNothing() {
    }
}

