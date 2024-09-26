package auditoriski.vezba11.MinMax_2023;

import java.util.*;

public class TestClass {

    static int NUM_OF_ELEM= 1000000;
    static int [] NIZA;

    static Random RANDOM_OBJ = new Random();

    public static void main(String[] args) {

        //generira stream od random int, so vrednost od 1 do 10 i gi stava vo nizata
        NIZA = RANDOM_OBJ
                .ints(NUM_OF_ELEM, 1, 11)
                .toArray();

        System.out.println(
                Arrays.stream(NIZA)
               .summaryStatistics()
        );
    }
}
