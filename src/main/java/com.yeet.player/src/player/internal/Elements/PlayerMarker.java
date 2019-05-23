package player.internal.Elements;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import renderer.external.Structures.Sprite;

import static player.external.CombatScreen.TILE_SIZE;

/** Triangular colored marker that follows above a sprite
 *  @author bpx
 */
public class PlayerMarker extends VBox {

    public static final double MARKER_SPACING = 20.0;

    public PlayerMarker(Color color, Sprite target){
        super();
        super.setPrefSize(target.getViewport().getWidth(),TILE_SIZE/3);
        super.setPrefSize(target.getViewport().getWidth(),TILE_SIZE/3);
        super.setMaxSize(target.getViewport().getWidth(),TILE_SIZE/3);
        super.setAlignment(Pos.TOP_CENTER);
        Polygon marker = new Polygon();
        marker.getPoints().addAll(0.0,0.0,TILE_SIZE/3,0.0,TILE_SIZE/6,TILE_SIZE/3);
        marker.setFill(color);
        NumberBinding xsum = Bindings.add(target.layoutXProperty(),0);
        super.layoutXProperty().bind(xsum);
        NumberBinding ysum = Bindings.add(target.layoutYProperty(),-MARKER_SPACING);
        super.layoutYProperty().bind(ysum);
        super.getChildren().addAll(marker);
    }
}
