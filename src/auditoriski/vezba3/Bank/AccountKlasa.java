package auditoriski.vezba3.Bank;

//Account pretstavuva abstraktna klasa, toa znaci deka od nea ne mozheme da instancirame direkno objekt

//pod Account mozhat da spagjaat site smetki, zato ne mozhime tuka da go iskoristime addInterest metodot,
//toj vazhi samo za smetkite podlozni na kamata

//abstract ni ukazhuva deka nema da mozhime da kreirame direktni instanci od taa klasa
public abstract class AccountKlasa {
    private String accountOwner;
    private int id; //se odnesuva na momentalnata smetka
                    //go chuvame lokalno, samo za eden Account
    private static int idSeed=1000; //go stavame da pocni od 1, sekvencijalno ke se zgolemva pri sekoe povikuvanje
                                //i go chuvame na nivo na klasa i pri sekoj povik na konstruktoror se zgolemva
    //koga ke treba da mu se dodeli ID na Account, zemi go globalnoto ID, zgolemi go za 1 i stavi go vo this.id
    //posto e idSeed++ prvo ke se stavi vrednosta pa posle ke se zgolemi
    //kako shto kreirame objekti, go zgolemuvame brojot id na nivo na cela klasa
    private double currentAmount;
    private AccountType accountType; //chuvame i kakov tip na Account e za da mozhime podocna da napravime proverka

    public AccountKlasa(String accountOwner, double currentAmount) {
        this.accountOwner = accountOwner;
        //id mora da e sekvencijalen broj, ne ja isprakjame vrednosta preku konstruktor
        this.id = idSeed++;
        this.currentAmount = currentAmount;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void addAmount(double amount) {
        this.currentAmount += amount;
    }

    //mora da naznacime deka metodot frla iskluchok i ke bidi faten vo klasata CannotWithdrawException
    public void withdrawAmount(double amount) throws CannotWithdrawMoneyException{
        if(currentAmount <= amount) {
            throw new CannotWithdrawMoneyException(currentAmount, amount);
        }
        this.currentAmount -= amount;

    }

    //bidejki e abstraktna klasa, koristime metod koj ke vrakja tipot na Account
    //sekoj child class si go implementira ovoj abstrakten metod i kazhuva kakov tip e
    public abstract AccountType getAccountType();

    @Override
    public String toString() {
        return String.format("%d: %.2f", id, currentAmount);
    }
}

