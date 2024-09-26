package auditoriski.vezba3.lambda;

//Anonimna klasa ja kreirame i instancirame vo ist moment, za 1 upotreba

public class AnonymousClassExample {

    //Anonimnata klasa mora da implementira nekoj interface
    //mora da se napravi Override na sekoj metod na toj interface vo anonimnata klasa !!!

    // IntelliJ samoto po sebe ni dava body od implementacijata na anonimna klasa
    // kako eden vid 'konstruktor' na FunctionalInterface
    FunctionalInterface Sobiranje = new FunctionalInterface() {
        @Override
        public double izvrshiOperacija(double a, double b) {
            return a+b;
        }
    };

    public static void main(String[] args) {
        AnonymousClassExample primer = new AnonymousClassExample();
        System.out.println(primer.Sobiranje.izvrshiOperacija(5, 7));
    }
}
