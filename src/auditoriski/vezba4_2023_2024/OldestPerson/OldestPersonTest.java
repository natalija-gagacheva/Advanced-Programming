package auditoriski.vezba4_2023_2024.OldestPerson;

import java.io.*;
import java.util.Comparator;
import java.util.stream.Stream;

//za da go najdime najstariot coek, mora da napravime mehanizam za objektite od klasata Person da bidat sporedlivi, t.e comparable
// odnosno mora klasata Person da go implementira interface-ot Comparable
class Person implements Comparable<Person>{
    String ime;
    int vozrast;

    public Person(String ime, int vozrast) {
        this.ime = ime;
        this.vozrast = vozrast;
    }

    @Override
    public String toString() {
        return "Coek{" +
                "ime='" + ime + '\'' +
                ", vozrast=" + vozrast +
                '}';
    }

    public Person (String linija) {
        this.ime = linija.split("\\s+")[0];
        this.vozrast = Integer.parseInt(linija.split("\\s+")[1]);
    }

    @Override
    public int compareTo(Person other) { //sporedbata ja pravime spored vozrasta
        return Integer.compare(this.vozrast, other.vozrast);
    }


}

public class OldestPersonTest {

    //zadachata bara da se otpechati imeto i vozrasta na najstariot coek
    //od bilo kakov vlezen protok
    public static Person pronajdiNajstar(InputStream inputstrim) {

        //citame red po red od datoteka, najdobro e da koristime BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(inputstrim));

        //celta e potokot od stringovi (stream) da se smeni vo luge od klasata Person
        //Konstruktorot na klasata Person ocekuva 2 raboti, ime i vozrast
        //shto znaci mora da go podelime vlezniot string so .split()

        //Promenlivata mora da bidi stream od tip Person

        return br.lines()
                //.map(linija -> new Person(linija.split("\\s+")[0], Integer.parseInt(linija.split("\\s+")[1])))
                //no bidejki ova reshenie e haos, ke napravime vtor konstruktor kade shto kako argument ke prima line
                .map(linija -> new Person(linija))
                //pred da go najdime najstariot coek, mora da napravime mehanizam za objektite od klasata Person da bidat sporedlivi, t.e comparable
                // odnosno mora klasata Person da go implementira interface-ot Comparable
                //na ovoj nacin ni e ovozmozheno da povikame .max()
                .max(Comparator.naturalOrder()) //ova znaci da gi sporeduva kako shto gi imame nie definirano
                                                //vrakja Optional so cel da se izbegni null pointer exception vo sluchaj stream-ot da e prazen
                .orElse(new Person("Nat 22")); //dokolku ne e najden max, resultatot da bidi Nat 22
    }           //.get() frla exception ako ne postoi, .orElse e po bezbedno

    public static void main(String[] args) {

        try {
            InputStream isFromFile = new FileInputStream(new File ("C:\\Users\\User\\IdeaProjects\\Napredno\\src\\auditoriski\\vezba4_2023_2024\\OldestPerson\\People"));
            Person najstar = pronajdiNajstar(isFromFile);
            System.out.println(najstar);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
