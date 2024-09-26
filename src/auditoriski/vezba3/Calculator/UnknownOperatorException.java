package auditoriski.vezba3.Calculator;

public class UnknownOperatorException extends Exception {

    //kreirame konstruktor so poraka

    public UnknownOperatorException(char operator) {
        super(String.format("This operator '%c' is not valid", operator));
    }
}
