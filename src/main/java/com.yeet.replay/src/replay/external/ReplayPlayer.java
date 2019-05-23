package replay.external;

import com.google.common.eventbus.EventBus;
import messenger.external.Event;
import replay.internal.Frame;
import replay.internal.Replay;
import replay.internal.ReplayHolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/** Tool for loading serialized {@code Replay} objects and playing them back
 *  @author bpx
 */
public class ReplayPlayer {

    private EventBus myEventBus;

    private Replay loadedReplay;

    private Timer myTimer;

    private int currentFrame;
    private Event[] eventList;

    public ReplayPlayer(EventBus eventBus){
        myEventBus = eventBus;
        myEventBus.register(this);
        myTimer = new Timer();
        currentFrame = 0;
    }

    private void advanceReplay(){
        try{
            if(eventList[currentFrame]!=null){
                myEventBus.post(eventList[currentFrame]);
            }
            currentFrame++;
        }
        catch(IndexOutOfBoundsException e){
            resetTimer();
        }

    }

    private void resetTimer() {
        myTimer.cancel();
        myTimer = new Timer();
    }

    /** Loads a replay from a {@code File}, throws {@code replay.external.InvalidReplayFileException} if an error occurs */
    public void load(File replayFile) throws InvalidReplayFileException {
        Replay replay;
        // Deserialization
        try
        {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(replayFile);
            ObjectInputStream in = new ObjectInputStream(file);
            // Method for deserialization of object
            ReplayHolder replayHolder = (ReplayHolder)in.readObject();
            in.close();
            file.close();
            loadedReplay = replayHolder.replay;
        }
        catch(IOException | ClassNotFoundException | ClassCastException ex)
        {
            throw new InvalidReplayFileException(ex.getMessage());
        }
        //convert replay file into usable
        Long length = loadedReplay.getFrameList().get(loadedReplay.getFrameList().size()-1).getTime();
        eventList = new Event[length.intValue()];
        for(Frame frame : loadedReplay.getFrameList()){
            Long time = frame.getTime();
            eventList[time.intValue()-1] = frame.getEvent();
        }
    }

    public void play(){
        myTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                advanceReplay();
            }
        }, (long) 0, (long) 1);
    }

    public String getStageName(){
        return loadedReplay.getStageName();
    }

    public String getDate(){
        return loadedReplay.getDate();
    }

    public HashMap<Integer, String> getCharacterMap(){
        return (HashMap<Integer, String>) loadedReplay.getCharacterMap().clone();
    }

    public HashMap<Integer, String> getColorMap(){
        return (HashMap<Integer, String>) loadedReplay.getColorMap().clone();
    }

    public String getGameMode(){
        return loadedReplay.getGameMode();
    }

    public int getTypeValue(){
        return loadedReplay.getTypeValue();
    }

    public void stop() {
        resetTimer();
    }

    public void setEventBus(EventBus eventBus){
        this.myEventBus = eventBus;
    }
}
