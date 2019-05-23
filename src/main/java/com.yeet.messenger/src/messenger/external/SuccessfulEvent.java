package messenger.external;

import messenger.external.Event;

public abstract class SuccessfulEvent extends Event {

    int initiatorID;

    protected SuccessfulEvent(int id){
        this.initiatorID = id;
    }

    public int getInitiatorID(){
        return initiatorID;
    }

}
