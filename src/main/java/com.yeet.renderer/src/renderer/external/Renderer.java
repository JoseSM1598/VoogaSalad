package renderer.external;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
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

/** External API for Renderer system, focused on creating UI elements and other graphical
 *  functionality necessary to edit and play games using the game engine.
 *  @author bpx
 *  @author ob29
 *  @author rr202
 *  @author ak457
 */
public interface Renderer{

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
    Button makeStringButton(String text, Color buttonColor, Boolean emphasis, Color textColor, Double fontSize, Double x, Double y, Double width, Double height);

    /** Creates a button using an image
     *  @param image The {@code ImageView} that will be used as the button
     *  @param x The x position of the button
     *  @param y The y position of the button
     *  @param width The width of the button
     *  @param height The height of the button */
    Button makeImageButton(Image image, Double x, Double y, Double width, Double height);

    /** Creates text
     *  @param text The text to display
     *  @param emphasis Whether to use emphasis font or plain font
     *  @param fontsize The size of the font
     *  @param color The fill {@code Color} for the font
     *  @param x The x position of the text
     *  @param y The y position of the text*/
    Text makeText(String text, Boolean emphasis, Integer fontsize, Color color, Double x, Double y);

    /** Creates a scrollable Pane that displays its contents in a grid
     *  @param contentList The content list, contains {@code ImageView} objects to display on the grid*/
    ScrollPane makeGridScrollPane(List<ImageView> contentList);

    /** Creates a scrollable Pane that displays its contents in a list
     *  @param dataList contains the {@code ScrollableItem} to display */
    ScrollPane makeListScrollPane(List<ScrollableItem> dataList);

    /** Draws a {@code Level} to the specified target
     *  @param root The target {@code Pane} to draw to
     *  @param image The {@code Level} to draw */
    void drawStage(Group root, Image image);

    /** Creates an editable {@code TextField}
     * @param text The default text to display in the {@code TextField}
     *  @param x The x position of the {@code TextField}
     * @param y The y position of the {@code TextField}
     * @param w The width of the {@code TextField}
     * @param h The height of the {@code TextField}
     */
    TextBox makeTextField(Consumer<String> fieldSetter, String text, Double x, Double y, Double w, Double h, Font font);

    /** Creates a {@code Slider} that modifies a field
     * @param text The label text for the slider
     * @param startVal the starting position of slider
     * @param fieldSetter The lambda that will modify the necessary parameter using the {@code Slider} value
     * @param x The x position of the {@code Slider}
     * @param y The y position of the {@code Slider}
     * @param w The width of the {@code Slider}
     */
    SliderBox makeSlider(String text, Double startVal,Consumer<Double> fieldSetter, Double x, Double y, Double w);

    /** Creates a {@code FileChooser} for a specific file type
     *  @param filetype The file type to be accepted, can be "image","audio",or "xml", or "all"
     */
    FileChooser makeFileChooser(String filetype);

    /** Creates a {@code DirectoryChooser}
     */
    DirectoryChooser makeDirectoryChooser();

    /** Creates a horizontal set of string-labelled {@code Button} objects where only one can be active at a time
     *  @param options The possible options for the buttons
     */
    SwitchButton makeSwitchButtons(List<String> options, boolean emphasis, Color bgColor, Color textColor, Double spacing, Double x, Double y, Double w, Double h);

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
    Carousel makeCarousel(List<String> options, boolean emphasis, Color bgColor, Color textColor, Double spacing, Double x, Double y, Double w, Double h);

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
    SpriteAnimation makeSpriteAnimation(Sprite sprite, Duration duration,
                                        Integer count, Integer columns,
                                        Double offsetX, Double offsetY,
                                        Double width, Double height);

    /** Creates a {@code Sprite} from an {@code Image} and sets its viewport to the default frame
     *  @param image The {@code Image} to conver to a {@code Sprite}
     *  @param offsetX The offset of the first frame in the x direction
     *  @param offsetY The offset of the first frame in the y direction
     *  @param width The width of the first frame
     *  @param height The height of the first frame
     */
    Sprite makeSprite(Image image, Double offsetX, Double offsetY, Double width, Double height);
    /**
     * Creates a ListView of all the directories or files under the given directory
     * @param directory The parent directory to extract directories and files from
     * @param wantDirectory Set to true if directories are desire, false if files are desired
     * @return The ListView that contains all of the directories or files under the directory parameter
     */
    ListView<String> makeDirectoryFileList(File directory, boolean wantDirectory);
}