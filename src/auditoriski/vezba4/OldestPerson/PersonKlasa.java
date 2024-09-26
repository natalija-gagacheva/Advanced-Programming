package auditoriski.vezba4.OldestPerson;

//Da se pronajdi imeto i vozrasta na najstariot od datotekata so iminja

public class PersonKlasa implements Comparable<PersonKlasa> {
    private String ime;
    private int vozrast;

    public PersonKlasa(String ime, int vozrast) {
        this.ime = ime;
        this.vozrast = vozrast;
    }

    //posto barame koj e najstar, mora da implementirame compare metodot od klasata Comparable
    @Override
    public int compareTo(PersonKlasa drugCoek) {
        //pravime sporedba na dva Integeri
        return Integer.compare(this.vozrast, drugCoek.vozrast);
        //so ovaa sintaksa, dokolku povikame .sort() ke se sortiraat od najmal do najgolem
        ///nemora this.vozrast, mozi samo vozrast
    }

    //toString metoda
    @Override
    public String toString() {
        return "PersonKlasa{" +
                "ime='" + ime + '\'' +
                ", vozrast=" + vozrast +
                '}';
    }

    //konstruktor shto ke prima string od .lines() za da mozi da gi pretvorime vo objekti od ovaa klasa
    public PersonKlasa(String linija) {
        String[] delcinja = linija.split("\\s+");
        this.ime = delcinja[0];
        //Mora da parsirame od string vo Integer value za da ja zemime vozrasta
        this.vozrast = Integer.parseInt(delcinja[1]);
    }
}

