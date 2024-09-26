package auditoriski.vezba4_2023_2024.WordCount;

import java.io.*;
import java.util.Scanner;
import java.util.function.Consumer;

class LineConsumer implements Consumer<String> {

    int brLinii = 0, brZborovi = 0, brKarakteri = 0;

    @Override
    public void accept(String s) {
        //na sekoja linija koja shto ke ja primime od nashiot consumer, gi zgolemvame promenlivite
        brLinii++;
        brZborovi= brZborovi + s.split("\\s+").length;
        brKarakteri= brKarakteri + s.length();

        System.out.println(String.format("Linii: %d, zborovi: %d, karakteri: %d", brLinii, brZborovi, brKarakteri));

    }
}

class WordCount {
    public static void countMethod(InputStream inputstrim) {

        Scanner skener = new Scanner(inputstrim);
        int brLinii = 0, brZborovi = 0, brKarakteri = 0;

        //se dodeka ima linii za citanje
        while (skener.hasNextLine()) {
            String linija = skener.nextLine();
            //kako shto ja citame linijata go zgolemuvame brojot
            brLinii++;
            //mora da ja isprocesirame linijata za da doznajme brKarakteri i zborovi
            //.split() finkcijata vrakja niza
            String[] zborovi = linija.split("\\s+");
            brZborovi = brZborovi + zborovi.length;
            brKarakteri = linija.length();
        }


        //Pechatime nekakov formatiran string
        System.out.println(String.format("Linii: %d, zborovi: %d, karakteri: %d", brLinii, brZborovi, brKarakteri));

    }

        public static void countWithStream(InputStream inputstrim) {

        //citanje linija po linija i koristenje na streams, naj idealen e buffered reader
            BufferedReader br = new BufferedReader(new InputStreamReader(inputstrim));

            //user created line consumer
            LineConsumer lineConsumer = new LineConsumer();

            //consumer se koristi vo .forEach()
            //za sekoja linija consumer ke primi po edna linija i ke gi zgolemi vrednostite na promenlivite
            br.lines().forEach(lineConsumer);

            //otkako ke zavrshi celiot ovoj stream, na ekran pechatime cel consumer
            System.out.println(lineConsumer);
        }
}

public class WordCounterWConsumer {

    public static void main(String[] args) {

        //kreiranje na inputStream shto ke chita od File
        //frla exception
        try {
            InputStream isFromFile = new FileInputStream(new File("C:\\Users\\User\\IdeaProjects\\Napredno\\src\\auditoriski\\vezba4_2023_2024\\WordCount\\source"));
            WordCount.countMethod(isFromFile);
            WordCount.countWithStream(isFromFile); //ovde nesto ne pecati ko sho treba ama nz sho e

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
