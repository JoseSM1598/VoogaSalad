package renderer.external;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import renderer.external.Structures.*;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

import static renderer.external.RenderUtils.toRGBCode;

/** Provides a high-level tool for the rapid creation of standardized and stylized core UI elements and graphics
 *  @author bpx
 *  @author ob29
 *  @author rr202
 *  @author ak457
 */
public class RenderSystem implements Renderer{

    public static final String DEFAULT_EMPHASIS_FONT = "AlegreyaSansSC-Black.ttf";
    public static final int DEFAULT_EMPHASIS_FONTSIZE = 50;
    public static final String DEFAULT_PLAIN_FONT = "OpenSans-Regular.ttf";
    public static final int DEFAULT_PLAIN_FONTSIZE = 25;

    public static final String BUTTON_FORMAT = "-fx-background-color: %s; -fx-font-family: '%s'; -fx-background-radius: %s; -fx-background-insets: 0; -fx-font-size: %s;";
    public static final String BUTTON_SCALE = "-fx-scale-x: %s; -fx-scale-y: %s;";
    public static final double BUTTON_SCALE_FACTOR = 1.2;

    private Font myEmphasisFont;
    private Font myPlainFont;

    private Double myButtonScaleFactor;

    /** Create a new {@code RenderSystem} with the specified default stylistic options*/
    public RenderSystem(Font plainFont,Font emphasisfont){
        myPlainFont=plainFont;
        myEmphasisFont = emphasisfont;
        myButtonScaleFactor = BUTTON_SCALE_FACTOR;
    }

    /** Default RenderSystem with default fonts**/
    public RenderSystem(){
        myEmphasisFont = Font.loadFont(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_EMPHASIS_FONT), DEFAULT_EMPHASIS_FONTSIZE);
        myPlainFont = Font.loadFont(this.getClass().getClassLoader().getResourceAsStream(DEFAULT_PLAIN_FONT), DEFAULT_PLAIN_FONTSIZE);
        myButtonScaleFactor = BUTTON_SCALE_FACTOR;
    }


    /** Creates a {@code Button} with a label
     *  @param text The label for the button
     *  @param buttonColor The hex string for the color of the button background
     *  @param emphasis Whether to use emphasis text or plain text
     *  @param textColor The fill {@code Color} for the label
     *  @param fontSize The font size for the text label of the button
     *  @param x The x position of the button
     *  @param y The y position of the button
     *  @param width The width of the button
     *  @param height The height of the button */
    public Button makeStringButton(String text, Color buttonColor, Boolean emphasis, Color textColor, Double fontSize, Double x, Double y, Double width, Double height){
        Font font = new Font(1.0);
        if(emphasis){
            font = myEmphasisFont;
        }
        else{
            font = myPlainFont;
        }
        Button button = new Button(text);
        //BUTTON_FORMAT: Color buttonColor, String fontName, Double borderRadius, Double fontSize
        button.setStyle(String.format(BUTTON_FORMAT,
                toRGBCode(buttonColor),
                font.getName(),height,fontSize));
        button.setTextFill(textColor);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setMinSize(width,height);
        button.setPrefSize(width,height);
        button.setMaxSize(width,height);
        Font finalFont = font;
        //BUTTON_SCALE: Double xScale, Double yScale
        button.setOnMouseEntered(event->button.setStyle(String.format(BUTTON_FORMAT+BUTTON_SCALE,toRGBCode(Color.DARKGOLDENROD),finalFont.getName(),height,fontSize, myButtonScaleFactor,myButtonScaleFactor)));
        button.setOnMouseExited(event -> button.setStyle(String.format(BUTTON_FORMAT+BUTTON_SCALE,toRGBCode(buttonColor),finalFont.getName(),height,fontSize,1.0,1.0)));
        return button;
    }
    public Font getPlainFont(){
        return myPlainFont;
    }

    public Font getEmphasisFont(){
        return myEmphasisFont;
    }


    public void buttonHoverEffect(ButtonBase button){
        button.setOnMouseEntered(event->button.setStyle(String.format(BUTTON_FORMAT+BUTTON_SCALE,toRGBCode(Color.BLACK),this.getPlainFont().getName(),20,15, 1.1,1.1)));
        button.setOnMouseExited(event -> button.setStyle(String.format(BUTTON_FORMAT+BUTTON_SCALE,toRGBCode(Color.BLACK),this.getPlainFont().getName(),20,15,1.0,1.0)));
    }

    public void styleButton(ButtonBase button){
        button.setStyle(String.format(BUTTON_FORMAT,
                toRGBCode(Color.BLACK),
                this.getPlainFont().getName(),20,15));
    }

    public void applyStyleAndEffect(ToggleButton t){
        styleButton(t);
        buttonHoverEffect(t);
        t.selectedProperty().addListener((p, ov, nv) -> {
            selectEffect(t);
        });
    }

    private void selectEffect(ToggleButton b){
        if(b.isSelected()){
            DropShadow drop = new DropShadow(12.0,Color.BLUE);
            drop.setHeight(30.0);
            b.setEffect(drop);
        }else{
            b.setEffect(null);
        }
    }


    /** Creates a button using an image
     *  @param image The {@code ImageView} that will be used as the button
     *  @param x The x position of the button
     *  @param y The y position of the button
     *  @param width The width of the button
     *  @param height The height of the button */
    public Button makeImageButton(Image image, Double x, Double y, Double width, Double height){
        Button button = new Button();
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        button.setGraphic(imageView);
        button.setLayoutX(x);
        button.setLayoutY(y);
        return button;
    }

    /** Creates text
     *  @param text The text to display
     *  @param emphasis Whether to use emphasis font or plain font
     *  @param fontsize The size of the font
     *  @param color The fill {@code Color} for the font
     *  @param x The x position of the text
     *  @param y The y position of the text*/
    public Text makeText(String text, Boolean emphasis, Integer fontsize, Color color, Double x, Double y){
        Text newtext = new Text(text);
        if(emphasis){
            newtext.setFont(myEmphasisFont);
            newtext.setStyle(String.format("-fx-font-size: %s; -fx-text-fill: %s;",fontsize,toRGBCode(color)));
        }
        else{
            newtext.setFont(myPlainFont);
            newtext.setStyle(String.format("-fx-font-size: %s; -fx-text-fill: %s;",fontsize,toRGBCode(color)));
        }
        newtext.setFill(color);
        newtext.setX(x);
        newtext.setY(y);
        return newtext;
    }

    /** Creates a scrollable Pane that displays its contents in a grid
     *  @param contentList The content list, contains {@code ImageView} objects to display on the grid*/
    public ScrollPane makeGridScrollPane(List<ImageView> contentList){
        return new ScrollPane();
    }

    /** Creates a scrollable Pane that displays its contents in a list
     *  @param dataList contains the {@code ScrollableItem} to display */
    public ScrollPane makeListScrollPane(List<ScrollableItem> dataList){
        return new ScrollPane();
    }

    /** Draws a {@code Level} to the specified target
     *  @param root The target {@code Group} to draw to
     *  @param image The {@code Level} to draw */
    public void drawStage(Group root, Image image){
        Level level = new Level(image);
        root.getChildren().add(level.getWindow());
    }

    /** Creates an editable {@code TextField}
     * @param text The default text to display in the {@code TextField}
     *  @param x The x position of the {@code TextField}
     * @param y The y position of the {@code TextField}
     * @param w The width of the {@code TextField}
     * @param h The height of the {@code TextField}
     */
    public TextBox makeTextField(Consumer<String> fieldSetter, String text, Double x, Double y, Double w, Double h, Font font){
        return new TextBox(fieldSetter,text,x,y,w,h,font);
    }
    //Default textfield with default plain font
    public TextBox makeTextField(Consumer<String> fieldSetter, String text, Double x, Double y, Double w, Double h){
        return new TextBox(fieldSetter,text,x,y,w,h,myPlainFont);
    }

    /** Creates a {@code Slider} that modifies a field
     * @param text The label text for the slider
     * @param fieldSetter The lambda that will modify the necessary parameter using the {@code Slider} value
     * @param x The x position of the {@code Slider}
     * @param y The y position of the {@code Slider}
     * @param w The width of the {@code Slider}
     */
    public SliderBox makeSlider(String text, Double startVal,Consumer<Double> fieldSetter, Double x, Double y, Double w){
        return new SliderBox(text, myPlainFont, startVal,fieldSetter, x, y, w);
    }

    /** Creates a {@code FileChooser} for a specific file type
     *  @param filetype The file type to be accepted, can be "image","audio",or "xml", or "all"
     */
    public FileChooser makeFileChooser(String filetype){
        FileChooser.ExtensionFilter extensionFilter;
        if(filetype.equalsIgnoreCase("xml")){
            extensionFilter = new FileChooser.ExtensionFilter("xml files (*.xml)", "*.xml");
        }
        else if(filetype.equalsIgnoreCase("audio")){
            extensionFilter = new FileChooser.ExtensionFilter("Audio files (*.mp3, *.wav, *.aac, *.aiff)", "*.mp3","*.wav","*.aac","*.aiff","*.m4a");
        }
        else if(filetype.equalsIgnoreCase("image")){
            extensionFilter = new FileChooser.ExtensionFilter("Image files (*.png, *.gif, *.jpg, *.mpo)", "*.png","*.gif","*.jpg","*.mpo");
        }
        else if(filetype.equalsIgnoreCase("all")){
            return new FileChooser();
        }
        else{
            throw new IllegalArgumentException("Invalid filetype parameter");
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(extensionFilter);
        return fileChooser;
    }

    /** Creates a {@code DirectoryChooser}
     */
    public DirectoryChooser makeDirectoryChooser(){
        return new DirectoryChooser();
    }

    /** Creates a horizontal set of string-labelled {@code Button} objects where only one can be active at a time
     *  @param options The possible options for the buttons
     */
    public SwitchButton makeSwitchButtons(List<String> options, boolean emphasis, Color bgColor, Color textColor, Double spacing, Double x, Double y, Double w, Double h){
        if(emphasis){
            return new SwitchButton(options,x,y,w,h,spacing,bgColor,textColor,myEmphasisFont);
        }
        else{
            return new SwitchButton(options, x,y,w,h,spacing, bgColor,textColor,myPlainFont);
        }
    }

    /** Creates a {@code Carousel} that displays {@code String} objects, that can be cycled through with left and right arrow buttons
     *  @param options The possible options for the {@code Carousel}
     *  @param emphasis Whether to use emphasis font or plain font
     *  @param bgColor The main background fill for the {@code Carousel}
     *  @param textColor The text fill color
     *  @param spacing Amount of spacing between main carousel elements
     *  @param x X position of the {@code Carousel}
     *  @param y Y Position of the {@code Carousel}
     *  @param w The width of the {@code Carousel} {@code String} display
     *  @param h The height of the {@code Carousel}
     */
    public Carousel makeCarousel(List<String> options, boolean emphasis, Color bgColor, Color textColor, Double spacing, Double x, Double y, Double w, Double h){
        if(emphasis){
            return new Carousel(options,spacing, x,y,w,h,bgColor,textColor,myEmphasisFont);
        }
        else{
            return new Carousel(options,spacing, x,y,w,h,bgColor,textColor,myPlainFont);
        }
    }

    /** Creates an animation using a {@code Sprite}
     * @param sprite The {@code Sprite} that will be animated
     * @param duration The total length of the animation
     * @param count The total number of frames
     * @param columns The number of frames per row
     * @param offsetX The offset of the first frame in the x direction
     * @param offsetY The offset of the first frame in the y direction
     * @param width The width of each animation frame
     * @param height The height of each animation frame
     */
    public SpriteAnimation makeSpriteAnimation(Sprite sprite, Duration duration,
                                               Integer count, Integer columns,
                                               Double offsetX, Double offsetY,
                                               Double width, Double height){

        return new SpriteAnimation(sprite, duration, count, columns, offsetX, offsetY, width, height);
    }

    /** Creates a {@code Sprite} from an {@code Image} and sets its viewport to the default frame
     *  @param image The {@code Image} to conver to a {@code Sprite}
     *  @param offsetX The offset of the first frame in the x direction
     *  @param offsetY The offset of the first frame in the y direction
     *  @param width The width of the first frame
     *  @param height The height of the first frame
     */
    public Sprite makeSprite(Image image, Double offsetX, Double offsetY, Double width, Double height){
        //Sprite sprite = new Sprite(image, width, height);
        //sprite.setViewport(offsetX,offsetY);
        Sprite sprite = new Sprite(image, offsetX, offsetY, width, height);
        return sprite;
    }

    /**
     * Creates a ListView of all the directories or files under the given directory
     * @param directory The parent directory to extract directories and files from
     * @param wantDirectory Set to true if directories are desire, false if files are desired
     * @return The ListView that contains all of the directories or files under the directory parameter
     */
    public ListView<String> makeDirectoryFileList(File directory, boolean wantDirectory) {
        ListView<String> fileList = new ListView<>();
        ObservableList<String> fileItems = FXCollections.observableArrayList();
        File[] directoryArray = directory.listFiles();
        for(int i = 0; i < directoryArray.length; i++) {
            if(directoryArray[i].isDirectory() && wantDirectory) {
                fileItems.add(directoryArray[i].getName());
            } else if(directoryArray[i].isFile() && !wantDirectory) {
                fileItems.add(directoryArray[i].getName());
            }
        }
        fileList.setItems(fileItems);
        return fileList;
    }

    public static void createErrorAlert(String title, String text){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(title);
        errorAlert.setContentText(text);
        errorAlert.showAndWait();
    }
}
