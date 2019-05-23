package player.internal.Elements;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.function.Consumer;

/** Draggable token used for selecting characters on the {@code CharacterSelectScreen}
 *  @author bpx
 */
public class DragToken extends StackPane {

    private double basex;
    private double basey;

    private double xorigin;
    private double yorigin;
    private double xtranslate;
    private double ytranslate;

    private Color myColor;
    private Circle myCircle;
    private Text myLabel;

    /**
     * Creates a new {@code DragToken} using the specified parameters
     *
     * @param label         The {@code Text} to use for the label
     * @param color         The {@code Color} of the token
     * @param x             The initial x position of the token
     * @param y             The initial y position of the token
     * @param radius        The size of the token
     * @param tokenConsumer Accepts a token upon mouse drag release
     */
    public DragToken(Text label, Color color, double x, double y, double radius, Consumer<DragToken> tokenConsumer) {
        super();
        myColor = color;
        myLabel = label;
        basex = x;
        basey = y;
        this.setPrefSize(radius, radius);
        this.setAlignment(Pos.CENTER);
        myCircle = new Circle(radius, color);
        this.getChildren().addAll(myCircle, label);
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setOnMousePressed(event -> {
            xorigin = event.getSceneX();
            yorigin = event.getSceneY();
            xtranslate = super.getTranslateX();
            ytranslate = super.getTranslateY();
        });
        this.setOnMouseDragged(event -> {
            setPosition(event.getSceneX(), event.getSceneY());
        });
        this.setOnMouseReleased(event -> {
            tokenConsumer.accept(this);
        });

    }

    /** Returns the text associated with the token */
    public String getLabel() {
        return myLabel.getText();
    }

    /**
     * Returns a {@code Point2D} representation of the token's center position
     */
    public Point2D getPosition() {
        return new Point2D(basex + getTranslateX() + this.getWidth() / 2, basey + getTranslateY() + this.getHeight() / 2);
    }

    /**
     * Sets the position of the token
     *
     * @param x The new x position
     * @param y the new y position
     */
    public void setPosition(double x, double y) {
        double xoffset = x - xorigin;
        double yoffset = y - yorigin;
        double newTranslateX = xtranslate + xoffset;
        double newTranslateY = ytranslate + yoffset;
        this.setTranslateX(newTranslateX);
        this.setTranslateY(newTranslateY);
    }

    /**
     * Sets the {@code Color} of the token
     *
     * @param color The new {@code Color} of the token
     */
    public void setColor(Color color) {
        myCircle.setFill(color);
    }

    /**
     * Changes the token back to the original color
     */
    public void resetColor() {
        myCircle.setFill(myColor);
    }

    /** Checks whether the token's center is inside the given bounds
     *  @param bounds The {@code Rectangle2D} representing the area to check bounds for
     */
    public boolean isInside(Rectangle2D bounds) {
        return (getPosition().getX() >= bounds.getMinX() && getPosition().getX() <= bounds.getMaxX() && getPosition().getY() >= bounds.getMinY() && getPosition().getY() < bounds.getMaxY());
    }

}
