/**
 * Class representation of each level or stage.
 */

package renderer.external.Structures;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import java.util.ArrayList;
import java.util.HashMap;

public class Level extends GridPane{
    private static final int TILE_WIDTH = 25;
    private static final int TILE_HEIGHT = 25;

    private Tile [][] grid;
    private int numCols;
    private int numRows;
    private String backgroundURL;
    private int windowHeight;
    private int windowWidth;

    private static final int WINDOW_HEIGHT = 400;
    private static final int WINDOW_WIDTH = 400;
    ImageView background;
    Pane window;

    public Level(Image bk){
        grid = new Tile[WINDOW_HEIGHT/TILE_HEIGHT][WINDOW_WIDTH/TILE_WIDTH];
        background = new ImageView();
        setBackground(bk);
        window = new Pane();
        window.setPrefWidth(WINDOW_WIDTH);
        window.setPrefHeight(WINDOW_HEIGHT);
        window.getChildren().add(background);

    }
    /**
     * Constructs the level
     * @param windowWidth = width of window of pane
     * @param windowHeight = height of window of pane
     * @param backgroundURL = url for background image
     */
    public Level(int windowWidth, int windowHeight, String backgroundURL){
        super();

        this.windowHeight = windowHeight;
        this.windowWidth = windowWidth;

        setGridLinesVisible(true);

        setMinWidth(windowWidth);
        setMaxWidth(windowWidth);
        setMinHeight(windowHeight);
        setMaxHeight(windowHeight);


        this.backgroundURL = backgroundURL;
        resetGrid();


        numCols = windowWidth/TILE_WIDTH;
        numRows = windowHeight/TILE_HEIGHT;


        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            getRowConstraints().add(rowConst);
        }
    }

    /**
     * resets grid using constructed window width and height
     */
    public void resetGrid(){
        grid = new Tile[windowHeight/TILE_HEIGHT][windowWidth/TILE_WIDTH];
        getChildren().clear();
        setBackground(backgroundURL);
    }

    /**
     * creates new tile given image, places it at index y, x (location x, y)
     * @param x
     * @param y
     * @param tileImage
     */
    public void processTile(int x, int y, Image tileImage, String tileName){
        if (y >= grid.length || x >= grid[0].length){
            return;
        }
        if (!isTile(x, y)){
            grid[y][x] = new Tile(tileImage, TILE_WIDTH, TILE_HEIGHT, x, y, tileName);
            add(grid[y][x], x, y);
        }
        else{
            getChildren().remove(grid[y][x]);
            grid[y][x] = null;
        }
    }

    /**
     * sets background image
     * @param backgroundURL image for background
     */
    public void setBackground(String backgroundURL){
        this.backgroundURL = backgroundURL;
        String formatted = String.format("-fx-background-image: url('%s');", backgroundURL);
        formatted = formatted + String.format("-fx-background-size: cover;");
        setStyle(formatted);
    }

    public int getTileWidth(){
        return TILE_WIDTH;
    }
    public int getTileHeight(){
        return TILE_HEIGHT;
    }

    /**
     * checks whether tile exists at current location
     * @param x
     * @param y
     * @return
     */
    public boolean isTile(int x, int y){
        return grid[y][x] != null;
    }

    public HashMap<String, ArrayList<String>> createLevelMap() {
        HashMap<String, ArrayList<String>> levelMap = new HashMap<>();
        levelMap.put("x", new ArrayList<>());
        levelMap.put("y", new ArrayList<>());
        levelMap.put("image", new ArrayList<>());
        for(int i = 0; i < numCols; i++) {
            for(int j = 0; j < numRows; j++) {
                if(isTile(i,j)) {
                    levelMap.get("x").add(Integer.toString(i));
                    levelMap.get("y").add(Integer.toString(j));
                    levelMap.get("image").add(grid[j][i].getImageName());
                }
            }
        }
        return levelMap;
    }
    public void setBackground(Image bk){
        background.setImage(bk);

    }

    public Pane getWindow(){
        return window;
    }

}
