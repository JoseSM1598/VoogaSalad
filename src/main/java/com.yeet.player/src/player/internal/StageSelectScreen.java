package player.internal;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import player.internal.Elements.StageGrid;
import renderer.external.Renderer;

import java.io.File;

/** Creates a dynamic display for all stages available to the user, allows the user to choose
 *  a stage to do battle on
 *  @author bpx
 */
public class StageSelectScreen extends Screen {

    private ImageView myStagePreview;
    private Text myStageName;

    private SceneSwitch nextScene;
    private String mySelectedStage;

    public StageSelectScreen(Group root, Renderer renderer, File gameDirectory, SceneSwitch prevScene, SceneSwitch nextScene) {
        super(root, renderer);
        this.nextScene = nextScene;
        ImageView background = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("stage_select_bg.jpg")));
        background.setFitWidth(1280);
        background.setFitHeight(800);
        VBox mainContainer = new VBox();
        mainContainer.setPrefSize(1280.0,800.0);
        mainContainer.setAlignment(Pos.CENTER);
        HBox topBar = new HBox();
        topBar.setPrefSize(1280.0,55.0);
        topBar.setAlignment(Pos.CENTER_LEFT);
        //set up back button
        Rectangle topBarSpacer = new Rectangle(15.0,55.0, Color.TRANSPARENT);
        ImageView backButton = new ImageView((new Image(this.getClass().getClassLoader().getResourceAsStream("back_button.png"))));
        backButton.setFitHeight(50.0);
        backButton.setFitWidth(50.0);
        Rectangle buttonHolderSpacer = new Rectangle(50.0,15.0, Color.TRANSPARENT);
        VBox buttonHolder = new VBox(buttonHolderSpacer,backButton);
        buttonHolder.setAlignment(Pos.CENTER);
        buttonHolder.setOnMouseEntered(event -> {
            buttonHolder.setScaleX(1.1);
            buttonHolder.setScaleY(1.1);
        });
        buttonHolder.setOnMouseExited(event -> {
            buttonHolder.setScaleX(1.0);
            buttonHolder.setScaleY(1.0);
        });
        buttonHolder.setOnMousePressed(event -> prevScene.switchScene());
        HBox stageContainer = new HBox(10.0);
        stageContainer.setPrefSize(1280.0,745.0);
        stageContainer.setAlignment(Pos.CENTER);
        VBox stagePreview = new VBox();
        stagePreview.setMinSize(415.0,360.0);
        stagePreview.setPrefSize(415.0,360.0);
        stagePreview.setMaxSize(415.0,360.0);
        stagePreview.setAlignment(Pos.CENTER);
        myStagePreview = new ImageView(new Image(this.getClass().getClassLoader().getResourceAsStream("random_stage.png")));
        myStagePreview.setFitWidth(415.0);
        myStagePreview.setFitHeight(260.0);
        StackPane stageNameHolder = new StackPane();
        stageNameHolder.setMinSize(415.0,100.0);
        stageNameHolder.setPrefSize(415.0,100.0);
        stageNameHolder.setMaxSize(415.0,100.0);
        stageNameHolder.setAlignment(Pos.CENTER);
        stageNameHolder.setStyle("-fx-background-color: black");
        myStageName = this.getMyRenderer().makeText("Random",true,50, Color.WHITE,0.0,0.0);
        VBox stageGridHolder = new VBox();
        stageGridHolder.setPrefSize(835.0,600.0);
        StageGrid stageGrid = new StageGrid(gameDirectory,(s,imageView)->setPreview(s,imageView),(s -> chooseStage(s)));
        topBar.getChildren().addAll(topBarSpacer,buttonHolder);
        stageNameHolder.getChildren().addAll(myStageName);
        stagePreview.getChildren().addAll(myStagePreview,stageNameHolder);
        stageGridHolder.getChildren().addAll(stageGrid);
        stageContainer.getChildren().addAll(stagePreview,stageGridHolder);
        mainContainer.getChildren().addAll(topBar,stageContainer);
        super.getMyRoot().getChildren().addAll(background,mainContainer);
    }

    public String getStage(){
        return mySelectedStage;
    }

    private void chooseStage(String s) {
        mySelectedStage = s;
        nextScene.switchScene();
    }

    private void setPreview(String s, ImageView imageView) {
        myStagePreview.setImage(imageView.getImage());
        myStageName.setText(s);
    }
}
