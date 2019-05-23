package replay.internal;

import messenger.external.Event;

import java.io.Serializable;

public class Frame implements Serializable {
    private final Event frameEvent;
    private final long frameTime;

    public Frame(Event event, long time){
        frameEvent = event;
        frameTime = time;
    }

    public Event getEvent(){
        return frameEvent;
    }

    public long getTime(){
        return frameTime;
    }
}
