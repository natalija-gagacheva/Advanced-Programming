package auditoriski.vezba5.GenerickoProgramiranje;

/*Mora da napravime da simulira iscrtuvanje na slucaen predmet od nekoja kutija
toa znaci deka nie kako kutija mora da mozime sekoj predmet da go iscrtame
odnsono sekoj predmet treba da bide "drawable"
za taa cel treba da implementirame interface
znaci, treba da primorame deka ona shto go cuvame vo tuka znae da se iscrta samo po sebe
*/

/*
T(Type) is typically used to denote a generic type. It is often used in generic classes, interfaces, and methods to represent any type.
E(Element) is commonly used to represent an element in a collection, such as in the Collection or List interfaces.
K(Key) and V(Value) are often used in the context of maps or dictionaries, where K represents the type of keys and V represents the type of values.
*/

//Genericki interface
// so ova kazuvame deka interface-ot znae da iscrta od tip <T>
//Kaj genericki tipovi V-value, E-Element, T-type
public interface DrawableInterface<T> {

    //metod
    //se vrakja nazad onoj element kojshto ke se iscrta
    T draw();
}
