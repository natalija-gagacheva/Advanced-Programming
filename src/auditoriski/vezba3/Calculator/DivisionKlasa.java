package auditoriski.vezba3.Calculator;

//posto go implementira interface-ot mora i abstraktniot metod calculate da go implementirame
public class DivisionKlasa implements StrategyInterface {
    @Override
    public double calculate(double broj1, double broj2) {
        if (broj2 != 0)
            return broj1 / broj2;
        else
            return broj1;
    }
}
