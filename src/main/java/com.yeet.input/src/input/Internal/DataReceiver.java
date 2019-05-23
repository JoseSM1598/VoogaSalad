package input.Internal;

import xml.XMLParser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Class for setting up the files needed for key input mappings and combo mappings
 */
public class DataReceiver {

    XMLParser myParserInput;
    XMLParser myParserCombo;
    //Holds our key mappings. All of the values will be an arraylist of size 1
    private Map<String, ArrayList<String>> inputKeys;
    private Map<String, ArrayList<String>> comboKeys;
    private int numPlayers;

    public DataReceiver(File GameDir) {

        myParserCombo = new XMLParser(new File(GameDir.getPath() + "/combosetup.xml"));
        myParserInput = new XMLParser(new File(GameDir.getPath() + "/inputsetup.xml"));
        var x = myParserInput.parseFileForAttribute("players", "numPlayers");
        numPlayers = Integer.parseInt(x.get(0));
    }

    public List<Map<String, String>> getKeys(){
        List<Map<String, String>> allPlayerInputs = new ArrayList<>();
        HashMap<String, ArrayList<String>> allInputs = myParserInput.parseFileForElement("input");
        for(int i = 0; i<numPlayers; i++){
            Map<String, String> playerInputs = new HashMap<>();
            for(String key : allInputs.keySet()){
                playerInputs.put(allInputs.get(key).get(i), key);
            }

            allPlayerInputs.add(playerInputs);
        }
        //inputKeys = myParserInput.parseFileForElement("input");
        return allPlayerInputs;

    }

    public List<Map<String, String>> getCombos(){
        List<Map<String, String>> allPlayerCombos = new ArrayList<>();
        HashMap<String, ArrayList<String>> allCombos = myParserCombo.parseFileForElement("input");
        System.out.println(allCombos);
        for(int i = 0; i<numPlayers; i++){
            Map<String, String> playerCombos = new HashMap<>();
            for(String key : allCombos.keySet()){
                playerCombos.put(allCombos.get(key).get(i), key);
            }
            allPlayerCombos.add(playerCombos);
        }
        return allPlayerCombos;
    }

    /**
     *
     * For the purpose of reversing the Map that is obtained from the data processor
     */
    public Map<String,String> reverse(HashMap<String,ArrayList<String>> map) {
        Map<String,String> rev = new HashMap<>();
        for(Map.Entry<String,ArrayList<String>> entry : map.entrySet())
            rev.put(entry.getValue().get(0), entry.getKey()); // reverse and remove the arrayList inside the map
        return rev;
    }

}
