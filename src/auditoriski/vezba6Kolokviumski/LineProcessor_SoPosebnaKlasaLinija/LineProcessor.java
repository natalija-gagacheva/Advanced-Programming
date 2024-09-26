//package auditoriski.vezba6Kolokviumski.LineProcessor_SoPosebnaKlasaLinija;
//
//import java.io.*;
//import java.util.*;
//
////Posto celta na zadachata e da pravime sporedbi na 2 linii od vlezenInput,
//// mora da ja implementirame klasata Comparable za da sporedvame Linija po Linija
//class Linija implements Comparable<Linija> {
//    String linija;
//    char letter;
//
//    public Linija(String linija, char letter) {
//        this.linija = linija;
//        this.letter = letter;
//    }
//
//    //ke izbroime kolku pati karakterot "letter" se pojavuva
//    private int brPojavuvanja() {
//        int counter=0;
//
//        //za sekoj element(bukva) vo nizata od karakteri
//        for (char bukva : linija.toCharArray())
//        //linija.toCharArray() kreira nova niza od karakteri od bukvite vo linijata
//        {
//            if(Character.toLowerCase(bukva)==this.letter)
//                counter++;
//        }
//        return counter;
//    }
//
//    //Sporedbata ja pravime spored toa kolku pati karakterot "letter" se pojavuva vo ovaa linija
//    @Override
//    public int compareTo(Linija o) {
//        return Integer.compare(this.brPojavuvanja(), o.brPojavuvanja());
//    }
//}
//
//class LineProcessor {
//
//    /* od vlezen potok ke gi procita site stringovi (sekoj od niv vo nov red)
//     * a na izlezen potok ke ja ispechati linijata/stringot koj go sodrzi karakterot C najmnogu pati
//     * Dokolku ima povekje takvi, da se ispechati poslednata */
//
//    List<Linija> listLinija;
//
//    public LineProcessor() {
//        listLinija = new ArrayList<>();
//    }
//
//
//    public void readLines(InputStream is, OutputStream os, char c) throws IOException {
//
//        //? zosto so scanner
//        Scanner scanner = new Scanner(is);
//        PrintWriter pw = new PrintWriter(os); //ke go koristime za da zapisuvame na output stream
//
//        //so skenerot ke citame linii, linijata ja prajme vo objekt, objektot go klavame vo lista
//        while (scanner.hasNextLine()) {
//            String scannedString = scanner.nextLine(); //Napredno programiranje
//            Linija linija = new Linija(scannedString, c); //"Napredno programiranje"
//            listLinija.add(linija); //"Napredno programiranje" -> "FINKI" ->
//        }
//
//        /* mora site linii shto gi cuvame da gi mapirame vo objekti od klasa line,
//        za da mozhime istite da gi sporedime, i da gi cuvame vo nekakva lista */
//
//    }
//
//    public class LineProcessorTest {
//
//        public static void main(String[] args) {
//            LineProcessor lineprocessor = new LineProcessor();
//
//            try {
//                lineprocessor.readLines(System.in, System.out, 'a');
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
