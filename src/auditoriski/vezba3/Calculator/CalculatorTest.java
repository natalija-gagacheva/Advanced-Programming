package auditoriski.vezba3.Calculator;
import java.util.Scanner;

public class CalculatorTest {

    //mora da napravime da prifakja bilo kakov odgovor (pr. N, No, n), toa go pravime preku funkcija
   //od string-ot go zemame prviot karakter i go pravime vo mala bukva
    public static char getCharFromStringToLower(String linija) {
        if (linija.trim().length() > 0) //ignorirame ako vnel prazno mesto
        {
            return Character.toLowerCase(linija.charAt(0));
        }
        else
            return '?';
    }

    public static void main(String[] args) {
        Scanner skener = new Scanner(System.in); //citanje od tastatura

        while(true) { // y/n
            CalculatorKlasa kalkulator = new CalculatorKlasa();
            System.out.println(kalkulator.init());

            while (true) //chitame linija po linija sekoj izbor na korisnikot
            {
                String linija = skener.nextLine(); ;
                char izbor = getCharFromStringToLower(linija);
                if (izbor == 'r') {
                    System.out.println(String.format("Final result = %f", kalkulator.getResult()));
                    break;
                }

                String[] delcinja = linija.split("\\s+");
                char operator = delcinja[0].charAt(0); //posto e String, go delime karakterot od 0ta pozicija
                double vrednostNaBroj = Double.parseDouble(delcinja[1]);

                //FAKJANJE ISKLUCHOK ----------------------------------------------------------------------------------------------------------------------------------------
                //metodot execute vo sluchaj da frli UnknownOperatorException iskluchok
                //iskluchokot mozhe da go fatime vo drug metod ili kako last resort vo main (momentalno sme vo main)
                // ako ne go fatime vo main, programata ke padne

                //koga ke se povika metodot execute, tamu ke imame Try Catch Block
                try {
                    String rezultat = kalkulator.execute(operator, vrednostNaBroj);
                    System.out.println(rezultat);
                    System.out.println(kalkulator);
                } catch (UnknownOperatorException e) {
                    System.out.println(e.getMessage());
                }
            }

            System.out.println(" (Y/N) ?" );
            String linija = skener.nextLine();
            char izbor = getCharFromStringToLower(linija);
            if (izbor == 'n') {
                break;
            }
        }
    }
}
