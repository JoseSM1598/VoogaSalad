package messenger.external;

public class AttackSuccessfulEvent extends SuccessfulEvent{

    String type;

    public AttackSuccessfulEvent(int initiatorID, String type){
        super(initiatorID);
        this.type = type;
    }

    public String getType(){
        return type;
    }

    @Override
    public String getName() {
        return String.format("Attack Successful Event: Player with id(%d) attacks successfully", initiatorID);
    }
}
