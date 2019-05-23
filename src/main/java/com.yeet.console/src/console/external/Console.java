package console.external;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import messenger.external.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

public class Console {

    private EventBus myEventBus;

    public Console(){
        myEventBus = EventBusFactory.getEventBus();
        startConsole();
    }

    /** Creates a separate {@code Thread} that waits for console input, and exits upon the 'quit' command*/
    private void startConsole() {
        Runnable runner = () -> {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String line = "";
            while (!line.equalsIgnoreCase("quit")) {
                try {
                    line = in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                createEvent(line);
            }

            try {
                in.close();
                System.out.println("Console shutting down.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Thread T = new Thread(runner);
        T.start();
    }

    /** Converts user console input to an actual {@code Event} which can be posted to the {@code EventBus}*/
    private void createEvent(String event){
        if (event.equalsIgnoreCase("test")) {
            myEventBus.post(new TestSuccesfulEvent());
        }
        else if(event.matches("gameover [0-3] \\([0-3]{2,4}\\)")){
            int winnerID = Integer.parseInt(event.substring(9,10));
//            LinkedList<Integer> rankList = new LinkedList<>();
            String[] rawRankList = event.split("[()]")[1].split("");
            ArrayList<Integer> rankList = new ArrayList<>();
            for(String s : rawRankList){
                rankList.add(Integer.parseInt(s));
            }
            myEventBus.post(new GameOverEvent(winnerID,rankList));
        }
        else if(event.matches("sethp [0-3] [0-9][0-9]")){
            HashMap<Integer,Double> healthmap = new HashMap<>();
            healthmap.put(Integer.parseInt(event.split(" ")[1]), Double.parseDouble(event.split(" ")[2]));
            myEventBus.post(new GetRektEvent(healthmap));
        }
        else if(event.matches("kill [0-3] [0-9]+")){
            int playerID = Integer.parseInt(event.substring(5,6));
            int livesLeft = Integer.parseInt(event.substring(7));
            myEventBus.post(new PlayerDeathEvent(playerID,livesLeft));
        }
    }


    /** Prints any {@code Event} sent through the {@code EventBus} to the console for debugging purposes.*/
    @Subscribe
    public  void printEvent(Event event){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if(!"PositionsUpdateEvent".equals(event.getName()) && !"GroundIntersectingEvent".equalsIgnoreCase(event.getName())){
            System.out.println("["+timestamp+"]"+" Console: "+event.getName());
        }
    }
}
