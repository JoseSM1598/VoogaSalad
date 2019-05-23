package player.external;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import input.external.InputSystem;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import messenger.external.*;
import physics.external.PhysicsSystem;
import physics.external.combatSystem.CombatSystem;
import player.internal.Elements.HealthDisplay;
import player.internal.Elements.PlayerMarker;
import player.internal.Elements.ScreenTimer;
import player.internal.GameLoop;
import player.internal.SceneSwitch;
import player.internal.Screen;
import renderer.external.Renderer;
import renderer.external.Structures.Sprite;
import renderer.external.Structures.SpriteAnimation;
import replay.external.InvalidDirectoryException;
import replay.external.Recorder;
import replay.external.SaveReplayFailedException;
import xml.XMLParser;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static renderer.external.RenderUtils.toRGBCode;

/** Displays a stage and visualizes character combat animation
 *  @author bpx
 */
public class CombatScreen extends Screen {

    public static final double TILE_SIZE = 40.0;
    private EventBus myMessageBus;
    private Recorder myRecorder;

    private boolean isRecording;

    private SceneSwitch prevScene;
    private BiConsumer<Integer, ArrayList<Integer>> nextScene;

    private InputSystem myInputSystem;
    private CombatSystem myCombatSystem;
    private PhysicsSystem myPhysicsSystem;
    private GameLoop myGameLoop;

    private XMLParser myParser;

    private File myGameDirectory;

    private HashMap<Integer, Point2D> myCharacterMap;
    private HashMap<Integer, Rectangle2D> myTileMap;
    private HashMap<Integer, Sprite> mySpriteMap;
    private HashMap<Integer, HashMap<String,SpriteAnimation>> myAnimationMap;
    private HashMap<Integer, HealthDisplay> myHealthMap;

    private HashMap<String, ArrayList<String>> myBackgroundMap;
    private HashMap<String,ArrayList<String>> myMusicMap;
    private HashMap<String, ArrayList<String>> myStageMap;
    private HashMap<String, ArrayList<String>> mySpawnMap;

    private ArrayList<ImageView> myTiles;

    private ScreenTimer myTimer;

    public CombatScreen(Group root, Renderer renderer, File gameDirectory, boolean record, SceneSwitch prevScene, BiConsumer<Integer, ArrayList<Integer>> nextScene) {
        super(root, renderer);
        isRecording = record;
        //set up message bus
        myMessageBus = EventBusFactory.getEventBus();
        myMessageBus.register(this);
        //set up scene links
        this.prevScene = prevScene;
        this.nextScene = nextScene;
        //set up resource managers
        myGameDirectory =  gameDirectory;
        myCharacterMap = new HashMap<>();
        myTileMap = new HashMap<>();
        mySpriteMap = new HashMap<>();
        myAnimationMap = new HashMap<>();
        myHealthMap = new HashMap<>();
        myTiles = new ArrayList<>();
        super.setOnKeyPressed(event->myMessageBus.post(new KeyInputEvent(event.getCode())));
    }

    public void setupCombatScene(HashMap<Integer, String> characterNames, HashMap<Integer, Color> characterColors, String gameMode, Integer typeValue, List<Integer> botList, String stageName){
        myParser = new XMLParser(new File(myGameDirectory.getPath()+"/stages/"+stageName+"/stageproperties.xml"));
        myBackgroundMap = myParser.parseFileForElement("background");
        myMusicMap = myParser.parseFileForElement("music");
        myStageMap = myParser.parseFileForElement("map");
        mySpawnMap =  myParser.parseFileForElement("position");
        ImageView background = new ImageView(new Image(myGameDirectory.toURI()+"data/background/"+myBackgroundMap.get("bgFile").get(0)));
        background.setFitWidth(1280.0);
        background.setFitHeight(800.0);
        HBox healthDisplayContainer = new HBox(95.0);
        healthDisplayContainer.setPrefSize(1280.0,154.0);
        healthDisplayContainer.setAlignment(Pos.TOP_CENTER);
        healthDisplayContainer.setLayoutX(0.0);
        healthDisplayContainer.setLayoutY(646.0);
        super.getMyRoot().getChildren().addAll(background);
        for(int i=0; i<myStageMap.get("x").size();i++){
            ImageView tile = new ImageView(new Image(myGameDirectory.toURI()+"/data/tiles/"+myStageMap.get("image").get(i)+".png"));
            tile.setFitHeight(TILE_SIZE);
            tile.setFitWidth(TILE_SIZE);
            tile.setLayoutX(Integer.parseInt(myStageMap.get("x").get(i))* TILE_SIZE);
            tile.setLayoutY(Integer.parseInt(myStageMap.get("y").get(i))* TILE_SIZE);
            myTiles.add(tile);
            myTileMap.put(i,new Rectangle2D.Double(Integer.parseInt(myStageMap.get("x").get(i))*TILE_SIZE,Integer.parseInt(myStageMap.get("y").get(i))*TILE_SIZE, TILE_SIZE, TILE_SIZE));
            super.getMyRoot().getChildren().add(tile);
        }
        for(int i=0;i<characterNames.keySet().size();i++){
            if(!characterNames.get(characterNames.keySet().toArray()[i]).equals("")){
                //create sprites and set default viewport
                XMLParser spritePropertiesParser = new XMLParser(new File(myGameDirectory.getPath()+"/characters/"+characterNames.get(characterNames.keySet().toArray()[i])+"/sprites/spriteproperties.xml"));
                HashMap<String,ArrayList<String>> spriteProperties = spritePropertiesParser.parseFileForElement("sprite");
                Sprite sprite = new Sprite(new Image(myGameDirectory.toURI()+"/characters/"+characterNames.get(characterNames.keySet().toArray()[i])+"/sprites/spritesheet.png"),
                        Double.parseDouble(((spriteProperties.get("offsetX").get(0)))),Double.parseDouble(((spriteProperties.get("offsetY").get(0)))),
                        Double.parseDouble(((spriteProperties.get("width").get(0)))),Double.parseDouble(spriteProperties.get("height").get(0)));
                sprite.setLayoutX(Integer.parseInt(mySpawnMap.get("xPos").get((int)characterNames.keySet().toArray()[i]))*TILE_SIZE);
                sprite.setLayoutY(Integer.parseInt(mySpawnMap.get("yPos").get((int)characterNames.keySet().toArray()[i]))*TILE_SIZE);
                mySpriteMap.put(i,sprite);
                myCharacterMap.put(i,new Point2D.Double(Integer.parseInt(mySpawnMap.get("xPos").get(i))*TILE_SIZE,Integer.parseInt(mySpawnMap.get("yPos").get(i))*TILE_SIZE));
                PlayerMarker marker = new PlayerMarker(characterColors.get(characterNames.keySet().toArray()[i]),sprite);
                super.getMyRoot().getChildren().addAll(marker,sprite);
                //set up animations for the sprite
                XMLParser animationPropertiesParser = new XMLParser(new File(myGameDirectory.getPath()+"/characters/"+characterNames.get(characterNames.keySet().toArray()[i])+"/attacks/attackproperties.xml"));
                HashMap<String, ArrayList<String>> animationInfo = animationPropertiesParser.parseFileForElement("attack");
                myAnimationMap.put(i,new HashMap<>());
                for(int j = 0; j<animationInfo.get("name").size(); j++){
                    Duration duration = Duration.seconds(Double.parseDouble(animationInfo.get("duration").get(j)));
                    String name = animationInfo.get("name").get(j);
                    int count = Integer.parseInt(animationInfo.get("count").get(j));
                    int columns = Integer.parseInt(animationInfo.get("columns").get(j));
                    double offsetX = Double.parseDouble(animationInfo.get("offsetX").get(j));
                    double offsetY = Double.parseDouble(animationInfo.get("offsetY").get(j));
                    double width = Double.parseDouble(animationInfo.get("width").get(j));
                    double height = Double.parseDouble(animationInfo.get("height").get(j));
                    SpriteAnimation animation = new SpriteAnimation(sprite,duration,count,columns,offsetX,offsetY,width,height);
                    myAnimationMap.get(i).put(name,animation);
                }
                XMLParser characterPropertiesParser = new XMLParser(new File(myGameDirectory.getPath()+"/characters/"+characterNames.get(characterNames.keySet().toArray()[i])+"/characterproperties.xml"));
                double x = Double.parseDouble(characterPropertiesParser.parseFileForAttribute("portrait","x").get(0));
                double y = Double.parseDouble(characterPropertiesParser.parseFileForAttribute("portrait","y").get(0));
                double size = Double.parseDouble(characterPropertiesParser.parseFileForAttribute("portrait","size").get(0));
                ImageView healthPortrait = new ImageView(new Image(myGameDirectory.toURI()+"/characters/"+characterNames.get(characterNames.keySet().toArray()[i])+"/"+characterNames.get(characterNames.keySet().toArray()[i])+".png"));
                healthPortrait.setViewport(new javafx.geometry.Rectangle2D(x,y,size,size));
                HealthDisplay healthDisplay;
                if(gameMode.equalsIgnoreCase("TIME")){
                    healthDisplay = new HealthDisplay(super.getMyRenderer().makeText(characterNames.get(characterNames.keySet().toArray()[i]),true,40,Color.WHITE,0.0,0.0),healthPortrait,characterColors.get(characterNames.keySet().toArray()[i]),characterColors.get(characterNames.keySet().toArray()[i]).darker());
                }
                else{
                    healthDisplay = new HealthDisplay(super.getMyRenderer().makeText(characterNames.get(characterNames.keySet().toArray()[i]),true,40,Color.WHITE,0.0,0.0),typeValue,healthPortrait,characterColors.get(characterNames.keySet().toArray()[i]),characterColors.get(characterNames.keySet().toArray()[i]).darker());
                }
                healthDisplayContainer.getChildren().add(healthDisplay);
                myHealthMap.put((int)characterNames.keySet().toArray()[i],healthDisplay);
            }
        }
        //set up combat systems
        myInputSystem = new InputSystem(myGameDirectory);
        myInputSystem.resetBindings();
        myMessageBus.register(myInputSystem);
        myPhysicsSystem = new PhysicsSystem();
        myGameLoop = new GameLoop(myPhysicsSystem,this);
        myMessageBus.register(myPhysicsSystem);
        myCombatSystem = new CombatSystem(getCharacterMap(),getTileMap(),myPhysicsSystem, myGameDirectory, characterNames);
        myMessageBus.register(myCombatSystem);
        //replay system
        HashMap<Integer, String> hexmap = new HashMap<>();
        for(Integer i : characterColors.keySet()){
            hexmap.put(i,toRGBCode(characterColors.get(i)));
        }
        try {
            myRecorder = new Recorder(myMessageBus,myGameDirectory,stageName,characterNames,hexmap,gameMode,typeValue);
        } catch (InvalidDirectoryException e) {
            myRecorder = new Recorder(myMessageBus,stageName,characterNames,hexmap,gameMode,typeValue);
        }
        //music and audio
        myMessageBus.post(new GameStartEvent(gameMode, typeValue, botList));
        //ui elements
        super.getMyRoot().getChildren().add(healthDisplayContainer);
        if(gameMode.equalsIgnoreCase("TIME")){
            myTimer = new ScreenTimer(typeValue,super.getMyRenderer().makeText("",true,70,Color.WHITE,0.0,0.0),(time)->myMessageBus.post(new TimeUpEvent()));
            myTimer.setLayoutX(969.0);
            myTimer.setLayoutY(34.0);
            super.getMyRoot().getChildren().add(myTimer);
        }
        else{
            myTimer = new ScreenTimer(typeValue,super.getMyRenderer().makeText("",true,70,Color.WHITE,0.0,0.0),(time)->doNothing());
        }
    }

    private void doNothing() {
    }

    public void startLoop(){
        myTimer.play();
        myGameLoop.startLoop();
        myRecorder.record();
    }

    public void stopLoop(){
        if(myTimer!=null){
            myTimer.pause();
        }
        if(myRecorder!=null){
            myRecorder.stop();
            if(isRecording){
                try {
                    myRecorder.save();
                } catch (SaveReplayFailedException e) {
                    e.printStackTrace();
                    myMessageBus.post(new SaveReplayFailedEvent());
                }
            }
        }
        //TODO: stops game loop
        if(myGameLoop!=null){
            myGameLoop.stopLoop();
        }
    }

    public HashMap<Integer, Point2D> getCharacterMap(){
        return (HashMap<Integer, Point2D>) myCharacterMap.clone();
    }

    public HashMap<Integer, Rectangle2D> getTileMap() {
        return myTileMap;
    }

    @Subscribe
    public void update(PositionsUpdateEvent positionsUpdateEvent){
        Map<Integer, Point2D> characterMap = positionsUpdateEvent.getPositions();
        Map<Integer, Double> directionsMap = positionsUpdateEvent.getDirections();

        for(int i: mySpriteMap.keySet()){
            mySpriteMap.get(i).setLayoutX(characterMap.get(i).getX());
            mySpriteMap.get(i).setLayoutY(characterMap.get(i).getY());
            if(directionsMap.get(i)==0){
                // face right
                mySpriteMap.get(i).setScaleX(-1);
            }
            else if(directionsMap.get(i)==Math.PI){
                //face left
                mySpriteMap.get(i).setScaleX(1);
            }
        }

//        for(int i=0;i<mySpriteMap.keySet().size();i++){
//            mySpriteMap.get(i).setLayoutX(characterMap.get(i).getX());
//            mySpriteMap.get(i).setLayoutY(characterMap.get(i).getY());
//            if(directionsMap.get(i)==0){
//                // face right
//                mySpriteMap.get(i).setScaleX(-1);
//            }
//            else if(directionsMap.get(i)==Math.PI){
//                //face left
//                mySpriteMap.get(i).setScaleX(1);
//            }
//        }
    }

    @Subscribe
    public void showAttackAnimation(AttackSuccessfulEvent attackSuccessfulEvent){
        int id = attackSuccessfulEvent.getInitiatorID();
        myAnimationMap.get(id).get("special").play();
    }

    @Subscribe
    public void showMoveAnimation(MoveSuccessfulEvent moveSuccessfulEvent){
        int id = moveSuccessfulEvent.getInitiatorID();
        myAnimationMap.get(id).get("run").play();
    }

    @Subscribe
    public void endCombat(GameOverEvent gameOverEvent){
        Platform.runLater(
                () -> {
                    stopLoop();
                    nextScene.accept(gameOverEvent.getWinnerID(),gameOverEvent.getRankList());
                }
        );
    }

    @Subscribe
    public void onRekt(GetRektEvent getRektEvent){
        //TODO add other functionality when hit happens
        for(int i : getRektEvent.getPeopleBeingRekt().keySet()){
            myHealthMap.get(i).setHealth((int)Math.round(getRektEvent.getPeopleBeingRekt().get(i)));
        }
    }

    @Subscribe
    public void onDeath(PlayerDeathEvent deathEvent){
        //TODO add other on death visuals
        myHealthMap.get(deathEvent.getId()).setLives(deathEvent.getRemainingLife());
        if(deathEvent.getRemainingLife()==0){
            myHealthMap.get(deathEvent.getId()).setOpacity(0.2);
        }
    }
}
