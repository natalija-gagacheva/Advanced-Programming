package auditoriski.vezba5.GenerickoProgramiranje;

import java.util.stream.IntStream;

public class KutijaTest {

    public static void main(String[] args) {

     // na Ispiti.finki.ukim.mk (Moodle) ne ni dozvolva da ostavame prazni <>, ke mora
     // Kutija<Integer> kutija = new Kutija<Integer>();
        KutijaWStream<Circle> kutija = new KutijaWStream<>();

        //namesto da pisime for-ciklus mozeme da rabotime so stream

        //IntTSrwam kreira stream od integeri, a so .range() mu kazuvame od kade do kade da odi
        //so .forEach() go dodavame elementot(broj)
        // vo objektot od klasata Kutija, so metodata koja sami ja kreiravme za dodavanje elementi od tip <E> generik
        IntStream.range(0,10)
                .forEach(i -> new Circle());

        IntStream.range(0,13) //posto se 13 elementi, a nie 10 dodadovme, za poslednite 3 ke izvadi null
                .forEach(i -> System.out.println(kutija.drawElement()));
                //vlecenje element
    }
}



