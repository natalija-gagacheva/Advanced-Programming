package auditoriski.vezba6Kolokviumski.Kolok2022.Rule;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/* Functional interfaces definirani vo Java: Predicate, Function...
 *Predicate - funksicki interface koj prima eden argument i vrakja boolean vrednost
 * Function - genericki funkcionalen interface koj prima argument od eden tip i vrakja od drug tip */

//Rule - genericka klasa so 2 genericki parametri za vlez i izlez
class Rule<IN, OUT> {

    // vo forma na instance fields, vo klasata treba da se cuvaat
    // implementacii na interfejsite Predicate and Function
    Predicate<IN> predicateCondition;
    Function<IN, OUT> functionMapper;

    public Rule(Predicate<IN> predicateCondition, Function<IN, OUT> functionMapper) {
        this.predicateCondition = predicateCondition;
        this.functionMapper = functionMapper;
    }

    /* ako e tochen predikatot, pretvori go vlezniot objekt od tip IN vo izlezen objekt od tip OUT
     * so pomosh na mapper funkcijata functionMapper */
    Optional<OUT> applyMetodOdZadacata(IN input) {

    /* predicateCondition is a Predicate<IN> meaning it takes an argument of type IN
    * and returns a boolean
    * the .test() method checks if the input satisfies a certain condition
    * if the input satisfies the predicite condition, the method applies the functionMapper to the input
    * in this case functionMapper is a Function<IN,OUT> meaning it takes an argument of type IN and returns
    * a value of type OUT */

        if (predicateCondition.test(input)) {
            return Optional.of(functionMapper.apply(input));
            /* The .apply() method of the function is called with the input to produce the result.
            * this result is then wrapped in an Optional*/
        } else {
            /* dokolku predikatot ne e ispolnet, vrakjame prazen Optional*/
            return Optional.empty();
        }

        /* OPTIONAL - In this example, Optional helps to handle the case where
        getValue() might return null without risking a NullPointerException. */
    }
}

class RuleProcessor {

    /* genericki staticki metod process koj prima dva argumenti: lista od vlezni podatoci IN,
    * kako i lista od pravila (objekti od klasata Rule) i pecati, zato e void
    * */
    static <IN, OUT> void process(List<IN> listOfInputs, List<Rule<IN, OUT>> listOfRules)
/*   <IN,OUT> se genericki funkcii                       Rule<> e genericki tip
*    i se dodavaat kako genericki parametri
*    na nivo na samata funckija, sho znaci deka
*    ako funckijata e staticka, nema potreba da
*    se deklariraat i na samiot RuleProcessor<IN,OUT>                                                   */
    {
        /* potrebno e vrz sekoj element od listata na inputs da go primeni sekoe pravilo
        * od listata na pravila*/
        for (IN edenInput : listOfInputs)
        {
            System.out.println(String.format("Input: %s", edenInput.toString()));

            for (Rule<IN, OUT> ednoPravilo : listOfRules)
            {
                /* da se ispechati rezultatot od primenata na praviloto, ako praviloto vrati rezultat
                znaci, ednoPravilo.applyMetodOdZadacata(edenInput) vrakja objekt od Optional
                 */
                Optional<OUT> result = ednoPravilo.applyMetodOdZadacata(edenInput);

                /* dokolku vrati rezultat, odnosno dokolku praviloto e prisutno
                 treba da go ispechatime istiot */
                if (result.isPresent()) {
                    System.out.println("Result: " + result.get());
                } else {
                    System.out.println("Condition not met");
                }
            }
        }
/*
        listOfInputs.forEach(input -> listOfRules.forEach(rule -> rule.apply(input)));
*/
    }
}

class Student {
    String id;
    List<Integer> grades;

    public Student(String id, List<Integer> grades) {
        this.id = id;
        this.grades = grades;
    }

    public static Student create(String line) {
        String[] parts = line.split("\\s+");
        String id = parts[0];

        List<Integer> grades =
                Arrays
                        .stream(parts)
                        .skip(1)
                        .map(linija -> Integer.parseInt(linija))
                        .collect(Collectors.toList());

        return new Student(id, grades);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", grades=" + grades +
                '}';
    }
}

public class RuleTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase == 1) { //Test for String to Integer
            List<Rule<String, Integer>> rulesList = new ArrayList<>();

            /*
            TODO: Create a rule where if the string contains the string "NP", the result would be index of the first occurrence of the string "NP"
            * */

            //definirame pravilo
            rulesList
                    .add(new Rule<>  /* predicate                   function               */
                            (linija -> linija.contains("NP"), linija -> linija.indexOf("NP"))
                    );

            /*
            TODO: Create a rule where if the string starts with the string "NP", the result would be length of the string
            * */

            rulesList
                    .add(new Rule<> /* predicate                   function               */
                            (linija -> linija.startsWith("NP"), linija -> linija.length())
                    );


            List<String> inputs = new ArrayList<>();

            while (sc.hasNext()) {
                inputs.add(sc.nextLine());
            }

            RuleProcessor.process(inputs, rulesList);


        } else { //Test for Student, Double
            List<Rule<Student, Double>> listOfRules = new ArrayList<>();

            //TODO Add a rule where if the student has at least 3 grades, the result would be the max grade of the student

            listOfRules
                    .add(new Rule<>
                            (edenStudent -> edenStudent.grades.size()>=3 , //predicate
                             edenStudent -> edenStudent
                                     .grades
                                     .stream()
                                     .mapToDouble(i -> i) //sekoe brojche se mapira vo double
                                     .max()
                                     .getAsDouble())            //function
                    );


            //TODO Add a rule where if the student has an ID that starts with 21, the result would be the average grade of the student
            //If the student doesn't have any grades, the average is 5.0

            listOfRules
                    .add(new Rule<>
                            (edenStudent -> edenStudent.id.startsWith("21") , //predicate
                                    edenStudent -> edenStudent //function
                                            .grades
                                            .stream()
                                            .mapToDouble(i -> i)
                                            .average()
                                            .orElse(5.0)
                            ));


            List<Student> students = new ArrayList<>();
            while (sc.hasNext()) {
                students.add(Student.create(sc.nextLine()));
            }

            RuleProcessor.process(students, listOfRules);
        }
    }
}