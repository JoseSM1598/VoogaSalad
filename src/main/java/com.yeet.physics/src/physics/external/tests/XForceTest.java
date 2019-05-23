package physics.external.tests;


import org.junit.jupiter.api.Test;
import physics.external.PhysicsVector;

import static org.junit.jupiter.api.Assertions.*;

public class XForceTest {

    private double expected = 120;

    @Test
    public void getMagnitude() {
        PhysicsVector XTest = new PhysicsVector(120, 0);
        PhysicsVector YTest = new PhysicsVector(120, -90);
        assertEquals(XTest.getMagnitude(), expected, 1);
        assertEquals(YTest.getMagnitude(), expected, 1);
    }
}