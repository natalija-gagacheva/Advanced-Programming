package auditoriski.vezba3.Calculator;

//posto go implementira interface-ot mora i abstraktniot metod calculate da go implementirame
public class SubstractionKlasa implements StrategyInterface {
    @Override
    public double calculate(double broj1, double broj2) {
        return broj1-broj2;
    }
}
