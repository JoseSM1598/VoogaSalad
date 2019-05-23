package player.internal.Elements;

import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/** Pop-in message bar with customizable text for displaying tooltips or other information
 *  @author bpx
 */
public class MessageBar extends StackPane {

    public static final double DURATION = 0.5;
    public static final double SPACER_WIDTH = 54.0;
    public static final int TITLE_WIDTH = 295;
    public static final double MESSAGE_WIDTH = 822.0;
    private final Double myX;
    private final Double myY;
    private final Double myWidth;
    private final Double myHeight;

    private Text myTitle;
    private Text myMessage;

    public MessageBar(Text titleText, Text messageText, Double x, Double y){
        super();
        myX = x;
        myY = y;
        myTitle = titleText;
        myMessage = messageText;
        ImageView background = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("message_bar.png")));
        myWidth = background.getImage().getWidth();
        myHeight = background.getImage().getHeight();
        super.setPrefSize(myWidth,myHeight);
        super.setAlignment(Pos.CENTER_LEFT);
        Rectangle leftSpacer = new Rectangle(SPACER_WIDTH,myHeight, Color.TRANSPARENT);
        HBox titleContainer = new HBox();
        titleContainer.setPrefSize(TITLE_WIDTH, myHeight);
        titleContainer.setAlignment(Pos.CENTER);
        titleContainer.getChildren().add(titleText);
        HBox messageContainer = new HBox();
        messageContainer.setPrefSize(MESSAGE_WIDTH,myHeight);
        messageContainer.setAlignment(Pos.CENTER);
        messageContainer.getChildren().add(messageText);
        HBox mainContainer = new HBox();
        mainContainer.getChildren().addAll(leftSpacer,titleContainer,messageContainer);
        super.setTranslateX(x);
        super.setTranslateY(y);
        super.getChildren().addAll(background,mainContainer);
    }

    public void show(){
        createTransition(myX + myWidth, myX);
    }

    public void hide()
    {
        createTransition(this.getTranslateX(), myX+myWidth);
    }

    private void createTransition(double fromX, double toX) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(DURATION),this);
        translateTransition.setFromX(fromX);
        translateTransition.setToX(toX);
        translateTransition.setCycleCount(1);
        translateTransition.play();
    }

    public void setTitle(String text){
        myTitle.setText(text);
    }

    public void setMessage(String text){
        myMessage.setText(text);
    }

}
