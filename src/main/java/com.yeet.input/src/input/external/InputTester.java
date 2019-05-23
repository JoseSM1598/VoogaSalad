package input.external;

import audio.external.AudioSystem;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import messenger.external.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class InputTester extends Application {
    private static final String GAME_DIR_STRING = "/src/main/java/com.yeet.main/resources/defaultgame";

    EventBus myMessageBus;
    public InputTester(){
        myMessageBus = EventBusFactory.getEventBus();
    }

    /**
     *
     * Tester for catching events
     */
    @Subscribe
    public void printAction(ActionEvent ae){
        System.out.println(ae.getName());
    }

    public void doSomething() throws InterruptedException {
        //NORMALLY, this would be called by the front-end
        KeyInputEvent test4 = new KeyInputEvent(KeyCode.A);
        myMessageBus.post(test4);

        KeyInputEvent test5 = new KeyInputEvent(KeyCode.B);
        myMessageBus.post(test5);
        KeyInputEvent test = new KeyInputEvent(KeyCode.P);
        myMessageBus.post(test);


        var start1 = new MenuStartEvent();
        //myMessageBus.post(start1);

        GameOverEvent over = new GameOverEvent(0, new ArrayList<Integer>());
        //myMessageBus.post(over);

        //KeyInputEvent test3 = new KeyInputEvent(KeyCode.S);
        //myMessageBus.post(test3);
        //KeyInputEvent test4 = new KeyInputEvent(KeyCode.D);
        //myMessageBus.post(test4);
    }


    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Path gameDirPath = Paths.get(System.getProperty("user.dir"), GAME_DIR_STRING);
        File GameDir = new File(gameDirPath.toString());
        InputSystem IS = new InputSystem(GameDir);
        InputTester IT = new InputTester();
        AudioSystem AS = new AudioSystem(GameDir);
        EventBusFactory.getEventBus().register(AS);
        EventBusFactory.getEventBus().register(IS);
        EventBusFactory.getEventBus().register(IT);
        IT.doSomething();
    }
}
