package renderer.external.Structures;


import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import renderer.external.RenderSystem;
import renderer.external.Scrollable;


public class ScrollItem implements Scrollable {

    private ToggleButton button;
    private ToggleButton imageButton;
    private Image image;
    private ImageView imageView1;
    private ImageView imageView2;
    private RenderSystem rs;


    public ScrollItem(Image image, Text desc){
        rs = new RenderSystem();
        this.image = image;
        this.imageView1 = new ImageView(image);
        this.imageView2 = new ImageView(image);
        imageView1.setPreserveRatio(true);
        imageView2.setPreserveRatio(true);
        resize(100);
        initializeButton(desc);
        initializeImageButton();
    }

    public void initializeButton(Text desc){
        button = new ToggleButton(desc.getText(),imageView1);
        button.setTextFill(Color.WHITE);
        button.wrapTextProperty().setValue(true);
        rs.applyStyleAndEffect(button);
    }

    private void initializeImageButton(){
        imageButton = new ToggleButton("",imageView2);
        rs.applyStyleAndEffect(imageButton);
    }


    public void resize(int val){
        imageView1.setFitHeight(val);
        imageView2.setFitHeight(val);
    }


    @Override
    public void setNodeGraphic(Node key, String text) {

    }




    public ToggleButton getButton() {
        return button;
    }

    public ToggleButton getImageButton(){return imageButton;}

    public void setPos(double x, double y){
        button.setLayoutX(x);
        button.setLayoutY(y);
    }

    public Image getImage(){
        return image;
    }
}
