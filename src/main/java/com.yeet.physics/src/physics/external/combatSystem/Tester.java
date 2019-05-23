package physics.external.combatSystem;

import java.util.*;

public class Tester {

    public static void main(String[] args){

        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);


        for(int i: list){
           System.out.println(i);
        }

        System.out.println();
        while(!list.isEmpty()){
            System.out.println(list.remove());
        }

    }

}
