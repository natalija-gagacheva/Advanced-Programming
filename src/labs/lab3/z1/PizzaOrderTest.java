package labs.lab3.z1;

import java.util.*;

class InvalidExtraTypeException extends Exception {
    public InvalidExtraTypeException(String message) {
        super(message);
    }
}

class InvalidPizzaTypeException extends Exception {

    public InvalidPizzaTypeException(String message) {
        super(message);
    }
}

class ItemOutOfStockException extends Exception {
    
}

interface ItemInterface {

    int getPrice();
    String getType();

}

class ExtraItem implements ItemInterface {
    private String ivType;

    //tuka odredvame kakov type e
    public ExtraItem(String type) throws InvalidExtraTypeException{
        if (type.equals("Coke")) {
            this.ivType=type; //mu ja zadavame vrednosta passed as argument to the instance variable
        } else if (type.equals("Ketchup")) {
            this.ivType=type;
        } else {
         throw new InvalidExtraTypeException("InvalidExtraTypeException");
        }
    }
    
    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public String getType()  //ovde samo go zemame type dokolku ni treba vo nekoja druga klasa
    {
        return ivType;
    }
}

class PizzaItem implements ItemInterface {

    private String ivtype;

    public PizzaItem(String type) throws InvalidPizzaTypeException {
        if (type.equals("Standard")) {
            this.ivtype = type;
        } else if (type.equals("Pepperoni")) {
            this.ivtype = type;
        } else if (type.equals("Vegetarian")) {
            this.ivtype = type;
        } else {
            throw new InvalidPizzaTypeException("InvalidPizzaTypeException");
        }
    }

    @Override
    public int getPrice() {
        return 0;
    }

    @Override
    public String getType()  {
        return ivtype;
    }
}

class Order {
    ItemInterface[] nizaOdItems;

    public Order() {
        nizaOdItems = new ItemInterface[0];
    }

    public void addItem(ItemInterface item, int numOfThatItem) {
        if (numOfThatItem>10) {
            throw new ItemOutOfStockException(item);

    }

}

public class PizzaOrderTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test Item
            try {
                String type = jin.next();
                String name = jin.next();
                ItemInterface item = null;
                if (type.equals("Pizza")) item = new PizzaItem(name);
                else item = new ExtraItem(name);
                System.out.println(item.getPrice());
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
        if (k == 1) { // test simple order
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    ItemInterface item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    ItemInterface item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 2) { // test order with removing
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    ItemInterface item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (jin.hasNextInt()) {
                try {
                    int idx = jin.nextInt();
                    order.removeItem(idx);
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 3) { //test locking & exceptions
            Order order = new Order();
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.addItem(new ExtraItem("Coke"), 1);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.removeItem(0);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
    }

}