package auditoriski.vezba4.WordCount;

import javax.sound.sampled.Line;
import java.io.*;
import java.util.Scanner;

//programa koja go prikazhuva brojot na znaci, zborovi i redovi vo dadena datoteka
public class WordCount {

    //metod za chitanje na podatoci

    //argumentot vo metodot e samo klasa InputStream so cel da ovozmozhime default citanje
    //na podatoci od bilo kade (file, tastatura...)
    //od kade ke chitame se odluchuva preku main metodata

    //--------------------------------------------------------------------------1st NACIN
    public static void readDataWSkener(InputStream inputStrim) {
        //chitanje od tastatura
        // Scanner skener = new Scanner(System.in); -- System e klasa od Java

        //chitanje od nekade
        int brLinii=0, brZborovi=0, brKarakteri=0;
        Scanner skener = new Scanner(inputStrim);

        //chitame se dodeka skener ima nova linija
        while (skener.hasNextLine()) {
            String linija = skener.nextLine();
            ++brLinii; //imame edna linija plus
            brZborovi += linija.split("\\s+").length; //imame tolku broj na zborovi vo linijata
            brKarakteri += linija.length(); //dolzina na stringot
        }
        System.out.printf("Scanner:\nBroj na Linii: %d \n Broj na zborovi: %d \n Broj karakteri: %d\n", brLinii, brZborovi, brKarakteri);

    }

    //--------------------------------------------------------------------------2nd NACIN
    public static void readDataWBufferedReader(InputStream inputStrim) throws IOException {
        //PREDNOSTI: -nudi moznost da gi zemime site linii od inputot i da se parsiraat

        int brLinii=0, brZborovi=0, brKarakteri=0;
                                            //mora da kazheme od kade ke chita bufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStrim));

        String linija;
        //citame se dodeka ne vnesivme prazna linija
        //br.readLine() pravi problem bidejki saka da frli IOException
        while ((linija = br.readLine()) != null) {
            ++brLinii;
            brZborovi += linija.split("\\s+").length;
            brKarakteri += linija.length();
        }
        System.out.printf("BufferedReader:\n Broj na Linii: %d \n Broj na zborovi: %d \n Broj karakteri: %d\n", brLinii, brZborovi, brKarakteri);

    }

    //--------------------------------------------------------------------------3rd OPTIMAL NACIN
    //NAJCESTO KORISTEN NACIN NA CITANJE DATA

    public static void readDataWBufferedReaderMapAndReduce(InputStream inputStrim) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStrim));

        //lines() vrakja stream od stringovi,
        //t.e string po string ni gi dava site linii na inputot
        LineCounterKlasa resultat = br.lines()
                //.map() prima Fucntion, t.e zemam eden tip i pretvoram vo drug
                //linijata e nekoj string
                //za sekoja edna linija od inputot ja mapirame vo nekoj dr tip, najcesto objekt
                //vo nashiot slucaj mapirame od linijata sho ja citame od vlezenInput vo objekt od klasata LineCounterKlasa
                .map(ednaLinijaOdInputot -> new LineCounterKlasa(ednaLinijaOdInputot)) //se povikuva konstruktorot koj shto prima string vo LineCounterKlasata
                //za sekoja ednaLinija ke se kreira po eden objekt od taa klasa
                //toa znaci deka za sekoj red od inputot ke imame nov line counter
                //nas ni treba od mnogu LineCounters da gi spoime vo eden
                //sho znaci ke iskoristime reduce()

                //identity value
                .reduce(
                        new LineCounterKlasa(0, 0, 0), (levo, desno) -> levo.suma(desno)
                );
                                                            //levo i desno se objekti od klasa LC
                                                            //levo e megjurezultatot kojshto se akumulira, pocnuvajki od 0,0,0
                                                            //desno e sekoj nareden
                                                            //otkako ke se povika levo.suma(desno) rezultatot se chuva vo levo
                                                            //koga ke dojdi sledniot "desno", "levo" veke ke gi ima site akumulirani preth rezultati

        //Example: Otkako ke se napravi .map()
        // imame 10 linii od vlezenInput, so toa imame 10 objekti od klasata LineCounters
        // potoa, koga ke pocnime so .reduce() go zema identity value, sho vo nashiot sluchaj e objektot
        // i mu pravi logika na dvata objekti (levo,desno) koi se preneseni kako argumenti
        // vo nashiot sluchaj go sobira momentalniot, so sledniot

        System.out.println(resultat);


    }

    //--------------------------------------------------------------------------4th NACIN
    public static void readDataWConsumer(InputStream inputStrim) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStrim));


        //so .lines() imame stream od linii, a .forEach kazhuva za sekoja edna linija da naprajme nesto
        //.forEach() e terminalna akcija
        //stream od linii e sekvenca od linii shto gi dobivame od vlezenInput

        //kreirame instanca od klasata LineConsumer
        LineConsumerKlasa lineconsumer = new LineConsumerKlasa();
                        //.forEach() bara konzumer nekakov, nie samite ke kreirame User-Consumer
                        //za sekoja linija, go isprakjame objektot lineconsumer kako argument
                        //sho znaci kreirame instanca koja go implementira metodot accept()
                        //odnosno znae na koj nacin da gi konzumira stream-ot od linii

        //iako LineConsumerKlasa e Functional Interface i mozime da go implementirame so lambda izraz ili so
        //anonimna klasa, no ako sakame postoi opcija da se implementira so realna klasa
        br.lines().forEach(lineconsumer);

        System.out.println(lineconsumer);
    }



    //--------------------------------------------------------------------------MAIN METHOD
    public static void main(String[] args) {

        //ako sakame da bidi chitanje od tastatura, ke go napishime metodot
        //kade shto argumentot ke bidi citanje od stdin
        //readDataWithSkener(System.in);

        //za da chitame data od file, ni treba objekt od klasata File
        File datoteka = new File("C:\\Users\\User\\IdeaProjects\\Napredno\\src\\auditoriski\\vezba4\\files\\Questions- OOAaD.txt");

        //sekogas kako argument mora da dademe argument od klasata InputStream
        //FileInputStream kreira nekakov exception, pa nie mora da napravime TRY-CATCH block
        try {
            readDataWSkener(new FileInputStream(datoteka));
            readDataWBufferedReader(new FileInputStream(datoteka));
            readDataWBufferedReaderMapAndReduce(new FileInputStream(datoteka));
            readDataWConsumer(new FileInputStream(datoteka));
        } catch (IOException e) {
            e.printStackTrace();


            //IOException e roditel na FileNotFoundException, pa zatoa nemame potreba od
            //2 fakjanja na iskluchokot so FileNotFoundException, sekako ke se fati samo so IOException
            //BEFORE:

            //try {
            //            readDataWSkener(new FileInputStream(datoteka));
            //            readDataWBufferedReader(new FileInputStream(datoteka));
            //        } catch (FileNotFoundException e) {
            //            e.printStackTrace();
            //        } catch (FileNotFoundException e) {
            //            e.printStackTrace();
            //        }
        }
    }
}
