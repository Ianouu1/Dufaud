import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class PiMonteCarlo {
    AtomicInteger nAtomSuccess;
    int nThrows;
    int nProcessors;
    double value;

    class MonteCarlo implements Runnable {
        @Override
        public void run() {
            double x = Math.random();
            double y = Math.random();
            if (x * x + y * y <= 1)
                nAtomSuccess.incrementAndGet();
        }
    }

    public PiMonteCarlo(int i, int processors) {
        this.nAtomSuccess = new AtomicInteger(0);
        this.nThrows = i;
        this.nProcessors = processors;
        this.value = 0;
    }

    public double getPi() {
        // sous-entends que pi = value
//        int nProcessors = Runtime.getRuntime().availableProcessors(); // va demander a l'os cb de ressource dispo (nombre de coeur)
        ExecutorService executor = Executors.newWorkStealingPool(nProcessors);
        //  itération parallele donc on cherche la boucle, elle est là :
        for (int i = 1; i <= nThrows; i++) {
            Runnable worker = new MonteCarlo();
            executor.execute(worker); // executor qui ressemble un peu a un thread.
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        value = 4.0 * nAtomSuccess.get() / nThrows;
        return value;
    }
}