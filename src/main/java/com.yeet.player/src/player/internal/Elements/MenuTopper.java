package player.internal.Elements;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import player.internal.SceneSwitch;
import renderer.external.Structures.Carousel;

import java.util.ArrayList;
import java.util.Arrays;

import static player.internal.Elements.CharacterChooseDisplay.FORMAT_RECT;
import static renderer.external.RenderUtils.toRGBCode;

/** Menu bar at top of character grid for accessing back button and changing game rules
 *  @author bpx
 */
public class MenuTopper extends HBox {

    public static final String DEFAULT_MODE = "Time";
    public static final String STOCK_TEXT = "Stock";
    public static final String TIME_TEXT = "Time";

    public enum Gamemode{
        TIME, STOCK;
    }

    public static final String[] times = new String[]{"1 min.","2 min.","3 min.","4 min.","5 min."};
    private final ArrayList<String> myTimeList = new ArrayList<>(Arrays.asList(times));

    public static final String[] stocks = new String[]{"1","2","3"};
    private final ArrayList<String> myStockList = new ArrayList<>(Arrays.asList(stocks));

    private Gamemode myGameMode;
    private Carousel myCarousel;

    private ImageView ruleIcon;
    private Text ruleText;

    private Image stockIcon;
    private Image timeIcon;

    /** Create a {@code MenuTopper} with a certain amount of spacing
     *  @param exampleText A {@code Text} sample to use for formatting the {@code MenuTopper}
     *  @param spacing Amount of spacing between elements horizontally
     *  @param sceneSwitch The {@code SceneSwitch} to trigger when the rules block is clicked
     */
    public MenuTopper(Text exampleText, double spacing, SceneSwitch sceneSwitch){
        super(spacing);
        this.setPrefSize(1280,75);
        this.setAlignment(Pos.CENTER_LEFT);
        myGameMode = Gamemode.TIME;
        Rectangle menuSpacer = new Rectangle(15,75, Color.TRANSPARENT);
        //set up back button
        ImageView backButton = new ImageView((new Image(this.getClass().getClassLoader().getResourceAsStream("back_button.png"))));
        backButton.setFitHeight(60.0);
        backButton.setFitWidth(60.0);
        VBox buttonHolder = new VBox(backButton);
        buttonHolder.setOnMouseEntered(event -> {
            buttonHolder.setScaleX(1.1);
            buttonHolder.setScaleY(1.1);
        });
        buttonHolder.setOnMouseExited(event -> {
            buttonHolder.setScaleX(1.0);
            buttonHolder.setScaleY(1.0);
        });
        buttonHolder.setOnMousePressed(event -> sceneSwitch.switchScene());
        //set up actual menu topper
        HBox menuTop = new HBox(5.0);
        menuTop.setAlignment(Pos.CENTER);
        menuTop.setPrefSize(914,75);
        menuTop.setStyle(String.format(FORMAT_RECT,"35 35 0 0", "35 35 0 0", toRGBCode(Color.web("#201D20"))));
        HBox smashBox = new HBox(24.0);
        smashBox.setAlignment(Pos.CENTER);
        smashBox.setPrefSize(280,62);
        smashBox.setMaxSize(280,62);
        smashBox.setStyle(String.format(FORMAT_RECT,"35 0 0 0","35 0 0 0",toRGBCode(Color.web("#FF1D20"))));
        ImageView smashIcon = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("smash_icon.png")));
        smashIcon.setFitWidth(50);
        smashIcon.setFitHeight(50);
        ImageView smashText = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("smash_text.png")));
        HBox ruleBox = new HBox();
        ruleBox.setAlignment(Pos.CENTER);
        ruleBox.setPrefSize(370,62);
        ruleBox.setMaxSize(370,62);
        ruleBox.setStyle(String.format(FORMAT_RECT,"0 0 0 0","0 0 0 0",toRGBCode(Color.WHITE)));
        ruleBox.setOnMousePressed(event -> handleRuleBlockClick());
        stockIcon = new Image(this.getClass().getClassLoader().getResourceAsStream("stock_icon.png"));
        timeIcon = new Image(this.getClass().getClassLoader().getResourceAsStream("time_icon.png"));
        ruleIcon = new ImageView(timeIcon);
        ruleIcon.setFitHeight(34.0);
        ruleIcon.setFitWidth(34.0);
        ruleText = new Text(DEFAULT_MODE);
        ruleText.setFont(exampleText.getFont());
        HBox carouselBox = new HBox();
        carouselBox.setAlignment(Pos.CENTER);
        carouselBox.setPrefSize(217,62);
        carouselBox.setMaxSize(217,62);
        carouselBox.setStyle(String.format(FORMAT_RECT,"0 35 0 0","0 35 0 0",toRGBCode(Color.WHITE)));
        myCarousel = new Carousel(myTimeList,0.0,0.0,120.0,60.0,4.0,Color.WHITE,Color.BLACK,exampleText.getFont());
        ruleBox.getChildren().addAll(ruleIcon,ruleText);
        carouselBox.getChildren().addAll(myCarousel);
        smashBox.getChildren().addAll(smashIcon,smashText);
        menuTop.getChildren().addAll(smashBox,ruleBox,carouselBox);
        this.getChildren().addAll(menuSpacer,buttonHolder,menuTop);
    }

    private void switchGameMode(){
        if(myGameMode.equals(Gamemode.TIME)){
            myGameMode = Gamemode.STOCK;
            myCarousel.setContent(myStockList);
            ruleText.setText(STOCK_TEXT);
            ruleIcon.setImage(stockIcon);
        }
        else{
            myGameMode = Gamemode.TIME;
            myCarousel.setContent(myTimeList);
            ruleText.setText(TIME_TEXT);
            ruleIcon.setImage(timeIcon);
        }
    }

    private void handleRuleBlockClick(){
        switchGameMode();
    }

    public String getGameMode(){
        if(myGameMode.equals(Gamemode.STOCK)){
            return("STOCK");
        }
        else{
            return("TIME");
        }
    }

    public Integer getTypeValue(){
        return Integer.parseInt(myCarousel.getValue().substring(0,1));
    }

}
