package input.Internal;

import messenger.external.KeyInputEvent;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * This class acts as an intermediate between the InputSystem and the InputHandler. This removes increased dependency between the two classes.
 * Essentially, it passes data and messages to and from the InputSystem and InputHandler
 */
public class Parser {

    private InputHandler handler;

    public Parser(File gameDir){
        handler = new InputHandler(gameDir); //pass in the combos to the timeHandler

    }
    /**
     * Parsing for handling combos (NOT IMPLEMENTED CORRECTLY)
     * @param q
     * @return
     *
     */
    public Map<Integer, List<String>> parse(List<KeyInputEvent> q) throws Exception {
        var output = handler.inputHandler(q);

        return output;
    }

    public void resetKeys(){
        handler.setUpMapping();
    }

}
