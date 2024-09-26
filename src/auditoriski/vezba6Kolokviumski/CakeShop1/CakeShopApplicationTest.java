package auditoriski.vezba6Kolokviumski.CakeShop1;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Item  {
    private String itemName;
    private int itemPrice;

    public Item(String itemName, int itemPrice) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

    public Item(String itemName) {
        this.itemName = itemName;
        this.itemPrice=0; //neka nema ceka vo toj daden moment
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                '}';
    }

}

//Input example: Vrakja 2 oti vtorata smetka e prazna
// 42 C2 1252 C33 1029
//36 (prazna)
class Naracki implements Comparable<Naracki>{
    private int id;
    private List<Item> itemLista;

    public Naracki() {
        id = -1; //inicijalno id postavuvame
        itemLista = new ArrayList<>();
    }

    public Naracki(int id, List<Item> itemLista) {
        this.id = id;
        this.itemLista = itemLista;
    }

    //konstruktor za citanje lines ili metod koj e staticki koj prima string i vrakja naracka
    public static Naracki createOrder(String linija) {
        String []niza = linija.split("\\s+");
        //[42] [C2] [1252] [C33] [1029]
        int orderId = Integer.parseInt(niza[0]);
        List<Item> ednaNaracka = new ArrayList<>();

        Arrays.stream(niza) //niza e niza od elementi
                .skip(1)//thats the id
                .forEach(edenElementOdNiza -> {
                    //pravime proverka dali e itemName ili itemPrice
                    if (Character.isAlphabetic(edenElementOdNiza.charAt(0))) {
                        String imeNaNaracka = edenElementOdNiza;
                        ednaNaracka.add(new Item(imeNaNaracka));
                    } else {
                        int cenaNaNaracka = Integer.parseInt(edenElementOdNiza);
                        ednaNaracka.get(ednaNaracka.size()-1).setItemPrice(cenaNaNaracka);
                    }
                }
                );
        return new Naracki(orderId, ednaNaracka);
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Item> getItemLista() {
        return itemLista;
    }

    @Override
    public String toString() {
        return id + " " + itemLista.size();
    }

    @Override
    public int compareTo(Naracki o) {
        return Integer.compare(this.itemLista.size(), o.itemLista.size());
    }
}

class CakeShopApplication {

    private List<Naracki> narackiLista;

    public CakeShopApplication() {
        narackiLista = new ArrayList<>();
    }

    /* od vlezen potok cita informacii za poveke naracki
       vo sekoj eden red se naogja info za edna naracka vo format
       orderId itemName itemPrice item2Name item2Price
       Metodot treba da vrati cel broj koj oznacuva kolku tocno proizvodi
       se naracani vo ramki na site naracki koi se uspesno procitani */
    public int readCakeOrders (InputStream inputstream) {

        //ja mapirame linijata vo nov objekt
        narackiLista = new BufferedReader(new InputStreamReader(inputstream))
                .lines()
                .map(linija -> Naracki.createOrder(linija))
                .collect(Collectors.toList());

        return narackiLista.stream()
                .mapToInt(ednaNaracka -> ednaNaracka.getItemLista().size())//broj na items vo edna naracka od listata so naracki
                .sum();
    }

    /* metod koj na izlezen potok ke ja ispechati narackata so najgolem br naracani produkti vo format:
    orderId totalOrderItems, kade totalOrderItems e brojot na site naracani produkti */
    public void printLongestOrder (OutputStream outputstream) {
        PrintWriter pw = new PrintWriter(outputstream);

        Naracki najdolgaNaracka = narackiLista.stream()
                .max(Comparator.naturalOrder()) //max vrakja Optional Wrapper
                .orElseGet(() -> new Naracki()); //ako ne najdis najgolema naracka, vrati prazna lista

        pw.write(najdolgaNaracka.toString());
        pw.flush();
    }
}

public class CakeShopApplicationTest {

    public static void main(String[] args) {
        CakeShopApplication cakeshopapplication = new CakeShopApplication();

        System.out.println("--- READING FROM INPUT STREAM ---");
        System.out.println(cakeshopapplication.readCakeOrders(System.in));

        System.out.println("--- PRINTING LARGEST ORDER TO OUTPUT STREAM ---");
        cakeshopapplication.printLongestOrder(System.out);


    }
}
