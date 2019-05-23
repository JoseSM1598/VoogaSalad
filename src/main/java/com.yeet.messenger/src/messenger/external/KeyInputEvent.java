package messenger.external;

import javafx.scene.input.KeyCode;
import java.sql.Timestamp;

/** More specific {@code InputEvent} for keyboard input
 *  @author bpx
 */
public class KeyInputEvent extends InputEvent{

    private KeyCode myCode;
    private Timestamp time;

    /** Creates a new {@code KeyInputEvent} based on a key press
     *  @param keyCode The {@code KeyCode} for the pressed key
     */
    public KeyInputEvent(KeyCode keyCode){
        time = new Timestamp(System.currentTimeMillis());
        myCode = keyCode;
    }

    /** Returns a copy of the {@code KeyCode} stored in the {@code InputEvent}*/
    public KeyCode getKey(){
        return KeyCode.getKeyCode(myCode.getName());
    }

    /**  return the timestamp of when the key was pressed */
    public Timestamp getTime(){
        return time;
    }

    /** Returns the {@code String} representation of the key input */
    @Override
    public String getName() {
        return myCode.getName();
    }
}
