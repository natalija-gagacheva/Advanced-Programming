package auditoriski.vezba5.StandardnaDevijacija_NeJaSfakjam;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class GenericMathOperationsTest {

    /*The method takes a single parameter numbersList,
    which is a list of elements that are of type Number or any subtype of Number
    The purpose of this method is to calculate and return a formatted string
    containing various statistical measures (min, max, average, standard deviation, count, and sum)
    for the numbers in the provided list
    */
    public static String statistika (List<? extends Number> numbersList) {

//1va varijanta: -------------------------------------------------------------------------------
//        //koristime gotova klasa od Java koja cuva statistiki za nekoja lista od broevi koja shto nie mu ja davame
//        DoubleSummaryStatistics doublesummarystatistics = new DoubleSummaryStatistics();
//
//        //na sledniot nacin ja popolnuvame ovaa statistika preku metodot accept()
//        numbersList.stream().forEach(i -> doublesummarystatistics.accept(i.doubleValue()));

//2ra varijanta: -------------------------------------------------------------------------------

        DoubleSummaryStatistics doublesummarystatistics = numbersList.stream() //converts the numbersList into a stream of Number objects.
                .mapToDouble(i -> i.doubleValue()) //the doubleValue() method is a method of the Number class, which returns the value of the Number object as a double
                .summaryStatistics(); //terminalna operacija which returns an instance of DoubleSummaryStatistics. This object contains various summary data such as count, sum, min, average, and max.

        double standardnaDevijacija = 0;

        for (Number broj: numbersList) {
            standardnaDevijacija = standardnaDevijacija+ (broj.doubleValue() - doublesummarystatistics.getAverage())
                    * (broj.doubleValue() - doublesummarystatistics.getAverage());
        }

        double finalnaStandardnaDevijacija = Math.sqrt(standardnaDevijacija / numbersList.size());

        return String.format("Min: %.2f\n Max: %.2f\nAverage: %.2f\nStandard deviation: %.2f\nCount: %d\nSum: %.2f",
                doublesummarystatistics.getMin(),
                doublesummarystatistics.getMax(),
                doublesummarystatistics.getAverage(),
                finalnaStandardnaDevijacija,
                doublesummarystatistics.getCount(),
                doublesummarystatistics.getSum());
    }

    public static void main(String[] args) {
        Random randomObj = new Random();

        List<Integer> integerList = new ArrayList<>();

        /*
        for (int i=0; i<100000; i++) {
            integerList.add(randomObj.nextInt(100) +1);
         }
         */

        //This fills integerList with 100,000 random integers in the range from 1 to 100.
        IntStream
                .range(0,100000) //So IntStream.range() listata ke sodrzi vo sebe 100,000 elementi
                .forEach(i -> //i e momentalniot integer od IntStream
                        integerList.add( //go dodava sekoj element vo listata
                        randomObj.nextInt(100) //generira random broj od 0 do 99
                        + 1)); //shifts the range by 1, t.e generira random broj od 1 do 100

        //Example:
        //[34, 7, 12, 56, 89, 99, 23, 45, 67, 100, ...]

        List<Double> doubleList = new ArrayList<>();

        /*
        for (int i=0; i<100000; i++) {
            doubleList.add(randomObj.nextDouble() * 100.0));
         }
         */
        IntStream.range(0,100000)
                .forEach(i -> doubleList
                .add(randomObj.nextDouble() //generates a random double value between 0.0 and 0.9
                        *100.0)); // scales this random double value up to the range 0.0 to 100.0.
                                // Therefore, randomObj.nextDouble() * 100.0 generates a random double between 0.0 and 100.0

        //Example: Each element is a double between 0.0 and 100.0. The list has 100,000 such doubles.
        //[12.34, 78.56, 45.67, 23.45, 99.99, 0.12, 55.78, 67.89, 100.0, 48.23, ...]

        System.out.println(statistika(integerList));
        System.out.println(statistika(doubleList));

    }
}
