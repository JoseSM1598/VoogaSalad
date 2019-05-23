package messenger.external;

import java.io.File;

public abstract class CreateDirectoryEvent extends Event {
    private String name;
    private File myDirectory;

    public CreateDirectoryEvent(String creationName, File gameDirectory){
        name = creationName;
        myDirectory = gameDirectory;
    }

    @Override
    public String getName() {
        return name;
    }

    public File getDirectory(){
        return myDirectory;
    }
}
