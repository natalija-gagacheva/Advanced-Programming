package auditoriski.vezba3.Bank;

//nasleduva od Accounts, implementira interface
public class InterestCheckingAccount extends AccountKlasa implements InterestBearingAccount {

    //mora da kreirame fiksna promenliva za kamatata
    //The combination of the two keywords helps create a constant.
    public static final double INTEREST_RATE = 0.03;
    @Override
    public void addInterest() { //go implementirame addInterest metodot od interface IBA
        //spored baranjeto treba da se dodade 3% na momentalnata suma
        //go implementirame metodot addAmount() od roditel klasata Account
        //so momentalnata sostojba koja sho ke ja zemime so getter posto e private vo roditel klasata
        //ako beshe protected ili package ke mozevme nie (deteto) da ja zemime vrednosta bez getter
        addAmount(getCurrentAmount() * INTEREST_RATE);
    }

    public InterestCheckingAccount(String accountOwner, double currentAmount) {
        super(accountOwner, currentAmount);
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.INTEREST;
    }
}
