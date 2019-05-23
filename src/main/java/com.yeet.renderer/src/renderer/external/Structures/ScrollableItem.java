package renderer.external.Structures;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.text.Font;
import renderer.external.RenderSystem;

public class ScrollableItem {

    private static final double TILE_WIDTH = 128;


    private Button button;
    private RenderSystem rs;
    private Font myEmphasisFont;
    private Font myPlainFont;
    private Image image;


    public ScrollableItem(Image image, double x, double y){
        this.image = image;

        rs = new RenderSystem();
        button = rs.makeImageButton(image,x,y,128.0,128.0);
        button.setBackground(Background.EMPTY); //toggle background


    }

//    public ScrollableItem(String text, double x, double y){
//
//    }

    public Button getButton() {
        return button;
    }

    public void setPos(double x, double y){
        button.setLayoutX(x);
        button.setLayoutY(y);
    }

    public Image getImage(){
        return image;
    }
}
