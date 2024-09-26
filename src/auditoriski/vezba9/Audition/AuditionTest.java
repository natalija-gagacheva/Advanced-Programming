package auditoriski.vezba9.Audition;

import java.util.*;

class Participant {
    String code;
    String name;
    int age;
    String city;

    public Participant(String code, String name, int age, String city) {
        this.code = code;
        this.name = name;
        this.age = age;
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }
}

class Audition {

    /* chuvame Set od Participants*/
    Map<String,Set<Participant>> mapParticipantsByCity;

    public Audition() {
        mapParticipantsByCity = new HashMap<>();
    }


    /* Kompleknosta na metodot treba da bide O(1) */
    public void addParticipant(String city, String code, String name, int age) {

        Participant ucesnik = new Participant(code, name, age, city);

//        if(mapParticipantsByCity.containsKey(city)) {
//            mapParticipantsByCity.get(city).add(ucesnik);
//
//        } else {
//            /* koristime HashSet za complexity = o(1) spored baranjata na zadachata */
//            mapParticipantsByCity.put(city, new HashSet<>());
//            /*otkako ke inicijalizirame Mapa so prazno mnozestvo, go povikuvame
//            * toj grad so praznoto mnozestvo za da dodademe ucesnik */
//            mapParticipantsByCity.get(city).add(ucesnik);
//        }

        if (! mapParticipantsByCity.containsKey(city)) //ako ne postoi gradot
        {
            mapParticipantsByCity.put(city, new HashSet<>()); //kreiraj nov set za toj grad
        }

        /* ne e dozvoleno dodavanje na nov ucesnik so ist kod */
        if (!mapParticipantsByCity.get(city).contains(ucesnik)) {
            mapParticipantsByCity.get(city).add(ucesnik); //dodaj ucesnik
        }
    }


    /* Kompleknosta ne treba da nadminuva O(n*logn),
    kade n e brojot na kandidati vo dadeniot grad*/
    public void listByCity(String city) {

        Set<Participant> sortedSetOfParticipants
                = new TreeSet<>(Comparator.comparing((Participant edenUcesnik) -> edenUcesnik.getName())
                .thenComparingInt((Participant ucesnik) -> ucesnik.getAge())
                .thenComparing((Participant::getCode)));

        sortedSetOfParticipants.addAll(mapParticipantsByCity.get(city));

        /* pechatenje na ucesnicite */
        sortedSetOfParticipants
                .stream()
                .forEach(edenUcesnik -> System.out.println(edenUcesnik));

    }
}
public class AuditionTest {
    public static void main(String[] args) {

        Audition audition = new Audition();
        List<String> cities = new ArrayList<String>();

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticipant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }

        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}
