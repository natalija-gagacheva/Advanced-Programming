package auditoriski.vezba6Kolokviumski.Kolok2022.RIsk;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Game  {
    List <Integer> attackerList;
    List <Integer> defenderList;

    public Game(List<Integer> attackerList, List<Integer> defenderList) {
        this.attackerList = attackerList;
        this.defenderList = defenderList;
    }

    public Game(String linija) {

        //niza od strings
        String [] half = linija.split(";");

        //niza od strings da ja pretvorime vo int i da ja stavime vo attackerList
        this.attackerList = parsirajFrlanjeVoInt(half[0]);
        this.defenderList = parsirajFrlanjeVoInt(half[1]);
    }

    //so cel da izbegneme duplicate code
    private List <Integer> parsirajFrlanjeVoInt(String linija) {
        return Arrays
                .stream(linija.split("\\s+")) //dopolnitelno go delime po prazni mesta
                .sorted(Comparator.reverseOrder())
                .map(ednoFrlanje -> Integer.parseInt(ednoFrlanje))
                .collect(Collectors.toList());
    }

    public boolean isUspeshnaIgra() {

        for (int i=0; i<attackerList.size(); i++) {
            int attackerElem = attackerList.get(i);
            int defenderElem = defenderList.get(i);

            if (attackerElem<=defenderElem) {
                return false;
            }
        }
        return true;
    }

    public boolean isUspeshnaIgraSoStream() {

        return IntStream
                .range(0, attackerList.size())
                //allMatch vrakja boolean
                .allMatch(i -> attackerList.get(i) <= defenderList.get(i));

    }

        @Override
    public String toString() {
        return "Game{" +
                "attackerList=" + attackerList +
                ", defenderList=" + defenderList +
                '}';
    }

}

class Risk {


    /* uspeshen napad:  5   3  4;2  4   1
                        x1 x2 x3;y1 y2 y3
                        attacker  defender
     */
    public long processAttacksData(InputStream is) throws IOException {
        //koga citame linija po linija od vlezenInput, najubo e so bufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        //kreirame lista od povekje Igri
        List<Game> ListaOdIgri = br
                .lines()
                .map(ednaLinija -> new Game(ednaLinija))
                .collect(Collectors.toList());

        br.close();

        long brNaUspesniIgri = ListaOdIgri
                .stream()
                .filter(ednaIgra -> ednaIgra.isUspeshnaIgra())
                .count();

        return brNaUspesniIgri;
    }

//    public long skratenoProcessAttacksData(InputStream is) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//
//        long broj = br.lines()
//                .map(ednaLinija -> new Game(ednaLinija))
//                .filter(ednaIgra -> ednaIgra.isUspeshnaIgra())
//                .count();
//
//        br.close();
//
//        return broj;
//    }
}

public class RiskTester {
    public static void main(String[] args)  {

        Risk risk = new Risk();

        try {
            System.out.println(risk.processAttacksData(System.in));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

