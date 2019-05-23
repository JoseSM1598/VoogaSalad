package physics.external.combatSystem;
import com.google.common.eventbus.EventBus;
import messenger.external.*;

import java.util.*;

/*
    Base class for AI implementation using Markov chain.
    @author xp19
 */

public abstract class Bot extends Player{

    protected PlayerState[] states = {PlayerState.MOVING, PlayerState.SINGLE_JUMP, PlayerState.ATTACKING, PlayerState.CROUCH};
    protected static Map<PlayerState, Integer> map;
    protected static EventBus eventBus = EventBusFactory.getEventBus();
    protected final int NUM_OF_STATES = states.length;
    Double[][] transitionMatrix;
    PlayerGraph playerGraph;
    Timer timer;

    protected Bot(){
        setIsBot(true);
    }

    static{
        map = new HashMap<>();
        map.put(PlayerState.MOVING, 0);
        map.put(PlayerState.SINGLE_JUMP, 1);
        map.put(PlayerState.ATTACKING, 2);
        map.put(PlayerState.CROUCH, 3);
    }

    /* set the transition matrix of a markov chain */
    protected void setTransitionMatrix(Double[][] transitionMatrix){
        if(transitionMatrix.length!=transitionMatrix[0].length){
            throw new IllegalArgumentException("Not a square matrix");
        }
        else if(transitionMatrix.length!=NUM_OF_STATES){
            throw new IllegalArgumentException(String.format("Wrong matrix size. Expected: %d Actual: %d", NUM_OF_STATES, transitionMatrix.length));
        }
        this.transitionMatrix = transitionMatrix;
    }

    public void setPlayerGraph(PlayerGraph playerGraph){
        this.playerGraph = playerGraph;
    }


    /* implement this method to define the rule of transitioning
        from initial state to a new state */
    public abstract void step();
    protected abstract void transition();
    /* determine what the next state is based on a probability distribution */
    protected PlayerState getNextState(Double[] distribution) {
        Random random = new Random();
        double prob = random.nextDouble();
        double cumulated = 0.0;
        int stateIndex = NUM_OF_STATES;
        for(int i = 0; i < distribution.length; i++){
            cumulated += distribution[i];
            if(prob<=cumulated){
                stateIndex = i;
                break;
            }
        }
        return states[stateIndex];
    }

    /* start the bot's action */
    public void start(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                step();
            }
        }, 1000, 100);
    }

    public void stop(){
        timer.cancel();
    }

    protected void takeActionBasedOnNextState(PlayerState nextState){
        switch (nextState){
            case MOVING:
                moveRandomly();
                break;
            case SINGLE_JUMP:
                eventBus.post(new JumpEvent(id));
                break;
            case ATTACKING:
                eventBus.post(new AttackEvent(id, AttackEvent.WEAK_TYPE));
                break;
            case CROUCH:
                eventBus.post(new CrouchEvent(id));
                break;
        }
    }

    /* move the bot in either direction by equal chance */
    protected void moveRandomly(){
        if(Math.random() < 0.5) {
            eventBus.post(new MoveEvent(id, true));
        }
        else{
            eventBus.post(new MoveEvent(id, false));
        }
    }


}
