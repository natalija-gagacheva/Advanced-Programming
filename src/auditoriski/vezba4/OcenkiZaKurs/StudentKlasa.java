package auditoriski.vezba4.OcenkiZaKurs;

//vo programata se chita imeto na datotekata
//ispitite se vrednuvaat 25% za prv, 30% za vtor, 45% za tret
//Na ekran treba da se ispechati lista od studenti podredeni spored ocenka vo opagjacki redosled vo sledniot format: LastName FirstName LetterGrade
//Vo datotekata se zapishuvaat rez vo sledniot format: Lastname Firstname exam1 exam2 exam3 totalpoints lettergrade
//Otkako ke se zapishe ovaa sodrzina vo datoteka, se pecati na ekran distribucijata na ocenkite

//pravime sporedba so drug student
public class StudentKlasa implements Comparable<StudentKlasa> {
    private String prezime;
    private String ime;
    private int ispit1, ispit2, ispit3;
    private char ocenka; //ke ja chuvame ocenkata kako posebna promenliva oti ke ni treba da koristime Getter i Setter

    public StudentKlasa(String prezime, String ime, int ispit1, int ispit2, int ispit3) {
        this.prezime = prezime;
        this.ime = ime;
        this.ispit1 = ispit1;
        this.ispit2 = ispit2;
        this.ispit3 = ispit3;
        setOcenka();
        //koga se kreira nov objekt sakame sekogas da se napravi setGrade() i da se postavi soodvetnata ocenka za sekoj objekt;
    }

    public char getOcenka() {
        return ocenka;
    }

    //spored brojot na poeni na ispit 1,2,3 sakame da presmetame vk ocenka
    public double presmetkaPoeni() {
        return ispit1*0.25 + ispit2*0.3 + ispit3*0.45;
    }

    //gi zemame poenite
    public void setOcenka() {
        double poeni = presmetkaPoeni();
        //na sekoe povikuvanje na konstruktorot, za sekoj objekt ke se presmetat nanovo poenite i ke se formira soodvetna ocenka
        if (poeni >= 91) {
            this.ocenka = 'A';
        } else if (poeni >= 81) {
            this.ocenka = 'B';
        } else if (poeni >= 71) {
            this.ocenka = 'C';
        } else if (poeni >= 61) {
            this.ocenka = 'D';
        } else {
            this.ocenka = 'E';
        }

    }

    //go pravime public static
    // oti nemozime preku objekt, morame preku klasata da go pristapime ovoj metod
    //koj shto ke prima linija od .lines().map() i ke vrati student
    public static StudentKlasa kreirajStudent (String linija) {

        //ja delime linijata
        String[] delcinja = linija.split(":");
        return new StudentKlasa(delcinja[0], //prezime
                delcinja[1], //ime
                Integer.parseInt(delcinja[2]), //exam1
                Integer.parseInt(delcinja[3]), //exam2
                Integer.parseInt(delcinja[3])); //exam3

        //namesto preku konstruktor da kreirame student za mapiranje,
        //koristime staticki metod
    }

    //Ne koristime stringBuilder, tuku obicna konkatanacija radi sho imame konechen broj na stringovi shto treba da se ispechatat
    @Override
    public String toString() {
        return prezime + " " + ime + " "  + getOcenka(); //ili samo grade mozhevme
    }

    //Metod za pechatenje na celosen format sho se bara vo zadachata
    public String pecatiCelosenFormat () {
        return String.format(" %s %s %d %d %d %.2f %c", prezime, ime, ispit1, ispit2, ispit3, presmetkaPoeni(), ocenka);
    }

    //Od zadachata ni se bara da gi podredime studentite po opagjacki redosled spored poeni
    @Override
    public int compareTo(StudentKlasa otherStudent) {
        //sporeduvame karakteri (A,B,C,D,E,F) zato ja povikuvame klasata Character
        //ako go smenime redosledot da bide (otherStudent.ocenka, this.ocenka) ke se sortira ponatamu po opagjacki redosled
        return Character.compare(this.ocenka, otherStudent.ocenka);
    }
}
