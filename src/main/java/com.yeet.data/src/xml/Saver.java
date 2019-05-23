package xml;

import java.io.File;

/**
 * The Saver interface is intended for use in creating any kind of save file.
 * The methods encapsulated are needed to create the save file.
 * @author ak457
 */
public interface Saver {
    /**
     * Generate a file to be placed in the directory specified by the filePath parameter
     * @param file The intended file to be created
     */
    void generateFile(File file);
}
