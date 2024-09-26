package auditoriski.vezba6Kolokviumski.LineProcessor;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class LineProcessor {

    /* od vlezen potok ke gi procita site stringovi (sekoj od niv vo nov red)
     * a na izlezen potok ke ja ispechati linijata/stringot koj go sodrzi karakterot C najmnogu pati
     * Dokolku ima povekje takvi, da se ispechati poslednata */

    List<String> listOfStrings;

    public LineProcessor() {
        listOfStrings = new ArrayList<>();
    }

    //metodot e private oti ke go koristime samo vo ovaa klasa
    private int brojPojavuvanja(String linijaString, char c) {
//        int counter = 0;
//
//        for (char bukva : linija.toLowerCase().toCharArray()) {
//            if (bukva == c) {
//                counter++;
//            }
//        }
//        return counter;

        //Expression kojshto pravi stream od site karakteri vo string-ot
        long result = linijaString
                .toLowerCase()
                .chars()
                .filter(edenKarakter -> ((char) edenKarakter == c))
                .count();

        return (int) result;

    }


    public void readLines(InputStream is, OutputStream os, char c) throws IOException {

        //najdobar za citanje linija po linija od vlezenInput
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        //Ja zema listata, kade shto sekoj element e nekakov string
        // ["FINKI"] -> ["Napredno programiranje"] -> ["Java"]
        listOfStrings = br
                .lines()
                .collect(Collectors.toList());
        //this code reads all the lines from vlezenInput stream, and collects them into a list of strings

        //prvo sporeduvame po brojot na pojavuvanja na znakot c
        Comparator<String> komparator = Comparator.comparing(linija -> brojPojavuvanja(linija, c));

        String result = listOfStrings
                .stream()
                //pa potoa ako se isti, gi pecatime po prirodniot redosled vo zadacata
                .max(komparator.thenComparing(Comparator.naturalOrder()))
                .orElse(" "); //ako ne najdi takov string vrakja prazen string

        PrintWriter pw = new PrintWriter(os);
        pw.println(result);
        pw.flush();

    }
}

public class LineProcessorTest {

        public static void main(String[] args) {
            LineProcessor lineprocessor = new LineProcessor();

            try {
                lineprocessor.readLines(System.in, System.out, 'a');
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
