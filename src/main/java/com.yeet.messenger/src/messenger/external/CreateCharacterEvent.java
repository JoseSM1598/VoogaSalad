package messenger.external;

import java.io.File;

public class CreateCharacterEvent extends CreateDirectoryEvent{
    public CreateCharacterEvent(String characterName, File characterDirectory) {
        super(characterName, characterDirectory);
    }
}
