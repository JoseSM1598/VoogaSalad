package messenger.external;

import java.util.List;

public class AttackIntersectEvent extends Event {

    private List<List<Integer>> playersIntersecting;

    public AttackIntersectEvent(List<List<Integer>> players){
        this.playersIntersecting = players;
    }

    public List<List<Integer>> getAttackPlayers(){
        return this.playersIntersecting;
    }

    @Override
    public String getName() {
        return "AttackIntersectEvent";
    }
}
