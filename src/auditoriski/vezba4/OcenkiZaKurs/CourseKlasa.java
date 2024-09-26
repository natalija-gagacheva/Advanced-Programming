package auditoriski.vezba4.OcenkiZaKurs;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//Vo ovoj Kurs nie ke cuvame studenti
public class CourseKlasa {

    //DOKOLKU VO OVAA SITUACIJA, CUVAME NIZA OD STUDENTI, NEMA DA NI GI DADAT CELOSNO SITE POENI
    //TREBA DA CUVAME KOLEKCIJA, ODNOSNO LISTA BIDEJKI NEMAME PRETSTAVA KOLKU STUDENTI KE IMAME
    //NIZA SE KORISTI KOGA KE IMAME KONECHEN BROJ NA ELEMENTI, OTHERWISE KORISTIME LISTA

    //prvicno cuvame od tip List, pa posle definirame od kakov tip ke cuvame
    //vnatre vo konstruktorot mozheme da stavame bilo kakva implementacija od List
    private List<StudentKlasa> listaStudenti;

    public CourseKlasa() {
        //ArrayList vo pozadina e implementirana so niza
        //Linked list e vistinska lista, no ne menva mnogu osven za execution time
        this.listaStudenti = new ArrayList<>();
    }

    //potreben ni e metod za citanje na podatoci
    public void citajPodatoci(InputStream inputstrim) {
        //gi citame podatocite so Bufferedreader
        BufferedReader br = new BufferedReader(new InputStreamReader(inputstrim));

        //vo listata od studenti, od buffered reader zemi gi liniite
        //mapiraj gi liniite, ne preku konstruktor tuku preku static metod na klasata Student
        //mora da e staticki za da go pristapime preku klasa, oti ne mozime da go pristapime preku objekt oti nemame objekt
        this.listaStudenti = br.lines()
                                .map(linija -> StudentKlasa.kreirajStudent(linija))
                                .collect(Collectors.toList());
                                //collect gi zema liniite i gi stava vo lista
    }

    //metod za pecatenje na podatoci vo opagjacki redosled
    public void sortiranoPecatenje(OutputStream outputstrim) {

        //postoi klasa koja ni ovozmozuva preku nejzin objekt da pecatime
        //koja raboti so buffered pecatenje
        //t.e stava vo nekoj buffer se shto saka da ispechati
        //i koga ke treba da ispechati pravi flush na toj buffer
        //shto znaci MORA DA BIDE POVIKANO .flush() ili .closed() na samiot objekt
        PrintWriter objektPrintWriter = new PrintWriter(outputstrim);

        //najcesto za pechatenje ke koristime .foreach()
        //.stream() za da stignime do site studenti
        //za sekoj edenStudent povikuva System.out.println
        //.sorted() ja sortira sekvencata vo rastecki redosled oti taka ni e implementiran compareTo metodot
        //.sorted() vo pozadina go razgleduva compareTo metodot za da znae na koj nacin da sortira
        this.listaStudenti.stream().sorted().forEach(edenStudent -> objektPrintWriter.println(edenStudent));
                                //koga ke dobieme outputstream ne ne interesira kade e toj
                                //odnosno, vo ovaj sluchaj output stream mozhe da bide system.out ili file
                                //no mora da pecatime vo ovaj sluchaj so objekt od PrintWriter

        objektPrintWriter.flush(); //ne e zatvoren, no ke ispechati

        //objektPrintWriter.close(); ------ vnatre samoto si pravi i flush()
    }

    //metod za pecatenje detalni podatoci vo File
    public void detalnoPecatenje(OutputStream outputstrim) {

        PrintWriter objektPrintWriter = new PrintWriter(outputstrim);

        //pecatime vo pogolemiot format spored baranjata na zadachata od metodot od klasata Student
        this.listaStudenti.stream().sorted().forEach(edenStudent -> objektPrintWriter.println(edenStudent.pecatiCelosenFormat()));
        objektPrintWriter.flush();
    }

    public void distributedPecatenje(OutputStream outputstrim) {

        //presmetuvame za sekoja ocenka po kolku pati se pojavuva
        PrintWriter objektPrintwriter = new PrintWriter(outputstrim);

        //formirame niza od 6 elementi (A,B,C,D,E,F)
        int[] gradeDistributionNiza = new int[6];

        for (StudentKlasa edenStudent : listaStudenti ) {
            gradeDistributionNiza[edenStudent.getOcenka() - 'A']++;
            //vo gradeDistributionNiza na pozicija[0] gi cuvame tie sho se od 'A' tip
        }

        //ja pecatime nizata
        for (int i=0; i<6; i++) {
            objektPrintwriter.printf("%c -> %d\n", i + 'A', gradeDistributionNiza[i]);
            //mora da pechatime so objekt od PrintWriter, se so System.in
        }

        objektPrintwriter.flush();
    }
}
