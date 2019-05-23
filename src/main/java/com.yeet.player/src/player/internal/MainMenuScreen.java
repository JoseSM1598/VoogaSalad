package player.internal;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import player.internal.Elements.MessageBar;
import renderer.external.Renderer;

/** Central hub for access to all game functionality
 *  @author bpx
 */
public class MainMenuScreen extends Screen {

    public static final String SMASH_TITLE = "SMASH";
    public static final String SMASH_MSG = "Jump right into a brawl with up to 4 players!";
    public static final String SETTINGS_TITLE = "SETTINGS";
    public static final String SETTINGS_MSG = "Adjust the game settings to your liking!";
    public static final String QUIT_TITLE = "QUIT";
    public static final String QUIT_MSG = "Aw, leaving so soon?";
    public static final String DEFAULT_TITLE = "Message";
    public static final String DEFAULT_MSG = "Welcome to Yeet Bros. Brawl!";
    public static final int MESSAGEBAR_TITLE_FONTSIZE = 50;
    public static final int MESSAGEBAR_MSG_FONTSIZE = 30;
    public static final double MESSAGEBAR_X = 174.0;
    public static final double MESSAGEBAR_Y = 676.0;

    private MediaPlayer selectSE;
    private MessageBar myMessageBar;

    public MainMenuScreen(Group root, Renderer renderer, Image bg, MediaPlayer sePlayer, SceneSwitch smashSceneSwitch, SceneSwitch quitSceneSwitch, SceneSwitch settingsSceneSwitch, SceneSwitch replaySceneSwitch) {
        super(root, renderer);
        myMessageBar = new MessageBar(this.getMyRenderer().makeText(DEFAULT_TITLE,true, MESSAGEBAR_TITLE_FONTSIZE, Color.WHITE,0.0,0.0),
                this.getMyRenderer().makeText(DEFAULT_MSG,false, MESSAGEBAR_MSG_FONTSIZE, Color.BLACK,0.0,0.0),
                MESSAGEBAR_X, MESSAGEBAR_Y);
        selectSE = sePlayer;
        ImageView background = new ImageView(bg);
        background.setFitHeight(800.0);
        background.setFitWidth(1280.0);
        ImageView smashButton = makeButton(new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("smash_button.png"))), SMASH_TITLE, SMASH_MSG,880.0,523.0,74.0,85.0, myMessageBar, event -> {
            selectSE.setOnEndOfMedia(() -> {
                smashSceneSwitch.switchScene();
                selectSE.stop();
            });
            selectSE.play();
        });
        ImageView settingsButton = makeButton(new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("settings_button.png"))), SETTINGS_TITLE, SETTINGS_MSG,610.0,146.0,649.0,254.0, myMessageBar, event -> {
            selectSE.setOnEndOfMedia(() -> {
                settingsSceneSwitch.switchScene();
                selectSE.stop();
            });
            selectSE.play();
        });
        ImageView quitButton = makeButton(new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("quit_button.png"))),QUIT_TITLE, QUIT_MSG,610.0,132.0,649.0,436.0, myMessageBar, event -> quitSceneSwitch.switchScene());
        ImageView replayButton = makeButton(new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("replay_button.png"))),"Replays","Relive your past matches!",192.0,192.0,1012.0,26.0,myMessageBar,event -> {
            selectSE.setOnEndOfMedia(() -> {
                replaySceneSwitch.switchScene();
                selectSE.stop();
            });
            selectSE.play();
        });
        super.getMyRoot().getChildren().addAll(background,myMessageBar,smashButton,settingsButton,quitButton,replayButton);
    }

    static ImageView makeButton(ImageView buttonImage, String messageTitle, String messageContent, double width, double height, double x, double y, MessageBar messageBar, EventHandler clickHandler) {
        ImageView button = buttonImage;
        button.setFitWidth(width);
        button.setFitHeight(height);
        button.setX(x);
        button.setY(y);
        button.setOnMouseEntered(event -> {
            button.setScaleY(1.1);
            button.setScaleX(1.1);
            messageBar.setTitle(messageTitle);
            messageBar.setMessage(messageContent);
            messageBar.show();

        });
        button.setOnMouseExited(event -> {
            button.setScaleY(1.0);
            button.setScaleX(1.0);
            messageBar.hide();
        });
        button.setOnMousePressed(clickHandler);
        return button;
    }
}
