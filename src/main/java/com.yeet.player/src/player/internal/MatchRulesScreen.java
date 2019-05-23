package player.internal;

import javafx.scene.Group;
import renderer.external.Renderer;

/** Allows user to change certain parameters for a match, such as win condition
 *  @author bpx
 */
public class MatchRulesScreen extends Screen {

    public MatchRulesScreen(Group root, Renderer renderer) {
        super(root, renderer);
    }
}
