package replay.external;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import messenger.external.*;
import replay.internal.Frame;
import replay.internal.Replay;
import replay.internal.ReplayHolder;
import replay.internal.ReplayUtilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import static replay.internal.ReplayUtilities.getDate;
import static replay.internal.ReplayUtilities.getTime;

/** Creates and plays back {@code replay.internal.Replay} objects, and can write them to file
 *  @author bpx
 */
public class Recorder {
    private EventBus myEventBus;

    private File myDirectory;
    private Replay myActiveReplay;
    private boolean isRecording;

    private long startTime;

    /** Create a new {@code replay.external.Recorder} to record {@code Event} objects
     *  @param recordTarget The target {@code EventBus} to record
     */
    public Recorder(EventBus recordTarget, String stageName, HashMap<Integer, String> characterMap, HashMap<Integer, String> characterColors, String gameMode, Integer typeValue){
        myEventBus = recordTarget;
        myActiveReplay = new Replay();
        setMetaData(stageName,characterMap, characterColors,gameMode,typeValue);
        isRecording = false;
        myEventBus.register(this);
    }

    /** Create a new {@code replay.external.Recorder} with a recording directory */
    public Recorder(EventBus recordTarget, File directory, String stageName, HashMap<Integer, String> characterMap, HashMap<Integer, String> characterColors, String gameMode, Integer typeValue) throws InvalidDirectoryException {
        this(recordTarget, stageName, characterMap, characterColors, gameMode, typeValue);
        setRecordingDirectory(directory);
    }

    /** Sets the recording directory and throws {@code InvalidDirectoryException} if the operation fails
     *  @param directory New parent directory
     */
    public void setRecordingDirectory(File directory) throws InvalidDirectoryException {
        if(directory.isDirectory()){
            myDirectory = directory;
        }
        else{
            throw new InvalidDirectoryException();
        }
    }


    /** Sets the meta information for the active replay
     *  @param stageName The name of the stage the combat took place on
     *  @param characterMap A map of player ID to the character they chose
     */
    public void setMetaData(String stageName, HashMap<Integer, String> characterMap, HashMap<Integer, String> characterColors, String gameMode, Integer typeValue){
        myActiveReplay.setStageName(stageName);
        myActiveReplay.setCharacterMap(characterMap);
        myActiveReplay.setDate(getDate());
        myActiveReplay.setTime(getTime());
        myActiveReplay.setColorMap(characterColors);
        myActiveReplay.setGameMode(gameMode);
        myActiveReplay.setTypeValue(typeValue);
    }


    /** Starts a new recording of events */
    public void record(){
        isRecording = true;
        startTime = System.currentTimeMillis();
    }

    /** Stops recording events */
    public void stop(){
        isRecording = false;
    }

    /** Saves the active replay to disk using serialization. Saves to project directory if recording directory is null,
     *  or specified directory if recording directory has been set.
     */
    public void save() throws SaveReplayFailedException {
        // Serialization
        try
        {
            //Saving of object in a file
            FileOutputStream file;
            File replayDirectory;
            if(myDirectory==null){
                replayDirectory = new File("replays");
            }
            else{
                replayDirectory = new File(myDirectory,"replays");
            }
            replayDirectory.mkdir();
            //Save file using automatically generated name, add (1), (2), etc. if file name taken
            String fileName = createFileName();
            File targetFile = new File(replayDirectory,fileName);
            int copy = 1;
            while(!targetFile.createNewFile()){
                targetFile = new File(myDirectory,targetFile.getName()+"("+copy+")");
                copy++;
            }
            file = new FileOutputStream(targetFile);
            ObjectOutputStream out = new ObjectOutputStream(file);
            // Method for serialization of object
            ReplayHolder replayHolder = new ReplayHolder(myActiveReplay);
            out.writeObject(replayHolder);
            out.close();
            file.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            throw new SaveReplayFailedException();
        }

    }

    /** Creates a file name for the replay file based on current system date and time */
    private String createFileName(){
        String timestamp = ReplayUtilities.getCurrentTimeUsingCalendar();
        return("replay_"+timestamp+".yeet");
    }

    private void capture(Event event){
        if(isRecording){
            myActiveReplay.add(new Frame(event,System.currentTimeMillis()-startTime));
        }
    }

    /** Below are methods for capturing front-end updating events
     *  but only records them if in recording mode.
     */
    @Subscribe
    public void getPositionupdate(PositionsUpdateEvent event){
        capture(event);
    }

    @Subscribe
    public void getAttackAnimation(AttackSuccessfulEvent event){
        capture(event);
    }

    @Subscribe
    public void getMoveAnimation(MoveSuccessfulEvent event){
        capture(event);
    }

    @Subscribe
    public void getGameOver(GameOverEvent event){
        capture(event);
    }

    @Subscribe
    public void getDamage(GetRektEvent event){
        capture(event);
    }

    @Subscribe
    public void getDeath(PlayerDeathEvent event){
        capture(event);
    }




}
