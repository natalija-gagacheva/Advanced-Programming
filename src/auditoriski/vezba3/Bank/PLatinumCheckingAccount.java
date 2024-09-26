package auditoriski.vezba3.Bank;

public class PLatinumCheckingAccount extends InterestCheckingAccount {

    public PLatinumCheckingAccount(String accountOwner, double currentAmount) {
        super(accountOwner, currentAmount);
    }

    @Override
    public void addInterest() {
        addAmount(getCurrentAmount() * INTEREST_RATE * 2);
    }
}
