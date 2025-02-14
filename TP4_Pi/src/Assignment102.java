
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Assignment102 {
    public static void main(String[] args) throws Exception {
        int nTotalValue = 120000000;
        int[] numWorkersValues = {1, 2, 3, 4, 5, 6, 8, 10, 12};
        boolean isStrongScalability = true;

        try (FileWriter writer = new FileWriter("data/Assignment102/scale_up_Ass102_myLaptop_12x10e7.csv")) {
            writer.write("Error,Ntotal,Nworkers,Time\n");
            for (int iterations = 0; iterations < 3; iterations++) {
                for (int workers : numWorkersValues) {
                    PiMonteCarlo PiVal;
                    if (isStrongScalability) { // Scalabilité FORTE :
                        PiVal = new PiMonteCarlo(nTotalValue, workers);
                    } else { // Scalabilité FAIBLE :
                        PiVal = new PiMonteCarlo(nTotalValue * workers, workers);
                    }

                    long startTime = System.currentTimeMillis();
                    double value = PiVal.getPi();
                    long stopTime = System.currentTimeMillis();
                    double error = Math.abs((value - Math.PI)) / Math.PI;

                    String result;
                    if (isStrongScalability) { // Scalabilité FORTE :
                        result = (error + "," + nTotalValue + "," + workers + "," + (stopTime - startTime));
                    } else { // Scalabilité FAIBLE :
                        result = (error + "," + nTotalValue * workers + "," + workers + "," + (stopTime - startTime));
                    }

                    System.out.println(result);
                    writer.write(result + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}