package physics.external;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.*;

class PositionCalculatorTest {

    @Test
    void updatePositions() {
        double XExpected = 0;
        double YExpected = -10;

        PhysicsSystem system = new PhysicsSystem();
        //system.addPhysicsBodies(1);
        system.gameObjects.get(0).currentForces.add(new PhysicsVector(10, PI/2));

        system.applyForces();

        system.updatePositions();

        assertEquals(XExpected, system.gameObjects.get(0).getMyCoordinateBody().getPos().getX());
        assertEquals(YExpected, system.gameObjects.get(0).getMyCoordinateBody().getPos().getY());
    }
}