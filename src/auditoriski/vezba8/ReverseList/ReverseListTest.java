package auditoriski.vezba8.ReverseList;

/* Reverse list
Да се напише метод за печатење на колекција во обратен редослед
со помош на Collections API но без употреба на ListIterator.*/

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ReverseListTest {

    public static <T> void printInReverseOrder(Collection<T> kolekcija) {

        /* Gi stavame elementite od kolekcijata vo listata */
        List<T> lista = new ArrayList<>();
        lista.addAll(kolekcija);

        /* pecatenje vo obraten redosled, pocnuvame od najposleden element
        i iteratorot ke se vrakja nanazad */
        for (int i=lista.size()-1; i>=0; i--) {
            System.out.println(lista.get(i));
        }
    }

    public static <T> void printReverse(Collection<T> kolekcija) {

        /* Gi stavame elementite od kolekcijata vo listata */
        List<T> lista = new ArrayList<>();
        lista.addAll(kolekcija);

        /* This method reverses the order of the list*/
        Collections.reverse(lista);

        /* The reversed list is printed using a stream, each element one by one*/
        lista.stream()
                .forEach(edenElement -> System.out.println(edenElement));

    }


        public static void main(String[] args) {

        /* Kreirame kolekcija od integers */
        List<Integer> kolekcijaOdIntegers = List.of(1,2,3,4,5,6,7,8,9,10);

        printInReverseOrder(kolekcijaOdIntegers);

        List<Integer> collectionOfIntegers = List.of(50, 60, 70, 80, 90);
        printReverse(collectionOfIntegers);
    }
}
