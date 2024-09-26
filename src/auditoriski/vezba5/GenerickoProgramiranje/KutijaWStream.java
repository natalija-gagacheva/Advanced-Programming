package auditoriski.vezba5.GenerickoProgramiranje;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Kaj genericki tipovi V-value, E-Element, T-type
/*
T(Type) is typically used to denote a generic type. It is often used in generic classes, interfaces, and methods to represent any type.
E(Element) is commonly used to represent an element in a collection, such as in the Collection or List interfaces.
K(Key) and V(Value) are often used in the context of maps or dictionaries, where K represents the type of keys and V represents the type of values.
*/

//Kutijata ja ogranicuvame na toj nacin shto tipot koj ke go cuva mora da bide drawable, da znae sam da se nacrta
//Morashe da kreirame primer klasa Circle koja shto go implementira toj interface za da bide drawable
//pred to imavme upotrebeno <Integer> ama toa ne e drawable

//Kaj KutijaWStream extends Drawable mozime ne i ne mora da stavime tip
//Zaradi sho ova znaci deka cisto go implementirame toj interface Drawable, a ako stavime tip
//primer extends DrawableInterface<E> znaci deka ke mora go implementira Drawable bash od toj tip
//se zavisi kolku sakame da go ogranicime
//ako cisto implementirame Drawable, togas mozi nazad da vrakja i Object
//nemora da vrati od soodveten tip
public class KutijaWStream<E extends DrawableInterface> {

    private List<E> elementsList;
    public static Random randomPromenliva = new Random();

    public KutijaWStream() {
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
            return null;
        int indeks = randomPromenliva.nextInt(elementsList.size());

        //.remove() funkcionalnsota vrakja <E>
        E brishi = elementsList.remove(indeks);
        return brishi; //go brishime toj element od listata
    }
}
