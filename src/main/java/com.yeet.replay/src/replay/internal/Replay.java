package replay.internal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/** Created and read by the recorder, can be saved to file to reproduce a sequence of events during a combat
 *  @author bpx
 */
public class Replay implements Serializable {

    //meta data
    private String stageName;
    private HashMap<Integer,String> characterMap;
    private HashMap<Integer, String> colorMap;
    private String gameMode;
    private int typeValue;
    private String date;
    private String time;

    private int index;
    private ArrayList<Frame> frameList;
    private int length;

    public Replay(){
        stageName = "";
        characterMap = new HashMap<>();
        date = "";
        index = -1;
        length = 0;
        frameList = new ArrayList<>();
    }

    public void setStageName(String stageName){
        this.stageName = stageName;
    }

    public String getStageName() {
        return stageName;
    }

    public void setCharacterMap(HashMap<Integer,String> characterMap){
        this.characterMap = characterMap;
    }

    public HashMap<Integer, String> getCharacterMap() {
        return characterMap;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setColorMap(HashMap<Integer, String> colorMap){
        this.colorMap = colorMap;
    }

    public HashMap<Integer, String> getColorMap() {
        return colorMap;
    }

    public void setGameMode(String gameMode){
        this.gameMode = gameMode;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setTypeValue(int typeValue){
        this.typeValue = typeValue;
    }

    public int getTypeValue() {
        return typeValue;
    }

    /** Add an {@code Event} to the replay
     *  @param frame Any kind of {@code Event}
     */
    public void add(Frame frame){
        frameList.add(frame);
        length++;
    }

    /** Returns the next {@code Event} in the {@code replay.internal.Replay}, throws {@code replay.internal.EndOfReplayException} if at end*/
    public Frame next() throws EndOfReplayException {
        if(index<length){
            index++;
            return frameList.get(index);
        }
        else{
            throw new EndOfReplayException();
        }
    }

    public int getIndex(){
        return index;
    }

    public int getLength(){
        return length;
    }

    public ArrayList<Frame> getFrameList(){
        return frameList;
    }

}
