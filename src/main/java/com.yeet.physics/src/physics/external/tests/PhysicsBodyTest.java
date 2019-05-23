package physics.external.tests;
/*
import org.junit.jupiter.api.Test;
import physics.external.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PhysicsBodyTest {

    @Test
    void createPhysicsBody() {
        PhysicsBody body1 = new PhysicsBody(0,50, new Coordinate(0,0), new Dimensions(0,0));

        PhysicsVector expectedAcc = new PhysicsVector(0,0);
        PhysicsVector expectedVel = new PhysicsVector(0,0);
        assertEquals(expectedAcc.getMagnitude(), body1.getAcceleration().getMagnitude());
        assertEquals(expectedAcc.getDirection(), body1.getAcceleration().getDirection());
        assertEquals(expectedVel.getMagnitude(), body1.getVelocity().getMagnitude());
        assertEquals(expectedVel.getDirection(), body1.getVelocity().getDirection());
    }

    @Test
    void applyForce() {
        PhysicsBody body2 = new PhysicsBody(0,50, new Coordinate(0,0), new Dimensions(0,0));

        PhysicsVector expectedAcc = new PhysicsVector(0.4,0);
        PhysicsVector expectedVel = new PhysicsVector(.05,0);

        body2.applyForce(new PhysicsVector(20, 0));
        assertEquals(expectedAcc.getMagnitude(), body2.getAcceleration().getMagnitude());
        assertEquals(expectedAcc.getDirection(), body2.getAcceleration().getDirection());
        assertEquals(expectedVel.getMagnitude(), body2.getVelocity().getMagnitude());
        assertEquals(expectedVel.getDirection(), body2.getVelocity().getDirection());
    }

    @Test
    void applyMultipleForces() {
        PhysicsBody body3 = new PhysicsBody(0,50, new Coordinate(0,0), new Dimensions(0,0));

        PhysicsVector expectedAcc = new PhysicsVector(Math.sqrt(2) * 0.4,PI/4);
        PhysicsVector expectedVel2 = new PhysicsVector(Math.sqrt(2) * 0.05,PI/4);

        PhysicsVector force1 = new PhysicsVector(20, 0);
        PhysicsVector force2 = new PhysicsVector(20, PI/2);
        List<PhysicsVector> vecList = new ArrayList<>();
        vecList.add(force1);
        vecList.add(force2);
        NetVectorCalculator calc = new NetVectorCalculator(vecList);
        PhysicsVector resultVec = calc.getNetVector();
        body3.applyForce(resultVec);

        assertEquals(expectedAcc.getMagnitude(), body3.getAcceleration().getMagnitude(), 0.000001);
        assertEquals(expectedAcc.getDirection(), body3.getAcceleration().getDirection(), 0.000001);
        assertEquals(expectedVel2.getMagnitude(), body3.getVelocity().getMagnitude(), 0.000001);
        assertEquals(expectedVel2.getDirection(), body3.getVelocity().getDirection(), 0.000001);

    }
}
*/