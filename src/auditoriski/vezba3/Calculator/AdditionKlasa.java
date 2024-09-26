package auditoriski.vezba3.Calculator;

//Addition klasata definira sobiranje, shto oznacuva strategija
//na ovoj nacin go koristime interface-ot
public class AdditionKlasa implements StrategyInterface {
    @Override
    public double calculate(double broj1, double broj2) {
        return broj1 + broj2; //tuka kazhuvame sho ke vrati taa specificna funkcija
    }
}
