package messenger.external;

import java.awt.geom.Point2D;

import java.util.Map;

public class PositionsUpdateEvent extends Event {

    private Map<Integer, Point2D> map;
    private Map<Integer, Double> directions;

    public PositionsUpdateEvent(Map<Integer, Point2D> map, Map<Integer, Double> directions){
        this.map = map;
        this.directions = directions;
    }

    public Map<Integer, Point2D> getPositions(){
        return this.map;
    }

    public Map<Integer, Double> getDirections(){
        return this.directions;
    }

    @Override
    public String getName() {
        return "PositionsUpdateEvent";
    }
}
