package physics.external.tests;

import org.junit.jupiter.api.Test;
import physics.external.NetVectorCalculator;
import physics.external.PhysicsVector;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.*;

class NetForceCalculatorTest {

    List<PhysicsVector> forces = new ArrayList<>();

    @Test
    void getNetForce() {
        forces.add(new PhysicsVector(100, 0));
        forces.add(new PhysicsVector(100, PI/2));
        NetVectorCalculator calc = new NetVectorCalculator(forces);
        PhysicsVector expected = new PhysicsVector(Math.sqrt(20000), PI/4);

        assertEquals(expected.getMagnitude(), calc.getNetVector().getMagnitude());
        assertEquals(expected.getDirection(), calc.getNetVector().getDirection());
        //System.out.println("Result Magnitude: " + calc.getNetVector().getMagnitude());
        //System.out.println("Result Direction: " + calc.getNetVector().getDirection());
    }

    @Test
    void manyForces() {
        forces.add(new PhysicsVector(100, PI/2));
        forces.add(new PhysicsVector(100, -PI/2));
        forces.add(new PhysicsVector(100, PI/2));
        forces.add(new PhysicsVector(100, -PI/2));
        forces.add(new PhysicsVector(200, 0));
        forces.add(new PhysicsVector(100, 1*PI));
        NetVectorCalculator calc = new NetVectorCalculator(forces);
        PhysicsVector expected = new PhysicsVector(100, 0);

        assertEquals(expected.getMagnitude(), calc.getNetVector().getMagnitude(), 1);
        assertEquals(expected.getDirection(), calc.getNetVector().getDirection(), 1);
        //System.out.println("Result Magnitude: " + calc.getNetVector().getMagnitude());
        //System.out.println("Result Direction: " + calc.getNetVector().getDirection());
    }
}