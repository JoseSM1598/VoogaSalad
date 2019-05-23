package physics.external;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collision between two physicsObjects
 *
 * @author skm44
 * @author jrf36
 */

public class Collision extends Interaction {

    private List<PhysicsObject> colliders;
    private Side side;

    public Collision(List<PhysicsObject> colliders, Side s){
        super(colliders);
        this.side = s;
        this.colliders = colliders;
    }

    public PhysicsObject getCollider1(){
        if(this.colliders.size()>= 1){
            return this.colliders.get(0);
        }
        return null;
    }

    public PhysicsObject getCollider2() {
        if (this.colliders.size() >= 2) {
            return this.colliders.get(1);
        }
        return null;
    }

    public List<PhysicsObject> getColliders(){
        return this.colliders;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }
}
