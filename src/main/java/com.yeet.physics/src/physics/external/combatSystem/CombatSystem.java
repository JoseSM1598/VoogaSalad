package physics.external.combatSystem;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import messenger.external.*;
import physics.external.PhysicsBody;
import physics.external.PhysicsSystem;
import xml.XMLParser;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.PI;

/*
    Responsible for processing CombatActionEvent posted to the message bus
    and parsing XML to send hitbox and hurtbox related data to physics system
    @author xp19
 */

public class CombatSystem {

    EventBus eventBus;
    private PlayerManager playerManager;
    private PhysicsSystem physicsSystem;
    private List<Integer> botList;
    private HashMap<Integer, Point2D> playerMap;
    private XMLParser xmlParser;
    private File gameDir;
    private HashMap<Integer, ArrayList<Double>> characterStats;
    private HashMap<Integer, Rectangle2D> tileMap;
    private static final int TILE_STARTING_ID = 1000;
    private static int tileID = TILE_STARTING_ID;
    private static final int PLAYER_STARTING_ID = 0;
    private static int playerID = PLAYER_STARTING_ID;


//    public CombatSystem(Player bot){
//        eventBus = EventBusFactory.getEventBus();
//        bot.id = 1;
//        playerManager = new PlayerManager(1);
//    }

    public CombatSystem(HashMap<Integer, Point2D> playerMap, HashMap<Integer, Rectangle2D> tileMap, PhysicsSystem physicsSystem, File gameDir, Map<Integer, String> characterNames){
        characterStats = new HashMap<>();
        tileID = TILE_STARTING_ID;
        playerID = PLAYER_STARTING_ID;
        // get character stats
        for(int id: characterNames.keySet()){
            String name = characterNames.get(id);
            xmlParser = new XMLParser(Paths.get(gameDir.getPath(), "characters", name, "characterproperties.xml").toFile());
            HashMap<String, ArrayList<String>> map = xmlParser.parseFileForElement("character");
            ArrayList<Double> stats = new ArrayList<>();
            // get attack damage
            Double damage = Double.parseDouble(map.get("attack").get(0));
            // get defense
            Double defense = Double.parseDouble(map.get("defense").get(0));
            // get health
            Double health = Double.parseDouble(map.get("health").get(0));
            stats.add(damage);
            stats.add(defense);
            stats.add(health);
            characterStats.put(id, stats);
        }

        eventBus = EventBusFactory.getEventBus();
        this.playerMap = playerMap;
//        playerManager = new PlayerManager(playerMap.keySet().size());
        this.physicsSystem = physicsSystem;
        this.tileMap = tileMap;
        // register players to physics engine
        for(int i = 0; i < playerMap.keySet().size(); i++){
//            System.out.println("MIN X: " + playerMap.get(i).getX());
            physicsSystem.addPhysicsObject(playerID, PhysicsSystem.DEFAULT_MASS, playerMap.get(i).getX(), playerMap.get(i).getY(),40,60, (int)playerMap.get(i).getX(), (int)playerMap.get(i).getY());
            playerID++;
        }
        // register tiles to physics engine
        for(int i=0;i < tileMap.keySet().size(); i++){
            physicsSystem.addPhysicsObject(tileID,0, tileMap.get(i).getX(),tileMap.get(i).getY(),tileMap.get(i).getWidth(),tileMap.get(i).getHeight(), (int)tileMap.get(i).getX(), (int)tileMap.get(i).getY());
            tileID++;
        }


        // get hit boxes and hurt boxes information
        for(int id: characterNames.keySet()){
            String name = characterNames.get(id);
            xmlParser = new XMLParser(Paths.get(gameDir.getPath(), "characters", name, "attacks", "attackproperties.xml").toFile());
            HashMap<String, ArrayList<String>> map = xmlParser.parseFileForElement("frame");


            double animationWidth = Double.parseDouble(xmlParser.parseFileForElement("attack").get("width").get(0));
            double animationHeight = Double.parseDouble(xmlParser.parseFileForElement("attack").get("height").get(0));


            double hitX = Double.parseDouble(map.get("hitXPos").get(0));
            double hitY = Double.parseDouble(map.get("hitYPos").get(0));
            double hitW = Double.parseDouble(map.get("hitWidth").get(0));
            double hitH = Double.parseDouble(map.get("hitHeight").get(0));
            double hurtX = Double.parseDouble(map.get("hurtXPos").get(0));
            double hurtY = Double.parseDouble(map.get("hurtYPos").get(0));
            double hurtW = Double.parseDouble(map.get("hurtWidth").get(0));
            double hurtH = Double.parseDouble(map.get("hurtHeight").get(0));





            hitX = hitX*40/animationWidth;
            hitY = hitY*60/animationHeight;
            hurtX = hurtX*40/animationWidth;
            hurtY = hurtY*60/animationHeight;

            hitW = hitW*40/animationWidth;
            hitH = hitH*60/animationHeight;
            hurtW = hurtW*40/animationWidth;
            hurtH = hurtH*60/animationHeight;


            System.out.println(hitX);
            System.out.println(hitY);
            System.out.println(hitW);
            System.out.println(hitH);
            System.out.println();
            System.out.println(hurtW);
            System.out.println(hurtH);
            System.out.println(hurtX);
            System.out.println(hurtY);

            // set hitbox
            physicsSystem.setHitBox(0, id,
                    hitX, hitY, hitW, hitH);
            // set hurtbox
            physicsSystem.setHitBox(1, id,
                    hurtX, hurtY, hurtW, hurtH);
           /* // set hurtbox
            physicsSystem.setHitBox(1, id,
                    Double.parseDouble(map.get("hurtXPos").get(0)), Double.parseDouble(map.get("hurtYPos").get(0)),
                    Double.parseDouble(map.get("hurtWidth").get(0)), Double.parseDouble(map.get("hurtHeight").get(0)));*/
        }

    }

    /** Returns the {@code PlayerState} of the player specified
     *  @param id The player to retrieve the state for
     */
    public PlayerState getPlayerState(int id){
        return playerManager.getPlayerByID(id).getPlayerState();
    }


    @Subscribe
    public void onCombatEvent(CombatActionEvent event){
        int id = event.getInitiatorID();
//        if(!botList.contains(id)){
            playerManager.changePlayerStateByIDOnEvent(id, event);
//        }
//        else{
//            System.out.println("Bot id: " + id);
//        }
    }

    @Subscribe
    public void onIdleEvent(IdleEvent idleEvent){
        int id = idleEvent.getId();
        if(!characterStats.keySet().contains(id)) return;
        if(playerManager.getPlayerByID(id).getPlayerState()!=PlayerState.SINGLE_JUMP
                && playerManager.getPlayerByID(id).getPlayerState()!=PlayerState.DOUBLE_JUMP){
            playerManager.setToInitialStateByID(id);
        }
    }

    @Subscribe
    public void onAttackSuccessfulEvent(AttackSuccessfulEvent event){
//        System.out.println("Attack!!!");
        physicsSystem.attack(event.getInitiatorID());
    }

    @Subscribe
    public void onMoveSuccessfulEvent(MoveSuccessfulEvent event){
        boolean direction = event.getDirection();
//        System.out.println("Move" + direction);
        // move left
        if(direction){
            physicsSystem.move(event.getInitiatorID(), PI);
        }
        // move right
        else{
            physicsSystem.move(event.getInitiatorID(), 0);
        }
    }

    @Subscribe
    public void onGroundIntersectEvent(GroundIntersectEvent event){
        List<Integer> playersOnGround = event.getGroundedPlayers();
        for(int id: playersOnGround){
            playerManager.setToInitialStateByID(id);
        }
    }

    @Subscribe
    public void onAttackIntersectEvent(AttackIntersectEvent event){
        Map<Integer, Double> playersBeingRekt = new HashMap<>();
        for(List<Integer> list: event.getAttackPlayers()){
            Player playerBeingAttacked = playerManager.getPlayerByID(list.get(0));
            Player playerAttacking = playerManager.getPlayerByID(list.get(1));
            playerAttacking.addAttackingTargets(playerBeingAttacked);
            boolean result = playerManager.hurt(list.get(0), list.get(1));
            if(result){
                eventBus.post(new GameOverEvent(playerManager.winnerID, playerManager.getRanking()));
            }
            playersBeingRekt.put(playerBeingAttacked.id, playerManager.getPlayerByID(list.get(0)).getHealth());
        }
        eventBus.post(new GetRektEvent(playersBeingRekt));
    }

    @Subscribe
    public void onJumpSuccessfulEvent(JumpSuccessfulEvent event){
        physicsSystem.jump(event.getInitiatorID());
    }

    @Subscribe
    public void onGameStart(GameStartEvent gameStartEvent){
        botList = gameStartEvent.getBots();
        playerManager = new PlayerManager(playerMap.size(), characterStats);
        playerManager.setBots(botList, physicsSystem);
        String type = gameStartEvent.getGameType().toLowerCase();
        if(type.equals("stock")){
            int life = gameStartEvent.getTypeValue();
            playerManager.setNumOfLives(life);
        }
        else{
            // timed
        }

        PlayerGraph graph = new PlayerGraph(playerManager, physicsSystem.getPositionsMap());
        for(int id: botList){
            ((Bot)playerManager.getPlayerByID(id)).setPlayerGraph(graph);
        }
        for(int id: botList){
            ((HardBot)playerManager.getPlayerByID(id)).start();
        }
    }

    @Subscribe
    public void onTimeUpEvent(TimeUpEvent timeUpEvent){
        eventBus.post(new GameOverEvent(playerManager.winnerID, playerManager.getRanking()));
    }

    @Subscribe
    public void onPlayerDeath(PlayerDeathEvent playerDeathEvent){
        int id = playerDeathEvent.getId();
        playerManager.respawnPlayer(id, characterStats.get(id));
        ((PhysicsBody)physicsSystem.getGameObjects().get(id)).respawn();
//        physicsSystem.addPhysicsObject(id, physicsSystem.DEFAULT_MASS, tileMap.get(id).getX(), tileMap.get(id).getY(), 40, 60);
    }

    @Subscribe
    public void onPositionUpdate(PositionsUpdateEvent positionsUpdateEvent){
        Map<Integer, Point2D> positionMap = positionsUpdateEvent.getPositions();
        for(int id: positionMap.keySet()){
            Point2D pos = positionMap.get(id);
            if(pos.getX()+40<0||pos.getX()-40>1200||pos.getY()+60<0||pos.getY()-60>800){
                int remainingLife = playerManager.outOfScreen(id);
                eventBus.post(new PlayerDeathEvent(id, remainingLife));
            }
        }

    }

    @Subscribe
    public void onGameOver(GameOverEvent gameOverEvent){
        for(int id: characterStats.keySet()){
            if(playerManager.getPlayerByID(id).isBot()){
                ((HardBot)playerManager.getPlayerByID(id)).stop();
            }
        }
    }

}