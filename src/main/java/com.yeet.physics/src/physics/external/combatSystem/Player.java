package physics.external.combatSystem;

import messenger.external.CombatActionEvent;
import messenger.external.PlayerState;

import java.util.ArrayList;
import java.util.List;

/*
    Represents the character a specific player controls. Responsible for keeping track of
    the character's stats and states
    @author xp19
 */

public class Player {

    protected int id;
    private String name;
    private double health = 100.0;
    private int numOfLives;
    private int powerLevel;
    private int score;
    private double attackDamage = 10.0;
    private double defense = 0.0;
    private List<Player> isAttackingTargets;
    private List<List<Integer>> hitboxes;
    private Player beingAttackedBy;
    private PlayerState playerState;
    private boolean isBot;
    private boolean isAlive = true;

    public Player(){
        playerState = PlayerState.INITIAL;
        isAttackingTargets = new ArrayList<>();
    }

    public Player(int id){
        this();
        this.id = id;
    }

    public Player(int id, double attackDamage, double defense, double health){
        this();
        this.id = id;
        this.attackDamage = attackDamage;
        this.defense = defense;
        this.health = health;
    }

    public PlayerState getPlayerState(){
        return playerState;
    }

    /* update the current player's state based on the event passed in */
    public void changePlayerStateOnEvent(CombatActionEvent event){
        playerState = playerState.changeStatesOnEvent(event);
    }

    /* reset player's state to default state */
    public void setToInitialState(){
        this.playerState = PlayerState.INITIAL;
    }

    /* set player's current state */
    protected void setPlayerState(PlayerState playerState){
        this.playerState = playerState;
    }

    /* add who is being attacked by this player */
    public void addAttackingTargets(Player target){
        isAttackingTargets.add(target);
        this.playerState = PlayerState.ATTACKING;
        target.setBeingAttackedBy(this);
        target.playerState = PlayerState.BEING_ATTACKED;
        target.freeTargets();
    }

    public double reduceHealth(double amt){
        this.health -= amt;
        return this.health;
    }

    public double getHealth(){
        return health;
    }

    public int getNumOfLives(){
        return numOfLives;
    }

    public int loseLife(){
        this.numOfLives -= 1;
        if(numOfLives<=0) isAlive = false;
        return this.numOfLives;
    }

    public void setNumOfLives(int numOfLives){
        this.numOfLives = numOfLives;
    }

    public double getAttackDamage(){
        return this.attackDamage;
    }

    public void setIsBot(boolean isBot){
        this.isBot = isBot;
    }

    /* set who is attacking this player */
    private void setBeingAttackedBy(Player attacker){
        this.beingAttackedBy = attacker;
    }

    private void freeTargets(){
        for(Player p: isAttackingTargets){
            p.setToInitialState();
        }
    }

    public void resetCombatStats(ArrayList<Double> stats){
        this.attackDamage = stats.get(0);
        this.defense = stats.get(1);
        this.health = stats.get(2);
    }

    public boolean isBot(){
        return isBot;
    }

    public boolean isAlive(){
        return isAlive;
    }

    @Override
    public String toString(){
        return String.format("Player %d named %s", id, name);
    }

}
