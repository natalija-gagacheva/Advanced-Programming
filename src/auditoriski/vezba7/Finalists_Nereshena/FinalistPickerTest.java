package auditoriski.vezba7.Finalists_Nereshena;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class InvalidPickerArgumentsException extends Exception {

    public InvalidPickerArgumentsException(String message) {
        super(message);
    }
}

class FinalistPicker {

    int vkBrFinalists; /*znajme deka brojkata na finalisti e od 1-30
                    sho znaci nema potreba da gi cuvame rednite broevi i da kreirame posebna klasa*/
    int brNaNagradi;

    /* za izbor na indeksite treba da kreirame Random objekti
    * treba da generirame sluchajni broevi od 1 do vkBrFinalists */
    static Random RANDOM = new Random();

    public FinalistPicker(int vkBrFinalists) {
        this.vkBrFinalists = vkBrFinalists;
    }

    /* metodot vrakja Lista od Integers koj ke pretstavuvaat rednite broevi na finalistite
    dodeka pak uslov za promenlivata (int brIzbraniFinalists) e istata da ne bide pogolema
    od vkBrojNaFinalisti i da ne bide <=0
     */
    public List<Integer> pick(int brIzbraniFinalists) throws InvalidPickerArgumentsException {

        if (brIzbraniFinalists > vkBrFinalists) {
            throw new InvalidPickerArgumentsException
                    ("The number cannot exceed the number of finalists");
        }

        if (brIzbraniFinalists <= 0) {
            throw new InvalidPickerArgumentsException
                    ("The number must be a positive number");
        }

        List<Integer> listaOdIzbraniFinalisti = new ArrayList<>();

        /* while ciklusot vrti se dodeka ne ja napolnime listata so celosniot
        * broj na izbraniFinalists */
        while(listaOdIzbraniFinalisti.size() != brIzbraniFinalists) {
            int coek = RANDOM.nextInt(vkBrFinalists) +1;

            /*sekoj coek sho ke go izgenerirame, treba da proverime dali negoviot
            index veke postoi vo listata, dokolku ne postoi go klavame vo listata, a
            dokolku postoi treba da prodolzi while ciklusot i da generirame nov objekt*/
            if (!listaOdIzbraniFinalisti.contains(coek)) {
                listaOdIzbraniFinalisti.add(coek);
            }
        }
        return listaOdIzbraniFinalisti;
    }
}
public class FinalistPickerTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numberOfFinalists = sc.nextInt();
        int numberOfPrizes = sc.nextInt();

        FinalistPicker fp = new FinalistPicker(5);

        try {
            System.out.println(fp.pick(numberOfPrizes));
        } catch (InvalidPickerArgumentsException e) {
            throw new RuntimeException(e);
        }

    }
}