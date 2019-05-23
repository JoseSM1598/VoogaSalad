package messenger.external;

public class IdleEvent extends Event {
    private int id;
    public IdleEvent(int id){
        this.id = id;
    }
    @Override
    public String getName() {
        return null;
    }

    public int getId(){
        return this.id;
    }
}
