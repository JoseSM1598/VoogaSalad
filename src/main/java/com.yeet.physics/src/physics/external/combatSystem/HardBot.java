package physics.external.combatSystem;

import com.google.common.eventbus.Subscribe;
import messenger.external.*;
import physics.external.PhysicsSystem;

import java.awt.geom.Point2D;
import java.util.Map;

/*
    A more intelligent version of bot than normal bot. It will perform action based on
    the action other players take and change its play style based on its health condition
    @author xp19
 */

public class HardBot extends NormalBot {

    Player target;
    boolean inRange = false;

    Double[][] defensive = {{0.8, 0.19, 0.01, 0.0},
                            {0.49, 0.5, 0.01, 0.0},
                            {0.9, 0.09, 0.01, 0.0},
                            {0.9, 0.09, 0.01, 0.0}};

    public HardBot(PhysicsSystem physicsSystem){
        super(physicsSystem);
        eventBus.register(this);
    }

    @Override
    public void step() {
        Map<Integer, Point2D> positions = physicsSystem.getPositionsMap();
        playerGraph.updatePositionMap(positions);

        Point2D targetPos = playerGraph.getNearestNeighbor(this.id);
        target = playerGraph.findPlayerByPosition(targetPos);


        double healthDiff = target.getHealth()-this.getHealth();
        /* if the bot is low on health compared to its target,
            switch to defensive mode */
        if(healthDiff>=50.0){
            setTransitionMatrix(defensive);
        }
        else{
            setTransitionMatrix(matrix);
        }

        if(!playerGraph.hasEnemyNearBy(this.id, DETECTION_RADIUS)){
            inRange = false;
            moveCloserToTarget(target, positions);
        }
        else{
            inRange = true;
            this.setPlayerState(PlayerState.ATTACKING);
            transition();
        }
    }

    @Override
    public void transition() {
        PlayerState currentState = this.getPlayerState();
        int row = map.get(currentState);
        PlayerState nextState = getNextState(matrix[row]);
        switch (nextState){
            case MOVING:
                // move away from target
                boolean direction = playerGraph.getFacingDirection(this.id, target.id);
                eventBus.post(new MoveEvent(this.id, !direction));
                break;
            case SINGLE_JUMP:
                eventBus.post(new JumpEvent(this.id));
                break;
            case ATTACKING:
                eventBus.post(new AttackEvent(this.id, AttackEvent.WEAK_TYPE));
                break;
            case CROUCH:

        }
    }

    @Subscribe
    public void onPlayerMovement(CombatActionEvent combatActionEvent){
        if(target==null) return;
        if(combatActionEvent.getInitiatorID()==this.id) return;
        if(combatActionEvent.getInitiatorID()==target.id){
            switch (combatActionEvent.getInputPlayerState()){
                case MOVING:
                    if(inRange) eventBus.post(new AttackEvent(this.id, AttackEvent.WEAK_TYPE));
                    break;
                case ATTACKING:
                    if(inRange){
                        // determine the dodge direction
                        boolean direction = playerGraph.getFacingDirection(this.id, target.id);
                        // dodge the opponent's attack
                        eventBus.post(new MoveEvent(this.id, direction));
                    }
                    break;
            }
        }
    }


}
