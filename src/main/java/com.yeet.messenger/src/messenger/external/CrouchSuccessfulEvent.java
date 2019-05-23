package messenger.external;

public class CrouchSuccessfulEvent extends SuccessfulEvent{

    public CrouchSuccessfulEvent(int initiatorID){
        super(initiatorID);
    }

    @Override
    public String getName() {
        return String.format("Player with id(%d) crouches successfully", initiatorID);
    }
}
