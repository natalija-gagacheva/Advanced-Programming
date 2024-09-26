package auditoriski.vezba4_2023_2024.FunctionalInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FunctionExample {
    // Funcskiski interface se onie interface koi imaat samo eden metod
    // i od niv mozime da napravime lambda izrazi

    //----------------------------------Function/FunctionalInterface------------------------------------
    // Function kako Functional Interface vo sebe ima eden metod - apply()
    // Primer za Function<String,Integer> metodot prima argument string a vrakja integer
    // Najcesto se koristi da konvertira od eden vo drug tip na podatok

    public static void main(String[] args) {

        List<String> stringsList = new ArrayList<>();
        stringsList.add("Test");
        stringsList.add("BitolA");
        stringsList.add("Wowow");

        // pozicija na koja sho se pojavuva bukvata A za prv pat

        // so Anonimna klasa
        Function<String, Integer> dolzinaFunction = new Function<String, Integer>() {

            @Override
            public Integer apply(String linija) {
                return linija.indexOf("A");
            }
        };

        // so lambda izraz
        Function<String, Integer> dolzinaFunctionLambda = ednalinija -> ednalinija.toLowerCase().indexOf("a");

        List<Integer> pechati = stringsList
                .stream()
                .map(dolzinaFunctionLambda)
                .collect(Collectors.toList());
        // ==> Kreiravme stream() od site 3 stringovi vo listata StringList.
        // ==> Sekoj element na listata, so pomosh na .map() operatorot, od String ja pretvarame vo Integer
        // ==> taa brojka Integer oznacuva na koja pozicija za prvpat se javila bukvata "A", bez razlika na goleminata na bukvite
        // ==> Site elementi od integers gi sobirame vo edna lista so pomosh na .collect(Collectors.toList())

        System.out.println(pechati);

        // dobivame [-1, 5, -1] radi sho vo nieden od elementite od listata ja nema bukvata A, osven vo "Bitola" i e na 5ta pozicija

    }
}
