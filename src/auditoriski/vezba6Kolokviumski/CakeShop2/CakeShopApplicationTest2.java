package auditoriski.vezba6Kolokviumski.CakeShop2;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class InvalidOrderException extends Exception {

    public InvalidOrderException(int orderId) {
        super(String.format("The order with id %d has less items than the minimum allowed.", orderId));
    }
}
enum Type {
    CAKE, PIE
}

//kreirame abstract class
abstract class Item  {
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

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    abstract Type getType();

    @Override
    public String toString() {
        return "Item{" +
                "itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                '}';
    }

}

class Cake extends Item {

    public Cake(String itemName) {
        super(itemName);
    }

    @Override
    Type getType() {
        return Type.CAKE;
    }
}

class Pie extends Item {
    public Pie(String itemName) {
        super(itemName);
    }

    @Override
    Type getType() {
        return Type.PIE;
    }

    @Override
    public int getItemPrice() {
        return super.getItemPrice() + 50;
    }
}

class Naracki implements Comparable<Naracki>  {
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
    public static Naracki createOrder(String linija, int minAmountItems) throws InvalidOrderException {
        String []niza = linija.split("\\s+");
        //[42] [C2] [1252] [C33] [1029]
        int orderId = Integer.parseInt(niza[0]);
        List<Item> ednaNaracka = new ArrayList<>();

        Arrays.stream(niza) //niza e niza od elementi
                .skip(1)//thats the id
                .forEach(edenElementOdNiza -> {
                            //pravime proverka dali e itemName ili itemPrice
                            if (Character.isAlphabetic(edenElementOdNiza.charAt(0))) {
                                if (edenElementOdNiza.charAt(0) == 'C') {
                                    String imeNaNaracka = edenElementOdNiza;
                                    ednaNaracka.add(new Cake(imeNaNaracka));
                                } else {
                                    String imeNaNaracka = edenElementOdNiza;
                                    ednaNaracka.add(new Pie(imeNaNaracka));
                                }
                            } else {
                                int cenaNaNaracka = Integer.parseInt(edenElementOdNiza);
                                ednaNaracka.get(ednaNaracka.size()-1).setItemPrice(cenaNaNaracka);
                            }
                        }
                );

        //pred da ja vratime narackata treba da napravime uslov kojshto frla isklucok
        //createOrder() metodot frla isklucok i go fakjame vo readCakeOrders()
        if (ednaNaracka.size() < minAmountItems) {
            throw new InvalidOrderException(orderId);
        }
        //nema logika da frlame i da fakjame isklucoci vo ist line of code


        return new Naracki(orderId, ednaNaracka);
    }

    //za sekoja Naracka ni treba suma na site poracani produkti
    public int vkCena() {
        return itemLista
                .stream()
                .mapToInt(linija -> linija.getItemPrice())
                .sum();
    }

    public int vkPies() {
        return (int) itemLista
                .stream()
                .filter(linija -> linija.getType().equals(Type.PIE))
                .count(); //vrakja kolku elementi ima vo stream
    }

    public int vkCakes() {
        return (int) itemLista
                .stream()
                .filter(linija -> linija.getType().equals(Type.CAKE))
                .count();
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
        return id + " " + itemLista.size() + " " + vkPies() + " " + vkCakes() + " " + vkCena();
    }

    @Override
    public int compareTo(Naracki o) {
        return Integer.compare(this.vkCena(), o.vkCena());
    }



}

class CakeShopApplication { //se cuva info za najmaliot broj na proizovdi koi mozhe da se naracaat vo ramki na edna naracka
    List<Naracki> narackiLista;
    int minAmountItems = 0;

    public CakeShopApplication() {
        narackiLista = new ArrayList<>();
    }

    public CakeShopApplication(int minAmountItems) {
        this.minAmountItems = minAmountItems;
        narackiLista = new ArrayList<>();
    }


    /* metod koj cita info za povekje naracki
        Vo sekoj eden red se naogja info za edna naracka vo format

        Prvata bukva od imeto na proizvodot go dava negoviot vid, c=cake, p=pie
        Treba da se spreci dodavanje naracka koja ima pomalku proizvodi od minimalno dozvolenite
        so InvalidOrderException i ke se ispecati poraka vo format
         "This order with id [id] has less items than the minimum amount"
    */
    public void readCakeOrders(InputStream inputstream) {
        //ja mapirame linijata vo nov objekt
        narackiLista = new BufferedReader(new InputStreamReader(inputstream))
                .lines()
                //vo ovoj moment ovoj metod readCakeOrders odlucuva dali ke se dodaj narackata ili ne
                //i istiot treba da se pokrie so Try-Catch block
                //odnosno, za sekoja edna linija shto ke ja primis povikaj go metodot createOrder
                //ako istiot raboti kako sto treba bez da frli isklucok, odnosno kreira naracka,
                // uspesno mozime da ja dodajme nea vo listata so naracki
                //no dokolku ne se kreira narackata, fati exception
                .map(linija -> {
                    try {
                        return Naracki.createOrder(linija, minAmountItems);
                    } catch (InvalidOrderException e) {
                        System.out.println(e.getMessage()); //sakame da se napishe nashata poraka
                        return null; //ni bara objekt, vrakjame null
                    }
                })
                //poravi isklucokot, ke imame null objects, pa ke mora da gi filtrirame
                //.filter(naracka -> naracka != null )
                .filter(Objects::nonNull)
                .collect(Collectors.toList());


    }

    /* Treba da bidat sortirani po opagjacki redosled
    * spored sumata na site naracani produkti vo ramki na narackata
    * Ima pokacuvanje na cenata na site pies za 50 denari
    * formatot za pecatenje na narackata e:
    * orderId totalOrderItems totalPies totalCakes totalAmount */
    public void printAllOrders(OutputStream outputStream) {
        PrintWriter pw = new PrintWriter(outputStream);

        narackiLista
                .stream()
                .sorted(Comparator.reverseOrder())
                .forEach(naracka -> pw.println(naracka.toString()));

        pw.flush();
    }

}
public class CakeShopApplicationTest2 {
    public static void main(String[] args) {
        CakeShopApplication csapp = new CakeShopApplication(4);

        System.out.println("--- READING FROM INPUT STREAM ---");
        csapp.readCakeOrders(System.in);

        System.out.println("--- PRINTING ALL ORDERS TO OUTPUT STREAM ---");
        csapp.printAllOrders(System.out);
    }
}
