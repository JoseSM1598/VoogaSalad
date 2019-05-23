package physics.external;

/**
 * Represents the specific sides that are intersecting during a collision
 *
 * @author skm44
 * @author jrf36
 */

public class Intersection {


    private Side intersector1;
    private Side intersector2;

    public Intersection(Side inter1) {
        this.intersector1 = inter1;
    }

    public Side getIntersector2() {
        return intersector2;
    }

    public void setIntersector2(Side intersector2) {
        this.intersector2 = intersector2;
    }

    public Side getIntersector() {
        return intersector1;
    }

    public void setIntersector1(Side intersector1) {
        this.intersector1 = intersector1;
    }
}
