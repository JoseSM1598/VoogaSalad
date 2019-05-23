package audio.Internal;

import javafx.scene.media.AudioClip;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Player {

    public void playClip(String path, double volume) throws MalformedURLException {
        File file = new File(path);
        AudioClip clip = new AudioClip(file.toURI().toURL().toString());
        clip.play(volume);
    }



    public void playLongAudio(String path){
        //URL resource = getClass().getResource(path);
        //Media sound = new Media(resource.toString());
        //MediaPlayer mediaPlayer = new MediaPlayer(sound);
        //mediaPlayer.setAutoPlay(true);
        //mediaPlayer.setVolume(1);
        //mediaPlayer.play();

        URL resource = getClass().getResource(path);
        //AudioClip clip = new AudioClip(resource.toString());
        AudioClip clip2 = new AudioClip(resource.toString());
        clip2.setCycleCount(AudioClip.INDEFINITE);
        clip2.play(15);
    }
}
