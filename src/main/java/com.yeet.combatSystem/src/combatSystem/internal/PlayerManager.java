package combatSystem.internal;

import messenger.external.CombatActionEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager{
    private static final int INITIAL_ID = 1;
    Map<Integer, Player> playerMap;
    int numOfPlayers;

    public PlayerManager(int numOfPlayers){
        playerMap = new HashMap<>();
        this.numOfPlayers = numOfPlayers;
        for(int id = INITIAL_ID; id <= numOfPlayers; id++){
            playerMap.put(id, new Player(id));
        }
    }

    public Player getPlayerByID(int id){
        if(id >= INITIAL_ID + numOfPlayers)
            throw new RuntimeException(String.format("ID %d does not exist", id));
        return playerMap.get(id);
    }

    public void changePlayerStateByIDOnEvent(int id, CombatActionEvent event){
        if(!checkIDValid(id)){
            throw new RuntimeException(String.format("ID %d does not exist", id));
        }
        playerMap.get(id).changePlayerStateOnEvent(event);
    }

    // check if the id passed in exists in the map
    private boolean checkIDValid(int id){
        return playerMap.containsKey(id);
    }

}
