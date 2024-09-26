package auditoriski.vezba9.Imenik;
import java.util.*;

class DuplicateNumberException extends Exception {
    public DuplicateNumberException(String number) {
        super(String.format("Duplicate number: [%s]", number));
    }
}

/* go pravime Comparable oti mora da bidat leksikografski podredeni */
class Contact implements Comparable<Contact> {
    String name;
    String phoneNumber;

    public Contact(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public int compareTo(Contact other) {
        int result = this.name.compareTo(other.name);
        if (result == 0) { //ako se ednakvi po ime sporedvame po broj
            return this.phoneNumber.compareTo(other.phoneNumber);
        } else {
            return result;
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s",name,phoneNumber);
    }
}

class PhoneBook {

    Set<String> mnozestvoAllPhoneNumbers; //hashSet- o(1)
    Map<String, Set<Contact>> mapSubstringsOfContacts; /* koristime set oti mora da bidat leksikografski podredeni */
    Map<String, Set<Contact>> mapContactsByName;

    public PhoneBook() {
        mnozestvoAllPhoneNumbers = new HashSet<>();
        mapSubstringsOfContacts = new HashMap<>(); /* za pristapot da bide o(1)*/
        mapContactsByName = new HashMap<>(); /* za pristapot da bide o(1)*/
    }

    /* Kompleksnosta ne treba da nadminuva o(logN) ~ toa ni kazuva deka ke treba da iskoristime
        barem HashSet vo kojshto ke gi cuvame veke dodelenite tel broevi vo imeniko */
    public void addContact(String name, String number) throws DuplicateNumberException {

        /*ako se obideme da dodademe kontakt so veke postoecki br, treba da se frli isklucok. */

        if (mnozestvoAllPhoneNumbers.contains(number)) {
            throw new DuplicateNumberException(number);
        } else {
            mnozestvoAllPhoneNumbers.add(number);
            Contact valueKontakt = new Contact(name, number);
            List<String> listOfSubstringsOfNumbers = zemiSubstring(number);

            for (String keyEdenSubStr : listOfSubstringsOfNumbers) {
                /* dokolku ne postoi kluchot (vo nashiot sluchaj to e keyEdenSubStr)
                * togas treba da stavi nov prazen set. Vaka izgleda:
                * Map<String, Set<Contact>> mapSubstringsOfContacts;
                * */
                mapSubstringsOfContacts.putIfAbsent(keyEdenSubStr, new TreeSet<>());
                /* Go zemame subStr, i na setot go dodavame noviot kontakt shto go kreiravme */
                mapSubstringsOfContacts.get(keyEdenSubStr).add(valueKontakt);
            }

            mapContactsByName.putIfAbsent(name, new TreeSet<>());
            // go zemame imeto i go dodavame vo setot od Kontakti
            mapContactsByName.get(name).add(valueKontakt);
        }
    }

    /* So ovoj metod gi dobivame site mozhni substrings od eden broj */
    private List<String> zemiSubstring (String telBroj) {

        List<String> resultLista = new ArrayList<>();

        /* dolzinata minimum treba da e 3, do dolzinata na samiot broj
        (see example.jpg)         */
        for (int subStrLen=3; subStrLen<=telBroj.length() ; subStrLen++) {

            /* i treba da se dvizi od 0 do dolzinata na subStrLen */
            for (int i=0; i<=telBroj.length()-subStrLen; i++) {
                /* Begin index e inclusive, pocnuva od i
                * ending index e exclusive, bez taja vrednost */
                resultLista.add(telBroj.substring(i,i+subStrLen));
            }
        }

        return resultLista;
    }


    public void contactsByNumber(String number) {

        /* dokolku ne se najdi brojot da se ispechati poraka not found */
        Set<Contact> setKontakti = mapSubstringsOfContacts.get(number);

        if (setKontakti == null) {
            System.out.println("NOT FOUND");
            return ; //za da zavrshi ovoj metod pravime return ;
        }

        mapSubstringsOfContacts
                .get(number)
                .forEach(kontakt -> System.out.println(kontakt));

    }

    public void contactsByName(String name) {

        /* dokolku ne se najdi brojot da se ispechati poraka not found */
        Set<Contact> setKontakti = mapContactsByName.get(name);

        if (setKontakti == null) {
            System.out.println("NOT FOUND");
            return ;
        }
        mapContactsByName
                .get(name)
                .forEach(kontakt -> System.out.println(kontakt));

    }
}

public class PhoneBookTest {

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");

            try {
                phoneBook.addContact(parts[0], parts[1]);
            } catch (DuplicateNumberException e) {
                throw new RuntimeException(e);
            }
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            if (parts[0].equals("NUM")) {
                phoneBook.contactsByNumber(parts[1]);
            } else {
                phoneBook.contactsByName(parts[1]);
            }
        }
    }
}


