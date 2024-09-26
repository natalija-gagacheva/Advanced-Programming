package auditoriski.vezba4_2023_2024.WordCount;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

class WordCounter {

    //metodot chita od bilo koj tip na InputStream
    //mozhe da bide file, baza na podatoci, od tastatura etc.
    public static void countMethod(InputStream inputstrim) {

        Scanner skener = new Scanner(inputstrim);
        int brLinii=0, brZborovi=0, brKarakteri=0;

        //se dodeka ima linii za citanje
        while (skener.hasNextLine()) {
            String linija = skener.nextLine();
            //kako shto ja citame linijata go zgolemuvame brojot
            brLinii++;
            //mora da ja isprocesirame linijata za da doznajme brKarakteri i zborovi
            //.split() finkcijata vrakja niza
            String[] zborovi = linija.split("\\s+");
            brZborovi= brZborovi+ zborovi.length;
            brKarakteri = linija.length();
        }

        //Pechatime nekakov formatiran string
        System.out.println(String.format("Linii: %d, zborovi: %d, karakteri: %d", brLinii, brZborovi, brKarakteri));
    }
}
public class WordCounterTest {

    public static void main(String[] args) {

        //kreiranje na inputStream shto ke chita od File
        //frla exception
        try {
            InputStream isFromFile = new FileInputStream(new File("C:\\Users\\User\\IdeaProjects\\Napredno\\src\\auditoriski\\vezba4_2023_2024\\WordCount\\source"));
            WordCounter.countMethod(isFromFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
