package renderer.external.Structures;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

import static renderer.external.RenderUtils.toRGBCode;

/**
 * Creates a carousel of {@code String} elements that can be progressed or regressed through arrow controls
 *  @author bpx
 */
public class Carousel extends HBox {

    public static final int INACTIVE = 30;
    public static final String FORMAT_FONT = "-fx-font-family: '%s'; -fx-font-size: %s; -fx-text-fill: %s;";

    private Color myMainColor;
    private Color mySecondaryColor;

    private Polygon myLeftArrow;
    private Polygon myRightArrow;

    private List<String> myContent;
    private Text myLabel;
    private int index;

    /** Create a new Carousel with the specified parameters
     *  @param content The possible options for the {@code Carousel}
     *  @param x X position of the {@code Carousel}
     *  @param y Y Position of the {@code Carousel}
     *  @param w The width of the {@code Carousel} {@code String} display
     *  @param h The height of the {@code Carousel}
     *  @param spacing Amount of spacing between main carousel elements
     *  @param mainColor The main background fill for the {@code Carousel}
     *  @param secondaryColor The text fill color
     *  @param font The font for the text display */
    public Carousel(List<String> content, Double x, Double y, Double w, Double h, Double spacing, Color mainColor, Color secondaryColor, Font font){
        super(spacing);
        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setPrefWidth(w);
        this.setPrefHeight(h);
        this.setAlignment(Pos.CENTER);
        this.myContent = content;
        myMainColor = mainColor;
        mySecondaryColor = secondaryColor;
        myLeftArrow = new Polygon();
        myLeftArrow.getPoints().setAll(0.0,h/3,
                                    2*h/3,0.0,
                                    2*h/3,2*h/3);
        formatArrow(myLeftArrow, Color.TRANSPARENT, INACTIVE);
        myLeftArrow.setStroke(Color.TRANSPARENT);
        this.getChildren().add(myLeftArrow);
        HBox textbox = new HBox();
        textbox.setStyle("-fx-background-color: "+toRGBCode(myMainColor));
        index = 0;
        Text label = new Text(myContent.get(index));
        this.myLabel = label;
        //FORMAT_FONT: String fontName, Double fontSize, Color textColor
        myLabel.setStyle(String.format(FORMAT_FONT,font.getName(),font.getSize(),toRGBCode(mySecondaryColor)));
        myLabel.setFill(mySecondaryColor);
        textbox.setMinWidth(w);
        textbox.setFillHeight(true);
        textbox.getChildren().add(myLabel);
        textbox.setAlignment(Pos.CENTER);
        this.getChildren().add(textbox);
        myRightArrow = new Polygon();
        myRightArrow.getPoints().setAll(0.0,2*h/3,
                                    0.0,0.0,
                                    2*h/3,h/3);
        initialArrowCheck(myRightArrow);
        myRightArrow.setStroke(Color.BLACK);
        this.getChildren().add(myRightArrow);
        myLeftArrow.setOnMousePressed(event -> leftArrowClicked());
        myRightArrow.setOnMousePressed(event -> rightArrowClicked());
    }

    private void initialArrowCheck(Polygon rightArrow) {
        if(myContent.size()>1){
            rightArrow.setFill(myMainColor);
            rightArrow.setStroke(Color.BLACK);
        }
        else{
            formatArrow(rightArrow, Color.TRANSPARENT, INACTIVE);
            rightArrow.setStroke(Color.TRANSPARENT);
        }
    }

    private void rightArrowClicked() {
        if(index<myContent.size()-1){
            index++;
            myLabel.setText(myContent.get(index));
        }
        if(index==myContent.size()-1){
            formatArrow(myRightArrow, Color.TRANSPARENT, INACTIVE);
            myRightArrow.setStroke(Color.TRANSPARENT);
        }
        if(index>0){
            formatArrow(myLeftArrow, myMainColor, 100);
            myLeftArrow.setStroke(Color.BLACK);
        }
    }

    private void leftArrowClicked() {
        if(index>0){
            index--;
            myLabel.setText(myContent.get(index));
        }
        if(index==0){
            formatArrow(myLeftArrow, Color.TRANSPARENT, INACTIVE);
            myLeftArrow.setStroke(Color.TRANSPARENT);
        }
        if(index<myContent.size()-1){
            formatArrow(myRightArrow, myMainColor, 100);
            myRightArrow.setStroke(Color.BLACK);
        }
    }

    public String getValue(){
        return myLabel.getText();
    }

    public void setContent(List<String> newcontent){
        myContent = newcontent;
        index = 0;
        myLabel.setText(newcontent.get(0));
        leftArrowClicked();
    }

    private void formatArrow(Polygon leftArrow, Color gray, int inactive) {
        leftArrow.setFill(gray);
        leftArrow.setOpacity(inactive);
    }
}
