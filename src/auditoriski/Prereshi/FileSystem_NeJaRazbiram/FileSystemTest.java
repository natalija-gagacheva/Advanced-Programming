//package auditoriski.Prereshi.FileSystem_NeJaRazbiram;
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
////da se frli isklucok od tip
////FileNameExistsException vo kojshto se prosleduva imeto koe vekje postoi
//class FileNameExistsException extends Exception{
//
//    //how the exception looks like gledame of vlezenInput from test cases
//    public FileNameExistsException(String nameOfFile, String nameOfFolder) { //konstruktor na exception
//        super(String.format("There is already a file named %s in the folder %s", nameOfFile, nameOfFolder));
//    }
//}
//
////da se implementira interface IFile so soodvetni metodi, taka shto sekoja datoteka/file
////kje gi ima slednite karakteristiki
//interface IFileInterface extends Comparable<IFileInterface> {
//
//    //da mozhe da se pristapi do negovoto ime
//    String getFileName();
//
//    //mozhe da se dobie negovata golemina vo long
//    long getFileSize();
//
//    //mozhe da se dobie String reprezentaicja na fajlot
//    //kakov argument zema dobivame od podolu tekstot od zadacata,
//    //i toa: pri generiranje na String reprezentacijata na direktoriumite,datotekite
//    //vo toj direktorium, da se vovlecheni so tab ("\t")
//    String getFileInfo(int indent); //vo argument cuvame kolku tabovi treba da slegvame na dolu
//
//    //mozhe da se sortira datotekata dokolku e kolekcija
//    //od datoteki spored goleminite na datotekite koi gi sodrzhi
//    void SortBySize();
//
//    /*mozhe da se presmeta goleminata na najgolemata obicna datoteka vo datotekata
//    za da otkrieme kakov tip treba da vrakja ovoj metod, sogledvame deka goleminite
//    na datotekite se cuvat kako long spored pogorniot metod */
//
//    long findLargestFile();
//
//}
///*postojat 2 tipa na datoteki, File i Folder. Potrebno e tie da go implementiraat IFile interface-ot
//i vo dvete klasi da se implementiraat metodite koi se deklarirani vo IFile interface
//da se zapazi na slednite faktori:
//1. Goleminata na eden Folder e suma od goleminite na site datoteki koi se naogjaat vo nego
//2. Pri generiranje na String reprezentacija na direktoriumite, datotekite i poddirektoriumite
//vo toj direkotium da se vovlecheni so tab ("\t")
//3. String reprezentacija na edna obicna datoteka
//*/
//
////kreirame posebna klasa za baranjeto vo zadachata kade shto se bara
////pri generiranje na String reprezentacija fajlovite vo eden direktorium da se vovlezheni so tab
//class IndentPrinter {
//
//    //ako ispratime kako argument 4, ke ni spoi 4 tabovi kako stringovi, 4 tab spaces
//    public static String printIndentation (int brTabs) { //brTabs se kolku indentacii ke treba da se ispechatat
//        return IntStream.range(0, brTabs) //creates a stream of integers from 0 to brTabs-1
//                .mapToObj(i -> "\t") //maps each integer of the stream into a String object representing a tab
//                .collect(Collectors.joining()); //spojuva poke stringovi vo eden so prazno mesto izmegju
//    }
//}
//
////Za eden File se chuvaat info za negovoto ime i golemina (vo long)
//class File implements IFileInterface {
//    protected String fileName;
//    protected long fileSize;
//
//    //kreirame 2 konstruktori
//    public File(String fileName) {
//        this.fileName = fileName;
//    }
//
//    public File(long fileSize, String fileName) {
//        this.fileSize = fileSize;
//        this.fileName = fileName;
//    }
//
//    @Override
//    public String getFileName() {
//        return this.fileName;
//    }
//
//    @Override
//    public long getFileSize() {
//        return this.fileSize;
//    }
//
//    @Override
//    public String getFileInfo(int indent) {
//        return String.format("%sFile name: %10s File size: %10d\n",
//                IndentPrinter.printIndentation(indent), getFileName(), getFileSize());
//    }
//
//    @Override
//    public void SortBySize() {
//        return; //nemozis da sortiras eden File po golemina, ova vazi za folderot
//    }
//
//    @Override
//    public long findLargestFile() {
//        return this.fileSize; //eden file e, ova vazi za folderot
//    }
//
//    //posto e super klasa, mozime tuka da go implementirame metodot
//    @Override
//    public int compareTo(IFileInterface o) {
//        return Long.compare(this.fileSize, o.getFileSize());
//    }
//}
//
////vo klasata Folder se cuvaat isti info kako i File, a dopolnitelno se cuva i lista od files
//class Folder extends File implements IFileInterface {
//        //pravime extends File zaradi sho i Folderot e tip na FIle
//
//    //mozhe da cuva i obicni i neobicni fajlovi, zato cuvame interface
//    private List<IFileInterface> filesLista;
//
//    /*za da kreirame konstruktor kakov shto se bara, mora da sogledame vo vlezenInput
//    od zadachata shto se prenesuva kako argument, vo nashiot sluchaj vlezenInput go dava imeto "test"     */
//    public Folder(String fileName) {
//        super(fileName); //povikuvame super constructor, oti klasata File e superclass na Folder
//        filesLista = new ArrayList<>();
//    }
//
//    //metod za dodavanje na bilo kakva datoteka vo listata na datoteki
//    //dokolku veke postoi datoteka so isto ime
//    // kako imeto na datotekata shto se dodava kako argument, da se frli isklucok od tip
//    //FileNameExistsException vo kojshto se prosleduva imeto koe vekje postoi
//    private boolean doesItExist(String nameOfFile) {
//        return filesLista.stream()
//                .map((IFileInterface file) -> file.getFileName())
//                .anyMatch(name -> name.equals(nameOfFile));
//    }
//    public void addFile(IFileInterface file) throws FileNameExistsException {
//        if (doesItExist(file.getFileName())) //ako postoi frlame isklucok
//            throw new FileNameExistsException(file.getFileName(), this.fileName);
//
//        //ako ne se frli isklucokot
//        filesLista.add(file);
//    }
//
//    @Override
//    public String getFileName() {
//        return this.fileName; //go prevzemame od super klasata, bez get posto se protected
//        //ke beshe so super.getFileName() da bea private
//    }
//
//    @Override
//    public long getFileSize() {
//        //goleminata na eden folder e suma od goleminite na site datoteki shto se naogjaat vo nego
//        //za toa ke koristime stream
//        return filesLista.stream()
//                //in java u can create variables from an interface
//                .mapToLong((IFileInterface edenFile) -> edenFile.getFileSize()) //pravime map to long oti vo long promenlivata se cuva goleminata na eden file
//                .sum();
//    }
//
//    @Override
//    public String getFileInfo(int indent) {
//        //ovoj metod gi vrakja skoro istite paramteri kako File, samo dopolnitelno treba da se ispechatat fajlovite
//        StringBuilder sb = new StringBuilder();
//
//        sb.append(String.format("%sFile name: %10s File size: %10d\n",
//                IndentPrinter.printIndentation(indent), getFileName(), getFileSize()));
//
//        //treba da gi dodajme site fajlovi
//        filesLista.stream().forEach(edenFile -> sb.append(edenFile.getFileInfo(indent +1)));
//
//        return sb.toString();
//
//    }
//
//    @Override
//    public void SortBySize() {
//        // Step 1: Create a Comparator to compare file sizes
//        Comparator<IFileInterface> comparator = Comparator.comparingLong(iFileInterface -> iFileInterface.getFileSize());
//
//        // Step 2: Sort the filesLista using the comparator
//        filesLista.sort(comparator);
//
//        // Step 3: Call SortBySize on each file in filesLista
//        filesLista.forEach(( IFileInterface edenFile) -> edenFile.SortBySize());
//    }
//
//    @Override
//    public long findLargestFile() {
//        OptionalLong result = filesLista.stream()
//                .mapToLong(iFileInterface -> iFileInterface.findLargestFile())
//                .max();
//
//        if (result.isPresent())
//            return result.getAsLong();
//        else return 0L;
//    }
//}
//
//class FileSystem {
//    private Folder root;
//
//    public FileSystem(Folder root) {
//        this.root = new Folder("root");
//    }
//
//    public void addFile(File novFile) {
//        root.addFile(novFile);
//    }
//
//    public long findLargest() {
//        return root.findLargestFile();
//    }
//
//    public void SortBySize() {
//        root.SortBySize();
//    }
//
//    @Override
//    public String toString() {
//        return this.root.getFileInfo(0);
//    }
//
//}
//
//public class FileSystemTest {
//
//    public static Folder readFolder (Scanner sc)  {
//
//        Folder folder = new Folder(sc.nextLine());
//        int totalFiles = Integer.parseInt(sc.nextLine());
//
//        for (int i=0;i<totalFiles;i++) {
//            String line = sc.nextLine();
//
//            if (line.startsWith("0")) {
//                String fileInfo = sc.nextLine();
//                String[] parts = fileInfo.split("\\s+");
//                folder.addFile(new File(parts[0], Long.parseLong(parts[1])));
//            }
//
//            else {
//                folder.addFile(readFolder(sc));
//            }
//        }
//
//        return folder;
//    }
//
//    public static void main(String[] args)  {
//
//        //file reading from vlezenInput
//
//        Scanner sc = new Scanner (System.in);
//
//        System.out.println("===READING FILES FROM INPUT===");
//        FileSystem fileSystem = new FileSystem();
//        try {
//            fileSystem.addFile(readFolder(sc));
//        } catch (FileNameExistsException e) {
//            System.out.println(e.getMessage());
//        }
//
//
//        System.out.println("===PRINTING FILE SYSTEM INFO===");
//        System.out.println(fileSystem.toString());
//
//        System.out.println("===PRINTING FILE SYSTEM INFO AFTER SORTING===");
//        fileSystem.SortBySize();
//        System.out.println(fileSystem.toString());
//
//        System.out.println("===PRINTING THE SIZE OF THE LARGEST FILE IN THE FILE SYSTEM===");
//        System.out.println(fileSystem.findLargest();
//
//    }
//}