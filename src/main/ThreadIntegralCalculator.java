package main;

import java.util.function.DoubleUnaryOperator;

public class ThreadIntegralCalculator implements Runnable {
    IntegralCalculator calculator;
    Main main;

    public ThreadIntegralCalculator(Main main, double a, double b, int n, DoubleUnaryOperator f) {
        calculator = new IntegralCalculator(a,b,n,f);
        this.main = main;
    }

    @Override
    public void run() {
        double v = calculator.calculate();
        main.sendResult(v);
    }
}
