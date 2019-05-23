package physics.external;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PassiveForceHandlerTest {

    @Test
    void update() {
        PhysicsSystem system = new PhysicsSystem();
        //system.addPhysicsBodies(1);

        PassiveForceHandler handler = new PassiveForceHandler(system.gameObjects);
        handler.update();
        system.applyForces();

        double expectedAcceleration = 9.8;
        double expectedVelocity = expectedAcceleration * 0.016666666;
        assertEquals(expectedAcceleration, system.gameObjects.get(0).getAcceleration().getMagnitude());
        assertEquals(expectedVelocity, system.gameObjects.get(0).getVelocity().getMagnitude());
    }
}