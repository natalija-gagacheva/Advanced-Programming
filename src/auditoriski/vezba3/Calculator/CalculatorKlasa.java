package auditoriski.vezba3.Calculator;

class CalculatorKlasa {
    private double result;
    private StrategyInterface strategija; //chuvame promenliva od tip Interface

    public CalculatorKlasa() {
        this.result = 0.0; //sekogas pocnuva od 0, taka e navedeno vo zadachata
    }

    public double getResult() {
        return result;
    }

    public String execute(char operator, double drugBroj) throws UnknownOperatorException {
//        if (operator == '+') {
//            result += drugBroj;
//        } else if (operator == '-') {
//
//        } else if (operator == '*') {
//
//        } else if (operator == '/') {
//
//        } else {
//
//        }
//      --> so cel da nemora da gi pishime site posebno, posto mozi da ima nekoja kompleksna logika
//          vo eden od metodite, i da zafakja mnogu linii kod sekoj metod posebno,
//          zato ke kreirame Interface sho ke ja vrshi taa funkcionalnost

        //isto kako da imame 4 razl funkcii kojshto go implementiraat sobiranje, odzemanje, mnozenje
        //no gi wrap-uvame pod eden interface vo razlicni klasi
        //za da mozime da izvrshime calculate metodot od interface-ot
        //makar i da neznajme tocno koja instanca ke se kreira

        //Ova e strategy Design Pattern - nacin na kreiranje na nekakov kod vo odredena situacija

        //imame presmetki koj mozime da gi piknime pod edna kapa
        //a vnatre se razlicni
        //primer, kolku dozi vakcini primile vo toj i toj grad
        //i sega presmetkata za eden grad da se praj na eden nacin, za drugi gradovi na vtor nacin
        //reshenie za to e koristenje na interface

        if (operator == '+') {
            strategija = new AdditionKlasa(); //na ovoj nacin se implementia interface-ot preku klasata Addition
        }
        if (operator == '*') {
            strategija = new MultiplicationKlasa();
        }
        if (operator == '-') {
            strategija = new SubstractionKlasa();
        }
        if (operator == '/') {
            strategija = new DivisionKlasa();
        }
        //ako imame operator kojsho ne go poznavame
        else throw new UnknownOperatorException(operator);

        result = strategija.calculate(this.result, drugBroj);
        return String.format ("result %c %.2f = %/2f", operator, drugBroj, result);
    }

    public String init() { //naj prvata linija kod
        return String.format("result = $.2f", result);
    }

    @Override
    public String toString() {
        return String.format("updated result = $.2f", result);
    }
}



