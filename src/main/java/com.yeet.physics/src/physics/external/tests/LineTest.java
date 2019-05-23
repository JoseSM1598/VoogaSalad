package physics.external.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import physics.external.Line;

import java.awt.geom.Point2D;

public class LineTest {

    @Test
    public void testBounds(){
        Point2D.Double p1 = new Point2D.Double(0,1);
        Point2D.Double p2 = new Point2D.Double(0,1);
        Line ln = new Line(p1, p2);

        int outSize = 2;
        for(int i = 0; i < outSize; i++){
            double[] targ = new double[2];
            targ[0] = 0;
            targ[1] = 0;
            assertEquals(targ[i], ln.getXBounds()[i], 0.01, "getXBounds is messed up");
        }

        outSize = 2;
        for(int i = 0; i < outSize; i++){
            double[] targ = new double[2];
            targ[0] = 1;
            targ[1] = 1;
            assertEquals(targ[i], ln.getYBounds()[i], 0.01, "getYBounds is messed up");
        }

        p1 = new Point2D.Double(0,0);
        p2 = new Point2D.Double(1,1);
        ln = new Line(p1, p2);

        outSize = 2;
        for(int i = 0; i < outSize; i++){
            double[] targ = new double[2];
            targ[0] = 0;
            targ[1] = 1;
            assertEquals(targ[i], ln.getXBounds()[i], 0.01, "getXBounds is messed up");
        }

        outSize = 2;
        for(int i = 0; i < outSize; i++){
            double[] targ = new double[2];
            targ[0] = 0;
            targ[1] = 1;
            assertEquals(targ[i], ln.getYBounds()[i], 0.01, "getYBounds is messed up");
        }
    }

}
