package renderer.external;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class RenderUtils {

    private RenderUtils(){}

    public static String toRGBCode( Color color )
    {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }
    
    public static void centerCrop(ImageView imageView){
        double newMeasure = (imageView.getImage().getWidth() < imageView.getImage().getHeight()) ? imageView.getImage().getWidth() : imageView.getImage().getHeight();
        double x = (imageView.getImage().getWidth() - newMeasure) / 2;
        double y = (imageView.getImage().getHeight() - newMeasure) / 2;

        Rectangle2D rect = new Rectangle2D(x, y, newMeasure, newMeasure);
        imageView.setViewport(rect);
        imageView.setSmooth(true);
    }

    public static void throwErrorAlert(String header, String content){
        RenderSystem.createErrorAlert(header, content);
    }
}
