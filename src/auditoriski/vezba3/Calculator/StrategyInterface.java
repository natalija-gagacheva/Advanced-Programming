package auditoriski.vezba3.Calculator;

//Kreirame Design Pattern ili Wrapper za odnesuvanje

//Strategy Interface definira strategija za nekakva kalkulacija, t.e kreirame abstrakten metod calculate preku kojshto kazvame deka nekakva funckionalnsot mora da ima ovoj interface
public interface StrategyInterface {

//  tuka pravime nekakva operacija, t.e kalkulacijata shto e vnatre vo metodata
//      if (operacija == '+') {
//            result += novBroj;
//        }

    public double calculate (double broj1, double broj2);
}