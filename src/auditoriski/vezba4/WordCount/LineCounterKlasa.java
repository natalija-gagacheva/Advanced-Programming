package auditoriski.vezba4.WordCount;

public class LineCounterKlasa {
    private int brLinii;
    private int brZborovi;
    private int brKarakteri;

    public LineCounterKlasa(int brLinii, int brZborovi, int brKarakteri) {
        this.brLinii = brLinii;
        this.brZborovi = brZborovi;
        this.brKarakteri = brKarakteri;
    }

    //kreirame konstruktor kojshto ke prima linija, t.e string
    //za sekoja linija/string broime linii,zborovi i karakteri

    public LineCounterKlasa(String linija) {
        ++brLinii;
        brZborovi += linija.split("\\s+").length;
        brKarakteri += linija.length();
    }

    public LineCounterKlasa suma(LineCounterKlasa drugLinecounter) {
        //pravime zbir od vrednostite na ovoj objekt i drugiot koj e prenesen kako argument
        return new LineCounterKlasa(this.brLinii + drugLinecounter.brLinii,
                                  this.brZborovi+ drugLinecounter.brZborovi,
                                 this.brKarakteri+drugLinecounter.brKarakteri);
    }

    @Override
    public String toString() {
        return String.format("BufferedReaderMapAndReduce:\n Broj na Linii: %d \n Broj na zborovi: %d \n Broj karakteri: %d", brLinii, brZborovi, brKarakteri);

    }
}
