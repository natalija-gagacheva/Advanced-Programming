package auditoriski.vezba4.FuntionalInterfaceExample;

import java.util.Random;
import java.util.function.*;

//Ke koristime funckiski interface od Java (.foreach, .filter, .map)
//Ako e funkciski interface- mora da mozhime da napravime Anonimna Klasa
public class FunctioinalInterfaceTest
{
    public static void main(String[] args) {

        //1ST FUNCTIONAL INTERFACE -----------------------------------------------------------------------
        //Sekoj predikat prima nekakva vrednost, i sekogas vrakja boolean (true/false)
        //Predikat se koristi koga sakame da napravime nekakvo filtriranje spored nekoj uslov

        Predicate<Integer> pomalOd100 = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer < 100;
            }
        };

        //------------ISTOTO, SO LAMBDA IZRAZ:
        Predicate<Integer> pomalkuOd100 = integer -> integer < 100;

        //2ND FUNCTIONAL INTERFACE -----------------------------------------------------------------------
        //Mu davame na nekomu neshto
        // Ne prima argument, vrakja nekakov rezultat
        Supplier<Integer> integerSaplajer = new Supplier<Integer>() {
            @Override
            public Integer get() //samiot metod na Supplier ne prima argumenti no vrakja nekakov rezultat
                        //no arguments
            {
                return new Random().nextInt(100); //implementacija
            }
        };

        //------------ISTOTO, SO LAMBDA IZRAZ
                                                  //no arguments      //implementacija
        Supplier<Integer> integerSaplajerLambdaIzraz = () -> new Random().nextInt(100);


        //3RD FUNCTIONAL INTERFACE -----------------------------------------------------------------------
        //Zemame, konzumirame (.foreach)
        //Prima argument, no ne vrakja rezultat

        Consumer<String> stringKonzumer = new Consumer<String>() {
            @Override
            public void accept(String s) { //prima argument
                System.out.println(s); //ne vrakja rezultat, nie samo pechatime
            }
        };

        //------------ISTOTO, SO LAMBDA IZRAZ
                                                //arguments      //pechatenje
        Consumer<String> stringKonzumerLambdaIzraz = System.out::println;
//PRED: Consumer<String> stringKonzumerLambdaIzraz = s -> System.out.println(s);

        //Vo ovoj sluchaj, Lambda izrazot mozhe da bide zamenet so metod reference
        //radi sho se povikuva nekoj metod, koj shto soodvetno prima eden argument
        //i znae how to handle that argument
        //method reference znaci deka davate reference kon nekoj metod, no ne kazhuvate eksplicitno



        //4TH FUNCTIONAL INTERFACE -----------------------------------------------------------------------
        //se pravi nekakva operacija vrz edniot argument za da se dobie drugiot

        Function<Integer, String> FormattedNumberString = new Function<Integer, String>() {
            @Override
            // tip sho vrakjam  //tip sho primam
            public String apply(Integer integer) {
                return String.format("%d\n", integer);
            }
        };

        //------------ISTOTO, SO LAMBDA IZRAZ
                                                    //nema vrska dali e num ili integer, placeholder e samo
        Function<Integer, String> FormattedNumberStringLambdaIzraz = num -> String.format("%d\n", num);

        //5TH FUNCTIONAL INTERFACE -----------------------------------------------------------------------
        //primame 2 tipa na argumenti (mozhe da se od ist ili razlichen tip) i vrakjame rezultat
        //najcesto se koristi za da reducira nesto

        BiFunction<Integer, Integer, String> SumNumbersAndFormat = new BiFunction<Integer, Integer, String>() {

            @Override
               // vrakjame           //primame
            public String apply(Integer integer1, Integer integer2) {
                return String.format("%d + %d = %d\n", integer1, integer2, integer2+integer1);
            }
        };

        //------------ISTOTO, SO LAMBDA IZRAZ
        BiFunction<Integer,Integer, String> sumNumbersAndFormatLambdaIzraz = (i1, i2) -> String.format("%d + %d = %d\n", i1, i2, i1+i2);
    }
}
