package messenger.external;

/*
    A state machine that determines whether a inputted combat action event is
    valid or not.
    @author xp19
 */

public enum PlayerState {

    /* the default player state when the player is not taking any action */
    INITIAL{
        @Override
        public PlayerState changeStatesOnEvent(CombatActionEvent event){
            // when player is in initial state, all action he takes will be successful
            event.onSuccess();
            return event.getInputPlayerState();
        }
    },

    /* the state when the player is moving in the left or right direction */
    MOVING{
        @Override
        public PlayerState changeStatesOnEvent(CombatActionEvent event){
            // when player is moving, all action he takes will be successful
            event.onSuccess();
            return event.getInputPlayerState();
        }
    },

    /* the state when the player is during attack animation */
    ATTACKING{
        @Override
        public PlayerState changeStatesOnEvent(CombatActionEvent event){
            event.onSuccess();
            return event.getInputPlayerState();
        }
    },

    /* the state when the player presses jump button once and still in the air */
    SINGLE_JUMP{
        @Override
        public PlayerState changeStatesOnEvent(CombatActionEvent event){
            event.onSuccess();
            switch (event.getInputPlayerState()){
                case SINGLE_JUMP:
                    return DOUBLE_JUMP;
            }
            return SINGLE_JUMP;
        }
    },

    /* the state when the player presses jump button two times consecutively to
    complete a double jump and still in the air */
    DOUBLE_JUMP{
        @Override
        public PlayerState changeStatesOnEvent(CombatActionEvent event){
            switch (event.getInputPlayerState()){
                // player fails to jump if he already completes a double jump
                case SINGLE_JUMP: case DOUBLE_JUMP:
                    event.onFailure();
                    return DOUBLE_JUMP;
            }
            event.onSuccess();
            return DOUBLE_JUMP;
        }
    },

    /* the state when the player is being attacked by another player */
    BEING_ATTACKED{
        @Override
        public PlayerState changeStatesOnEvent(CombatActionEvent event){
            /* all combat actions (jump, move, attack) the player takes while he is
                being attacked is invalid except when the event resets player's state
                to INITIAL */
            switch (event.getInputPlayerState()){
                case INITIAL: return INITIAL;
            }
            return BEING_ATTACKED;
        }
    },

    CROUCH{
        @Override
        public PlayerState changeStatesOnEvent(CombatActionEvent event) {
            event.onSuccess();
            return event.getInputPlayerState();
        }
    };


    public abstract PlayerState changeStatesOnEvent(CombatActionEvent event);

}
