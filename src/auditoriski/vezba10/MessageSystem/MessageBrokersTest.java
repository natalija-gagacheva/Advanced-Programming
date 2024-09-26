package auditoriski.vezba10.MessageSystem;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

class PartitionDoesNotExistException extends Exception {
    public PartitionDoesNotExistException(String topic, int number) {
        super(String.format("The topic %s does not have a partition with number %d", topic, number));
    }
}

class UnsupportedOperationException extends Exception {

}

class Message implements Comparable<Message> {
    LocalDateTime timeStamp;
    String sodrzhina;
    String kluch;
    Integer particija;
    Integer limit;

    public Message(LocalDateTime timeStamp, String sodrzhina, String kluch) {
        this.timeStamp = timeStamp;
        this.sodrzhina = sodrzhina;
        this.kluch = kluch;
    }

    public Message(LocalDateTime timeStamp, String sodrzhina, Integer particija, String kluch) {
        this.timeStamp = timeStamp;
        this.sodrzhina = sodrzhina;
        this.particija = particija;
        this.kluch = kluch;
    }

    public Message(LocalDateTime timeStamp, Integer limit) {
        this.timeStamp = timeStamp;
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "Message{" +
                "kreiranVo=" + timeStamp +
                ", sodrzhina='" + sodrzhina + '\'' +
                '}';
    }

    @Override
    public int compareTo(Message other) {
        //treba da bidat sortirani spored timestamp
        return this.timeStamp.compareTo(other.timeStamp);
    }
}

//klasa za
class Partition {
    int redenBroj;
    /* Zaradi shto porakite treba da bidat sortirani spored timestamp, zato gi chuvame vo TreeSet
    * TreeSet se koristi koga sakame da gi sortirame elementite spored odreden komparator*/
    TreeSet<Message> setOdPoraki;

    public Partition(int redenBroj) {
        this.redenBroj = redenBroj;
        setOdPoraki = new TreeSet<>();
    }

    void addMessageInPartition(Message ednaPoraka) {

        if (ednaPoraka.timeStamp.isBefore(MessageBroker.START_DATE)) {
            return ; //ne prodolzuvame voopsto
        }

        /* dokolku imame dostignato max kapaictet i ne mozheme poke da dodavame novi particii
        ja trgame onaa poraka so najstar timestamp */
        if (setOdPoraki.size()==MessageBroker.MAX_CAPACITY_PER_PARTITION) {
            setOdPoraki.remove(setOdPoraki.first());
        }
        setOdPoraki.add(ednaPoraka);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("%2d : Count of messages: %6d\n", redenBroj, setOdPoraki.size());
        sb.append("Messages:\n");
        /*se pechatat site poraki oddeleni so nov red
        toa go pravime so toa shto sekoj objekt go pretvarame vo negovata toString notacija         */
        String porakiToString = setOdPoraki.stream()
                .map(ednaPoraka -> ednaPoraka.toString())
                .collect(Collectors.joining("\n"));

        sb.append(porakiToString);

        return sb.toString();
    }
}

class PartitionAssigner {

    public static Integer assignPartition(Message message, int partitionsCount) {
        return (Math.abs(message.kluch.hashCode()) % partitionsCount) + 1;
    }
}

//klasa za chuvanje na poraki po temi
class Topic implements Comparable<Topic> {
    String topicName;
    int partitionsCount;
    /*Particiite treba da bidat sortirani spored nivniot reden broj i potrebno e da mozhime lesno da pristapime do niv
    Vo zadachata edna particija ima poke poraki i treba da dodavame nova poraka spored nejzinata particija*/
    TreeMap<Integer, Partition> mapOfPartitions;

    public Topic(String topicName, int partitionsCount) {
        this.topicName = topicName;
        this.partitionsCount = partitionsCount;
        mapOfPartitions = new TreeMap<>();

        /*bitno e vednas da gi kreirame particiite, zaradi shto nemozime ponatamu da gi koristime
        computeIfPresent ili computeIfAbsent, posto vo test primerite mozi particijata da bide prazna (legal)*/
        for (int i=1; i<=partitionsCount; i++) {
            mapOfPartitions.put(i, new Partition(i));
        }
    }

    /* Metod za dodavanje na nova poraka vo ovoj Topic. Dodavanjeto se pravi vo
    * soodvetna particija koja se chuva kako info vo porakata message */
    void addMessageInTopic(Message poraka) throws PartitionDoesNotExistException {

        Integer redenBrojNaParticija = poraka.particija;

        //dokolku porakata nema particija
        if (redenBrojNaParticija==null) {
            redenBrojNaParticija = PartitionAssigner.assignPartition(poraka,partitionsCount);
        }

        //dokolku redniot broj na particijata ne se sodrzhi vo mapata
        if (!mapOfPartitions.containsKey(redenBrojNaParticija)) {
            throw new PartitionDoesNotExistException(topicName, redenBrojNaParticija);
        }

        //ako particijata postoj treba da ja dodademe vo mapata od particii vo eden Topic
        mapOfPartitions.get(redenBrojNaParticija).addMessageInPartition(poraka);
    }

    /* Metod za promena na brojot na particii. Dokolku se proba da se namali brojot na particii
    * da se frli isklucok kako vo potpisot na funkcijata*/
    void changeNumberOfPartitions (int newPartitionsNumber) throws UnsupportedOperationException {

        if (newPartitionsNumber < partitionsCount) {
            throw new UnsupportedOperationException();
        } else if (newPartitionsNumber > partitionsCount){
            /* vo sluchaj da imame 7 particii, a newPartitionsNumber e 9,
            treba da se dodadat 8 i 9 zaedno
             Pocnuvame od i=partitionsCount +1 oti 7mata veke postoj, odime na 8mata*/
            for (int i=partitionsCount+1; i<=newPartitionsNumber; i++) {
                mapOfPartitions.putIfAbsent(i, new Partition(i));
            }
            //dopolnitelno treba da se promeni brojot na particii
            partitionsCount = newPartitionsNumber;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Topic:%10s Partitions:%5d\n", topicName, partitionsCount));

        String mapParticiiToString = mapOfPartitions
                .values()
                .stream()
                .map(ednaParticija -> ednaParticija.toString())
                .collect(Collectors.joining("\n"));

        sb.append(mapParticiiToString);

        return sb.toString();
    }

    @Override
    public int compareTo(Topic o) {
        return this.topicName.compareTo(o.topicName);
    }
}

class MessageBroker {
    Map<String,Topic> mapOfTopics;
    //poshto vazhi za sekoja particija, promenlivata mozhi da bide static
    static LocalDateTime START_DATE;
    static int MAX_CAPACITY_PER_PARTITION;

    public MessageBroker(LocalDateTime startDate, int maxCapacityPerPartition) {
        this.START_DATE = startDate;
        this.MAX_CAPACITY_PER_PARTITION = maxCapacityPerPartition;
        mapOfTopics = new TreeMap<>(); //potrebno e da se sortirani spored nivnoto ime
    }

    /* metod za dodavanje na nov topic so odreden broj na particii vo nego*/
    void addTopicToBroker(String topicName, int partitionsCount){

        //dokolku ne postoi, dodavame nov
        if (!mapOfTopics.containsKey(topicName)) {
            mapOfTopics
                    .put(topicName, new Topic(topicName, partitionsCount));
        }
    }

    public void addMessageToBroker(String topic, Message poraka) throws PartitionDoesNotExistException {

        if(poraka.timeStamp.isBefore(START_DATE))
            return ;

        mapOfTopics.get(topic).addMessageInTopic(poraka);

    }

    /* Metod za promena na brojot na particii na opredelen topic */
    public void changeTopicSettings(String topicName, Integer partitionsCount) throws UnsupportedOperationException {

        mapOfTopics
                .get(topicName)
                .changeNumberOfPartitions(partitionsCount);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Broker with %d topics", mapOfTopics.size()));
        String mapTopicsToString = mapOfTopics
                .values()
                .stream()
                .map(edenTopic -> edenTopic.toString())
                .collect(Collectors.joining("\n"));

        sb.append(mapTopicsToString);
        return sb.toString();

    }
}

public class MessageBrokersTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String date = sc.nextLine();
        LocalDateTime localDateTime = LocalDateTime.parse(date);
        Integer partitionsLimit = Integer.parseInt(sc.nextLine());
        MessageBroker broker = new MessageBroker(localDateTime, partitionsLimit);
        int topicsCount = Integer.parseInt(sc.nextLine());

        //Adding topics
        for (int i = 0; i < topicsCount; i++) {
            String line = sc.nextLine();
            String[] parts = line.split(";");
            String topicName = parts[0];
            int partitionsCount = Integer.parseInt(parts[1]);
            broker.addTopicToBroker(topicName, partitionsCount);
        }

        //Reading messages
        int messagesCount = Integer.parseInt(sc.nextLine());

        System.out.println("===ADDING MESSAGES TO TOPICS===");
        for (int i = 0; i < messagesCount; i++) {
            String line = sc.nextLine();
            String[] parts = line.split(";");
            String topic = parts[0];
            LocalDateTime timestamp = LocalDateTime.parse(parts[1]);
            String message = parts[2];
            if (parts.length == 4) {
                String key = parts[3];
                try {
                    broker.addMessageToBroker(topic, new Message(timestamp, message, key));
                } catch (PartitionDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                Integer partition = Integer.parseInt(parts[3]);
                String key = parts[4];
                try {
                    broker.addMessageToBroker(topic, new Message(timestamp, message, partition, key));
                } catch (PartitionDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("===BROKER STATE AFTER ADDITION OF MESSAGES===");
        System.out.println(broker);

        System.out.println("===CHANGE OF TOPICS CONFIGURATION===");
        //topics changes
        int changesCount = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < changesCount; i++) {
            String line = sc.nextLine();
            String[] parts = line.split(";");
            String topicName = parts[0];
            Integer partitions = Integer.parseInt(parts[1]);
            try {
                broker.changeTopicSettings(topicName, partitions);
            } catch (UnsupportedOperationException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("===ADDING NEW MESSAGES TO TOPICS===");
        messagesCount = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < messagesCount; i++) {
            String line = sc.nextLine();
            String[] parts = line.split(";");
            String topic = parts[0];
            LocalDateTime timestamp = LocalDateTime.parse(parts[1]);
            String message = parts[2];
            if (parts.length == 4) {
                String key = parts[3];
                try {
                    broker.addMessageToBroker(topic, new Message(timestamp, message, key));
                } catch (PartitionDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                Integer partition = Integer.parseInt(parts[3]);
                String key = parts[4];
                try {
                    broker.addMessageToBroker(topic, new Message(timestamp, message, partition, key));
                } catch (PartitionDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("===BROKER STATE AFTER CONFIGURATION CHANGE===");
        System.out.println(broker);


    }
}