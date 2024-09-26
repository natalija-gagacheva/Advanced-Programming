package auditoriski.vezba3.Bank;

//mozhe da nasleduvame od bilo koja klasa sho nasledva od Exception, ne mora direk
//pr extends RuntimeException


public class CannotWithdrawMoneyException extends Exception{

    ///gi isprakjame variables vo konstruktorot na exception klasata
    public CannotWithdrawMoneyException(double currentAmount, double amount) {
        super(String.format("Your current amount is: %.2f, and you can not draw withdraw this amount: %.2f",
                currentAmount, amount));
    }
}
