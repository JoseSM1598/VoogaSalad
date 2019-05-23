package messenger.external;

import java.io.File;
import java.util.Map;

/** Super class for telling the Editor to change an option
 *  @author bpx
 */
public abstract class OptionChangeEvent extends Event {
    private File myFile;
    private String tag;
    private Map<String, String> valueMap;

    public OptionChangeEvent(File file, String tag, Map<String,String> valueMap){
        this.myFile = file;
        this.tag = tag;
        this.valueMap = valueMap;
    }

    public File getFile(){
        return myFile;
    }

    public String getTag(){
        return tag;
    }

    public Map<String,String> getValueMap(){
        return valueMap;
    }
}
