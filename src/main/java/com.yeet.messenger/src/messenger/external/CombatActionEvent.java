package messenger.external;

import com.google.common.eventbus.EventBus;

/*
    Represent specific combat action such as jump, attack or move a player can do in the game
    @author xp19
 */

public abstract class CombatActionEvent extends Event {

    protected int initiatorID;// the player's ID who initiates this combat action
    protected PlayerState inputPlayerState;
    protected static EventBus eventBus = EventBusFactory.getEventBus();

    protected CombatActionEvent(int initiatorID, PlayerState playerState){
        this.initiatorID = initiatorID;
        this.inputPlayerState = playerState;
    }

    public int getInitiatorID(){
        return initiatorID;
    }

    public PlayerState getInputPlayerState(){
        return inputPlayerState;
    }

    // called when this combat action event is successful
    public abstract void onSuccess();
    // called when this combat action event fails
    public abstract void onFailure();

    @Override
    public String getName() {
        return null;
    }
}
