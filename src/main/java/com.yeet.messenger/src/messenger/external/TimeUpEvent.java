package messenger.external;

/** {@code Event} that is sent when a match's time runs out
 *  @author bpx
 */
public class TimeUpEvent extends Event {
    @Override
    public String getName() {
        return "Time up event";
    }
}
