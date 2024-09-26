package auditoriski.vezba4.ArrayList;

//Listata samata po sebe pretstavuva interface
// i mozhe da ima 2 implementacii: arrayList ili linkedList

import java.util.ArrayList;
import java.util.List;

public class ArrayListTest {

    public static void main(String[] args) {

        //chuvame klasen/referencirachi podatochen tip (klasi)
        List<Integer> integerLista = new ArrayList<>(100);
        List<String> stringLista = new ArrayList<>();

        //lista od listi - matrica implementirana so listi
        //List<List<String>>

        stringLista.add("Hello");
        stringLista.add("Bye");
        stringLista.add("Cookie");
        stringLista.add("Latte");
        stringLista.add("Hello");
        //pri pechatenje, za sekoj element vo listata se povikuva toString metod
        //za da se ispechati 8, se povikuva toString na integer
        //ako koristime user defined klasa, taa klasa ke mora da ima to string implementacija
        System.out.println(stringLista);

        //kako da odredime koj element se naogja na odredena pozicija
        System.out.println(stringLista.get(3));

        //proverka dali odreden element postoi vo listata
        System.out.println(stringLista.contains("Hello"));
        System.out.println(stringLista.contains("Mello"));

        //dokolku elementot postoi, da znaeme tocno kade se naogja vo ramki na taa lista
        System.out.println(stringLista.indexOf("Hello"));
        System.out.println(stringLista.lastIndexOf("Hello"));

        integerLista.add(2);
        integerLista.add(5);
        integerLista.add(9);
        integerLista.add(1);

        //lambda izraz se koristi koga imam funkciski interface kojshto sakame da go implementirame
        //izrazot (i -> i > 5) pretstavuva predikat shto vo sluchajot pretstavuva funkc interface
        //predikat pretstavuva funkciski interface kojshto zema eden argument i vrakja boolean vrednost:
        System.out.println(integerLista.removeIf(i -> i > 5));
        //vo ovoj sluchaj za sekoe i, ako i>5 izbrishi go elementot od listata
        System.out.println(integerLista);

        //ova vrakja stream od objekti, t.e sekvenca od objekti
        //posto e stream od objekti, ako izvrshuvame chain of actions na ovoj stream(), primer .map() ili .filter()
        //istite ke se izvrshuvaat vrz soodvetnite objekti na stream-ot
        //NO AKO NEMAME TERMINALNA AKCIJA, nisto od ova nema da se izvrshi
        //terminalni kacii se .collect() i .foreach()
        //integerLista.stream()
    }
}
