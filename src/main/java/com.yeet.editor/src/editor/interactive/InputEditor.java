package editor.interactive;

import editor.EditorManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import renderer.external.RenderUtils;
import renderer.external.Scrollable;
import renderer.external.Structures.InputItem;
import renderer.external.Structures.ScrollablePaneNew;
import java.io.File;
import java.nio.file.Paths;
import java.util.*;

public class InputEditor extends EditorSuper {
    private static final int DEFAULT_NUM_TABS = 4;
    private static final String[] DEFAULT_MOVES = {"JUMP","DOWN","LEFT","RIGHT","ATTACK"};

    private List<TextField> userInputs;
    private List<ObservableList> inputTypes;
    private List<ScrollablePaneNew> myScrolls;
    private List<HashMap<String,String>> bindings;
    private TabPane tabs;
    private int currentTabId;
    private int numTabs;


    public InputEditor(EditorManager em, Scene prev){
        super(new Group(), em, prev);
        currentTabId = 1;
        numTabs = DEFAULT_NUM_TABS;
        bindings = new ArrayList<>();
        userInputs = new ArrayList<>();
        inputTypes = FXCollections.observableArrayList();
        myScrolls = new ArrayList<>();
        tabs = new TabPane();
        tabs.getTabs().addAll(makeTabs());
        tabs.setTabMinWidth(100.0);
        Button test = myRS.makeStringButton("Show Bindings",Color.BLACK,true,Color.WHITE,18.0,650.0,80.0,150.0,50.0);
        setRequirements();
        Text t = new Text();
        t.setFont(myRS.getEmphasisFont());
        t.setScaleX(.5);
        t.setScaleY(.5);
        test.setOnMouseClicked(e -> {
                    showBindings(t);
                });
        Button save = myRS.makeStringButton("Save",Color.BLACK,true,Color.WHITE,18.0,650.0,150.0,150.0,50.0);
        save.setOnMouseClicked(e -> createSaveFile());
        Button remove = myRS.makeStringButton("remove input",Color.BLACK,true,Color.WHITE,18.0,650.0,150.0,150.0,50.0);
        remove.setOnMouseClicked(e -> removeSelectedItem());
        VBox v = new VBox(10,test,remove,save,t);
        v.setLayoutX(420.0);
        v.setLayoutY(185.0);
        root.getChildren().addAll(tabs,v);
    }

    private void showBindings(Text t){
        getBindings();
        String res = "";
        for(int i = 0; i < numTabs; i++){
            String map = bindings.get(i).toString();
            res += "Player " + Integer.toString(i+1) +  " " + map + "\n";
        }
        t.setText(res);



    }

    private void removeSelectedItem(){
        String selected = myScrolls.get(currentTabId-1).getSelectedItem().getButton().getText();
        userInputs.get(currentTabId-1).clear();
        if(Arrays.asList(DEFAULT_MOVES).contains(selected)){
            RenderUtils.throwErrorAlert("Not deletable","Cannot remove a basic move");
        } else {
            myScrolls.get(currentTabId-1).removeItem();
        }
    }

//    private void throwErrorAlert(String header, String content){
//        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
//        errorAlert.setHeaderText(header);
//        errorAlert.setContentText(content);
//        errorAlert.showAndWait();
//    }



    private List<Tab> makeTabs(){
        List<Tab> tablist = new ArrayList<>();
        for(int i = 0; i < numTabs; i++) {
            Tab t = new Tab();
            t.setText("Player " + (i + 1));
            t.setId(Integer.toString(i+1));
            t.setOnSelectionChanged(e ->
                    currentTabId = Integer.parseInt(t.getId()));
            t.setOnCloseRequest(e -> {
                numTabs--;
                System.out.println(numTabs);
            });
            t.setContent(generateContent());
            tablist.add(t);
        }
        return tablist;

    }

    private Pane generateContent(){
        Pane p = new Pane();

        ObservableList l = FXCollections.observableArrayList();
        inputTypes.add(l);
        p.getChildren().addAll(makeVBox());
        return p;
    }


    private void addItemtoScroll(){
        String text = userInputs.get(currentTabId-1).getText();
        userInputs.get(currentTabId-1).clear();
        if(text.indexOf(" ") >= 0 || text.indexOf("_") >= 0 || inputTypes.get(currentTabId-1).contains(text.toUpperCase())){
            RenderUtils.throwErrorAlert("Invalid Input","Blank spaces, underscores, and repeat inputs are invalid");
        }else {
            inputTypes.get(currentTabId-1).add(text.toUpperCase());
            InputItem testItem = new InputItem(new Text(text.toUpperCase()));
            myScrolls.get(currentTabId-1).addItem(testItem);
        }
    }

    private void setRequirements(){
        for(int i = 0; i < numTabs; i++){
            for(int j = 0; j < DEFAULT_MOVES.length; j++){
                inputTypes.get(i).add(DEFAULT_MOVES[j]);
                myScrolls.get(i).addItem(new InputItem(new Text(DEFAULT_MOVES[j])));
            }
        }
    }

    private VBox makeVBox(){
        VBox v = new VBox(20.0);
        ScrollablePaneNew sp = new ScrollablePaneNew(200.0,200.0,300.0,500.0);
        sp.setMaxHeight(150.0);
        sp.setMaxWidth(150.0);
        myScrolls.add(sp);
        v.getChildren().addAll(createUserCommandLine(),sp.getScrollPane());
        v.setLayoutX(100.0);
        v.setLayoutY(80.0);
        return v;
    }


    public List<ObservableList> getInputTypes(){
        return inputTypes;
    }


    public Set<String> getMoveSet() {
        Set<String> moves = new HashSet<>();
        bindings = getBindings();
        for (int i = 0; i < bindings.size(); i++) {
            HashMap<String, String> bindingsMap = bindings.get(i);
            for (String move : bindingsMap.keySet()) {
                moves.add(move);
            }
        }
        return moves;
    }
    public List<HashMap<String,String>> getBindings(){
        bindings.clear();
        for(int i = 0; i < numTabs; i++) {
            bindings.add(new HashMap<>());
            for (Scrollable s : myScrolls.get(i).getItems()) {
                String move = s.getButton().getText();
                Text keyText = (Text) s.getButton().getGraphic();
                String key = keyText.getText();
                bindings.get(i).put(move, key);
            }
        }
        return bindings;
    }

    private TextField createUserCommandLine() {
        TextField t = new TextField();
        t.setPrefWidth(200);
        t.setPrefHeight(50);
        t.setOnKeyPressed(e ->{
            if(e.getCode() == KeyCode.ENTER){
                addItemtoScroll();
            } });
        userInputs.add(t);

        return t;
    }

    @Override
    public String toString(){
        return "Input Editor";
    }

    private void createSaveFile() {
        HashMap<String, ArrayList<String>> structure = new HashMap<>();
        HashMap<String, ArrayList<String>> data = new HashMap<>();
        TreeSet<String> moves = new TreeSet<>();
        bindings = getBindings();
        for(int i = 0; i < bindings.size(); i++) {
            HashMap<String, String> bindingsMap = bindings.get(i);
            for(String move : bindingsMap.keySet()) {
                moves.add(move);
                data.putIfAbsent(move, new ArrayList<>());
            }
        }
        structure.put("input", new ArrayList<>(moves));
        for(String s: data.keySet()) {
            for(int i = 0; i < bindings.size(); i++) {
                HashMap<String, String> bindingsMap = bindings.get(i);
                if(bindingsMap.containsKey(s)) {
                    data.get(s).add(bindingsMap.get(s));
                } else {
                    data.get(s).add("");
                }
            }
        }
        structure.put("players", new ArrayList<>(List.of("numPlayers")));
        data.put("numPlayers", new ArrayList<>(List.of(Integer.toString(numTabs))));
        File save = Paths.get(myEM.getGameDirectoryString(), "inputsetup.xml").toFile();
        generateSave(structure, data, save);
    }
}
