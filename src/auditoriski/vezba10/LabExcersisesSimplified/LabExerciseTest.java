package auditoriski.vezba10.LabExcersisesSimplified;

import java.util.*;
import java.util.stream.Collectors;

class Student  {
    String index;
    /* lista na poeni na lab vezhbi dobieni po nekoj predmet. Po predmetot
     * se izveduvaat max 10 lab vezhbi */
    List<Integer> listaOdPoeni;
    final static int COUNT_OF_LAB=10;

    public Student(String index, List<Integer> listaOdPoeni) {
        this.index = index;
        this.listaOdPoeni = listaOdPoeni;
    }

    public String getIndex() {
        return index;
    }

    public List<Integer> getListaOdPoeni() {
        return listaOdPoeni;
    }

    public boolean dobivaPotpis() {
        return listaOdPoeni.size()>=8;
    }

    @Override
    public String toString() {
        //211526 NO 0.00
        return String.format("%s %s %.2f",index,
                dobivaPotpis() ? "YES" : "no", //ternaren izraz, dokolku dobiva potpis pechati yes, inaku no
                sumarniPoeniZaEdenStudent());
    }

    public double sumarniPoeniZaEdenStudent() {
        double result = listaOdPoeni
                .stream()
                /*In this case, the lambda expression simply returns ednaVezba as is, which works if ednaVezba is already of type double.

                 .mapToDouble(ednaVezba -> ednaVezba.doubleValue())
                 the lambda expression explicitly calls the doubleValue() method on each element (ednaVezba) in the stream.
                 */
                .mapToDouble(ednaVezba -> ednaVezba)
                .sum();

        return result/COUNT_OF_LAB;
    }

    public int godinaNaStudiranje() {
        return 24 - Integer.parseInt(index.substring(0,2));
    }

}

class LabExercises {

    List<Student> listaOdStudenti;

    public LabExercises() {
        listaOdStudenti = new ArrayList<>();
    }

    public void addStudent(Student student) {
        listaOdStudenti.add(student);
    }

    /* metod kojshto ke gi pechati prvite n studenti sortirani spored sumarnite poeni, a
     * dokolku se isti sumarnite poeni, spored index
     * Pechatenjeto treba da bide vo rastecki redosled dokolku "rastecki" e true, a vo sprotivno vo opagjacki*/
    public void printByAveragePoints(boolean rastecki, int n) {

        //ako e vo rastecki redosled
        Comparator<Student> komparator = Comparator.comparing((Student edenStudent) -> edenStudent.sumarniPoeniZaEdenStudent())
                .thenComparing((Student edenStudent) -> edenStudent.getIndex());

        //ako ne e vo rastecki redosled
        if (!rastecki) {
            komparator = komparator.reversed();
        }

        listaOdStudenti
                .stream()
                .sorted(komparator)
                .limit(n)
                .forEach(System.out::println);
    }

    /* Metod koj vrakja lista od studenti koi ne dobile potpis
     * (t.e imaat povekje od 2 otsustva), sortirnani spored inex, potoa spored sumarni poeni*/
    public List<Student> failedStudents() {

        Comparator<Student> komparator = Comparator.comparing((Student student) -> student.getIndex())
                .thenComparing((Student student) -> student.sumarniPoeniZaEdenStudent());

        return listaOdStudenti
                .stream()
                //gi filtrirame onie studenti koi nemaat potpis
                .filter(edenStudent -> !edenStudent.dobivaPotpis())
                .sorted(komparator)
                .collect(Collectors.toList());
    }

    /* Metod kojshto vrakja mapa od prosekot od sumarnite poeni na studentite
     * spored godina na studiranje. Da se ignoriraat studentite koi ne dobile potpis*/
    public Map<Integer, Double> getStatisticsByYear() {
        //index, prosek

        Map<Integer, Double> result = listaOdStudenti
                .stream()
                .filter((Student edenstudent) -> edenstudent.dobivaPotpis())
                .collect(Collectors.groupingBy(
                        //1st argument: kluchot spored koj ke gi grupirame vo novata mapa ~ spored godinata
                        (Student edenstudent) -> edenstudent.godinaNaStudiranje(),
                        //2nd: vo kakov podatochen tip ke gi smestime i logikata/presmetkata ~ prosek od site sumarni poeni na eden student
                        Collectors.averagingDouble(Student::sumarniPoeniZaEdenStudent)
                ));

        return result;
    }
}

public class LabExerciseTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LabExercises labExercises = new LabExercises();
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            String[] parts = input.split("\\s+");
            String index = parts[0];
            List<Integer> points = Arrays.stream(parts).skip(1)
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toList());

            labExercises.addStudent(new Student(index, points));
        }

        System.out.println("===printByAveragePoints (ascending)===");
        labExercises.printByAveragePoints(true, 100);
        System.out.println("===printByAveragePoints (descending)===");
        labExercises.printByAveragePoints(false, 100);
        System.out.println("===failed students===");
        labExercises.failedStudents().forEach(System.out::println);
        System.out.println("===statistics by year");
        labExercises.getStatisticsByYear().entrySet().stream()
                .map(entry -> String.format("%d : %.2f", entry.getKey(), entry.getValue()))
                .forEach(System.out::println);

    }
}