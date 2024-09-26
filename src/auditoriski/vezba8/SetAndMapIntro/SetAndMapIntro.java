package auditoriski.vezba8.SetAndMapIntro;

import java.util.*;

public class SetAndMapIntro {

    /* Postojat 3 tipovi na sets */
    public static void main(String[] args) {

        /* vo TreeSet elementite mora da bidat comparable
         Set ni garantira deka ke imame unique elements, nema duplikati
         Kompleksnosta za pristap do eden element e O(log)
         dodeka kompleksnosta za iteriranje na eden TreeSet e  O(n*logn)
         dodavanje e O(logn), brishenje eO(n*logn) posto prvo treba da se najde pa da se izbrishe elementot */

        // ~ TreeSet se koristi koga vo zadachata se bara da nema duplikati i da bidat elementite sortirani
        Set<Integer> treeInSet = new TreeSet<>(); //rastecki redosled
        Set<Integer> treeInSetReverse = new TreeSet<>(Comparator.reverseOrder()); //opagjacki redosled


        /* Vo HashSet operaciite za dodavanje, brishenje, itn.. imaat kompleknost od O(1) */
        Set<Integer> treeHashSet = new HashSet<>();
        Set<String> hasStringSet = new HashSet<>();

        Set<String> linkedHashSet = new LinkedHashSet<>();

    }
}
