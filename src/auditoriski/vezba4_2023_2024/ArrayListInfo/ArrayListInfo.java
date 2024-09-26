package auditoriski.vezba4_2023_2024.ArrayListInfo;

import java.util.ArrayList;

public class ArrayListInfo {

    public static void main(String[] args) {

        //genericka podatocna struktura, ne mozheme da cuvame primitivni podatocni strukturi
        //dokolku sakame niv da gi cuvame, gi koristime nivnite wrappers pr: ArrayList<Integer>

        //deklaracija na lista
        ArrayList<String> stringLista;

        //inicijalizacija na lista, bez inicijalizacija stringLista=null
        //vo () mozime da vnesime nekakov kapacitet na listata, no najcesto ke deklarirame arrayLists so prazen konstruktor
        stringLista = new ArrayList<>();

        for (int i=0; i<10; i++) {
            stringLista.add(String.format("NP %d", i));
        }

        for (int i=0; i<10; i++) {
            stringLista.add(String.format("VTOR %d", i));
        }

        //ArrayList sama po sebe si poseduva toString metoda
        //Problem mozi da se javi pri pechatenje dokolku korsitime nasha klasa
        //i nemame preoptovareno toString metoda
        System.out.println(stringLista);

        //metodi
        System.out.println(stringLista.get(3));
        System.out.println(stringLista.contains("NP 0"));

        //pechatenje na site elementi od listata vo novi redovi
                                //za sekoj string vo listata, ispechati go elementot
        stringLista.stream().forEach(edenString -> System.out.println(edenString));

        //od listata izbrishi gi site elementi koi go ispolnuvaat uslovot
        stringLista.removeIf(edenString -> edenString.startsWith("N"));
        System.out.println(stringLista);

    }
}
