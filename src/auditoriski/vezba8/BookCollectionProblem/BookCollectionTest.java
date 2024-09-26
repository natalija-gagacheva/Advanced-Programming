package auditoriski.vezba8.BookCollectionProblem;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* Koga treba da sporeduvame 2 objekti spored razlinci kriteriumi, kreirame Comparator klasi*/
class TitleAndPriceComparator implements Comparator<Book> {

    @Override
    public int compare(Book o1, Book o2) {
        int sporedbaNaslovi = o1.naslov.compareTo(o2.naslov);
        if (sporedbaNaslovi == 0) {
            /*sporedba na cenite */
            return Float.compare(o1.cena, o2.cena);
        } else {
            return sporedbaNaslovi;
        }
    }
}

class PriceComparator implements Comparator<Book> {

    @Override
    public int compare(Book o1, Book o2) {
        int result = Float.compare(o1.cena, o2.cena);
        if (result == 0)
            return o1.naslov.compareTo(o2.naslov);
        else
            return result;
    }
}

class Book  {
    String naslov;
    String kategorija;
    float cena;

    public Book(String naslov, String kategorija, float cena) {
        this.naslov = naslov;
        this.kategorija = kategorija;
        this.cena = cena;
    }

    public String getNaslov() {
        return naslov;
    }

    public String getKategorija() {
        return kategorija;
    }

    public float getCena() {
        return cena;
    }

    //1984 (A) 11.30
    @Override
    public String toString() {
        return String.format("%s (%s) %.2f", naslov, kategorija, cena);
    }

//    @Override
//    public int compareTo(Book o) { --- not useful for the 2nd printing method
//
//        /* sporedba na naslovite */
//        int sporedbaNaslovi = this.naslov.compareTo(o.naslov);
//        if (sporedbaNaslovi == 0) {
//            /*sporedba na cenite */
//            return Float.compare(this.cena, o.cena);
//        } else {
//            return sporedbaNaslovi;
//        }
//    }
}

class BookCollection {
    List<Book> listaOdKnigi;

    public BookCollection() {
        listaOdKnigi = new ArrayList<>();
    }

    /* metod za dodavanje na kniga vo kolekcija */
    public void addBook(Book book) {
        listaOdKnigi.add(book);
    }

    /* metod koj gi pecati site knigi od dadena kategorija,
    sporedbata na stringot se pravi bez razlika na mali i golemi bukvi
    i pecatenjeto treba da bide sortirano spored naslovot na knigata, ili cena */
    public void printByCategory(String category) {

        listaOdKnigi
                .stream()
                .filter(ednaKniga -> ednaKniga.kategorija.equalsIgnoreCase(category))
                .sorted(new TitleAndPriceComparator()) /* ke gi sortirame spored implementiraniot Comparator vnatre vo klasata Book */
                .forEach(ednaKniga -> System.out.println(ednaKniga));
    }

    /* враќа листа на најевтините N книги
    (ако има помалку од N книги во колекцијата, ги враќа сите) */
    public List<Book> getCheapestN(int n) {

        List<Book> nova = listaOdKnigi
                .stream()
                .sorted(new PriceComparator())
                .limit(n)
                .collect(Collectors.toList());//gi sobirame prvite N knigi vo lista

        if (listaOdKnigi.size() < n) {
            return listaOdKnigi;
        } else
            return nova;
    }
}

public class BookCollectionTest {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();

        BookCollection booksCollection = new BookCollection();

        Set<String> categories = fillCollection(scanner, booksCollection);

        System.out.println("=== PRINT BY CATEGORY ===");

        for (String category : categories) {
            System.out.println("CATEGORY: " + category);
            booksCollection.printByCategory(category);
        }

        System.out.println("=== TOP N BY PRICE ===");
        print(booksCollection.getCheapestN(n));
    }

    static void print(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    static TreeSet<String> fillCollection(Scanner scanner, BookCollection collection)
    {
        TreeSet<String> categories = new TreeSet<String>();

        while (scanner.hasNext())
        {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            Book book = new Book(parts[0], parts[1], Float.parseFloat(parts[2]));
            collection.addBook(book);
            categories.add(parts[1]);
        }
        return categories;
    }
}
