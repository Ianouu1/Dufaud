import java.io.FileWriter;
import java.io.IOException;

/**
 * Approximates PI using the Monte Carlo method.  Demonstrates
 * use of Callables, Futures, and thread pools.
 */
public class Pi {
    public static void main(String[] args) throws Exception {
        int nTotal = 1200000000;
        int[] numWorkersValues = {1, 2, 3, 4, 5, 6, 8, 10, 12};

        boolean isStrongScalability = false;

        try (FileWriter writer = new FileWriter("data/Pi/scale_out_Pi_myLaptop_12x10e7.csv")) {
            writer.write("Error,Ntotal,Nworkers,Time\n");
            for (int iterations = 0; iterations < 3; iterations++) {
                for (int numWorkers : numWorkersValues) {
                    long startTime = System.currentTimeMillis();
                    int adjustedTotal;
                    long total;
                    double pi;
                    long stopTime;
                    String result;

                    if (isStrongScalability){ // Scalabilité FORTE :
                        System.out.println("ntotal : " + nTotal + " numWorkers : " + numWorkers);
                        adjustedTotal = nTotal / numWorkers;
                        total = new Master().doRun(adjustedTotal, numWorkers);
                        stopTime = System.currentTimeMillis();
                        pi = 4.0 * total / adjustedTotal / numWorkers;
                        result = (Math.abs((pi - Math.PI)) / Math.PI) + "," + nTotal + "," + numWorkers + "," + (stopTime - startTime);
                    } else { // Scalabilité FAIBLE :
                        System.out.println("ntotal : " + nTotal*numWorkers + " numWorkers : " + numWorkers);
                        total = new Master().doRun(nTotal, numWorkers);
                        stopTime = System.currentTimeMillis();
                        pi = 4.0 * total / nTotal / numWorkers;
                        result = (Math.abs((pi - Math.PI)) / Math.PI) + "," + nTotal * numWorkers + "," + numWorkers + "," + (stopTime - startTime);
                    }
                    writer.write(result + "\n");
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




