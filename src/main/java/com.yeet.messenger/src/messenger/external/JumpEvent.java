package messenger.external;

/*
    Represents a
 */

public class JumpEvent extends CombatActionEvent {

    public JumpEvent(int initiatorID) {
        super(initiatorID, PlayerState.SINGLE_JUMP);
    }

    /* called if this jump event is successful, will post a JumpSuccessfulEvent
     through the event bus for physics engine to handle */
    @Override
    public void onSuccess() {
        eventBus.post(new JumpSuccessfulEvent(initiatorID));
        System.out.println(getName());
    }

    @Override
    public void onFailure() {
        System.out.println(String.format("Player with id(%d) fails to jump.", initiatorID));
    }

    @Override
    public String getName() {
        return String.format("Player with id(%d) attempts to jump.", initiatorID);
    }
}
