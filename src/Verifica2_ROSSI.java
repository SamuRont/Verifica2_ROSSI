import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


class MyTask implements Callable<Result> {

    private long numero;

    public MyTask(long numero) {
        this.numero = numero;
    }

    @Override
    public Result call() {

        ArrayList<Long> fattor = new ArrayList<>();

        long inizioMS = System.currentTimeMillis();
        long inizioNS = System.nanoTime();

        long temp = numero;

        // Fattorizzazione
        for (long i = 2; i * i <= temp; i++) {
            while (temp % i == 0) {
                fattor.add(i);
                temp /= i;
            }
        }
        if (temp > 1) fattor.add(temp);

        long fineNS = System.nanoTime();
        long fineMS = System.currentTimeMillis();
        long durata = (fineNS - inizioNS) / 1000;

        return new Result(numero, inizioMS, inizioNS, fattor, fineMS, fineNS, durata);
    }
}

  class FattorizzazioneAsincorna{
    public static void main(String[] args) throws Exception {
        Scanner a = new Scanner(System.in);
        System.out.println("Inserisci 6 numeri di tipo Long (grazie)");

        long[] value = new long[6];
        for (int i = 0; i < 6; i++) {
            System.out.println("Numero" + (i + 1));
            value[i] = a.nextLong();
        }
        ExecutorService exec = Executors.newFixedThreadPool(3);
        // i 6 oggetti future
        Future<Result> f1 =  exec.submit(new MyTask(value[0]));
        Future<Result> f2 =  exec.submit(new MyTask(value[1]));
        Future<Result> f3 =  exec.submit(new MyTask(value[2]));
        Future<Result> f4 =  exec.submit(new MyTask(value[3]));
        Future<Result> f5 =  exec.submit(new MyTask(value[4]));
        Future<Result> f6 =  exec.submit(new MyTask(value[5]));


        // stampa risultati
        printTask(1, f1.get());
        printTask(2, f2.get());
        printTask(3, f3.get());
        printTask(4, f4.get());
        printTask(5, f5.get());
        printTask(6, f6.get());

        exec.shutdown();



    }
    // funzione per stampare tutti i risultati che se no devo scriverlo mille volte
      private static void printTask(int id, Result r) {
          System.out.println("\nTASK " + id );
          System.out.println("Numero = " + r.numero);
          System.out.println("Orario start (ms)    = " + r.inizioMs);
          System.out.println("Orario start2 (ns)       = " + r.inizioNs);
          System.out.println("Fattori trovati       = " + r.fattor);
          System.out.println("Orario fine (ms)      = " + r.fineMs);
          System.out.println("Orario fine (ns)         = " + r.fineNs);
          System.out.println("Tempo impiegato       = " + r.durata + " us");
      }
}

// classe con i valori perchè non mi piacciono gli array list
class Result {
    long numero;
    long inizioMs;
    long inizioNs;
    ArrayList<Long> fattor;
    long fineMs;
    long fineNs;
    long durata;

    public Result(long numero, long inizioMs, long inizioNs,
                  ArrayList<Long> fattor, long fineMs, long fineNs, long durata) {
        this.numero = numero;
        this.inizioMs = inizioMs;
        this.inizioNs = inizioNs;
        this.fattor = fattor;
        this.fineMs = fineMs;
        this.fineNs = fineNs;
        this.durata = durata;
    }
}


