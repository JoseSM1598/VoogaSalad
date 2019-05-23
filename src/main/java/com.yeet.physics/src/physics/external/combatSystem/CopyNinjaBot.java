package physics.external.combatSystem;

import com.google.common.eventbus.Subscribe;
import messenger.external.*;
import physics.external.PhysicsSystem;

import java.awt.geom.Point2D;
import java.util.Map;

/*
    An interesting variation of bots implementation made for fun. This bot will copy
    the actions performed by the player closest to it. For movement event, it will
    reverse the direction.
    @author xp19
 */

public class CopyNinjaBot extends Bot {

    Player target;
    PhysicsSystem physicsSystem;

    public CopyNinjaBot(PhysicsSystem physicsSystem){
        super();
        this.physicsSystem = physicsSystem;
    }

    @Override
    public void step() {
        //update positions
        Map<Integer, Point2D> positions = physicsSystem.getPositionsMap();
        playerGraph.updatePositionMap(positions);

        Point2D targetPos = playerGraph.getNearestNeighbor(this.id);
        target = playerGraph.findPlayerByPosition(targetPos);
    }

    @Override
    protected void transition() {

    }

    @Subscribe
    public void copyPlayerMoves(CombatActionEvent combatActionEvent){
        if(target==null) return;
        if(combatActionEvent.getInitiatorID()==this.id) return;

        if(combatActionEvent.getInitiatorID()==target.id){
            switch (combatActionEvent.getInputPlayerState()){
                case MOVING:
                    boolean direction = ((MoveEvent) combatActionEvent).getDirection();
                    eventBus.post(new MoveEvent(this.id, !direction));
                    break;
                case SINGLE_JUMP:
                    eventBus.post(new JumpEvent(this.id));
                    break;
                case ATTACKING:
                    eventBus.post(new AttackEvent(this.id, AttackEvent.WEAK_TYPE));
                    break;
                case CROUCH:
                    eventBus.post(new CrouchEvent(this.id));
                    break;
            }
        }

    }

}
