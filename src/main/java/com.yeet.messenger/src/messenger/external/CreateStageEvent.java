package messenger.external;

import java.io.File;

public class CreateStageEvent extends CreateDirectoryEvent {
    public CreateStageEvent(String stageName, File stageDirectory){
        super(stageName, stageDirectory);
    }
}
