package auditoriski.vezba4_2023_2024.ReadingIntro;

import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ReadingIntro {

    public static void main(String[] args) {

//---------------------------------------Citanje na stringovi ---------------------------------------------------------------------

        //vo () ja pishuvame vrednosta na konstruktorot na skenerot, t.e od kade ke citame
        Scanner skener = new Scanner(System.in);

        List<String> inputsLista = new ArrayList<String>();

        //citame se dodeka ima sledna linija
        //za da oznacime kraj na citanje vo terminal pisime ctrl+D
        while (skener.hasNext()) {
            String linija = skener.nextLine();
            //sekoja linija kako shto ke ja citame ke ja dodavame vo listata na inputsLista
            inputsLista.add(linija);
        }

        System.out.println(inputsLista);

//--------------------------------------- Citanje na stringovi so buffered reader ---------------------------------------------------------------------

        //koga sakame da chitame red po red od datoteka

        //br ocekuva objekt od Reader, i vo toj objekt prakjame argument System.in shto pretstavuva vlezen potok
        //na polaganje ke treba da citame od generalen vlezenInput stream
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> inputsLista2 = new ArrayList<String>();

        //citanje na site linii od vlezenInput stream
        //.lines() vrakja potok od linii
        //.collect(Collectors.toList()) na ovoj nacin gi sobirame liniite i pretvorame vo elementi od lista
        inputsLista2 = br.lines().collect(Collectors.toList());

        System.out.println(inputsLista2);


//---------------------------------------Formatirano vcituvanje na podatoci ---------------------------------------------------------------------
        //citame n, pa posle citame n broevi, vo ova situacija mora da koristime skener
        int n;
        n = skener.nextInt();
        ArrayList<Integer> broeviLista = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            broeviLista.add(skener.nextInt());
        }
        System.out.println(broeviLista);
    }
}
