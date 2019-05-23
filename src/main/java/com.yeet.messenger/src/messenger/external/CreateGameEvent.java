package messenger.external;

import java.io.File;

public class CreateGameEvent extends CreateDirectoryEvent {
    public CreateGameEvent(String creationName, File gameDirectory){
        super(creationName, gameDirectory);
    }
}
