package auditoriski.vezba5.ProsekNaSlucajniBroevi;

//da se napishe programa koja zapishuva n slucajni broevi vo binarna datoteka
//potoa gi vcituva i go presmetuva nivniot prosek

import java.io.*;
import java.util.Random;

public class BinaryNumbers {

    //celosnata pateka na fajlot
    //IntellJ nema da mozi da go procita toj file radi sho ne ni e povrzan da cita takov tip na binary files
    public static final String FILE_NAME = "C:\\Users\\User\\IdeaProjects\\Napredno\\src\\auditoriski\\vezba5\\ProsekNaSlucajniBroevi\\numbersFile";

    private static void generateFile(int n) {

        //objectOUTPUTstream ni ovozmozuva zapishuvaje kod vo binarna datoteka
        try {
            ObjectOutputStream objectoutputstream = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            Random randomObjekt = new Random();

            for (int i=0; i<n; i++) {
                //generira random broj i go zapishva vo klasata
                int randomBr = randomObjekt.nextInt(1000);
                objectoutputstream.writeInt(randomBr);
            }
            objectoutputstream.flush(); //so ova kazhuvame deka se sho ima vo bufferot se pechati negde
            //ako povikame .close() implicitno se povikuva i .flush()
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static double presmetuvanjeProsek() {
        int count = 0;
        double sum = 0.0;

        try {
            //objectINPUTstream se koristi za citanje od binaren file kojshto go kreiravme so objectOUTPUTstream
            //kreiranje na nov FileInputStream frla isklucok
            ObjectInputStream objectinputstream = new ObjectInputStream(new FileInputStream(FILE_NAME));

            //koga ke citame od BinaryFile, nakraj koga ke proba da go procita sledniot red
            //ke frli end of file exception, sho znaci mora da go prekinime, pred da go procita sledniot red
            try {
                while (true) {
                    int broj = objectinputstream.readInt();
                    sum = sum + broj;
                    count++;
                }
            } catch (EOFException e) {
                System.out.println("End of file was reached");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return sum / count;
    }

    public static void main(String[] args) {
        generateFile(1000);
        System.out.println("Average:" + presmetuvanjeProsek());
    }
}
