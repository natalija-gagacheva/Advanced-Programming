package auditoriski.vezba5.PriorityQueue;

import java.util.ArrayList;
import java.util.List;
//Priority Queue sekogas go vrakja elementot so najgolem prioritet
//za ovaa prioritetna redica ne e navedeno kakov tip na podatoci treba da cuva
//Sho znaci treba da cuvame podatochen generik
class PriorityQueueELEMENT<T> implements Comparable<PriorityQueueELEMENT<T>> {

    //posto ne znaeme element od kakov tip ke se cuva, koristime generici bilo E ili T
    private T element;
    private int priority;

    public PriorityQueueELEMENT(T element, int priority) {
        this.element = element;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "PriorityQueueElement{" +
                "element=" + element +
                ", priority=" + priority +
                '}';
    }

    @Override
    public int compareTo(PriorityQueueELEMENT<T> other) {
        return Integer.compare(this.priority, other.priority);
    }

    public int getPriority() {
        return priority;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public T getElement() {
        return element;
    }
}

class PriorityQueue<T>  {

    //zadachata bara redicata da se implementira kako lista od nekoj tip
    //za sekoj element od listata go cuvame elementot i koj mu e priority
    //ako cuvame samo List<T> toa ke go oznacuva samo item-ot t.e elementot
    //zatoa treba da kreirame posebna klasa posto treba da cuvame 2 promenlivi
    private List<PriorityQueueELEMENT<T>> elementsLista;

    //ne sprovedvame lista kako argument za konstruktorot oti celta ni e da ja inicijalizirame listata vnatre
    //najcesto we will pass the list as an argument when a list needs to be provided from an external source
    public PriorityQueue() {

        //pri sekoe povikuvanje objektot, se inicilizira nova lista, so cel da se izbegnat null ptr exceptions
        this.elementsLista = new ArrayList<PriorityQueueELEMENT<T>>();
    }

    //od zadacata ni se bara da kreirame metod za dodavanje i odzemanje element

    public void add(T item, int priority) {
        PriorityQueueELEMENT<T> novDodaenElement = new PriorityQueueELEMENT<T>(item, priority);

        //gi dodavame elementite spored prioritet
        //nema da koristime stream oti ni treba da koristime break
        int i;
        for (i = 0; i< elementsLista.size(); i++) {

            if (novDodaenElement.compareTo(elementsLista.get(i)) <= 0) break;

            //ke pravime sporedba spored prioritet, zato ke ja koristime klasata Comparable
            //se dodeka leviot e pomal ili ednakov na desniot
            //koga ke dojdi elementot da e pogolem, napravi break

        }
            //elementsLista.add(novDodaenElement); ---- go dodava elementot na kraj
             elementsLista.add(i, novDodaenElement);//go dodava elementot na specificna pozicija
    }

    public T remove() {
        if (elementsLista.size()==0) return null;

            //.remove() spored mene raboti kako .stream() nesto
            //go brishime elementot so najvisok prioritet, vo nashiot slucaj posledniot element
            //spored metodot nas ni treba da ni vrati T, dodeka .remove() vrakja PriorityQueueELEMENT
            //za da go zemime T, koristime .getElement
        return elementsLista.remove(elementsLista.size() -1).getElement();
        }
    }


public class PriorityQueueTest {
    public static void main(String[] args) {

        PriorityQueue<String> priorityqueue = new PriorityQueue<>();
        priorityqueue.add("middle1", 49);
        priorityqueue.add("middle2", 50);
        priorityqueue.add("middle3", 51);
        priorityqueue.add("top", 100);
        priorityqueue.add("bottom", 10);

        //ovaa promenliva sluzi za cuvanje na site stringovi koishto .remove() funckijata
        // koja sho go brishi elementot od listata so najgolem prioritet
        // i vrakja
        /*
        The remove() method in the PriorityQueue class is designed to return an element
         of the generic type T. However, in our PriorityQueueTest class,
         we are using a PriorityQueue<String>, which means the type T is specifically
         String in this instance.
         */

        //edno po edno gi brisime elementite od priority queue
        //vo zavisnost na nivniot prioritet i gi pecati na ekran
        //se dodeka ne se isprazni redicata, odnosno .remote() ne vrati null
        String element;
        while ((element = priorityqueue.remove()) != null) {
            System.out.println(element);
        }
    }
}
