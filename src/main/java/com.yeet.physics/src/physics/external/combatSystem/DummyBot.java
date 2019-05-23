package physics.external.combatSystem;

import messenger.external.*;

/*
    A bot implementation that will perform action completely randomly
    @author xp19
 */

public class DummyBot extends Bot{

    Double[][] matrix = {{0.25, 0.25, 0.25, 0.25},
                         {0.25, 0.25, 0.25, 0.25},
                         {0.25, 0.25, 0.25, 0.25},
                         {0.25, 0.25, 0.25, 0.25}};

    public DummyBot(){
        super();
        setTransitionMatrix(matrix);
        setPlayerState(PlayerState.MOVING);
    }

    @Override
    public void step() {
        transition();
    }

    @Override
    public void transition() {
        PlayerState currentState = this.getPlayerState();
        int row = map.get(currentState);
        PlayerState nextState = getNextState(matrix[row]);
        takeActionBasedOnNextState(nextState);
    }

}
