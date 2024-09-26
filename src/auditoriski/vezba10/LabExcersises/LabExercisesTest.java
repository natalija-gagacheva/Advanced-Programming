package auditoriski.vezba10.LabExcersises;

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

        //za da se izvade prosekot, potrebno ni e mapa vo koja se chuva zbirot na poenite na studentite za sekoja godina
        Map<Integer, Double> mapSumOfPointsByYear = new HashMap<>();
        //mapa shto ke go cuva brojot na studenti za sekoja godina,
        Map<Integer, Integer> mapCountByYear = new HashMap<>();

        listaOdStudenti
                .stream()
                //se ignoriraat onie bez potpis
                .filter(edenstudent -> edenstudent.dobivaPotpis())
                //za sekoj student od listata na studenti ja oddelvame godinata, i ja stavame vo mapa
                .forEach(edenstudent -> {
                    //dokolku dosega ne postoela godinata, stavi ja i inicijaliziraj ja sumata na 0.0
                    mapSumOfPointsByYear.putIfAbsent(edenstudent.godinaNaStudiranje(), 0.0);
                    //istoto i so vtorata mapa
                    mapCountByYear.putIfAbsent(edenstudent.godinaNaStudiranje(), 0);

                    /*dokolku postoi godinata, pravime presmetki
                    SYNTAX: computeIfPresent(Key, BiFunction<Key, Value, NewValue>) */
                    mapSumOfPointsByYear.computeIfPresent(
                            /*1st argument: godina na studiranje
                            The extracted year is used as the key to find the current sum of points in the mapSumOfPointsByYear map */
                            edenstudent.godinaNaStudiranje(),
                            /* 2nd argument: bifunction za da se izvlechat sumarnite rez na eden student
                            ~~~ value represents the current sum of points already stored in the map for that year */
                            (key, value) -> value + edenstudent.sumarniPoeniZaEdenStudent()
                            //Example: {3: 165.0} is the map entry for all students with index starting '21'
                    );

                    /*updates the count of students for every year
                    SYNTAX: computeIfPresent(Key, BiFunction<Key, Value, NewValue>) */
                    mapCountByYear.computeIfPresent(
                            edenstudent.godinaNaStudiranje(),
                            (key, value) -> ++value //2nd argument: za 1 se zgolemuva brojot na studenti
                    );

                });

        //eden po eden go zemame kluchot od prvata mapa, t.e ja zemame godinata
        mapSumOfPointsByYear
                .keySet()
                .stream()
                /*za sekoja godina(key) pravime average so toa shto ja zemame godinata od ednata mapa, i pravime nekakva kalkulacija
                odnosno soodvetno na taa godina, ja zemame vrednosta na poeni (odnosno sumOfPoints) i delime so brojot na studenti za taa godina
                Ova e povtorno Bifunction: computeIfPresent(Key, BiFunction<Key, Value, NewValue>) samo so porazlicna logika*/
                .forEach(godina -> mapSumOfPointsByYear.computeIfPresent(godina, (k, v) -> v / mapCountByYear.get(godina)));

                return mapSumOfPointsByYear;

    }
}

public class LabExercisesTest {

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