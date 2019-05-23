package messenger.external;

import java.io.File;

public class CreateCopyEvent extends CreateDirectoryEvent {
    private File mySource;
    public CreateCopyEvent(String name, File source, File destination) {
        super(name, destination);
        mySource = source;
    }

    public File getSource() {
        return mySource;
    }
}
