package auditoriski.vezba8.CountOccurances;

import java.util.Collection;

public class CountOccurancesTest {

    /* metodot vrakja broj na pojavuvanje na string-ot "linija" vo
    * kolekcijata od kolekcija od string-ovi

    /* Da pretpostavime deka Collection c sodrzi N kolekcii i deka
    * sekoja od ovie kolekcii sodrzi N objekti
    * Koe e vremeto na izvrshuvanje na vashiot metod?
    * ODGOVOR: Kompleksnosta e n^2, mora da se izmine sekoja
    kolekcija i vo edna kolekcija sekoja kolekcija dopolnitelno vo nea da se izmine
    t.e n kolekcii so n objekti, toa e matrica NxN i kompleksnosta ke bide n^2
     bidejki imame vgnezdeni ciklusi     */

    /* Da pretpostavime deka e potrebno 2 milisekundi da se izvrshi
    * za N = 100. Kolku ke bide vremeto na izvrshuvanje koga N=300 */

    public static int count(Collection<Collection<String>> c, String linija) {
        int counter = 0;

        /* ja iterirame kolekcijata red po red */
        for (Collection<String> edenRed : c) { //sekoj red vo edna kolekcija
            for (String edenElement : edenRed) {//sekoj element vo redot vo kolekcijata
                if (edenElement.equalsIgnoreCase(linija)) {
                    ++counter;
                }
            }
        }

        return counter;
    }

    /* Implementacija na metodot so Funckionalno Programiranje */

    public static int countWStreams(Collection<Collection<String>> c, String linija) {

        /* Koga imame kolekcija od kolekcii, najdobar operator koga
        * sakame da proverime nesto e flatMap, takashto za sekoja
        * kolekcija shto e vnatre vo glavnata kolekcija povikuvame nejzin stream, t.e
        * gi dobivame site strings koi se sodrzhat vo eden red/kolekcija */

        long result = c.stream()//vrakja stream od collections
                .flatMap(ednaKolekcija -> ednaKolekcija.stream())
                //na sekoja kolekcija povikuvame stream() kojshto vrakja stream
                // od strings sodrzhani vo kolekcijata od kolekcii od strignovi
                .filter(edenString -> edenString.equalsIgnoreCase(linija)) //sekoj string vo eden red/kolekcija
                .count(); //vrakja long

        return (int) result;
    }


    public static void main(String[] args) {

    }
}
