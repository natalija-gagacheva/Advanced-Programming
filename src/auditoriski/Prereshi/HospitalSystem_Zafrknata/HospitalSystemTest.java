package auditoriski.Prereshi.HospitalSystem_Zafrknata;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

enum Gender {
    FEMALE,
    MALE
}

interface Patient {
//    private long skrienKod;
//    private String ime;
//    private int godini;
//    private Gender gender;
//    private String matichenLekar;

    /** ponatamu pravime Override na ovie metodi soodvetno vo
     * klasite koi ponatamu ke go implementiraat ovoj interface*/
    long getCode();
    String getName();
    int getAge();
    String getGender();
    String getDoctor();
}

/* genericki interface posto imame Patient Service od povekje tipovi
  vo metodot printResults vo klasata HospitalSystem
  vo sluchajot <T> generikot pretstavuva objekt od klasata pacient zato mora
  <T extends Patient, t.e mora da naznacime kakov generik e od koj tip>
*/
interface PatientService<T extends Patient & Comparable<T>> {

    //metodot prima lista od pacienti, ponatamu tie pacienti mozhat da bidat od bilo koj tip
    //primer PatiendfromDB1 and PatientfromDB2
    ArrayList<T>patientsInformation(ArrayList<T>patients, String filter);
}

/*klasata cuva lista od nekakvi pacienti, samo ne znaeme dali e pDB1, pDB2 etc
pa zatoa go ogranicvame so <T extends Patient>
 go ogranicuvame na nivo na klasa, ponatamu segde treba da nasledva sekoj objekt od klasata so <T>*/
class HospitalSystem<T extends Patient & Comparable<T>> {

    private ArrayList<T> listaPacienti;

    public HospitalSystem(ArrayList<T> listaPacienti) {
        this.listaPacienti = listaPacienti;
    }


    public void printResults() {

        //Lambda expression implementacija

        // 1. servis koj ke gi vrati site pacienti koi imaat maticen lekar so ime filter

        //patients_list koja ja isprakjame kako argument
        //se odnesuva na nekoja lista koja ke bide predadena koga ke se povika findAllWithDoctor
        //dodeka pak pri povikot na findAllWithDoctor.patientsInformation()
        //soodvetno gi prenesuvame listaPacienti od konstruktorot
        //ke koristam patients_list, x, i patients to demonstrate deka ne se povrzani so listaPacienti
        PatientService<T> findAllWithDoctor = (patients_list, filterUslov) ->
                patients_list
                    .stream()
                    .filter(edenPacient -> edenPacient.getDoctor().equals(filterUslov))
                    .collect(Collectors.toCollection(ArrayList::new));
                    //koristime toCollection oti sakame tochen tip na lista

        //*  2. servis koj ke gi vrati site pacienti koi ne se postari od 60 godini i se od polot filter
        PatientService<T> countAllWithGenderAndAgeLessThanSixty = (x, filterUslov) ->
                       x.stream()
                        .filter(edenPacient -> edenPacient.getAge()<=60 && edenPacient.getGender().equals(filterUslov))
                        .collect(Collectors.toCollection(ArrayList::new));

        //*  3. serivis koj ke gi vrati site pacienti so ime filter sortirani vo rastecki redosled po nivniot skrien kod*/
        //tipot na pacient mora da e comparable, ako se bara sortiranje
        PatientService<T> findAllWithNameSorted = (patients, filterUslov) ->
                patients
                        .stream()
                        .filter(edenPacient -> edenPacient.getName().equals(filterUslov))
                        .sorted()
                        .collect(Collectors.toCollection(ArrayList::new));


        System.out.println("FIRST SERVICE INFORMATION");
        findAllWithDoctor.patientsInformation(this.listaPacienti, "Peter").forEach(System.out::println);

        System.out.println("SECOND SERVICE INFORMATION");
        countAllWithGenderAndAgeLessThanSixty.patientsInformation(this.listaPacienti, "FEMALE").forEach(System.out::println);

        System.out.println("THIRD SERVICE INFORMATION");
        findAllWithNameSorted.patientsInformation(this.listaPacienti, "Sarah").forEach(System.out::println);
    }
}

class PatientFromDB1 implements Patient, Comparable<PatientFromDB1> {
        private final long code;
        private final String name;
        private final int age;
        private final Gender gender;
        private final String doctor;

        public PatientFromDB1(long code, String name, int age, Gender gender, String doctor) {
            this.code = code;
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.doctor = doctor;
        }

        public static PatientFromDB1 createPatientFromDB1(String line) {
            String[] parts = line.split("\\s+");
            long code = Long.parseLong(parts[0]);
            String name = parts[1];
            int age = Integer.parseInt(parts[2]);
            Gender gender = parts[3].equals("FEMALE") ? Gender.FEMALE : Gender.MALE;
            String doctor = parts[4];
            return new PatientFromDB1(code, name, age, gender, doctor);
        }

    @Override
    public long getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getAge() {
        return this.age;
    }

    @Override
    public String getGender() {
        return this.gender.toString();
    }

    @Override
    public String getDoctor() {
        return this.doctor;
    }


        @Override
        public String toString() {
            return "PatientFromDB1{" +
                    "code=" + code +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    ", gender=" + gender +
                    ", doctor='" + doctor + '\'' +
                    '}';
        }

        @Override
        public int compareTo(PatientFromDB1 o) {
            return Long.compare(this.code, o.code);
        }
    }

class PatientFromDB2 implements Patient, Comparable<PatientFromDB2> {
        private final String code;
        private final String name;
        private final int age;
        private final Gender gender;
        private final String doctor;
        private final String symptom;

        public PatientFromDB2(String code, String name, int age, Gender gender, String doctor, String symptom) {
            this.code = code;
            this.name = name;
            this.age = age;
            this.gender = gender;
            this.doctor = doctor;
            this.symptom = symptom;
        }

        public static PatientFromDB2 createPatientFromDB2(String line) {
            String[] parts = line.split("\\s+");
            String code = parts[0];
            String name = parts[1];
            int age = Integer.parseInt(parts[2]);
            Gender gender = parts[3].equals("FEMALE") ? Gender.FEMALE : Gender.MALE;
            String doctor = parts[4];
            String symptom = parts[5];
            return new PatientFromDB2(code, name, age, gender, doctor, symptom);
        }

        @Override
        public long getCode() {
            return this.code.hashCode();
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public int getAge() {
            return this.age;
        }

        @Override
        public String getGender() {
            return this.gender.toString();
        }

        @Override
        public String getDoctor() {
            return this.doctor;
        }

        @Override
        public String toString() {
            return "PatientFromDB2{" +
                    "code='" + code + '\'' +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    ", gender=" + gender +
                    ", doctor='" + doctor + '\'' +
                    ", symptom='" + symptom + '\'' +
                    '}';
        }

        @Override
        public int compareTo(PatientFromDB2 o) {
            return Long.compare(this.getCode(), o.getCode());
        }
    }

public class HospitalSystemTest {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            int db1Count = Integer.parseInt(scanner.nextLine());

            ArrayList<PatientFromDB1> patientsFromDB1 = IntStream.range(0, db1Count)
                    .mapToObj(i -> PatientFromDB1.createPatientFromDB1(scanner.nextLine()))
                    .collect(Collectors.toCollection(ArrayList::new));

            int db2Count = Integer.parseInt(scanner.nextLine());

            ArrayList<PatientFromDB2> patientsFromDB2 = IntStream.range(0, db2Count)
                    .mapToObj(i -> PatientFromDB2.createPatientFromDB2(scanner.nextLine()))
                    .collect(Collectors.toCollection(ArrayList::new));

            HospitalSystem<PatientFromDB1> hospital1System = new HospitalSystem<>(patientsFromDB1);
            HospitalSystem<PatientFromDB2> hospital2System = new HospitalSystem<>(patientsFromDB2);

            System.out.println("--- FIRST HOSPITAL PATIENTS' INFORMATION ---");
            hospital1System.printResults();

            System.out.println("--- SECOND HOSPITAL PATIENTS' INFORMATION ---");
            hospital2System.printResults();
        }
    }
