package auditoriski.Prereshi.Sensors2_NiStefanNeJaDoreshi;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class BadMeasureException extends Exception {
    int timestamp;
    String sensorId;

    public BadMeasureException( int timestamp, String sensorId) {
        this.timestamp = timestamp;
        this.sensorId = sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    @Override
    public String getMessage() {
        return
                String.format("Error in timestamp %d from sensor %s", timestamp, sensorId);
    }
}

//dokolku za senzorot ne e zapishana nitu edna vrednost
class BadSensorException extends Exception {
    String sensorId;

    public BadSensorException( String sensorId) {
        super(String.format("No readings for sensor %d", sensorId));
        this.sensorId = sensorId;
    }
}

//interface koj ovozmovuza da pristapime
// do Latitude and Longitude coordinates
//na nekoja lokacija
interface IGeo {
    double getLatitude();
    double getLongitude();

    //default metod vo interface e metod koj ima vo sebe nekakva implementacija
    //i mozhe da se napishe so cel da ne mora da se pravi overide vo klasite koj sho go implementiraat interfejsot
    default double calculateDistance(IGeo other) {
        return Math.sqrt
                (Math.pow(this.getLatitude()-other.getLatitude(),2)
                        +
                        Math.pow(this.getLongitude()-other.getLongitude(),2));
    }
}

class Measurement {
    private int timestamp;
    private double value;

    public Measurement(int timestamp, double value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    //frlame isklucok onamu kade shto kreirame nov objekt od Measurement
    //zaradi shto se frla isklucok dokolku e vnesena negativna vrednost
    //a vrednostite se citaat vo ovaa klasa
    public static Measurement kreirajMeasurement(String linija, String sensorId) throws BadMeasureException {
        //2 2 4 6:3 7:9 8:4
        String []parts = linija.split(":");
        int timestamp = Integer.parseInt(parts[0]);
        double value = Double.parseDouble(parts[1]);

        if (value < 0 ) //ako vrednosta e negativna
            throw new BadMeasureException(timestamp, sensorId);

        return new Measurement(timestamp,value);
    }
}

class Sensor implements IGeo {
    String sensorID;
    IGeo locationOfSensorObjectFromIGeoInterface;
    List<Measurement> measurementList;

    public Sensor(String sensorID, IGeo locationOfSensorObjectFromIGeoInterface, List<Measurement> measurementList) {
        this.sensorID = sensorID;
        this.locationOfSensorObjectFromIGeoInterface = locationOfSensorObjectFromIGeoInterface;
        this.measurementList = measurementList;
    }

    //statichen za da mozi da kreirame Sensor bez da instancirame poseben objekt
    public static Sensor kreirajSensor(String linija) throws BadMeasureException, BadSensorException
    //2 2 4 6:3 7:9 8:4
    {
        String []parts = linija.split("\\s+");

        String id = parts[0];

        if (parts.length==3) { //ako dolzinata e 3, nema nikakvi sensorski merenja
            throw new BadSensorException(id);
            //this exception is not handled here, but it is propagated to the method kreirajSensor
        }

        //mora preku anonimna klasa da kreirame objekt na interfejsot IGeo
        //so cel da ne pisuvame posebna implementacija
        //NO NE MOZHEME DA ISKORISTIME LAMBDA IZRAZ AKO IMA POKE OD 1 METOD interfejsot
        IGeo lokacija = new IGeo() {
            @Override
            public double getLatitude() {
                return Double.parseDouble(parts[1]);
            }

            @Override
            public double getLongitude() {
                return Double.parseDouble(parts[2]);
            }
        };

        //posto rabotime so niza, ne lista
        //2 2 4 6:3 7:9 8:4
        //0 1 2  3   4   5
        //fakjame isklucok vo momentot koga ke se kreiraat merenjata
        List<Measurement> measurements = new ArrayList<>();
        long toSkip = 3;
        for (String part : parts) {
            if (toSkip > 0) {
                toSkip--;
                continue;
            }

            //nema tuka da go fakjame isklucokot, ke frlime BadMeasureException
            //ponatamu ke go fatime vo main, kako krajna opcija
            Measurement measurement = Measurement.kreirajMeasurement(part, id);
            measurements.add(measurement);
        }

        return new Sensor(id, lokacija, measurements);
    }

    @Override
    public double getLatitude() {
        return 0;
    }

    @Override
    public double getLongitude() {
        return 0;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "sensorID='" + sensorID + '\'' +
                '}';
    }
}
class ExtremeValue {

}
class GeoSensorApplication {

    List<Sensor> sensors;

    public GeoSensorApplication() {
        sensors = new ArrayList<>();
    }

    //citanje na sensorski merenja

    //tuka se propagiraat dvata isklucoci, ama na kraj they are handled in the main method
    public void readGeoSensors(Scanner scanner) throws BadSensorException, BadMeasureException {
        //Mora da koristime skener radi sho osven sensors koristime i drugi vrednosti od inputstream

        //dokolku se procita negativna vrednost, potrebno e da bide frlen isklucok
        while (scanner.hasNextLine()) {
            String linija = scanner.nextLine();
            sensors.add(Sensor.kreirajSensor(linija));
        }
    }

    //metod za filtriranje na site sensori koi se naogjaat vo radius od [distance] metri
    //od lokacijata [location]. Objektot [location] e instanciran od klasa koja go implementira IGeo interfejsot so dve metodi
    public List<Sensor> inRange(IGeo otherLokacijaObjekt, double distance) {

        return sensors
                .stream()
                .filter(edenSensor ->
                        //rastojanieto od sensor.lokacija do lokacijata shto e predadena vo metodot e pomala od distance
                        //t.e gi filtrirame site koi se naogjaat na rastojanie pomalo od distance, do nekoja dr lokacija
                        edenSensor.locationOfSensorObjectFromIGeoInterface.calculateDistance(otherLokacijaObjekt)<distance)
                .collect(Collectors.toList());
    }

    public double averageValue() {
        return 0.0;
    }

    public double averageDistanceValue(IGeo iGeo, double dis, long t1, long t2) {
        return 0.0;
    }

    //vrakja lista od ekstremni procitani vrednosti
    //i toa min i max vrednost, zaedno so sensorId shto se napraveni vo
    //vremenski period timeFrom do timeTo
    public List<ExtremeValue> extremeValues(long timeFrom, long timeTo) {
        return null;
    }
}

public class SensorAnalysis2 {

    public static void main(String[] args) {
        GeoSensorApplication app = new GeoSensorApplication();

        Scanner s = new Scanner(System.in);
        double lat = s.nextDouble();
        double lon = s.nextDouble();
        double dis = s.nextDouble();
        long t1 = s.nextLong();
        long t2 = s.nextLong();

        s.nextLine();

        System.out.println("Access point on {" + lat + ", " + lon + "} distance:" + dis + " from:" + t1 + " - to:" + t2);

        try {
            app.readGeoSensors(s);


            System.out.println(app.inRange(new IGeo() {
                @Override
                public double getLatitude() {
                    return lat;
                }

                @Override
                public double getLongitude() {
                    return lon;
                }
            }, dis));

            System.out.println(app.averageValue());
            System.out.println(app.averageDistanceValue(new IGeo() {
                @Override
                public double getLatitude() {
                    return lat;
                }

                @Override
                public double getLongitude() {
                    return lon;
                }
            }, dis, t1, t2));

            System.out.println(app.extremeValues(t1, t2));
        } catch (BadSensorException e) {
            System.out.println(e.getMessage());
        } catch (BadMeasureException e) {
            System.out.println(e.getMessage());
        } finally {
            s.close();
        }
    }
}
