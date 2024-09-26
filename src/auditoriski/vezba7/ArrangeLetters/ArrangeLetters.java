package auditoriski.vezba7.ArrangeLetters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ArrangeLetters {

    public static String arrangeZbor (String zbor) {
        /* pSk */

        String result = zbor
                .chars() //go konvertira 'zbor' vo IntStream
                .sorted()//sortirame
                .mapToObj(edenKarakter -> String.valueOf((char) edenKarakter))
                /* sekoj karakter od stream-ot .char() go mapirame vo string
                so toa shto sekoj karakter go pretvara vo char posto neli preth e IntStream
                a so metodot String.valueOf() se pretvara vo string
                 */
                .collect(Collectors.joining()); //go kreirame povtorno zborot

        return result;

    }

    public static String arrangeRechenica (String rechenica) {
        /* "kO pSk sO" -> "Ok Os Skp" */
        String []parts = rechenica.split("\\s+");

        String result = Arrays
                .stream(parts) /* [kO, pSk, sO] */
                .map(edenZbor -> arrangeZbor(edenZbor)) /* map ke gi vrati zborojte po redosled [Ok, Skp, Os] */
                .sorted() /* zborovite treba sami po sebe da gi sortirame alphabetically [Ok, Os, Skp] */
                .collect(Collectors.joining(" ")); /* stream-ot od nizata so zborovi treba da go spoime vo recenica, kade ke bidat oddeleni so prazno mesto */

        return result;

    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in); //citame recenica po recenica od tastatura

        String recenica = sc.nextLine();

        System.out.println(arrangeRechenica(recenica));
    }
}
