package auditoriski.vezba6Kolokviumski.Kolok2022.Quiz;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

/* Со помош на исклучок од тип InvalidOperationException
да се спречи додавање на прашање со повеќе понудени одговори
во кое како точен одговор се наоѓа некоја друга опција освен опциите A/B/C/D/E.
*/

class InvalidOperationException extends Exception{
    public InvalidOperationException(String message) {
        super(message);
    }
}

//ova ke bide ili interface ili abstract class
//cim imame abstrakten metod, i klasata ke stane abstraktna
abstract class Question implements Comparable<Question> {
    String text;
    int points;

    public Question(String text, int points) {
        this.text = text;
        this.points = points;
    }

    //definirame abstrakten metod
    abstract float checkAnswer(String studentAnswer);

    @Override
    public int compareTo(Question o) {
        return Integer.compare(this.points, o.points);
    }

    @Override
    public String toString() {
        return "Question{" +
                "text='" + text + '\'' +
                ", points=" + points +
                '}';
    }

    public int getPoints() {
        return points;
    }
}

class QuestionFactory {

    static List<String> ALLOWED_ANSWERS = Arrays.asList("A","B","C","D","E");

    public static Question kreirajQuestion(String linija) throws InvalidOperationException {
        String []parts = linija.split(";");

        //MC;Question1;3;E
        String typeOfQ = parts[0];
        String text = parts[1];
        int points = Integer.parseInt(parts[2]);
        String answer = parts[3];

        if (typeOfQ.equals("MC")) {
            //treba da vnimavame odgovorot na MC da e A,B,C,D,E
            if (!ALLOWED_ANSWERS.contains(answer)) {
                //tuka samo go frlame isklucokot, kako ke se spravime odi ponatamu so Try-Catch block
                throw new InvalidOperationException(String.format("%s is not allowed option for this question", answer));
            }
            return new MultipleChoiceQ(text, points, answer);
        } else {
            return new TrueFalseQ(text, points, Boolean.parseBoolean(answer));
        }

    }
}

class TrueFalseQ extends Question {
    boolean correctAnswer;

    public TrueFalseQ(String text, int points, boolean correctAnswer) {
        super(text, points);
        this.correctAnswer = correctAnswer;
    }

    public TrueFalseQ(String text, int points) {
        super(text, points);
    }

    @Override
    float checkAnswer(String studentAnswer) {
//        boolean odgovorNaStudent = Boolean.parseBoolean(studentAnswer);
//
//        if (odgovorNaStudent == correctAnswer) {
//            return points;
//        } else
//            return 0;
//
        return (correctAnswer == Boolean.parseBoolean(studentAnswer)) ? points : 0.0f;
    }


    @Override
    public String toString() {
        //True/False Question: Question3 Points: 2 Answer: false
        return String.format("True/False Question: %s Points: %d Answer: %s",
                text, points, correctAnswer);
    }
}

class MultipleChoiceQ extends Question {
    String correctAnswer;

    public MultipleChoiceQ(String text, int points, String correctAnswer) {
        super(text, points);
        this.correctAnswer = correctAnswer;
    }

    public MultipleChoiceQ(String text, int points) {
        super(text, points);
    }

    @Override
    float checkAnswer(String studentAnswer) {
        if (studentAnswer.equals(correctAnswer)) {
            return points;
        } else {
            return (points - 0.2f);
        }
    }

    @Override
    public String toString() {
        //True/False Question: Question3 Points: 2 Answer: false
        return String.format("Multiple Choice Question: %s Points: %d Answer: %s",
                text, points, correctAnswer);
    }
}

class Quiz {

    List<Question> listOfQuestions;

    //zosto tuka ne e this.listOfQuestions
    public Quiz() {
        listOfQuestions = new ArrayList<>();
    }

    //go dodavame prashanjeto vo listata od prashanja
    void addQuestionToList(String linija) throws InvalidOperationException {

        /*treba da odredime dali da go fatime isklucokot
        ili da propagira ponatamu i vo drug del od kodot da go fatime
        Odluka: vo zadachata se bara da se spreci dodavanje na prashanje od tip Multiple Choice*/
        listOfQuestions.add(QuestionFactory.kreirajQuestion(linija));
    }

    //pecatenje na site prasanja na kvizot
    //podredeni spored brojot na poeni na prasanjata vo opagjacki redosled
    void printQuiz(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);

        listOfQuestions
                .stream()
                .sorted(Comparator.reverseOrder())
                .forEach(ednoPrasanje -> pw.print(ednoPrasanje));


        pw.flush();
    }

    /* 5
    A
    false
    true
    true
    C
     */
    void answerQuiz(List<String> listOfStudentAnswers, OutputStream os) throws InvalidOperationException {

        //prvo nesto pravime proverka dali brojot na dadeni odgovori go nadminva brojot na tocni odgovori
        if (listOfStudentAnswers.size() != listOfQuestions.size()) {
            throw new InvalidOperationException(
                    String.format("Answers and questions must be of same length!\n"));
        }

        PrintWriter pw = new PrintWriter(os);

        //TF: ako e tochen odgovorot, site poeni, ako e netochen, 0 poeni
        //MC: ako e tochen, site poeni, ako e netochen togas negativni poeni, t.e 20% od vk poeni

        //ne treba celata logika da bidi vo eden metod, zato ke kreirame abstract method vo Question klasata

        /*
        result :
        1. -0.60
        2. -0.80
        3. 2.00
        Total pts: 0.60
         */

        float sumaNaPoeni = 0;
        for (int i = 0; i < listOfStudentAnswers.size(); i++) {

            String odgovorNaStudent = listOfStudentAnswers.get(i);
            float osvnoeniPoeni = listOfQuestions.get(i).checkAnswer(odgovorNaStudent);

            pw.println(String.format
                    ("%d. %.2f", i + 1, osvnoeniPoeni));

            sumaNaPoeni= sumaNaPoeni + osvnoeniPoeni;

        }

        pw.println(String.format
                ("Total points: %.2f", sumaNaPoeni));

        pw.flush();
    }
}

public class QuizTester {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Quiz quiz = new Quiz();

        int numberOfQuestions = Integer.parseInt(sc.nextLine()); //3

        /*
        MC;Question1;3;E
        MC;Question2;4;E
        TF;Question3;2;false
         */
        for (int i = 0; i < numberOfQuestions; i++) {
            try {
                quiz.addQuestionToList(sc.nextLine());
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        }

        /*  3
            D
            B
            false
        */
        List<String> listOfStudentAnswers = new ArrayList<>();

        int numberOfAnswers = Integer.parseInt(sc.nextLine());

        for (int i = 0; i < numberOfAnswers; i++) {
            listOfStudentAnswers.add(sc.nextLine());
        }

        int typeOfTestCase = Integer.parseInt(sc.nextLine());

        if (typeOfTestCase==1) //go pechatime kvizot
        {
        quiz.printQuiz(System.out);
        }
        else if (typeOfTestCase==2) //gi pechatime odgovorite
        {
            try {
                quiz.answerQuiz(listOfStudentAnswers, System.out);
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage()); //tuka se pechati porakata koja ke bide frlena od exception
            }
        }
        else {
            System.out.println("Invalid test case");
        }
    }
}