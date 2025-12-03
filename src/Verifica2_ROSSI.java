import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


class MyTask implements Callable<Result> {

    private long n;

    public MyTask(long n) {
        this.n = n;
    }

    @Override
    public Result call() {

        ArrayList<Long> fattori = new ArrayList<>();

        long startMs = System.currentTimeMillis();
        long startNs = System.nanoTime();

        long temp = n;

        // Fattorizzazione
        for (long i = 2; i * i <= temp; i++) {
            while (temp % i == 0) {
                fattori.add(i);
                temp /= i;
            }
        }
        if (temp > 1) fattori.add(temp);

        long endNs = System.nanoTime();
        long endMs = System.currentTimeMillis();
        long durataUs = (endNs - startNs) / 1000;

        return new Result(n, startMs, startNs, fattori, endMs, endNs, durataUs);
    }
}

  class FattorizzazioneAsincorna{
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Inserisci 6 numeri di tipo Long da fattorizzare grazie");

        long[] value = new long[6];
        for (int i = 0; i < 6; i++) {
            System.out.println("Numero" + (i + 1));
            value[i] = sc.nextLong();
        }
        ExecutorService exec = Executors.newFixedThreadPool(3);
        Future<Result> f1 =  exec.submit(new MyTask(value[0]));
        Future<Result> f2 =  exec.submit(new MyTask(value[1]));
        Future<Result> f3 =  exec.submit(new MyTask(value[2]));
        Future<Result> f4 =  exec.submit(new MyTask(value[3]));
        Future<Result> f5 =  exec.submit(new MyTask(value[4]));
        Future<Result> f6 =  exec.submit(new MyTask(value[5]));



    }
}

class Result {
    long number;
    long startMs;
    long startNs;
    List<Long> factors;
    long endMs;
    long endNs;
    long durationUs;

    public Result(long number, long startMs, long startNs,
                  List<Long> factors, long endMs, long endNs, long durationUs) {
        this.number = number;
        this.startMs = startMs;
        this.startNs = startNs;
        this.factors = factors;
        this.endMs = endMs;
        this.endNs = endNs;
        this.durationUs = durationUs;
    }
}

