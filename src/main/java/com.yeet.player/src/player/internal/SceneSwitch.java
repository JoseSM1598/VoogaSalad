package player.internal;

/** Functional interface for passing scene switching lambda expressions
 *  @author bpx
 */
@FunctionalInterface
public interface SceneSwitch {

    /** Lambda takes no parameters, returns nothing, just switches the scene as defined in the implementation */
    void switchScene();
}
