package auditoriski.vezba5Kolokviumska.Container_NeJaSfakjam;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

//interface-ot vo nashiot sluchaj e toj sho ogranicva Comparable
//sekoja klasa ponatamu koja ke go implementira ovoj interface ke e ogranichena so Comparable
//koga imame interface, toj interface proshiruva na nekoj drug interface, taja e sintaksata
interface WeightableInterface extends Comparable<WeightableInterface> {

    double getWeightMethod();

    @Override
    default int compareTo(WeightableInterface other) { //default implementacija na nekoja funkcija
        return Double.compare(getWeightMethod(), other.getWeightMethod());
    }

}

class WeightableDouble implements WeightableInterface {
    double weight;

    public WeightableDouble(double weight) {
        this.weight = weight;
    }

    @Override
    public double getWeightMethod() {
        return weight;
    }

}

class WeightableString implements WeightableInterface {
    String word;

    public WeightableString(String word) {
        this.word = word;
    }

    @Override
    public double getWeightMethod() {
        return word.length();
    }
}

//generic class sho cuva nekakov tip T
//celta e da chuva elementi za koi mozhe da se izmeri nivnata tezhina, t.e go implementiraat Weightable interface-ot
class Container<T extends WeightableInterface> {

    private List<T> listaOdElementi;

    public Container() {
        listaOdElementi = new ArrayList<T>();
    }

    public void addElement(T elementVariable) {
        listaOdElementi.add(elementVariable);
    }

    //da vrati lista od elementi koi se polesni od elementVariable
    public List<T> lighterThan(T elementVariable) {

        List<T> result = listaOdElementi.stream()
                //vo .filter() pisuvame nekakov uslov, za to koristime PREDICATE
                //za .compareTo da funkcionira mora da napravime sporedlivost na elementite koi gi cuvame
                //elementite sto gi cuvame se od tip T, a tip T nasleduva od WeightableInterface
                .filter(i -> i.compareTo(elementVariable) < 0)
                .collect(Collectors.toList()); //terminalna akcija

        return result;
    }

    //da se vrati lista na elementi poteski od a i polesni od b
    public List<T> between(T a, T b) {

        List<T> result = listaOdElementi.stream()
                .filter(i -> i.compareTo(a) > 0 && i.compareTo(b) < 0)
                .collect(Collectors.toList());
        return null;
    }

    //kreirame metod za presmetvanje na tezinata
    public double containerSum() {

        double result = listaOdElementi.stream()
                .mapToDouble(i -> i.getWeightMethod()).sum();

        return result;
    }
    //sporedba na tezina na 2 containers. Metodot vrakja 0 ako containers imaat ista tezina
    //-1 ako container vo argument e potezok i 1 ako conteiner vo argument e polesen
    //tezinata na container se presmetuva kako suma od tezinite na site elementi vo container
    public int compare(Container<? extends WeightableInterface> container2) {
                            //bilo kakov tip na promenliva koja sho e weightable mozi tuka da vlezi kako argument

        int result = Double.compare(this.containerSum(), container2.containerSum());
        return result;
    }
}
public class ContainerTester {

    public static void main(String[] args) {
        Container<WeightableDouble> container1 =  new Container();
        Container<WeightableDouble> container2 =  new Container();
        Container<WeightableString> container3 =  new Container();

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int p = sc.nextInt();
        double a = sc.nextDouble();
        double b = sc.nextDouble();

        WeightableDouble wa = new WeightableDouble(a);
        WeightableDouble wb = new WeightableDouble(b);

        for (int i=0; i<n; i++) {
            double weight = sc.nextDouble();
            container1.addElement(new WeightableDouble(weight));
        }

        for (int i=0; i<n; i++) {
            double weight = sc.nextDouble();
            container2.addElement(new WeightableDouble(weight));
        }

        for (int i=0; i<n; i++) {
            String s = sc.next();
            container3.addElement(new WeightableString(s));
        }

        List<WeightableDouble> resultSmaller = container1.lighterThan(wa);
        List<WeightableDouble> resultBetween = container1.between(wa,wb);

        System.out.println("Lighter than" +wa.getWeightMethod()+ ":");

        for (WeightableDouble wd : resultSmaller) {
            System.out.println(wd.getWeightMethod());
        }

        System.out.println("Between "+wa.getWeightMethod()+ "and" + wb.getWeightMethod());

        for (WeightableDouble wd : resultBetween) {
            System.out.println(wd.getWeightMethod());
        }

        System.out.println("Comparison: ");
        System.out.println(container1.compare(container2));
        System.out.println(container1.compare(container3));
        //Required Type: Container <WeightableDouble>
        //Provided: Container <WeightableString>
    }

}
