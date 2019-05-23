package dataSystem;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import dataSubsystem.FileSystemHandler;
import javafx.event.Event;
import messenger.external.*;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import xml.XMLParser;
import javax.print.attribute.Attribute;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A system module created at runtime, much like Player and CombatSystem
 * Receives messages from the message bus pertaining to Events that deal with the files in the file system
 * @author ak457
 */
public class DataSystem {
    private EventBus myEB;
    private FilePath myFP;
    private FileSystemHandler myFileSystem;

    public DataSystem() {
        myEB = EventBusFactory.getEventBus();
        myEB.register(this);
        myFileSystem = new FileSystemHandler();
    }

    /**
     * In the event that a new game is created
     * @param event a CreateGameEvent that specifies that a new game is being created
     */
    @Subscribe
    public void createInitialGameFiles(CreateGameEvent event) {
        File defaultFile = event.getDirectory();
        defaultFile.mkdir();
        myFileSystem.createDirectory(defaultFile.getPath() + myFP.STAGEPATH.getPath());
        myFileSystem.createDirectory(defaultFile.getPath() + myFP.CHARACTERPATH.getPath());
        myFileSystem.createDirectory(defaultFile.getPath() + myFP.DATAPATH.getPath());
        myFileSystem.createDirectory(defaultFile.getPath() + myFP.BACKGROUNDPATH.getPath());
        myFileSystem.createDirectory(defaultFile.getPath() + myFP.BGMPATH.getPath());
        myFileSystem.createDirectory(defaultFile.getPath() + myFP.TILEPATH.getPath());
        myFileSystem.createDirectory(defaultFile.getPath() + myFP.SPLASHPATH.getPath());
        myFileSystem.createDirectory(defaultFile.getPath() + myFP.MODE.getPath());
        myFileSystem.createDirectory(defaultFile.getPath() + myFP.TIME.getPath());
        myFileSystem.createDirectory(defaultFile.getPath() + myFP.STOCK.getPath());
        myFileSystem.createFile(defaultFile.getPath()+myFP.GAMEPROPERTIES.getPath());
        String defaultGamePath = System.getProperty("user.dir")+myFP.DEFAULTGAMEPATH.getPath();
        String backgroundPath = myFP.DEFAULTBACKGROUND.getPath();
        String musicPath = myFP.DEFAULTBGM.getPath();
        String tilePath = myFP.DEFAULTTILE.getPath();
        String splashPath = myFP.DEFAULTSPLASH.getPath();
        String comboPath = myFP.DEFAULTCOMBO.getPath();
        myFileSystem.copyFile(new File(defaultGamePath + backgroundPath), new File(defaultFile.getPath()+ backgroundPath));
        myFileSystem.copyFile(new File(defaultGamePath + musicPath), new File(defaultFile.getPath()+ musicPath));
        myFileSystem.copyFile(new File(defaultGamePath + tilePath), new File(defaultFile.getPath()+ tilePath));
        myFileSystem.copyFile(new File(defaultGamePath + splashPath), new File(defaultFile.getPath()+ splashPath));
        myFileSystem.copyFile(new File(defaultGamePath + comboPath), new File(defaultFile.getPath()+ comboPath));
    }

    /**
     * In the event a user wants to save the current game state in game
     * @param event An event that will specify that the user is trying to save midgame
     * Note: Not currently implemented
     */
    @Subscribe
    public void saveGameFiles(Event event) {

    }

    @Subscribe
    public void createStageFiles(CreateStageEvent event) {
        File gameStageDirectory = event.getDirectory();
        gameStageDirectory.mkdir();
        File stageProperties = new File(gameStageDirectory.getPath()+myFP.STAGEPROPERTIES.getPath());
        try {
            stageProperties.createNewFile();
        } catch (IOException e) {
            System.out.println("Could not create stage file");
        }
    }
    @Subscribe
    public void createCopyFile(CreateCopyEvent event) {
        myFileSystem.copyFile(event.getSource(), event.getDirectory());
    }
    @Deprecated
    private void overwriteGameFile(File xmlFile) {
        XMLParser parser = new XMLParser(xmlFile);
        Document xmlDoc = parser.createDocument(xmlFile);
        NodeList voogaNodes = xmlDoc.getElementsByTagName("VOOGASalad");
        HashMap<String, ArrayList<String>> structure = new HashMap<>();
        HashMap<String, ArrayList<String>> data = new HashMap<>();
        for(int i = 0; i < voogaNodes.getLength(); i++) {
            if(voogaNodes.item(i) instanceof Element) {
                Element voogaElem = (Element) voogaNodes.item(i);
                NodeList voogaElemNodes = voogaElem.getChildNodes();
                for(int j = 0; j < voogaElemNodes.getLength(); j++) {
                    if(voogaElemNodes.item(j) instanceof Attribute) {
                        Attr voogaAttr = (Attr) voogaElemNodes.item(j);
                        //structure.putIfAbsent(voogaElem.getTagName(), voogaAttr.getName());

                    }
                }
            }
        }
    }

    @Subscribe
    public void createCharacterFiles(CreateCharacterEvent event) {
        File characterDirectory = event.getDirectory();
        characterDirectory.mkdir();
        myFileSystem.createDirectory(characterDirectory.getPath()+ myFP.ATTACK.getPath());
        myFileSystem.createDirectory(characterDirectory.getPath()+ myFP.SOUND.getPath());
        myFileSystem.createDirectory(characterDirectory.getPath()+ myFP.SPRITE.getPath());
        myFileSystem.createFile(characterDirectory.getPath() + myFP.CHARACTERPROPERTIES.getPath());
        myFileSystem.createFile(characterDirectory.getPath() + myFP.ATTACKPROPERTIES.getPath());
        myFileSystem.createFile(characterDirectory.getPath() + myFP.SOUNDPROPERTIES.getPath());
        myFileSystem.createFile(characterDirectory.getPath() + myFP.SPRITEPROPERTIES.getPath());
    }

    @Subscribe
    public void deleteDirectory(DeleteDirectoryEvent event) {
        myFileSystem.deleteFile(event.getDirectory());
    }
}
