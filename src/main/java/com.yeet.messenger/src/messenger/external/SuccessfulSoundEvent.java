package messenger.external;

public class SuccessfulSoundEvent extends SuccessfulEvent{

    public SuccessfulSoundEvent(int id){
        super(id);
    }

    @Override
    public String getName() {
        return String.format("Sound successfully played");
    }
}
