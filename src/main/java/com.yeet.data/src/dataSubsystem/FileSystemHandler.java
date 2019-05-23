package dataSubsystem;

import renderer.external.RenderSystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * A class meant to handle any operations relating to the file system.
 * @author ak457
 */
public class FileSystemHandler {
    RenderSystem myRS;

    public FileSystemHandler() {
        myRS = new RenderSystem();
    }

    /**
     * Create a new directory
     * @param path The file path of the new directory
     */
    public void createDirectory(String path) {
        File directory = new File(path);
        directory.mkdir();
    }

    /**
     * Create a new file
     * @param path The file path of the new file
     */
    public void createFile(String path) {
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            myRS.createErrorAlert("Problem creating new file","Please check code logic");
        }
    }

    /**
     * Deletes the file and any files that are located in its lower directories
     * @param file The file that will be deleted
     */
    public void deleteFile(File file) {
        while (file.exists()) {
            deleteFilesWithinDirectory(file);
        }
    }

    /**
     * Helper method for deleteFile
     * @param file The current file in question
     */
    private void deleteFilesWithinDirectory(File file) {
        File[] files = file.listFiles();
        if(files == null || files.length == 0) {
            file.delete();
        } else {
            for(File f : files) {
                deleteFile(f);
            }
        }
    }

    /**
     * Copy a source file to a destination file
     * Code based off of example found on https://www.journaldev.com/861/java-copy-file
     * @param source The file that is being copied
     * @param dest The file location that is being pasted to
     */
    public void copyFile(File source, File dest) {
        if(source.exists()) {
            try {
                Files.copy(source.toPath(), dest.toPath());
            } catch (IOException e) {
                myRS.createErrorAlert("Problem copying file","Please check code logic");
            }
        }
    }
}
