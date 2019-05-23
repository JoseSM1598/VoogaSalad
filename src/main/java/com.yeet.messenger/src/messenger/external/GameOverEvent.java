package messenger.external;

import java.util.ArrayList;

public class GameOverEvent extends Event{

    private int winnerID;
    private ArrayList<Integer> rankList;

    public GameOverEvent(int winnerID, ArrayList<Integer> rankList){
        super();
        this.winnerID = winnerID;
        this.rankList = rankList;
    }

    @Override
    public String getName() {
        return "Game Over Event: Player "+winnerID+" wins!";
    }

    public int getWinnerID(){
        return winnerID;
    }

    public ArrayList<Integer> getRankList(){
        return rankList;
    }
}
