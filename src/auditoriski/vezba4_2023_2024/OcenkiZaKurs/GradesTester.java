package auditoriski.vezba4_2023_2024.OcenkiZaKurs;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//posto se bara vo zadacite studentite da se sortiraat potrebno e da se implementira Comparable
class StudentKlasa implements Comparable<StudentKlasa> {
    String prezime;
    String ime;
    int ispit1, ispit2, ispit3;

    //konstruktor kojshto ke chita cela linija
    public StudentKlasa(String linija) {
        this.prezime = linija.split(":")[0];
        this.ime = linija.split(":")[1];
        this.ispit1 = Integer.parseInt(linija.split(":")[2]);
        this.ispit2 = Integer.parseInt(linija.split(":")[3]);
        this.ispit3 = Integer.parseInt(linija.split(":")[4]);
    }

    public double totalPoints() {
        return ispit1 * 0.25 + ispit2*0.3 + ispit3*0.45;
    }

    public String ocenka() {
        double poeni= totalPoints();
        if (poeni >= 91) {
            return "A";
        } else if (poeni >= 81) {
            return "B";
        } else if (poeni >= 71) {
            return "C";
        } else if (poeni >= 61) {
            return "D";
        } else {
            return "F";
        }
    }

    @Override
    public int compareTo(StudentKlasa o) {
        return Double.compare(this.totalPoints(), o.totalPoints());
    }

    @Override
    public String toString() {
        return String.format("%s %s %d %d %d %.2f %s", prezime, ime, ispit1, ispit2, ispit3, totalPoints(), ocenka());
    }
}

class GradeSystem {

    List<StudentKlasa> studentiLista = new ArrayList<>();

    //loading data from vlezenInput stream
    public void loadData(InputStream inputstrim) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputstrim));

        studentiLista = br.lines()
                .map(linija -> new StudentKlasa(linija))
                .collect(Collectors.toList());
    }

    //print result to output stream
    public void printFinalResult (OutputStream os) {

        // od studentite pravime strimovi, gi sortirame
        // pa so .forEach ke gi ispechatime na output stream
        //KOLOKVIUMSKO :
        // ZA DA PECHATIME NA OUTPUT STREAM, POTREBNO E DA GO NAPRAVIME TO SO PRINTWRITER

        PrintWriter pw = new PrintWriter(os); //raboti kako system out
        studentiLista.stream().sorted(Comparator.reverseOrder()).forEach(edenStudent -> pw.println(edenStudent));

        pw.flush();
        pw.close();
    }


}
public class GradesTester {

    public static void main(String[] args) {

        try {
            InputStream isFromFile = new FileInputStream(new File("C:\\Users\\User\\IdeaProjects\\Napredno\\src\\auditoriski\\vezba4_2023_2024\\OcenkiZaKurs\\students"));
            GradeSystem gs = new GradeSystem();
            gs.loadData(isFromFile);
            gs.printFinalResult(System.out);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
