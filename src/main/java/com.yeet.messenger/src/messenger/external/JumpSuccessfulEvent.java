package messenger.external;

public class JumpSuccessfulEvent extends SuccessfulEvent {

    public JumpSuccessfulEvent(int id){
        super(id);
    }

    @Override
    public String getName() {
        return String.format("Player with id(%d) jumps successfully", initiatorID);
    }
}
