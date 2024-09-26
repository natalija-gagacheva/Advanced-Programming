package auditoriski.vezba3.Bank;

// vo Bank se chuva lista od site vidovi na Accounts
// postoi metod totalAssets koj ja vrakja sumata na sostojbata na site smetki
// isto taka sodrzhi metod addInterest koj go povikuva metodot addInterest
// na onie smetki koi se podlozhni na kamata - OGRANICUVANJE


import java.util.Arrays;

public class Bank {
    private AccountKlasa[] accountsNiza;
    private int totalAccounts;
    private int maxAccounts;

    public Bank(int maxAccounts) {
        this.maxAccounts = maxAccounts;
        //kreirame nova niza od smetki spored max broj na smetki prenesen vo konstruktorot
        this.accountsNiza = new AccountKlasa[maxAccounts];
        this.totalAccounts = 0;
    }

    public void addAccount (AccountKlasa akauntNovDodaen) {
        //ako stignime do max elements in array
        if (totalAccounts == maxAccounts) {
            //stariot pokazhuvach na account pomesti go kon novata niza sho se kreira
            accountsNiza = Arrays.copyOf(accountsNiza, maxAccounts*2);  //vrakja niza
            maxAccounts *= 2;
        }
        accountsNiza[totalAccounts++] = akauntNovDodaen;
    }

    //ja vrakja sumata na sostojbata na site smetki
    public double totalAssets() {
        double total = 0;
        for (AccountKlasa edenAccount : accountsNiza) {
            total+= edenAccount.getCurrentAmount();
        }
        return total;
    }

    //go povidkuva addInterest na site smetki podlozni na kamata
    public void addInterestToAllAccounts() {
        for (AccountKlasa edenAccount : accountsNiza) {
            //dodavame kamata na sekoja smetka sho treba preku interface-ot
            //mora da proverime dali objektot e instanca od interface-ot
            if (edenAccount.getAccountType().equals(AccountType.INTEREST)) {
                //za da mozhime da pristapime do metodite koi sho gi barame potrebno e da kastirame
                InterestBearingAccount IBA = (InterestBearingAccount) edenAccount;
                IBA.addInterest();
            }
        }
    }
}
