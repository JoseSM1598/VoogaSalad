package messenger.external;

import java.util.Map;

public class GetRektEvent extends Event{

    Map<Integer, Double> peopleBeingRekt;

    public GetRektEvent(Map<Integer, Double> peopleBeingRekt){
        this.peopleBeingRekt = peopleBeingRekt;
    }

    public Map<Integer, Double> getPeopleBeingRekt(){
        return peopleBeingRekt;
    }

    @Override
    public String getName() {
        return peopleBeingRekt + " are being rekt.";
    }
}
