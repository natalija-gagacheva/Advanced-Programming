package auditoriski.vezba9.Names;

/* Napishete programa koja kje najde kolku isti iminja se naogjaat i na dvete list,
* odnosno unisex iminja */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class NamesTest {

    public static Map<String, Integer> kreirajMapOdFile(String filePath) throws FileNotFoundException {

        Map<String,Integer> hashMap = new HashMap<>();

        InputStream is = new FileInputStream(filePath);
        Scanner skener = new Scanner(is);

        while (skener.hasNext()) {
            String linija = skener.nextLine();
            String []parts= linija.split("\\s+");
            String name = parts[0];
            int frequency = Integer.parseInt(parts[1]);

            hashMap.put(name, frequency);
        }

        return hashMap;
    }

    public static void main(String[] args) throws FileNotFoundException {

        Map<String, Integer> mapBoyName
                = kreirajMapOdFile("C:\\Users\\User\\IdeaProjects\\Napredno\\src\\auditoriski\\vezba9\\Names\\boynames");

        Map<String, Integer> mapGirlNames
                = kreirajMapOdFile("C:\\Users\\User\\IdeaProjects\\Napredno\\src\\auditoriski\\vezba9\\Names\\girlnames");


        // kreirame Set od site iminja vo dvete Maps so cel da nema duplikati
        Set<String> setOdSiteIminja = new HashSet<>();
        setOdSiteIminja.addAll(mapBoyName.keySet());
        setOdSiteIminja.addAll(mapGirlNames.keySet());

        Map<String,Integer> mapUnisexNamesSorted = new LinkedHashMap<>();

        setOdSiteIminja
                .stream()
                .filter(ednoIme -> mapBoyName.containsKey(ednoIme)
                        && mapGirlNames.containsKey(ednoIme))
        /* checks if the current name (ednoIme) is present as a key
        in both mapBoyName and mapGirlNames */
                .forEach(ednoIme -> mapUnisexNamesSorted.put(ednoIme, mapBoyName.get(ednoIme) + mapGirlNames.get(ednoIme)));
//              Pechatenje:
//                .forEach(ednoIme -> System.out.println
//                        (String.format("%s, M: %d, F: %d", ednoIme, mapBoyName.get(ednoIme), mapGirlNames.get(ednoIme)) ));

        System.out.println(mapUnisexNamesSorted);

    }
}
