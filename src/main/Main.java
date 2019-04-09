package main;

public class Main {

    double totalResult;
    int finishedThreadCount;

    public static void main(String[] args) {
	    new Main().run();
    }

    private void run() {
        double a = 0;
        double b = Math.PI;
        int n = 1000_000_000;
//        IntegralCalculator calculator = new IntegralCalculator(a,b,n,x->Math.sin(x));
        int threads = 20;
        double delta = (b-a)/threads;
        totalResult = 0;
        finishedThreadCount = 0;
        long start = System.currentTimeMillis();

        for (int i = 0; i < threads; i++) {
            ThreadIntegralCalculator t = new ThreadIntegralCalculator(this, a + i * delta, a + (i + 1) * delta, n / threads, Math::sin);
            new Thread(t).start();
        }

        try {
            synchronized (this) {
                while (finishedThreadCount < threads) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        double res = calculator.calculate();

        long finish = System.currentTimeMillis();
        System.out.println("res = " + totalResult);
        System.out.println(finish-start);
    }

    public synchronized void sendResult(double v) {
        totalResult += v;
        finishedThreadCount++;
        notify();
    }
}
