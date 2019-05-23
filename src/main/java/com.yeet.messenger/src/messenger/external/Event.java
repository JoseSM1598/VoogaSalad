package messenger.external;

import java.io.Serializable;

/** Superclass for anything that will be sent through the {@code EventBus}*/
public abstract class Event implements Serializable {

    /** Return the name of the event*/
    public abstract String getName();
}
