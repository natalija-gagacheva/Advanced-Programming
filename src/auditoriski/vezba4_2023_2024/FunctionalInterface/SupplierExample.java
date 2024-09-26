package auditoriski.vezba4_2023_2024.FunctionalInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SupplierExample {

    //Funcskiski interface se onie interface koi imaat samo eden metod
    //i od niv mozime da napravime lambda izrazi

//----------------------------------Supplier/FunctionalInterface------------------------------------
//Supplier ne prima vrednosti, tuku samo vrakja rezultat

    public static void main(String[] args) {

        //Anonimna klasa
        Supplier<Integer> supplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return new Random().nextInt(10);
            }
        };

        //Lambda izraz, nemame argumenti zato se prazni ()
        Supplier<Integer> supplierLambda = () -> new Random().nextInt(10);

        //sekoj pat koga ke se povika Supplier, ke generira random broj od 1 do 10
        for (int i=0; i<10; i++) {
            System.out.println(supplierLambda.get());
        }


    }
}
