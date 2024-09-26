package auditoriski.vezba4_2023_2024.FunctionalInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ConsumerExample {

    //Funcskiski interface se onie interface koi imaat samo eden metod
    //i od niv mozime da napravime lambda izrazi

//----------------------------------Supplier/FunctionalInterface------------------------------------
//Genericki funkciski interface kojshto prima argument, no ne vrakja rezultat
//Najcesto se koristi za pecatenje, ili nekakva void akcija
//Se koristi vo stream-ovi, vo .forEach()

    public static void main(String[] args) {

        List<String> stringsList = new ArrayList<>();
        stringsList.add("Test");
        stringsList.add("blabla");
        stringsList.add("Makedonija");
        stringsList.add("NApredno programiranje teST");

        //anonimna klasa
        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer broj) {
                System.out.println(broj);
            }
        };

        //lambda izraz
        Consumer<Integer> consumerLambda = broj -> System.out.println(broj);

        for (int i=0; i<10; i++) {
            consumerLambda.accept(i);
            //prifati go brojot [i] odnosno ke go ispechati toj broj
        }

        Consumer<String> consumerLambdaString = linija -> System.out.println(linija);

        //ke gi ispechati site strings vo listata stringList
        stringsList.stream().forEach(consumerLambdaString);


    }
}
