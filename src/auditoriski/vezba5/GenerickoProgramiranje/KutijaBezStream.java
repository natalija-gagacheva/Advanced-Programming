package auditoriski.vezba5.GenerickoProgramiranje;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/*
da se napishe genericka klasa koja simulira iscrtuvanje na slucaen predmet od edna kutija
klasata mozi da sodrzi lista so iminja i da izberi edno slucajno ime
ili pak da sodrzi lista so broevi za lotarija i izbira slucaen broj
kreirajte metod ass za dodavanje objekt
i metod isEmpty koj proveruva dali kutijata e prazna
na kraj implementiraj metod drawItem koj slucajno izbira objekt od kutijata i go vrakja kako rez
ako se obideme da izvleceme od prazna kutija vrakja null
 */

//Kaj genericki tipovi V-value, E-Element, T-type
/*
T(Type) is typically used to denote a generic type. It is often used in generic classes, interfaces, and methods to represent any type.
E(Element) is commonly used to represent an element in a collection, such as in the Collection or List interfaces.
K(Key) and V(Value) are often used in the context of maps or dictionaries, where K represents the type of keys and V represents the type of values.
*/

public class KutijaBezStream<E> { //klasata treba da znae da iscrta objekt od nekoj tip

    private List<E> elementsList;
    public static Random randomPromenliva = new Random();
    /* List e interface, pa metodite shto ponatamu ke gi koristime se odnesuvaat na site metodi shto gi imaat site tipovi na listi
    Na toj nacin, dokolku nekoj saka da smeni od ArrayList<E>() vo LinkedList<>(), zadacata nema da napravi nikakov problem odnosno ke si raboti soodvetno
    zaradi shto implementaciite dvete proizleguvaat od List interface-ot
    i nema da se javi problem dokolku se smeni tipot na implementacija na toj interface
     */

    public KutijaBezStream() {
        this.elementsList = new ArrayList<E>();
    }

    public void add(E element) { //metodot go bara zadacata
        elementsList.add(element);
    }

    public boolean isEmpty() { //metodot go bara zadacata
        return elementsList.isEmpty();
    }

    public E drawElement() {
        if (isEmpty())
            return null; //dokolku listata e prazna, nemozi da se nacrta element
        //treba da izberime objekt od kutijata i da go vratime kako rezultat

        //nejkime indeksot shto ke ni go generira random da izlegva nadvor od goleminata na listata
        //.nextInt() metodot generira random int pocnuvajki od 0 do goleminata na elementsList
        //sho znaci ako dolzinata na listata e 5, brojkite koj mozi da se generiraat se 0,1,2,3,4
        //primer lista=[10, 20, 30, 40, 50]
        //taka, ako indeks=2, togas ke go vrati elementot na pozicija 2 ili 30
        int indeks = randomPromenliva.nextInt(elementsList.size());

        //temp promenliva kade shto go cuvame elementot sho sme go izvajle od listata
        E temp = elementsList.get(indeks);
        elementsList.remove(temp);
        return temp;
    }
}
