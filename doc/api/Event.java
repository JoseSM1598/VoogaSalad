/** Interface for anything that will be sent through the {@code EventBus}*/
public interface Event {

    /** Return the name of the event*/
    public String getName();
}
