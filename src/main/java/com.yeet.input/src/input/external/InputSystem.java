package input.external;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import input.Internal.Parser;
import messenger.external.*;

import java.io.File;
import java.util.*;


/**
 * I chose this class because it is, in my opinion, the better of the two systems I wrote. It has a clear and streamlined way of not only setting
 * up the Input and Combo data structures, but also retrivieing the correct attack from the correct player upon recieving an InputKeyEvent.
 *
 */

public class InputSystem {

    private EventBus myMessageBus;
    private Parser myParser;
    private List<KeyInputEvent> commandHolder;
    private Timer timer;
    private File GameDir;


    public InputSystem(File GameDirectory) {
        try {
            myMessageBus = EventBusFactory.getEventBus();
            commandHolder = new LinkedList<>();
            timer = new Timer();
            GameDir = GameDirectory;
            myParser = new Parser(GameDir);
            setUpTimer();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error has occurred in the input system");
        }
    }

    private void setUpTimer() throws Exception{
        //Set the schedule function and rate
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //Called each time when 1000 milliseconds (1 second) (the period parameter)
                if(commandHolder.size() >0){
                    try {
                        var inputs = myParser.parse(commandHolder); // Should return  mapping of player number to commands
                        for (Map.Entry<Integer, List<String>> pair : inputs.entrySet()) {
                            System.out.println(pair.getKey()+ " " +pair.getValue());
                            if(pair.getValue().isEmpty()){
                                myMessageBus.post(new IdleEvent(pair.getKey()));
                            }
                            postEvent(pair.getValue(), pair.getKey());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    commandHolder.clear();
                }
            }
            },
                //Set how long before to start calling the TimerTask (in milliseconds)
                0,
                //Set the amount of time between each execution (in milliseconds)
                250);
    }


    /**
     My method for sending out information regarding inputs. I'll have to create
     a class that represents an attack input
     */
    //TODO: Use reflection on this!
    public void postEvent(List<String> s, int player){
        //TRUE if left, FALSE if right
        for(String action : s){
            //System.out.println(player);
            CombatActionEvent keyEvent;
            if(action.equals("LEFT")){
                keyEvent = new MoveEvent(player, true);
            }
            else if(action.equals("RIGHT")){
                keyEvent = new MoveEvent(player, false);
            }
            else if (action.equals("JUMP")){
                keyEvent =  new JumpEvent(player);
            }
            else if (action.equals("DOWN")){
                keyEvent = new CrouchEvent(player);
            }
            else{
                keyEvent = new AttackEvent(player, action);
            }
            myMessageBus.post(keyEvent);

        }

    }
    /**
     / The other subcribe event that listens for Key Inputs.
     / DISCLAIMER: Listen for KeyInputEvent for now ONLY
     */
    @Subscribe
    public void getKey(KeyInputEvent inputEvent){
        //System.out.println(inputEvent.getName());
        commandHolder.add(inputEvent);
    }


    /**
     / Listens for game over. Tells this system to stop listening for inputs
     */
    @Subscribe
    public void stopListening(GameOverEvent gameOver){
        timer.cancel();
        timer.purge();
    }

    public void resetBindings(){
        myParser.resetKeys();
    }

}
