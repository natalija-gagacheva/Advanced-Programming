package auditoriski.vezba6Kolokviumski.MojDDV;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/*
Да се имплементира класа MojDDV која што од влезен тек ќе чита информации за скенирани фискални сметки
од страна на еден корисник за таа апликација.

Постојат 3 типа на данок на додадена вредност: A-18% од вредноста, B-5%, V-0%
 */

enum TipNaDanokEnum {
    A,B,V
}
class Item {
    private int cena;
    private TipNaDanokEnum tipDanok;

    public Item(int cena, TipNaDanokEnum tipDanok) {
        this.cena = cena;
        this.tipDanok = tipDanok;
    }

    //konstruktor za koga ke sakame samo cenata da ja ispratime
    public Item(int cena) {
        this.cena = cena;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public TipNaDanokEnum getTipDanok() {
        return tipDanok;
    }

    public void setTipDanok(TipNaDanokEnum tipDanok) {
        this.tipDanok = tipDanok;
    }

    //na nivo na samiot item potrebno e da se napravi presmetka na danok
    public double presmetkaNaDanok() {
        if (tipDanok.equals(TipNaDanokEnum.A)) {
            return cena * 0.18;
        } else if (tipDanok.equals(TipNaDanokEnum.B)) {
            return cena * 0.05;
        } else
            return cena * 0.0; //za tip V
    }
}

class AmountNotAllowedException extends Exception {
    public AmountNotAllowedException(int amount) {
        super(String.format("receipt with amount %d is not allowed to be scanned", amount));
    }
}

/*potrebno e negde da se cuvaat site podatoci za fiskalnata smetka
Податоците за фискалните сметки се во следниот формат
ID item_price1 item_tax_type1 item_price2 item_tax_type2 item_price-n item_tax_type-n
На пр: 1234 1789 A 1238 B 1222 V 111 V */

class FiskalnaSmetka implements Comparable<FiskalnaSmetka> {
    private long id;
    private List<Item> itemsLista;

    public FiskalnaSmetka(long id, List<Item> itemsLista) {
        this.id = id;
        this.itemsLista = itemsLista;
    }

    //konstruktor samo so id---samo ne znam zosto
    public FiskalnaSmetka(long id) {
        this.id = id;
        this.itemsLista = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    //metodot mora da bide static posto go nemame objektot vo momentot koga go gradime
    //vaka izlgeda linijata koja ja primame: 1234 1789 A 1238 B 1222 V 111 V
    public static FiskalnaSmetka kreirajFiskalna (String linija) throws AmountNotAllowedException {
        String[] parts = linija.split("\\s+");
        long id = Long.parseLong(parts[0]);

        List<Item> fiskalnaItemsLista = new ArrayList<>();

        //Koristime klasa Arrays oti rabotime so niza
        Arrays.stream(parts) //isprakjame niza i vrakja sekvenca od array elementi
                .skip(1) //prviot veke go zedovme za id
                .forEach(i -> {
                    //mora da napravime proverka dali zemame price ili danocen tip
                    if(Character.isDigit(i.charAt(0))) //provervame dali prviot karakter na elementot od nizata e Digit, za ako e price
                    {
                        int cena = Integer.parseInt(i);
                        fiskalnaItemsLista.add(new Item(cena)); ///mora da kreirame nov konstruktor kojshto ke prima samo cena
                    } else {
                        int posledenElement = fiskalnaItemsLista.size()-1; //go zemame posledno dodaeniot element, koj se naogja na pozicija -1 od dolzinata na listata
                        TipNaDanokEnum danok = TipNaDanokEnum.valueOf(i); //mu ja dodeluvame vrednosta na TipNaDanok da bide vrednosta na i


                        fiskalnaItemsLista.get(posledenElement).setTipDanok(danok);
                    }
                }
                );

        //ne go fakjame tuka isklucokot
        //ako nekoja smetka ima nad 30.000 ti prodolzi so citanje na ostanatite, ama nea ne ja vklucuvaj
        //i frli isklucok, pa naknadno ke se fati na dr mesto
        if (iznosNaSmetka(fiskalnaItemsLista) > 30000) {
            throw new AmountNotAllowedException(iznosNaSmetka(fiskalnaItemsLista));
        }

        /*ne smeeme da frlame isklucok i da go fatime vo try-catch block, primer
        try {
            if (iznosNaSmetka(fiskalnaItemsLista) > 30000)
            throw new AmountNotAllowedException(iznosNaSmetka(fiskalnaItemsLista));
         } catch (AmountNotAllowedException)
         */

        //ako se e kako sho treba, kreiraj fiskalna smetka
        return new FiskalnaSmetka(id, fiskalnaItemsLista);
    }

    //Pri kreiranje na nov objekt fiskalnaSmetka, treba da proverime dali taja nadminva 30.000 den
    public static int iznosNaSmetka(List<Item> itemsLista) {

        //lista od elementi e, mora da iterirame niz site elementi preku stream
        int result = itemsLista.stream()
                .mapToInt(i -> i.getCena()).sum();


        return result;
    }

    //ke kreirame metod kojshto ne e static i ke raboti na lokalno nivo za promenlivite vo samata klasa
    public int iznosNaSmetka() {

        //lista od elementi e, mora da iterirame niz site elementi preku stream
        int result = itemsLista.stream()
                .mapToInt(i -> i.getCena()).sum();


        return result;
    }

    public double povratokNaDanok() {
        double result = itemsLista.stream()
                .mapToDouble(i  //za sekoj item vo fiskalna smetka
                        -> i.presmetkaNaDanok())//go pretvarame vo double vrednost od rezultatot na cenata+danokot
                .sum();
        return result;
    }

    //zaradi metodot pecatiSorted mora da implementirame comparable
    @Override
    public int compareTo(FiskalnaSmetka o) {

        return Comparator.comparing( (FiskalnaSmetka fiskalnaSmetka) -> fiskalnaSmetka.povratokNaDanok()) //mora da gi sortirame prvicno spored povratokot na ddv
                .thenComparing(fiskalnaSmetka1 -> fiskalnaSmetka1.iznosNaSmetka())//posle spored suma na artikli
                .compare(this, o); //sekogas koga koristime Comparator, mora da naznacime koj gi sporedvame
    }

    @Override
    public String toString() {
        return String.format("ID %d SUM_OF_AMOUNTS %d TAX_RETURN %f", id, iznosNaSmetka(), povratokNaDanok());
    }
}

class MojDDV {

    private List<FiskalnaSmetka> listaOdSmetki;


    public MojDDV() {
        listaOdSmetki = new ArrayList<>();
    }

    /*
    metod kojshto gi cita od vlezen stream podatoci za poveke fiskalni smetki ----- БАРА ЛИСТА ОД СМЕТКИ
    dokolku e skenirana fiskalna smetka so iznos pogolem od 30.000 denari potrebno e da frli isklucok
    od tip AmountNotAllowedException. Definirajte kade ke se frla isklucokot, i kade ke bide faten, na nacin shto
    ovaa funkcija ke moze da gi procita site fiskalni koishto se skenirani
    Isklucokot treba da ispechati poraka
    "receipt with amount 31545 is not allowed to be scanned"
     */
    public void citajRecords(InputStream inputstream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputstream));

        listaOdSmetki = br.lines()
                //ja citame sekoja linija i ja pretvarame vo fiskalna smetka
                //no za toa da e vozmozno mora da napravime metod kojshto ke cita String
                .map(linija -> {
                    try {
                        return FiskalnaSmetka.kreirajFiskalna(linija);
                    } catch (AmountNotAllowedException e) {
                        System.out.println(e.getMessage());
                        return null; //posto inevitably ke dobieme nekoja null vrednost
                        //nie ke mora da gi "iscistime" tie vrednosti od listata
                    }
                }) //metodot frla isklucok
                .collect(Collectors.toList());


        listaOdSmetki = listaOdSmetki.stream()
                .filter(Objects::nonNull) //otstranuvanje na null vrednostite od Listata
                .collect(Collectors.toList());
    }

    /*
    metod kojshto na izlezen tek gi pechati site skenirani fiskalni smetki vo format
    "ID SUM_OF_AMOUNTS TAX_RETURN",
    sortirani spored povratokot na DDV. Dokolku povratokot e ist, se sortiraat
    spored sumata na kupenite artikli
     */
    public void pecatiSorted(PrintStream printstream) {
        //da ne rabotime so System.out.println
        PrintWriter printwriter = new PrintWriter(printstream);

        listaOdSmetki.stream().sorted()
                .forEach( i-> printwriter.println(i.toString()));

        //SEKOGAS MORA FLUSH
        printwriter.flush();
    }

    /*
    metod kojshto na izlezen tek pechati statistiki za povratokot na DDV od site skenirani fiskalni smetki vo format
    min: MIN max: MAX sum: SUM count: COUNT average: AVERAGE
     */
    public void pecatiStatistics(PrintStream printstream) {
        //za ova imame klasa posebna od java, DoubleSummaryStatistics
        //a posto se barat statistiki za povratok na DDV, toj metod vrakja double
        //ovaa klasa se povikuva samo ako vrednosta e doubleStream

        PrintWriter printwriter = new PrintWriter(printstream);

        //dobivame stream od double vrednosti, i povikuvame summary statistics
        DoubleSummaryStatistics dss = listaOdSmetki.stream()
                .mapToDouble((FiskalnaSmetka fs) -> fs.povratokNaDanok())
                .summaryStatistics();

        printwriter.println (String.format
                ("min: %.2f max: %.2f sum: %.2f count: %d average: %.2f",
                dss.getMin(), dss.getMax(), dss.getSum(), dss.getCount(), dss.getAverage()));

        printwriter.flush();
    }
}

public class MojDDVTest {
    public static void main(String[] args) {
        MojDDV mojddv = new MojDDV(); //ovde "povikuvame" lista od smetki

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojddv.citajRecords(System.in);

        System.out.println("===PRINTING SORTED RECORDS BY TAX RETURNS TO OUTPUT STREAM===");
        mojddv.pecatiSorted(System.out);

        System.out.println("===PRINTING SUMMARY STATISTICS FOR TAX RETURNS TO OUTPUT STREAM===");
        mojddv.pecatiStatistics(System.out);

    }
}
