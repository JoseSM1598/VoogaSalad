package messenger.external;

import java.io.File;
import java.util.Map;

public class VolumeChangeEvent extends OptionChangeEvent {

    public VolumeChangeEvent(File file, String tag, Map<String, String> valueMap) {
        super(file, tag, valueMap);
    }

    @Override
    public String getName() {
        String name = "Volume Change Event: File = "+ getFile().getName()+" Tag = "+getTag()+" Values =";
        for(String s : getValueMap().keySet()){
            name+=" "+s+":"+getValueMap().get(s);
        }
        return name;
    }

}
