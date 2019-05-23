package messenger.external;

import java.io.File;

public class DeleteDirectoryEvent extends Event {
    private String myName;
    private File myDirectory;

    public DeleteDirectoryEvent(String name, File directory) {
        myName = name;
        myDirectory = directory;
    }

    @Override
    public String getName() {
        return myName;
    }

    public File getDirectory(){
        return myDirectory;
    }
}
