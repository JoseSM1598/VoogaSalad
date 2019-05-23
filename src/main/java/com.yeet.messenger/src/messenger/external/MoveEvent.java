package messenger.external;

public class MoveEvent extends CombatActionEvent{

    // indicate the direction in which the player move
    boolean isGoingLeft;

    public MoveEvent(int initiatorID, boolean isGoingLeft) {
        super(initiatorID, PlayerState.MOVING);
        this.isGoingLeft = isGoingLeft;
    }

    public boolean getDirection(){
        return isGoingLeft;
    }

    @Override
    public void onSuccess() {
        eventBus.post(new MoveSuccessfulEvent(initiatorID, isGoingLeft));
    }

    @Override
    public void onFailure() {

    }

    @Override
    public String getName(){
        return "Move Event: Player "+initiatorID+" attempts to move";
    }
}
