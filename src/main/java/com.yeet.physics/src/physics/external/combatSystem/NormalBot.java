package physics.external.combatSystem;

import messenger.external.*;
import physics.external.PhysicsSystem;

import java.awt.geom.Point2D;
import java.util.*;

/*
    An implementation of AI that is more intelligent than the dummy bot.
    Will search for the closest player and move towards him to attack.
 */

public class NormalBot extends Bot {
    protected PhysicsSystem physicsSystem;
    protected static final double DETECTION_RADIUS = 20.0;

    Double[][] matrix = {{0.1,0.1,0.7,0.1},
                         {0.1,0.1,0.8,0.0},
                         {0.2,0.3,0.5,0.0},
                         {0.05,0.05,0.9,0.0}};

    public NormalBot(PhysicsSystem physicsSystem){
        super();
        setTransitionMatrix(matrix);
        setPlayerState(PlayerState.MOVING);
        this.physicsSystem = physicsSystem;
    }

    @Override
    public void step() {
        //update positions
        Map<Integer, Point2D> positions = physicsSystem.getPositionsMap();
        playerGraph.updatePositionMap(positions);

        if(!playerGraph.hasEnemyNearBy(this.id, DETECTION_RADIUS)){
            Point2D targetPos = playerGraph.getNearestNeighbor(this.id);
            Player target = playerGraph.findPlayerByPosition(targetPos);
            moveCloserToTarget(target, positions);
            System.out.println("Move Closer.");
        }
        else{
            this.setPlayerState(PlayerState.ATTACKING);
            transition();
        }
    }

    @Override
    public void transition() {
        PlayerState currentState = this.getPlayerState();
        int row = map.get(currentState);
        PlayerState nextState = getNextState(matrix[row]);
        takeActionBasedOnNextState(nextState);
    }

    protected void moveCloserToTarget(Player target, Map<Integer, Point2D> positions){
        Point2D selfPos = positions.get(this.id);
        Point2D targetPos = positions.get(target.id);
        System.out.println(selfPos);
        System.out.println(targetPos);

        // first decrease the horizontal distance difference
        double dx = targetPos.getX() - selfPos.getX();
        if(Math.abs(dx)>=DETECTION_RADIUS){
            // target is on the left of the bot
            if(dx<0) eventBus.post(new MoveEvent(this.id, true));
            // target is on the right of the bot
            else eventBus.post(new MoveEvent(this.id, false));
        }
        // then decrease the vertical distance difference
        else{
            double dy = targetPos.getY() - selfPos.getY();
            if(dy!=0){
                // target is at a higher elevation than the bot
                if(dy<0) eventBus.post(new JumpEvent(this.id));
            }
        }
    }

}
