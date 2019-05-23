package player.internal;

import com.google.common.eventbus.EventBus;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import messenger.external.BGMVolumeEvent;
import messenger.external.EventBusFactory;
import messenger.external.FXVolumeEvent;
import messenger.external.VolumeChangeEvent;
import player.internal.Elements.MessageBar;
import renderer.external.Renderer;
import renderer.external.Structures.SliderBox;
import xml.XMLParser;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

import static player.internal.Elements.CharacterChooseDisplay.FORMAT_RECT;
import static player.internal.MainMenuScreen.*;
import static player.internal.SettingsScreen.*;

/** Screen for changing sound settings, sends messages to the editor
 *  @author bpx
 */
public class SoundsSettingsScreen extends Screen {

    private final EventBus myMessageBus = EventBusFactory.getEventBus();
    private MessageBar myMessageBar;

    private HashMap<String,String> myVolumeMap;
    private Button saveButton;

    private Consumer<Double> mySEChanger;

    private File volumeProperties;

    private SliderBox musicSllider;
    private SliderBox soundSlider;
    private SliderBox voiceSlider;

    public SoundsSettingsScreen(File gameDirectory, Group root, Renderer renderer, Image bg, SceneSwitch settingsSwitch, Consumer<Double> seChanger) {
        super(root, renderer);
        myMessageBar = new MessageBar(this.getMyRenderer().makeText(SOUNDS_TITLE,true,MESSAGEBAR_TITLE_FONTSIZE, Color.WHITE,0.0,0.0),
                this.getMyRenderer().makeText(SOUNDS_MSG,false,MESSAGEBAR_MSG_FONTSIZE,Color.BLACK,0.0,0.0),
                MESSAGEBAR_X,MESSAGEBAR_Y);
        myVolumeMap = new HashMap<>();
        mySEChanger = seChanger;
        volumeProperties = new File(gameDirectory.getPath()+"/gameproperties.xml");
        XMLParser volumePropertiesParser = new XMLParser(volumeProperties);
        HashMap<String, ArrayList<String>> volumeInfo = volumePropertiesParser.parseFileForElement("volume");
        HBox musicBox = createVolumeSlider("music_icon.png", "Music", this::setMusicVolume);
        HBox soundBox = createVolumeSlider("sound_icon.png","Sound", this::setSoundVolume);
        HBox voiceBox = createVolumeSlider("voice_icon.png","Voice", this::setVoiceVolume);
        ImageView background = new ImageView(bg);
        background.setFitHeight(SCREEN_HEIGHT);
        background.setFitWidth(SCREEN_WIDTH);
        StackPane topBar = makeTopBar(super.getMyRenderer().makeText("Sounds",true,55,Color.BLACK,0.0,0.0),
                makeButton(new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("back_button.png"))),
                "Back","Return to the main menu!",53.74,60.72,0.0,0.0,myMessageBar,
                event->settingsSwitch.switchScene()));
                new StackPane();
        StackPane mainContainer = new StackPane();
        mainContainer.setPrefSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        mainContainer.setAlignment(Pos.CENTER);
        VBox sliderContainer = new VBox(30.0);
        sliderContainer.setPrefSize(980.0,395.0);
        sliderContainer.setMaxSize(980.0,395.0);
        sliderContainer.setStyle(String.format(FORMAT_RECT,"0 77 0 77","0 77 0 77","rgba(255,255,255,0.4)")+"-fx-border-color: black; -fx-border-width: 3;");
        sliderContainer.setAlignment(Pos.CENTER);
        saveButton = super.getMyRenderer().makeStringButton("Save",Color.BLACK,true,Color.WHITE,40.0,925.0,678.0,313.0,80.0);
        saveButton.setOnMousePressed(event -> handleSaveButtonPressed());
        musicSllider.setNewValue(Double.parseDouble(volumeInfo.get("music").get(0)));
        soundSlider.setNewValue(Double.parseDouble(volumeInfo.get("sound").get(0)));
        voiceSlider.setNewValue(Double.parseDouble(volumeInfo.get("voice").get(0)));
        disableSaveButton();
        sliderContainer.getChildren().addAll(musicBox,soundBox,voiceBox);
        mainContainer.getChildren().addAll(sliderContainer);

        super.getMyRoot().getChildren().addAll(background,mainContainer,saveButton,topBar);
    }

    private void handleSaveButtonPressed(){
        disableSaveButton();
        myMessageBus.post(new VolumeChangeEvent(volumeProperties,"volume",myVolumeMap));
    }

    private void disableSaveButton(){
        saveButton.setOpacity(0.5);
        saveButton.setDisable(true);
    }

    private void enableSaveButton(){
        saveButton.setDisable(false);
        saveButton.setOpacity(1.0);
    }

    private void setVoiceVolume(Double volume) {
        myVolumeMap.put("voice",String.valueOf(volume));
        enableSaveButton();
    }

    private void setSoundVolume(Double volume) {
        myVolumeMap.put("sound",String.valueOf(volume));
        enableSaveButton();
        mySEChanger.accept(volume/100);
        myMessageBus.post(new FXVolumeEvent(volume/100));
    }

    private void setMusicVolume(Double volume) {
        myVolumeMap.put("music",String.valueOf(volume));
        enableSaveButton();
        myMessageBus.post(new BGMVolumeEvent(volume/100));
    }

    private HBox createVolumeSlider(String fileName, String label, Consumer<Double> volumeSetter) {
        HBox sliderBoxContainer = new HBox(48.0);
        sliderBoxContainer.setPrefSize(886.0,69.0);
        sliderBoxContainer.setAlignment(Pos.CENTER);
        ImageView sliderIcon = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream(fileName)));
        sliderIcon.setFitWidth(48.0);
        sliderIcon.setFitHeight(48.0);
        Text exampleText = super.getMyRenderer().makeText(label,true,59, Color.BLACK,0.0,0.0);
        SliderBox sliderBox = new SliderBox(label,exampleText.getFont(),100.0,volumeSetter,0.0,0.0,788.0);
        sliderBox.setMinWidth(788.0);
        sliderBox.setSliderWidth(430.0);
        sliderBox.setSpacing(46.5);
        sliderBoxContainer.getChildren().addAll(sliderIcon,sliderBox);
        if(label.equalsIgnoreCase("music")){
            musicSllider = sliderBox;
        }
        else if(label.equalsIgnoreCase("sound")){
            soundSlider = sliderBox;
        }
        else if(label.equalsIgnoreCase("voice")){
            voiceSlider = sliderBox;
        }
        return sliderBoxContainer;
    }

}
