package auditoriski.vezba8.BookCollectionProblem_Java8;

import java.util.*;
import java.util.stream.Collectors;

/* so Java8, so pomos na lambda izrazi mozime da deklarirame komparatori vo samata klasa Book  */
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

}

class BookCollection {
    List<Book> listaOdKnigi;

    //so anonimna klasa
    final Comparator<Book> titleAndPriceComparator = new Comparator<Book>() {
        @Override
        public int compare(Book o1, Book o2) {
            int result = o1.naslov.compareTo(o2.naslov);
            if (result == 0) {
                return Float.compare(o1.cena, o2.cena);
            } else {
                return result;
            }
        }
    };

    final Comparator<Book> priceComparator = new Comparator<Book>() {
        @Override
        public int compare(Book o1, Book o2) {
            int result = o1.naslov.compareTo(o2.naslov);
            if (result == 0) {
                return Float.compare(o1.cena, o2.cena);
            } else {
                return result;
            }
        }
    };

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
                .sorted(titleAndPriceComparator) /* ke gi sortirame spored implementiraniot Comparator vnatre vo klasata Book */
                .forEach(ednaKniga -> System.out.println(ednaKniga));
    }

    /* враќа листа на најевтините N книги
    (ако има помалку од N книги во колекцијата, ги враќа сите) */
    public List<Book> getCheapestN(int n) {

        List<Book> nova = listaOdKnigi
                .stream()
                .sorted(priceComparator)
                .limit(n)
                .collect(Collectors.toList());//gi sobirame prvite N knigi vo lista

        if (listaOdKnigi.size() < n) {
            return listaOdKnigi;
        } else
            return nova;
    }
}

public class BookCollectionTest2 {
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
