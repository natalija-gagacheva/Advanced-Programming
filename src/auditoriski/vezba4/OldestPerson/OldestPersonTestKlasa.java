package auditoriski.vezba4.OldestPerson;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OldestPersonTestKlasa {

    //metod za citanje na podatoci
    //vrakjame List od person
    public static List<PersonKlasa> readData(InputStream inputstrim) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputstrim));

        //najcesto ke koristime map
        //vo ovoj sluchaj sakame od string da pretvorime vo nov Person
        //za taa cel, mora da napravime soodveten konstruktor
        return br.lines().map(linija -> new PersonKlasa(linija))
                //collect ima 2 situacii, da prima Supplier ili Collector
                //Collector e interface kosjho ovozmozuva kreiranje na nekvakvi kolekcii
                //na ovoj nacin, so Collectors.toList() go sobirame toj vlezenInput i gi pretvarame vo Lista od lugje
                .collect(Collectors.toList());
    }

    //------------------------------------------------------MAIN CLASS FOR TESTING
    public static void main(String[] args) {

        //sekogas tuka treba da ja pishime apsolutnata pateka
        File datoteka = new File("C:\\Users\\User\\IdeaProjects\\Napredno\\src\\auditoriski\\vezba4\\OldestPerson\\People");

        ///ja zemame Listata od luge koja shto ni ja vrati Collectors
                                                    //chitame od Files, FileInputStream frla iskluchok
        try {
            List<PersonKlasa> listaOdLugje = readData(new FileInputStream(datoteka));
            //otkako ja procitavme celata lista na lugje, mozime da najdime max

            Collections.sort(listaOdLugje); //sortirame od najmal kon najgolem
            //go zemame posledniot, radi nacinot na koja sho se sortira listata
            System.out.println(listaOdLugje.get(listaOdLugje.size()-1));

            //----------------------------------VTOR NACIN
            //.stream() na lista ni dava stream/sekvenca od elementite na taa lista
            //.max() bara Comparator

            //vrakja .stream() od listata so luge, zema niven max ako se podredeni po rastecki redosled
            //so .get() se zema toj element, mora da iskoristime .get() za da izlezime nadvor od stream-ot

            //Dokolku nema vnatre od baraniot element, ke vrati prazen optional blagodarenie na
            //java.util.Optional <T> kojshto pretstavuva wrapper na bilo koj objekt koj shto sprecuva da imam null pointer exception
            //ako ne napravime proverka so .isPresent() tuku direk so .get() probvame da go zemime elementot
            // taka nemashe da imame Optional, vlegvame vo risk od null pointer exception
            if(listaOdLugje.stream().max(Comparator.naturalOrder()).isPresent()) {
                System.out.println(listaOdLugje.stream().max(Comparator.naturalOrder()).get());
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
