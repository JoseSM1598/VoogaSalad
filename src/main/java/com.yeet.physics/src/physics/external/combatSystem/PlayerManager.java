package physics.external.combatSystem;

import com.google.common.eventbus.EventBus;
import messenger.external.CombatActionEvent;
import messenger.external.EventBusFactory;
import messenger.external.GameOverEvent;
import messenger.external.PlayerDeathEvent;
import physics.external.PhysicsSystem;

import java.util.*;

/*
    Responsible for any interaction with player-related data
    @author xp19
 */

public class PlayerManager {
    private static final int INITIAL_ID = 0;
    EventBus eventBus = EventBusFactory.getEventBus();
    Map<Integer, Player> playerMap;
    int numOfPlayers;
    int numOfAlivePlayers;
    Queue<Integer> ranking;
    int winnerID;

    public PlayerManager(int numOfPlayers){
        playerMap = new HashMap<>();
        ranking = new LinkedList<>();
        this.numOfPlayers = numOfPlayers;
        this.numOfAlivePlayers = numOfPlayers;
        for(int id = INITIAL_ID; id < numOfPlayers; id++){
            playerMap.put(id, new Player(id));
        }
    }

    public PlayerManager(int numOfPlayers, Map<Integer, ArrayList<Double>> stats){
        playerMap = new HashMap<>();
        ranking = new LinkedList<>();
        this.numOfPlayers = numOfPlayers;
        this.numOfAlivePlayers = numOfPlayers;
        for(int id = INITIAL_ID; id < numOfPlayers; id++){
            ArrayList<Double> stat = stats.get(id);
            playerMap.put(id, new Player(id, stat.get(0), stat.get(1), stat.get(2)));
        }
    }


    public Player getPlayerByID(int id){
        if(id >= INITIAL_ID + numOfPlayers)
            throw new RuntimeException(String.format("ID %d does not exist", id));
        return playerMap.get(id);
    }

    public void setToInitialStateByID(int id){
        if(id >= INITIAL_ID + numOfPlayers)
            throw new RuntimeException(String.format("ID %d does not exist", id));
        playerMap.get(id).setToInitialState();
    }

    public void changePlayerStateByIDOnEvent(int id, CombatActionEvent event){
        if(!checkIDValid(id)){
            throw new RuntimeException(String.format("ID %d does not exist", id));
        }
        playerMap.get(id).changePlayerStateOnEvent(event);
    }

    public void setBots(List<Integer> botsID, PhysicsSystem physicsSystem){
        for(int id: botsID){
//            getPlayerByID(id).setIsBot(true);
            playerMap.put(id, new HardBot(physicsSystem));
        }
    }

    public int getAlivePlayers(){
        for(int id: playerMap.keySet()){
            if(playerMap.get(id).getNumOfLives()>=1){
                return id;
            }
        }
        return 0;
    }

    public boolean hurt(int beingAttacked, int attacker){
        Player playerBeingAttacked = getPlayerByID(beingAttacked);
        Player playerAttacking = getPlayerByID(attacker);
        double health = playerBeingAttacked.reduceHealth(playerAttacking.getAttackDamage());
        if(health<=0.0){
            int remainingLife = playerBeingAttacked.loseLife();
            eventBus.post(new PlayerDeathEvent(beingAttacked, remainingLife));
            if(remainingLife<=0){
                ranking.offer(beingAttacked);
                if(--numOfAlivePlayers == 1){
                    winnerID = attacker;
                    ranking.offer(attacker);
                    return true;
                }
            }
        }
        return false;
    }

    public int outOfScreen(int id){
        int remainingLife = getPlayerByID(id).loseLife();
        if(remainingLife<=0){
            ranking.offer(id);
            if(--numOfAlivePlayers==1){
                for(int i: playerMap.keySet()){
                    if(playerMap.get(i).isAlive()){
                        winnerID = i;
                        ranking.offer(winnerID);
                        eventBus.post(new GameOverEvent(winnerID, getRanking()));
                        break;
                    }
                }
            }
        }
        return remainingLife;
    }

//    <0,3,1,2> 2 is first place
    public ArrayList<Integer> getRanking(){
        Map<Integer, Integer> map = new TreeMap<>();
        int rank = numOfPlayers;
        while(!ranking.isEmpty()){
            int id = ranking.remove();
            map.put(id, rank);
            rank--;
        }
        ranking = new LinkedList<>();
        return new ArrayList<>(map.values());
    }

    public void setNumOfLives(int numOfLives){
        for(int id: playerMap.keySet()){
            playerMap.get(id).setNumOfLives(numOfLives);
        }
    }

    public void respawnPlayer(int id, ArrayList<Double> stats){
        getPlayerByID(id).resetCombatStats(stats);
    }

    // check if the id passed in exists in the map
    private boolean checkIDValid(int id){
        return playerMap.containsKey(id);
    }

}
