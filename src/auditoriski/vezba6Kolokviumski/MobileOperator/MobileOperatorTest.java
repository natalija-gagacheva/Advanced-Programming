package auditoriski.vezba6Kolokviumski.MobileOperator;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/*ke ja krstime Customer, oti kolku potroshil minuti/sms/gb e vo odnos na klientot, ne samiot packet shto nudi
t.e brojot na minuti kojstho nekoj gi potroshil se odnesuvaat na customer, ne na package
t.e vo ramki na paket ne mozheme da cuvame kolku minuti potroshil nekoj*/
class InvalidIDException extends Exception {
    public InvalidIDException(String message) {
        super(message);
    }
}
abstract class Customer {
    String customerID;
    int minutes;
    int SMS;
    float GB;
    /*private int osnovnaCena;
    ~nema da imame voopsto osnovnaCena promenliva posto
    taja varira vo zavisnost dali customer e S ili M */

    //bidejki metodot e static, nema pristap do instance variables, mora da go prenesime kako argument
    private static boolean isCustomerIDValid(String customerID) {

        if (customerID.length() != 7 )
            return false;

        for (int i=0; i<3; i++) {
            if(!Character.isDigit(customerID.charAt(i))) //dokolku ne e brojka
                return false; //ne e validen ID
        }

        //dokolku gi pominam site ovie proverki, znaci e validen ID
        //tuka ne frlame isklucok, tuka samo vrakja boolean true/false vrednost metodot
        return true;
    }


    //uste vo samiot konstruktor proveruvame dali customerID e valid
    public Customer(String customerID, int minutes, int SMS, float GB) throws InvalidIDException {

        if(!isCustomerIDValid(customerID)) {
            throw new InvalidIDException(
            (String.format("%s is not a valid sales rep ID", customerID)));
        }

        this.customerID = customerID;
        this.minutes = minutes;
        this.SMS = SMS;
        this.GB = GB;
    }


    //mora da ima eden abstrakten metod so cel klasata da e abstrakna
    // nesto shto seuste ne go znaeme i go odlucuvame vo odnos na
    // klasite koi ja implementiraat ovaa abstrakna klasa
    abstract double vkCena();

    //abstract class
    abstract double presmetkaNaProvizija();

}

class SCustomer extends Customer {

    //Dokolku edna promenliva e ista za sekoja instanca od taa klasa, istata promenliva treba da bide STATIC
    static int BASE_PRICE_S = 500;

    //besplatnite sms,gb, minuti se isti za sekoj Customer_S, t.s i tie ke se static
    static int FREE_MINUTES_S=100;
    static int FREE_SMS_S = 50;
    static int FREE_GB_S = 5;

    /* za sekoe nadminuvanje nad dozvolenoto, ima fiknso pokacuvanje
    sho e static za sekoja instanca od ovaa klasa */
    static int PRICE_PER_MINUTES_S = 5;
    static int PRICE_PER_SMS_S = 6;
    static int PRICE_PER_BG_S = 25;
    static double COMISSION_PERCENT_S=0.07;

    //vo konstruktorot nema da ja prakjame osnovnata cena, bidejki
    //taa e ista za site S_customers, t.s nema poenta da se menva pri kreiranje na nov objekt
    public SCustomer(String id, int minutes, int SMS, float GB) throws InvalidIDException {
        super(id, minutes, SMS, GB);
    }

    //Vo files go imam metodot preku if/else uslovi
    @Override
    double vkCena() {
        double vkupnaCena = BASE_PRICE_S;

        vkupnaCena =
                vkupnaCena + PRICE_PER_MINUTES_S * Math.max(0, minutes - FREE_MINUTES_S);
        /*dokolku razlikata izmegju minutes i FREE_MINUTES_S e da recime 15min, max od 0 i 15 e 15
        no dokolku minutes se pomali od FREE_MINUTES_S, max() ke dade negativen broj i max od 0 i -5 ke bide 0, shto znaci vk cena ne se menuva*/

        vkupnaCena =
                vkupnaCena + PRICE_PER_BG_S * Math.max(0, GB - FREE_GB_S);
        vkupnaCena =
                vkupnaCena + PRICE_PER_SMS_S * Math.max(0, SMS - FREE_SMS_S);

        return vkupnaCena;

    }

    @Override
    double presmetkaNaProvizija() {
        return vkCena() * COMISSION_PERCENT_S ;
    }


}

class MCustomer extends Customer {

    //Dokolku edna promenliva e ista za sekoja instanca od taa klasa, istata promenliva treba da bide STATIC
    static int BASE_PRICE_S = 750;

    //besplatnite sms,gb, minuti se isti za sekoj Customer_S, t.s i tie ke se static
    static int FREE_MINUTES_S=150;
    static int FREE_SMS_S = 60;
    static int FREE_GB_S = 10;

    /* za sekoe nadminuvanje nad dozvolenoto, ima fiknso pokacuvanje
    sho e static za sekoja instanca od ovaa klasa */
    static int PRICE_PER_MINUTES_S = 4;
    static int PRICE_PER_SMS_S = 4;
    static int PRICE_PER_BG_S = 20;
    static double COMISSION_PERCENT_M =0.07;

    //vo konstruktorot nema da ja prakjame osnovnata cena, bidejki
    //taa e ista za site S_customers, t.s nema poenta da se menva pri kreiranje na nov objekt
    public MCustomer(String id, int minutes, int SMS, float GB) throws InvalidIDException {
        super(id, minutes, SMS, GB);
    }

    //Vo files go imam metodot preku if/else uslovi
    @Override
    double vkCena() {
        double vkupnaCena = BASE_PRICE_S;

        vkupnaCena =
                vkupnaCena + PRICE_PER_MINUTES_S * Math.max(0, minutes - FREE_MINUTES_S);
        /*dokolku razlikata izmegju minutes i FREE_MINUTES_S e da recime 15min, max od 0 i 15 e 15
        no dokolku minutes se pomali od FREE_MINUTES_S, max() ke dade negativen broj i max od 0 i -5 ke bide 0, shto znaci vk cena ne se menuva*/

        vkupnaCena =
                vkupnaCena + PRICE_PER_BG_S * Math.max(0, GB - FREE_GB_S);
        vkupnaCena =
                vkupnaCena + PRICE_PER_SMS_S * Math.max(0, SMS - FREE_SMS_S);

        return vkupnaCena;

    }

    @Override
    double presmetkaNaProvizija() {
        return vkCena() * COMISSION_PERCENT_M;
    }

}

class SalesRep implements Comparable<SalesRep> {
    String salesRepID;
    List<Customer> customerList;

    public SalesRep(String salesRepID, List<Customer> customerList) {
        this.salesRepID = salesRepID;
        this.customerList = new ArrayList<>();
    }

    //kreirame metod za proverka na uslovot za frlanje na isklucok
    private static boolean isSalesRepIDValid(String id) {
        if (id.length() != 3 )
            return false;

        for (int i=0; i<3; i++) {
            if(!Character.isDigit(id.charAt(i))) //dokolku ne e brojka
                return false; //ne e validen ID
        }

        //dokolku gi pominam site ovie proverki, znaci e validen ID
        //tuka ne frlame isklucok, tuka samo vrakja boolean true/false vrednost metodot
        return true;
    }

    /* Eden Sales Rep:
    salesrepId customerId packageType mins sms gbs
     475        4642771     M          248 90 14.94 2281930 S 139 48 6.19 4040003 M 189 100 11.90 5064198 M 159 78 9.32
        0            1      2           3   4   5
     */

    //mora da bidi static so cel da mozhi da se instancira bez da treba
    //prethodno da se kreira nov objekt
    public static SalesRep kreirajSalesRep(String linija) throws InvalidIDException {
        String []parts = linija.split("\\s+");

        //zaradi sho se static metodot, mora da kreirame nova promenliva, oti static methods don't have access to instance variable, t.e
        //nemame pristap do salesRepID that was previously declared in the class

        String SalesRepID = parts[0]; //475

        //tuke NE kreirame TRY-CATCH block
        //glavna cel e da se propagira ponatamu isklucokot, na kraj if all else fails da se fati vo main
        if(!isSalesRepIDValid(SalesRepID))
        {
            throw new InvalidIDException
                    (String.format("%s is not a valid sales rep ID", SalesRepID));
        }

        //Spored uslovot na zadacata, dokolku e greshen id togas da se ignorira celiot negov izveshtaj
        //t.e voopsto da ne se stavi vo listata na

        List<Customer> customers = new ArrayList<>();
        for (int i=1; i<parts.length; i+=5)
        {
            String customerID = parts[i];
            String typeOfPackage = parts[i+1];
            int minutes = Integer.parseInt(parts[i+2]);
            int SMS = Integer.parseInt(parts[i+3]);
            float GB = Integer.parseInt( parts[i+4]);
            try {
                if (typeOfPackage.equals("M")) {
                    customers.add(new MCustomer(customerID,minutes,SMS,GB));
                } else {
                    customers.add(new SCustomer(customerID, minutes, SMS, GB));
                }
            } catch (InvalidIDException e) {
                //ako fatime isklucok, samo se pechati porakata
                System.out.println(e.getMessage());
            }

        }

        return new SalesRep(SalesRepID, customers);
    }

    @Override
    public String toString() {

        DoubleSummaryStatistics dss = customerList
                                            .stream()
                                            .mapToDouble(edenCustomer -> edenCustomer.vkCena())
                                            .summaryStatistics();

        return String.format("%s Count: %d Min: %.2f Average: %.2f Max: %.2f Comission: %.2f",
                salesRepID,
                dss.getCount(),
                dss.getMin(),
                dss.getAverage(),
                dss.getMax(),
                sumNaProvizija());
    }

    //zbir od site provizii na customers vo edna lista
    private double sumNaProvizija() {

        return customerList
                .stream()
                .mapToDouble(edenCustomer -> edenCustomer.presmetkaNaProvizija())
                .sum();

    }

    @Override
    public int compareTo(SalesRep o) {
        return Double.compare(this.sumNaProvizija(), o.sumNaProvizija());
    }
}

class MobileOperator {
    private List<SalesRep> ListOfSalesRep;

    public MobileOperator() {
        ListOfSalesRep = new ArrayList<>();
    }

    public void readSalesRepData(InputStream is) throws IOException {
        //koga citame linija po linija koristime buffered reader
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        //so ovoj del se inicijalizira nova lista
        ListOfSalesRep = br
                .lines()
                .map(linija -> {
                    try {
                        //ako uspesno se kreira objektot od SalesRep klasata, se stava vo Collectors.toList()
                        return SalesRep.kreirajSalesRep(linija);
                    } catch (InvalidIDException e) {
                        //dokolku ne se kreira uspesno nov objekt, t.e se frli isklucok
                        // ja vrakjame negovata poraka
                        System.out.println(e.getMessage());
                        return null; //mora da vratime prazen objekt
                    }
                })
                .filter(Objects::nonNull) //gi filtrirame site objekti koi ne se null
                .collect(Collectors.toList());

        br.close();
    }

    /* Izveshtaite da bidat ispechateni sortirani vo opagjacki redosled spored provizijata na sales rep vraboteniot*/

    public void printSalesReport(OutputStream out) {
        PrintWriter pw = new PrintWriter(out);

        ListOfSalesRep
                .stream()
                .sorted(Comparator.reverseOrder()) //vo opagjacki redosled
                .forEach(edenSalesRep -> pw.println(edenSalesRep));

        pw.flush();
    }
}

public class MobileOperatorTest {
    public static void main(String[] args) {

        MobileOperator mobileOperator = new MobileOperator();

        System.out.println("---- READING OF THE SALES REPORTS ----");
        try {
            mobileOperator.readSalesRepData(System.in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("---- PRINTING FINAL REPORTS FOR SALES REPRESENTATIVES ----");
        mobileOperator.printSalesReport(System.out);
    }
}