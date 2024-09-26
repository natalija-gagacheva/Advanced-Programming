package auditoriski.vezba11.TaskWThreads_Runnable;

import java.util.*;

//Niska
class PrinterNitka extends Thread {
    int numberToPrint; //represents the local storage

    public PrinterNitka(int numberToPrint) {
        this.numberToPrint = numberToPrint;
    }

    @Override
    public void run() {
        System.out.println(numberToPrint);
    }
}
public class PrintingWithThreads {

    public static void main(String[] args) {

        //kreirame nitki
        List<Thread> listaOdThreads = new ArrayList<>();

        int n=10;
        for (int i=1; i<n; i++) {
            int finalI = i;
            listaOdThreads.add(new Thread(() -> System.out.println(finalI)));
        }

        //starts all the threads
        listaOdThreads
                .forEach(ednaNitka -> ednaNitka.start());

        //waits for the threads to end (ne koristime streams, join frla exception)
        for (Thread ednaNitka : listaOdThreads) {
            try {
                ednaNitka.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
