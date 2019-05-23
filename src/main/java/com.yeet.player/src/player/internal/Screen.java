package player.internal;

import javafx.scene.Group;
import javafx.scene.Scene;
import renderer.external.Renderer;

/** Super class for all scenes for the player
 *  @author bpx
 */
public abstract class Screen extends Scene {

    private Group myRoot;
    private Renderer myRenderer;

    public Screen(Group root, Renderer renderer) {
        super(root);
        myRoot = root;
        myRenderer = renderer;
    }

    public Group getMyRoot(){
        return myRoot;
    }

    public Renderer getMyRenderer(){
        return myRenderer;
    }

}
