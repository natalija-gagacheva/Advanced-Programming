package auditoriski.vezba4_2023_2024.LambdaIzraz;

// Functional interface - interface koi ima tocno eden metod i od koj mozheme da napisheme lambda izraz
interface OperationInterface {

    //ovoj interface poseduva tocno eden edinstven metod
    //lambda expressions can be used instead of Anonimous class
    //IF AND ONLY IF the interface has exactly one method
    double izvrshi(int a, int b);
}

//Zastaren metod
//class Addition implements OperationInterface {
//
//    int a,b;
//
//    public Addition(int b, int a) {
//        this.b = b;
//        this.a = a;
//    }
//
//    @Override
//    public int executeMethod(int a, int b) {
//        return a+b;
//    }
//}

public class LambdaIntro {

    public static void main(String[] args) {

        //Sega, za da mozime da definirame objekt od tip Operation, nema potreba da koristime nova klasa
        //tuku so pomosh na anonimna klasa, vednash mozheme da definirame kako ke se izvrshuva metodot od interface-ot

        //sega, nie kreirame 3 objekt od OperationInterface, bez da treba da kreirame klasi
        OperationInterface sobiranje = new OperationInterface() {

            @Override
            public double izvrshi(int a, int b) {
                return a+b;
            }
        };

        OperationInterface odzemanje = new OperationInterface() {

            @Override
            public double izvrshi(int a, int b) {
                return a-b;
            }
        };

        //Anonymous operation can be replaced with lambda expression
        OperationInterface mnozenje = (a, b) -> a*b;

        //Ke napravime lambda izraz za delenje
        //Lambda izrazite se pishuvaat so strelki
        //levo od strelka: vlezen argument na edinstveniot metod na interface-ot, za koi ne mora da se definiraat tipovite
        //desno od strelka: izlez, shto pretstavuva return na metodot za toj specifichen objekt (pr.delenje)

        //za da bide rezultatot decimalen, treba barem eden od promenlivite da go kastirame
        OperationInterface delenje = (a,b) -> a/(float)b;

        int a=5, b=8;

        //sega, za da se izvrshat ovie operacii samo gi povikuvame soodvetnite metodi
        System.out.println(sobiranje.izvrshi(a,b));
        System.out.println(odzemanje.izvrshi(a,b));
        System.out.println(mnozenje.izvrshi(a,b));
        System.out.println(delenje.izvrshi(a,b));
    }

    //Anonimnite klasi mozhat da bidat zameneti so lambda izrazi
    //AKO I SAMO AKO IMAME TOCNO EDEN METOD VO INTERFACE-OT KADE SHTO SAKAME DA KREIRAME LAMBDA IZRAZI

}
