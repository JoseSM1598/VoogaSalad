package combatSystem.internal;

import messenger.external.CombatActionEvent;
import messenger.external.PlayerState;


import java.util.List;

/*
    Represents the character a specific player controls. Responsible for keeping track of
    the character's stats and states
    @author xp19
 */

public class Player {

    private int id;
    private String name;
    private int health;
    private int numOfLives;
    private int powerLevel;
    private int attackDamage;
    private float attackSpeed;
    private List<Player> isAttackingTargets;
    private List<List<Integer>> hitboxes;
    private Player beingAttackedBy;
    private PlayerState playerState;

    public Player(){
        playerState = PlayerState.INITIAL;
    }

    public Player(int id){
        this();
        this.id = id;
    }

    public PlayerState getPlayerState(){
        return playerState;
    }

    /* update the current player's state based on the event passed in */
    public void changePlayerStateOnEvent(CombatActionEvent event){
        playerState = playerState.changeStatesOnEvent(event);
    }

    /*  */
    public void landOnGround(){

    }

    /* add who is being attacked by this player */
    public void addAttackingTargets(Player target){
        isAttackingTargets.add(target);
        target.setBeingAttackedBy(this);
    }

    /* set who is attacking this player */
    private void setBeingAttackedBy(Player attacker){
        this.beingAttackedBy = attacker;
    }

    @Override
    public String toString(){
        return String.format("Player %d named %s", id, name);
    }

}
