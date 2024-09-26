package auditoriski.vezba11.MinMax_2023_moreEfficient;
import java.util.*;
import java.util.stream.IntStream;

class Nitka extends Thread {
    //promenlivite se local storage za sekoja nitka, od koj oddel vo nizata da zapocne da prebaruva
    int startFrom;
    int end;
    IntSummaryStatistics intsumstat = new IntSummaryStatistics();

    public Nitka(int startFrom, int end) {
        this.startFrom = startFrom;
        this.end = end;
    }

    @Override
    public void run() { //ovaa nitka e zadolzena da presmeta summary statistics (min/max/sum) za nizata od glavnata klasa TestClass
        for (int i=startFrom; i<end; i++) {
            intsumstat.accept(TestClass.NIZA[i]);
        }
    }

    @Override
    public String toString() {
        return "Nitka [" +
                "startFrom=" + startFrom +
                ", end=" + end + " " + intsumstat +
                ']';
    }
}
public class TestClass {

    static int NUM_OF_ELEM= 1000000;
    static int [] NIZA;
    static int NUM_THREADS=100;

    static Random RANDOM_OBJ = new Random();

    public static void main(String[] args) {

        //generira stream od random int, so vrednost od 1 do 10 i gi stava vo nizata
        NIZA = RANDOM_OBJ
                .ints(NUM_OF_ELEM, 1, 11)
                .toArray();

        List<Nitka> listaOdThreads = new ArrayList<>();

        int dolzinaNaSubNiza = NUM_OF_ELEM/NUM_THREADS;

        //odreduvame pocetok i kraj na sekoj oddel vo nizata
        for (int i=0; i<NUM_THREADS; i++) {
            int pocetokNaOddel = i * dolzinaNaSubNiza;
            int krajNaOddel = (i+1) * dolzinaNaSubNiza;
            listaOdThreads.add(new Nitka(pocetokNaOddel,krajNaOddel));
        }

        //starting the threads
        for (Nitka oneThread : listaOdThreads) {
            oneThread.start();
        }

        for (Nitka oneThread : listaOdThreads) {
            try {
                oneThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //printing each thread
        for (Nitka oneThread : listaOdThreads) {
            System.out.println(oneThread);
        }

        //minimum na celata niza
        int minCelaNiza = listaOdThreads
                                    .stream()
                                    .mapToInt(edenThread -> edenThread.intsumstat.getMin())
                                    .min()
                                    .getAsInt();

        System.out.println(minCelaNiza);

    }
}
