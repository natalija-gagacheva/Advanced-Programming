package auditoriski.vezba3.lambda;

public class LambdaTest {

    // Lambda izraz se koristat so cel da ne koristime Anonimni klasi
    // tie ke ni bidat povtorna implementacija na nekoj Interface, vo nashiot slucaj FunctionalINterface

    public static void main(String[] args) {

        // shto prima izrazot () - argumentite koi gi prima metodot na interfaceot, vo nashiot sluchaj samo eden e
        // ->
        // shto vrakja izrazot {} - body

        FunctionalInterface complexBody = (x,y) -> {
            System.out.println("filler text");
            return x+y;
        };

        FunctionalInterface simpleBody = (x,y) -> x * y;
    }
}
