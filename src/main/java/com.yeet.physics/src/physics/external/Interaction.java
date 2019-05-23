package physics.external;

import java.util.List;

public abstract class Interaction {

    private List<PhysicsObject> bodies;

    public Interaction(List<PhysicsObject> bodies){
        this.bodies = bodies;
    }


}
