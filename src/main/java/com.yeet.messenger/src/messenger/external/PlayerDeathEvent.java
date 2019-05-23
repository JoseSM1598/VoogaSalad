package messenger.external;

public class PlayerDeathEvent extends Event {

    private int id;
    private int remainingLife;

    public PlayerDeathEvent(int id, int remainingLife){
        this.id = id;
        this.remainingLife = remainingLife;
    }

    public int getId(){
        return id;
    }

    public int getRemainingLife(){
        return remainingLife;
    }

    @Override
    public String getName() {
        return null;
    }
}
