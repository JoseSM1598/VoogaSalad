package renderer.external.Structures;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile extends ImageView{

    private int xIndex;
    private int yIndex;
    private String myImageName;

    public Tile (Image image, int w, int h, int x, int y, String imageName){
        super(image);
        setFitWidth(w);
        setFitHeight(h);
        xIndex = x;
        yIndex = y;
        myImageName = imageName;
    }

    //unused
    public void setLocation(int x, int y){
        //setX(x*width);
        //setY(y*height);
        xIndex = x;
        yIndex = y;
    }

    public String getImageName() {
        return myImageName;
    }
}
