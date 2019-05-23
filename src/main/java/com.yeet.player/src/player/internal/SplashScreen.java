package player.internal;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import renderer.external.Renderer;

import java.io.File;

/** The first screen the user sees when the game is launched
 *  @author bpx
 */
public class SplashScreen extends Screen {

    private MediaPlayer startSE;

    public SplashScreen(Group root, Renderer renderer, File directory, SceneSwitch sceneSwitch) {
        super(root, renderer);
        startSE = new MediaPlayer(new Media(new File("src/main/java/com.yeet.player/resources/start.mp3").toURI().toString()));
        ImageView splash = new ImageView(new Image(String.format("%s%s",directory.toURI(),"splash.png")));
        splash.setFitHeight(800);
        splash.setFitWidth(1280);
        Rectangle rect = new Rectangle(0.0,700.0,1280.0,30.0);
        rect.setFill(Color.web("#ff3c00"));
        Text text = super.getMyRenderer().makeText("P r e s s  a n y  k e y . . .",false,20,Color.WHITE,0.0,0.0);
        HBox textbox = new HBox(text);
        textbox.setLayoutX(920.0);
        textbox.setLayoutY(705.0);
        super.getMyRoot().getChildren().addAll(splash,rect,textbox);
        super.setOnKeyPressed(event -> {
            startSE.setOnEndOfMedia(()-> {
                startSE.stop();
                sceneSwitch.switchScene();
            });
            startSE.play();
        });
    }
}
