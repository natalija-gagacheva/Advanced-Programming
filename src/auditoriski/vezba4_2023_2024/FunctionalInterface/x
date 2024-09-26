package auditoriski.vezba4_2023_2024.FunctionalInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PredicateExample {
    //Funcskiski interface se onie interface koi imaat samo eden metod
    //i od niv mozime da napravime lambda izrazi

//----------------------------------Predicate/FunctionalInterface------------------------------------
//Predicate e nekakov true/false condition
//Predicate e genericki funckiski interface kojshto ocekuva eden genericki tip, primer Integer
//Ovoj interface ima samo eden metod vo nego, kojshto vrakja boolean
//metodot proveruva dali nekoj uslov e ispolnet ili ne

//Predikatite se koristat najcesto vo FILTER stream() operator

    public static void main(String[] args) {

        List<String> stringsList = new ArrayList<>();
        stringsList.add("Test");
        stringsList.add("blabla");
        stringsList.add("Makedonija");
        stringsList.add("NApredno programiranje teST");

        //So Anonimna klasa
        Predicate<Integer> positiveNumberPredicate = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer>0;
            }
        };

        //So Lambda izraz
        Predicate<Integer> filterPositiveNumberLambda = broj -> broj>0;
        Function<String, Integer> barajIndeksLambda = ednalinija -> ednalinija.toLowerCase().indexOf("a");

        List<Integer> pechati1 = stringsList
                .stream()
                .map(barajIndeksLambda)
                .collect(Collectors.toList());

        //Primer zadacha: filtriraj gi samo pozitivnite broevi od stream-ot
        //Odnosno, filtriraj gi indeksite na onie elementi kade bukvata A se javuva barem ednas

        List<Integer> pechati2 = stringsList.stream()
                .map(barajIndeksLambda)
                .filter(filterPositiveNumberLambda)
                .collect(Collectors.toList());

        System.out.println(pechati1);
        System.out.println(pechati2);

        //Ishodot e
        // [-1, 2, 1, 1]
        //[2, 1, 1]
        //Bidejki samo prviot element "test" ne ja socinuva bukvata a
    }
}