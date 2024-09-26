package auditoriski.vezba2.CombinationLock;
//NEDOVRSHENA


//Katanec so kombinacii Combination Lock i treba da gi ima slednite svojstva
// 1. kombinacijata e skriena
// 2. katanecot mozhe da se otvori samo ako se vnese tochna kombinacija
// 3. kombinacijata mozhe da se promeni samo ako se znae preth
//Dopolnitelno treba da se dizajnira klasa so javni metodi open i changeCombo
// i privatni podatoci koi ja chuvaat kombinacijata

public class CombinationLock {
    private int combination;
    private boolean isOpen;
    private static int DEFAULT_COMBINATION = 100;

    //if we don't handle the exception, we need to extend
    public CombinationLock(int combination) {
        if (isValid(combination))
            this.combination = combination;
        else
            this.combination = DEFAULT_COMBINATION;
        //ako kombinacijata ne e validna

        this.isOpen = false;
    }

    //kreirame privaten metod, proverka dali e 3cifren
    private boolean isValid(int combination) {
        return combination >= 100 && combination <= 999;
    }


}
