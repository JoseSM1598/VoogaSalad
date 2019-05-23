package physics.external.combatSystem;

import java.awt.geom.Point2D;
import java.util.*;

/*
    Primarily designed for AI implementation.
    Perform any necessary calculations that is needed by bots to make informed
    decisions about what step to take next. (eg. who is the nearest player? is there
    any opponent nearby)
    @author xp19
 */

public class PlayerGraph {

    Map<Player, Set<Player>> graph;
    private PlayerManager playerManager;
    private Map<Integer, Point2D> positionMap;

    public PlayerGraph(PlayerManager playerManager, Map<Integer, Point2D> positionMap){
        this.playerManager = playerManager;
        this.positionMap = positionMap;
        // build the graph
        graph =  new HashMap<>();
        for(int i = 0; i < playerManager.numOfPlayers; i++){
            graph.putIfAbsent(playerManager.getPlayerByID(i), new HashSet<>());
            // add neighbours
            for(int j = 0; j < playerManager.numOfPlayers; j++){
                // self is not added to the list of neighbors
                if(j!=i){
                    graph.get(playerManager.getPlayerByID(i)).add(playerManager.getPlayerByID(j));
                }
            }
        }
    }

    public boolean hasEnemyNearBy(int id, double distance){
        Point2D closest = getNearestNeighbor(id);
        Player self = playerManager.getPlayerByID(id);
        Point2D selfPos = positionMap.get(self.id);
        return selfPos.distance(closest) <= distance;
    }

    public void updatePositionMap(Map<Integer, Point2D> positionMap){
        this.positionMap = positionMap;
    }

    public Point2D getNearestNeighbor(int id){
        Player self = playerManager.getPlayerByID(id);
        Point2D selfPos = positionMap.get(self.id);
        // order points in ascending order of distance from selfPos
        Queue<Point2D> queue = new PriorityQueue<>(Comparator.comparing(selfPos::distance));

        for(Player adj: graph.get(self)){
            queue.offer(positionMap.get(adj.id));
        }

        // get the closest neighbor
        Point2D closest = queue.peek();
        return closest;
    }

    /* true if target is on the left of self */
    public boolean getFacingDirection(int self, int target){
        Point2D selfPos = positionMap.get(self);
        Point2D targetPos = positionMap.get(target);
        return targetPos.getX()<selfPos.getX();
    }

    public Player findPlayerByPosition(Point2D pos){
        for(int i: positionMap.keySet()){
            if(positionMap.get(i).equals(pos)){
                return playerManager.getPlayerByID(i);
            }
        }
        return playerManager.getPlayerByID(0);
    }


}
