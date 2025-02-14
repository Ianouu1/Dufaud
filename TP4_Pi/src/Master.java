import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Creates workers to run the Monte Carlo simulation
 * and aggregates the results.
 */
public class Master {
    public long doRun(int totalCount, int numWorkers) throws InterruptedException, ExecutionException {

        long startTime = System.currentTimeMillis();

        // Créé une collection de taches qui recevra les résultats des workers
        List<Callable<Long>> tasks = new ArrayList<Callable<Long>>();
        for (int i = 0; i < numWorkers; ++i) {
            tasks.add(new Worker(totalCount)); // chaque worker le même nombre de tâches à réaliser
        }

        // Run them and receive a collection of Futures
        ExecutorService exec = Executors.newFixedThreadPool(numWorkers);
        List<Future<Long>> results = exec.invokeAll(tasks); // creer une liste de resultats qui vont arriver dans le futur
        long total = 0;

        // Assemble the results.
        for (Future<Long> f : results) {
            // c maintenant qu'on reccupere toutes les taches finies !!!!!!! verifier si c reel en mode "a quel moment..;"
            total += f.get();
        }
        double pi = 4.0 * total / totalCount / numWorkers;

        long stopTime = System.currentTimeMillis();

//        System.out.println("\nPi : " + pi );
//        System.out.println("Error: " + (Math.abs((pi - Math.PI)) / Math.PI) +"\n");
//
//        System.out.println("Ntot: " + totalCount*numWorkers);
//        System.out.println("Available processors: " + numWorkers);
//        System.out.println("Time Duration (ms): " + (stopTime - startTime) + "\n");

        System.out.println((Math.abs((pi - Math.PI)) / Math.PI) + " " + totalCount * numWorkers + " " + numWorkers + " " + (stopTime - startTime));

        exec.shutdown();
        return total;
    }
}
