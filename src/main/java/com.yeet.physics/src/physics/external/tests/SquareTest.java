package physics.external.tests;

import org.junit.jupiter.api.Test;
import physics.external.Coordinate;
import physics.external.Dimensions;
import physics.external.Line;
import physics.external.Square;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SquareTest {

    public Square createSquare(){
        Coordinate start = new Coordinate(1,0);
        Dimensions dims = new Dimensions(5,2);
        List<Line> path = new ArrayList<Line>();

        Point2D.Double top0 = new Point2D.Double(start.getX(), start.getY());
        Point2D.Double top1 = new Point2D.Double(start.getX()+ dims.getSizeX(), start.getY());
        Line top = new Line(top0, top1);

        Point2D.Double bottom0 = new Point2D.Double(start.getX(), start.getY()+dims.getSizeY());
        Point2D.Double bottom1 = new Point2D.Double(start.getX()+ dims.getSizeX(), start.getY()+dims.getSizeY());
        Line bottom = new Line(bottom0, bottom1);

        Point2D.Double right0 = new Point2D.Double(start.getX() + dims.getSizeX(), start.getY());
        Point2D.Double right1 = new Point2D.Double(start.getX() + dims.getSizeX(), start.getY() + dims.getSizeY());
        Line right = new Line(right0, right1);

        Point2D.Double left0 = new Point2D.Double(start.getX(), start.getY());
        Point2D.Double left1 = new Point2D.Double(start.getX(), start.getY() + dims.getSizeY());
        Line left = new Line(left0, left1);

        path.add(top);
        path.add(bottom);
        path.add(right);
        path.add(left);

        Square test = new Square(path);
        return test;
    }

    @Test
    void boundsMinTestX(){
        Square test = this.createSquare();
        assertEquals(1.0, test.getMinX(), 0.01, "Test getMinX");
    }

    @Test
    void boundsMinTestY(){
        Square test = this.createSquare();
        assertEquals(0.0, test.getMinY(), 0.01, "Test getMinY");
    }

    @Test
    void boundsMaxTestX(){
        Square test = this.createSquare();
        assertEquals(6.0, test.getMaxX(), 0.1, "Test getMaxX");
    }

    @Test
    void boundsMaxTestY(){
        Square test = this.createSquare();
        assertEquals(2.0, test.getMaxY(), 0.1, "Test getMaxY");
    }

}