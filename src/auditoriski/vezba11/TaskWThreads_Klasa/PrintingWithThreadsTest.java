package auditoriski.vezba11.TaskWThreads_Klasa;

import java.util.*;

//Niska
class PrinterNitka extends Thread {
    int numberToPrint; //represents the local storage

    public PrinterNitka(int numberToPrint) {
        this.numberToPrint = numberToPrint;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(numberToPrint*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(numberToPrint);
    }
}
public class PrintingWithThreadsTest {

    public static void main(String[] args) {

        //kreirame nitki
        List<Thread> listaOdThreads = new ArrayList<>();

        int n=10;
        for (int i=1; i<n; i++) {
            listaOdThreads.add(new PrinterNitka(i));
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
