package replay.internal;

import java.io.Serializable;

/** Holds a {@code Replay} for serialization
 *  @author bpx
 */
public class ReplayHolder implements Serializable {

    public Replay replay;

    public ReplayHolder(Replay replay){
        this.replay =replay;
    }
}
