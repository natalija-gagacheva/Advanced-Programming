package auditoriski.vezba9.FileSystem;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

class File implements Comparable<File> {
    char imeNaPapka; //go chuvame folderot na eden file
    String ime;
    int golemina;
    LocalDateTime kreiranoVo;

    public File(char imeNaPapka, String ime, int golemina, LocalDateTime kreiranoVo) {
        this.imeNaPapka = imeNaPapka;
        this.ime = ime;
        this.golemina = golemina;
        this.kreiranoVo = kreiranoVo;
    }

    public String getIme() {
        return ime;
    }

    public int getGolemina() {
        return golemina;
    }

    public LocalDateTime getKreiranoVo() {
        return kreiranoVo;
    }

    @Override
    public int compareTo(File other) {
        /* Sporedba po datum na kreiranje, pa ime, pa golemina */
        Comparator<File> komparator =
                Comparator.comparing((File edenFile) -> edenFile.getKreiranoVo())
                        .thenComparing((File edenFile) -> edenFile.getIme())
                        .thenComparing ((File edenFile) -> edenFile.getGolemina());

        return komparator.compare(this, other);
    }

    public String izdvoiYearAndMonth() {

        return String.format("%s - %s", kreiranoVo.getMonth().toString(), kreiranoVo.getDayOfMonth());
    }
}

/* Vo klasata za FileSystem gi chuvame site datoteki/files shto ke pristignat vo eden folder/papka */
class FileSystem {

    List<File> listaOdFiles = new ArrayList<>();

    public FileSystem() {

    }

    /* metod za dodavanje na nova datoteka File vo papka so dadenoto ime */
    public void dodajFile (char papka, String ime, int golemina, LocalDateTime kreiranoVo) {

        listaOdFiles.add(new File(papka, ime, golemina, kreiranoVo));
    }

    /* vrakja lista na site skrieni datoteki*/
    public List<File> findAllHiddenFilesWithSizeLessThen(int size) {

        return listaOdFiles
                .stream()
                .filter(edenFile -> edenFile.getIme().startsWith(".")) //filtriranje spored toa sekoe ime na papkata vo koj se naogja eden file da bide tocka
                .filter(edenFile -> edenFile.getGolemina()<size)
                .sorted()
                .collect(Collectors.toList());
    }

    /* vrakja vk golemina na site datoteki koi se naogjaat vo folderite
    * kojshto se zadadeni vo listata preku argument */
    public int totalSizeOfFilesFromFolders (List<Character> folderArgument) {

        return listaOdFiles
                .stream()
                //filtrirame: sekoj file chij ime na folder se sodrzhi vo listata od folderot prenesen kako argument
                .filter(edenFile -> folderArgument.contains(edenFile.imeNaPapka))
                .mapToInt(edenFile -> edenFile.getGolemina())
                .sum();

    }

    /* vrakja mapa vo koja files se grupirani spored godinata na kreiranje
    * odnosno key: godinata, dodeka pak value: site files kreirani vo taa godina */
    public Map<Integer, Set<File>> byYear() {

        return listaOdFiles
                .stream()
                .collect(Collectors.groupingBy(
                        //od sekoj file we extract the year
                        edenFile -> edenFile.kreiranoVo.getYear(),
                        //specifirame kakva sakame da ni bide mapata
                        () -> new TreeMap<>(),
                        //ke gi soberime vo tree set oti se bara da bidat sortirani
                        Collectors.toCollection(() -> new TreeSet<>())
                        )
                );
    }

    /* vrakja mapa vo koja za sekoj mesec i den (nezavisno od godinata) se presmetuva
    * vkupnata golemina na site datoteki kreirani vo toj mesec i toj den
    * Mesecot se dobiva so povik na metodot getMonth(), a denot so getDayOfMonth() */
    public Map<String, Long> sizeByMonthAndDay() {

        return listaOdFiles
                .stream()
                .collect(Collectors.groupingBy(
                        //gi grupira spored mesec i godina
                        edenFile -> edenFile.izdvoiYearAndMonth(),
                        //ja presmetuva godinata
                        Collectors.summingLong(edenFile -> edenFile.getGolemina())
                ));
    }
}

public class FileSystemTest {
    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            fileSystem.dodajFile(parts[0].charAt(0), parts[1],
                    Integer.parseInt(parts[2]),
                    LocalDateTime.of(2016, 12, 29, 0, 0, 0).minusDays(Integer.parseInt(parts[3]))
            );
        }
        int action = scanner.nextInt();
        if (action == 0) {
            scanner.nextLine();
            int size = scanner.nextInt();
            System.out.println("== Find all hidden files with size less then " + size);
            List<File> files = fileSystem.findAllHiddenFilesWithSizeLessThen(size);
            files.forEach(System.out::println);
        } else if (action == 1) {
            scanner.nextLine();
            String[] parts = scanner.nextLine().split(":");
            System.out.println("== Total size of files from folders: " + Arrays.toString(parts));
            int totalSize = fileSystem.totalSizeOfFilesFromFolders(Arrays.stream(parts)
                    .map(s -> s.charAt(0))
                    .collect(Collectors.toList()));
            System.out.println(totalSize);
        } else if (action == 2) {
            System.out.println("== Files by year");
            Map<Integer, Set<File>> byYear = fileSystem.byYear();
            byYear.keySet().stream().sorted()
                    .forEach(key -> {
                        System.out.printf("Year: %d\n", key);
                        Set<File> files = byYear.get(key);
                        files.stream()
                                .sorted()
                                .forEach(System.out::println);
                    });
        } else if (action == 3) {
            System.out.println("== Size by month and day");
            Map<String, Long> byMonthAndDay = fileSystem.sizeByMonthAndDay();
            byMonthAndDay.keySet().stream().sorted()
                    .forEach(key -> System.out.printf("%s -> %d\n", key, byMonthAndDay.get(key)));
        }
        scanner.close();
    }
}