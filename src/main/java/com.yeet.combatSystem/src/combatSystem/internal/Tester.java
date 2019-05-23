package combatSystem.internal;

import combatSystem.external.CombatSystem;
import messenger.external.CombatActionEvent;
import messenger.external.EventBusFactory;
import messenger.external.JumpEvent;

import java.util.Random;

public class Tester {

    static Double[][] A = { { 4.00, 3.00, 3.00}, { 2.00, 1.00 , 1.00}, {1.00, 2.00, 3.00} };
    static Double[][] B = { { -0.500, 1.500 }, { 1.000, -2.0000 } };

    public static Double[][] multiplicar(Double[][] A, Double[][] B) {

        int aRows = A.length;
        int aColumns = A[0].length;
        int bRows = B.length;
        int bColumns = B[0].length;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }

        Double[][] C = new Double[aRows][bColumns];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bColumns; j++) {
                C[i][j] = 0.00000;
            }
        }

        for (int i = 0; i < aRows; i++) { // aRow
            for (int j = 0; j < bColumns; j++) { // bColumn
                for (int k = 0; k < aColumns; k++) { // aColumn
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return C;
    }

    public static void main(String[] args){
//        CombatSystem combatSystem = new CombatSystem(4);
//        EventBusFactory.getEventBus().register(combatSystem);
//        CombatActionEvent event = new JumpEvent(1);
//        EventBusFactory.getEventBus().post(event);
//        EventBusFactory.getEventBus().post(event);
//        EventBusFactory.getEventBus().post(event);
//        Random random = new Random();
//        for(int i = 0; i < 10; i++){
//            System.out.println(random.nextDouble());
//        }

        Double[][] C = multiplicar(A, A);
        for(int r = 0; r < C.length; r++){
            for(int c = 0; c < C[0].length; c++){
                System.out.print(C[r][c] + " ");
            }
            System.out.println();
        }

//        System.out.println();

    }
}
