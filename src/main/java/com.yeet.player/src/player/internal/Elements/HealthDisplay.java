package player.internal.Elements;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

import static renderer.external.RenderUtils.toRGBCode;

/** GUI display for a character's in-combat information including character portrait, name, and health percentage
 *  @author bpx
 */
public class HealthDisplay  extends StackPane {

    public static final double DISPLAY_WIDTH = 229.77;
    public static final double DISPLAY_HEIGHT = 131;
    public static final double PORTRAIT_SIZE = 91.0;
    public static final double POLYGON_HEIGHT = 43.99;

    private Text percentageText;
    private Text livesText;

    /** Constructor if lives are being used */
    public HealthDisplay(Text playerText, int initialLives, ImageView playerPortrait, Color portraitColor, Color polygonColor){
        super();
        createDisplay(playerText, playerPortrait, portraitColor, polygonColor);
        HBox livesDisplay = new HBox(10.0);
        livesDisplay.setMinSize(DISPLAY_WIDTH,DISPLAY_HEIGHT);
        livesDisplay.setAlignment(Pos.BOTTOM_LEFT);
        Rectangle livesSpacer = new Rectangle(29.45,DISPLAY_HEIGHT,Color.TRANSPARENT);
        ImageView heartIcon = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("heart_icon.png")));
        heartIcon.setFitWidth(16.0);
        heartIcon.setFitHeight(15.0);
        livesText = new Text();
        livesText.setFont(playerText.getFont());
        livesText.setStyle("-fx-font-size: 20; -fx-stroke: white;");
        setLives(initialLives);
        livesDisplay.getChildren().addAll(livesSpacer,heartIcon,livesText);
        this.getChildren().addAll(livesDisplay);
    }

    /** Constructor if lives are not being used */
    public HealthDisplay(Text playerText, ImageView playerPortrait, Color portraitColor, Color polygonColor){
        super();
        createDisplay(playerText, playerPortrait, portraitColor, polygonColor);
    }

    /** Common method used between different health display creations */
    private void createDisplay(Text playerText, ImageView playerPortrait, Color portraitColor, Paint polygonColor) {
        this.setPrefSize(DISPLAY_WIDTH, DISPLAY_HEIGHT);
        this.setAlignment(Pos.TOP_CENTER);
        StackPane topAlignedContainer = new StackPane();
        topAlignedContainer.setPrefSize(DISPLAY_WIDTH,DISPLAY_HEIGHT);
        topAlignedContainer.setAlignment(Pos.TOP_CENTER);
        HBox topContainer = new HBox(5.0);
        topContainer.setPrefSize(DISPLAY_WIDTH,PORTRAIT_SIZE);
        topContainer.setAlignment(Pos.TOP_CENTER);
        StackPane portraitContainer = new StackPane();
        portraitContainer.setMinSize(PORTRAIT_SIZE,PORTRAIT_SIZE);
        portraitContainer.setPrefSize(PORTRAIT_SIZE,PORTRAIT_SIZE);
        portraitContainer.setMaxSize(PORTRAIT_SIZE,PORTRAIT_SIZE);
        portraitContainer.setStyle("-fx-background-color: "+toRGBCode(portraitColor));
        ImageView portraitView = playerPortrait;
        portraitView.setFitWidth(PORTRAIT_SIZE);
        portraitView.setFitHeight(PORTRAIT_SIZE);
        portraitContainer.getChildren().addAll(portraitView);
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.0f, 0.0f, 0.0f));
        percentageText = new Text("100");
        percentageText.setFont(playerText.getFont());
        percentageText.setStyle("-fx-font-size: 70;-fx-fill: linear-gradient(#bababa 20%, #ffffff 65%, #a6a6a6 100%);");
        percentageText.setStroke(Color.BLACK);
        percentageText.setStrokeWidth(2.0);
        percentageText.setEffect(ds);
        Text percentageSymbol = new Text("%");
        percentageSymbol.setFont(playerText.getFont());
        percentageSymbol.setStyle("-fx-font-size: 25;-fx-fill: linear-gradient(#bababa 20%, #ffffff 65%, #a6a6a6 100%);");
        percentageSymbol.setStroke(Color.BLACK);
        percentageSymbol.setStrokeWidth(2.0);
        percentageSymbol.setEffect(ds);
        HBox textContainer = new HBox(percentageText,percentageSymbol);
        textContainer.setMinSize(DISPLAY_WIDTH- PORTRAIT_SIZE,PORTRAIT_SIZE);
        textContainer.setMaxSize(DISPLAY_WIDTH,PORTRAIT_SIZE);
        textContainer.setAlignment(Pos.CENTER);
        topContainer.getChildren().addAll(portraitContainer,textContainer);
        topAlignedContainer.getChildren().addAll(topContainer);
        StackPane bottomAlignedContainer = new StackPane();
        bottomAlignedContainer.setMinSize(DISPLAY_WIDTH,DISPLAY_HEIGHT);
        bottomAlignedContainer.setPrefSize(DISPLAY_WIDTH,DISPLAY_HEIGHT);
        bottomAlignedContainer.setMaxSize(DISPLAY_WIDTH,DISPLAY_HEIGHT);
        bottomAlignedContainer.setAlignment(Pos.BOTTOM_CENTER);
        StackPane nameContainer = new StackPane();
        nameContainer.setMinSize(DISPLAY_WIDTH,34.48);
        nameContainer.setPrefSize(DISPLAY_WIDTH,34.48);
        nameContainer.setMaxSize(DISPLAY_WIDTH,34.48);
        nameContainer.setAlignment(Pos.CENTER_LEFT);
        Polygon namePolygon = new Polygon();
        namePolygon.getPoints().addAll(0.0, POLYGON_HEIGHT,42.5,0.0, DISPLAY_WIDTH,9.5);
        namePolygon.setFill(polygonColor);
        namePolygon.setEffect(ds);
        Text nameText = playerText;
        nameText.setStyle("-fx-font-size: 25;");
        nameText.setStroke(Color.rgb(0,0,0,0.5));
        nameText.setStrokeWidth(3.0);
        nameText.setStrokeType(StrokeType.OUTSIDE);
        HBox nameTextContainer = new HBox(nameText);
        nameTextContainer.setMinSize(DISPLAY_WIDTH,POLYGON_HEIGHT);
        nameTextContainer.setAlignment(Pos.TOP_CENTER);
        nameContainer.getChildren().addAll(namePolygon,nameTextContainer);
        bottomAlignedContainer.getChildren().addAll(nameContainer);
        super.getChildren().addAll(topAlignedContainer,bottomAlignedContainer);
    }

    /** Sets the displayed number of lives */
    public void setLives(int lives){
        livesText.setText("x "+lives);
    }

    /** Sets the value of the health display */
    public void setHealth(int healthValue){
        percentageText.setText(String.valueOf(healthValue));
    }
}
