package auditoriski.vezba3.Bank;

public class NonInterestCheckingAccount extends AccountKlasa {
    public NonInterestCheckingAccount(String accountOwner, double currentAmount) {
        super(accountOwner, currentAmount);
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.NON_INTEREST;
    }
}
