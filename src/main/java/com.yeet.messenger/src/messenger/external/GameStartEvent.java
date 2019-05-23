package messenger.external;

import java.util.List;

public class GameStartEvent extends Event{

    private String myGameType;
    private Integer myTypeValue;
    private List<Integer> botList;

    public GameStartEvent(String gameType, Integer typeValue, List<Integer> botList){
        myGameType = gameType;
        myTypeValue = typeValue;
        this.botList = botList;
    }

    @Override
    public String getName() {
        return("Game Start Event");
    }

    /** Returns the ID of the bot players */
    public List<Integer> getBots(){
        return botList;
    }

    /** Returns the game type*/
    public String getGameType(){
        return myGameType;
    }

    /** Returns the value associated with the game type */
    public Integer getTypeValue(){
        return myTypeValue;
    }
}
