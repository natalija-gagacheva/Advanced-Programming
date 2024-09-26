package auditoriski.vezba4.WordCount;

import java.util.function.Consumer;

//implementirame Consumer interface, ke konzumira Stringovi
public class LineConsumerKlasa implements Consumer<String> {

    private int brLinii=0;
    private int brZborovi=0;
    private int brKarakteri=0;

    //mora da go implementirame metodot od samiot interface
    @Override
    public void accept(String linija) {
        ++brLinii;
        brZborovi=brZborovi+linija.split("\\s+").length;
        brKarakteri=brKarakteri+linija.length();

    }

    //mora da se implementira toString metodot za da kazhime kako sakame da se pechati
    @Override
    public String toString() {
        return String.format("Consumer:\n Broj na Linii: %d \n Broj na zborovi: %d \n Broj karakteri: %d", brLinii, brZborovi, brKarakteri);

    }


}
