package auditoriski.vezba11.MinMax_2021;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

class SearcherThread extends Thread {
    //lokalni promenlivi samo za eden thread
    //vo koj del od nizata ARRAY ovoj thread da bara min/max, posto neli gi delime na pomali delovi
    int start;
    int end;
    int max;

    public SearcherThread(int start, int end) {
        this.start = start;
        this.end = end;
        this.max= TestClass.NIZA[start]; //pocnuva od prviot element vo toj oddel
    }

    public int getMax() {
        return max;
    }

    @Override
    public void run() {
        //start+1 posto veke prviot element go initialize na max
        for (int i=start+1; i<end; i++) {
            if (TestClass.NIZA[i]>max)
                max = TestClass.NIZA[i];
        }
    }
}

public class TestClass {

    static int NUM_OF_ELEM =1000000; //broj na elementi
    static int NUM_OF_THREADS = 1000; //broj na nitki koj ke go prebaruvaat max

    static Random RANDOM_OBJ = new Random(); //random generator za generiranje brojki
    static int NIZA [] = new int [NUM_OF_ELEM]; //niza od elementi

    public static void main(String[] args) {
        for (int i = 0; i< NUM_OF_ELEM; i++) {
            NIZA[i]= RANDOM_OBJ.nextInt(100000);
        }

        List<SearcherThread> listaOdSearchers = new ArrayList<>();

        int oddel = NUM_OF_ELEM / NUM_OF_THREADS;

        for(int start = 0; start< NUM_OF_ELEM; start=start+oddel) {
            int end = start + oddel;
            listaOdSearchers.add(new SearcherThread(start, end));
        }

        listaOdSearchers.forEach(edenSearcher ->edenSearcher.start());
        for (SearcherThread edensearcher : listaOdSearchers) {
            try {
                edensearcher.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //Kolku vreme e potrebno da ja pomine celata niza
        LocalDateTime startTime = LocalDateTime.now();
        int maxOddel = Arrays
                .stream(NIZA)
                .max()
                .getAsInt();
        LocalDateTime endTime = LocalDateTime.now();
        System.out.printf("Finding max with linear search: %d\n", Duration.between(startTime,endTime).toMillis());

        System.out.println(
                listaOdSearchers
                        .stream()
                        .mapToInt(edenSearcher -> edenSearcher.getMax())
                        .max()
                        .getAsInt()
        );
    }
}
