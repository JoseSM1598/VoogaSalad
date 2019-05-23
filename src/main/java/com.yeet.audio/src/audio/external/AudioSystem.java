package audio.external;

import audio.Internal.Player;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import messenger.external.*;

import java.io.File;
import java.net.MalformedURLException;

public class AudioSystem {

    private EventBus myMessageBus;
    private String path;
    private Player myPlayer;
    private File GameDir;

    private double fxvol;
    private double bgmvol;
    private double menuvol;
    private double fightvol;
    private AudioClip ko;
    private MediaPlayer menuMP;
    private MediaPlayer bgmMP;
    private MediaPlayer voiceMP;
    private MediaPlayer fightMP;

    public AudioSystem(File GameDirectory){
        fxvol = 1;
        bgmvol = 1;
        menuvol = 1;
        fightvol = 1;
        GameDir = GameDirectory;
        setRootPath();
        ko = new AudioClip(GameDirectory.toURI()+"/data/bgm/ko.mp3");
        myMessageBus = EventBusFactory.getEventBus();
        myPlayer= new Player();
        String menuPath = path + "/data/bgm/Theme.m4a";
        String bgmPath = path + "/data/bgm/BGM.mp3";
        String fightPath = path + "/data/bgm/Fight.m4a";
        try {
            menuMP = new MediaPlayer(new Media(new File(menuPath).toURI().toURL().toString()));
            bgmMP = new MediaPlayer(new Media(new File(bgmPath).toURI().toURL().toString()));
            fightMP = new MediaPlayer(new Media(new File(fightPath).toURI().toURL().toString()));
        } catch (MalformedURLException e) {
            //bad url
        }
    }

    /**
     / This is the subscription to the ActionEvents that are posted by the inputsystem.
     */
    @Subscribe
    public void playAction(ActionEvent event) throws MalformedURLException {
        //String newPath = path + "/characters/Lucina1/sounds/" + event.getName() +".mp3";
        String newPath = path + "/characters/Lucina1/sounds/" + "JAB.mp3";
        //String newPath = "/example_character_1/attacks/JAB.mp3";
        //System.out.println(newPath);
        myPlayer.playClip(newPath, fxvol);
    }

    /**
     / Listens for the combat system to play the sound
     */
    @Subscribe
    public void playAction(SuccessfulEvent event) throws MalformedURLException{
        String newPath;
        if(event instanceof AttackSuccessfulEvent){
            newPath = path + "/characters/Lucina1/sounds/" + "ATTACK.mp3";
        }
        else if(event instanceof JumpSuccessfulEvent){
            newPath = path + "/characters/Lucina1/sounds/" + "JUMP.wav";
        }
        else{
            newPath = path + "/characters/Lucina1/sounds/" + "WALKING.wav";
        }

        myPlayer.playClip(newPath, fxvol);

        //myMessageBus.post(new SuccessfulSoundEvent(1));
    }

    @Subscribe
    public void playGameMusic(GameStartEvent event) throws MalformedURLException {
        menuMP.pause();
        playMusic(bgmMP, bgmvol);
    }

    @Subscribe
    public void changeBGMVolume(BGMVolumeEvent newVol){
        bgmvol = newVol.getVolume();
        menuvol = newVol.getVolume();
        fightvol = newVol.getVolume();
        bgmMP.setVolume(newVol.getVolume());
        menuMP.setVolume(newVol.getVolume());
        fightMP.setVolume(newVol.getVolume());
    }


    @Subscribe
    public void playMenuMusic(MenuStartEvent event) throws MalformedURLException {
        menuMP.play();
        bgmMP.stop();
    }

    @Subscribe
    public void exitMenu(ExitMenuEvent event){
        menuMP.stop();
    }

    @Subscribe
    public void selectScreen(FightStartEvent event) throws MalformedURLException {
        playMusic(fightMP, fightvol);
    }

    @Subscribe
    public void exitSelectScreen(FightEndEvent event){
        fightMP.stop();
    }

    @Subscribe
    public void changeFXVolume(FXVolumeEvent newVol){
        fxvol = newVol.getVolume();
        ko.setVolume(newVol.getVolume());
    }

    @Subscribe
    public void playerDeath(PlayerDeathEvent death){
        ko.play();
    }

    /**
     / This is the subscription to the ActionEvents that are posted by the inputsystem.
     */
    @Subscribe
    public void gameOver(GameOverEvent gameOver){
        bgmMP.stop();
    }


    private void playMusic( MediaPlayer player, double volume){
        player.setCycleCount(MediaPlayer.INDEFINITE);
        player.setVolume(volume);
        player.play();
    }

    private void setRootPath(){
        path = GameDir.getPath();
    }
}
