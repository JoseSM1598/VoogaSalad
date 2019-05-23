package messenger.external;

import java.util.List;

public class GroundIntersectEvent extends Event {

    private List<Integer> playersIntersecting;

    public GroundIntersectEvent(List<Integer> players){
        this.playersIntersecting = players;
    }

    public List<Integer> getGroundedPlayers(){
        return this.playersIntersecting;
    }

    @Override
    public String getName() {
        return "GroundIntersectingEvent";
    }
}
