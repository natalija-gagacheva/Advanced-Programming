package auditoriski.vezba4.OcenkiZaKurs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class GradesTestKlasa {

    //Mainot ke bide daden

    public static void main(String[] args) {

        CourseKlasa kurs = new CourseKlasa();

        File inputDatoteka = new File("C:\\Users\\User\\IdeaProjects\\Napredno\\src\\auditoriski\\vezba4\\OcenkiZaKurs\\studenti");
        File outputDatoteka = new File("C:\\Users\\User\\IdeaProjects\\Napredno\\src\\auditoriski\\vezba4\\OcenkiZaKurs\\rezultati");

        try {
            kurs.citajPodatoci(new FileInputStream(inputDatoteka));
            //citanje podatoci od file,
            //metodot ne e static tuku obichen oti go pristapuva kako instanca na kursot

            System.out.println("===Printing sorted students to screen===");
            kurs.sortiranoPecatenje(System.out); //na ekran

            System.out.println("===Printing detailed report to file===");
            kurs.detalnoPecatenje(new FileOutputStream(outputDatoteka)); //vo file

            System.out.println("===Printing grade distribution to screen===");
            kurs.distributedPecatenje(System.out); //na ekran

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
