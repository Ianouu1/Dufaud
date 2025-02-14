import java.io.*;
import java.net.*;

/**
 * Master is a client. It makes requests to numWorkers.
 */
public class MasterSocket {
    static int maxServer = 12;
    static final int[] tab_port = {25545, 25546, 25547, 25548, 25549, 25550, 25551, 25552, 25553, 25554, 25555, 25556};
    static String[] tab_total_workers = new String[maxServer];
    static final String ip = "127.0.0.1";
    static BufferedReader[] reader = new BufferedReader[maxServer];
    static PrintWriter[] writer = new PrintWriter[maxServer];
    static Socket[] sockets = new Socket[maxServer];


    public static void main(String[] args) throws Exception {

        // MC parameters
        int totalCount = 120000000; // total number of throws on a Worker
        double total = 0; // total number of throws inside quarter of disk
        double pi = 0.0;


        int numWorkers = maxServer;
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String s; // for bufferRead

        System.out.println("#########################################");
        System.out.println("# Computation of PI by MC method        #");
        System.out.println("#########################################");

        try (FileWriter csvWriter = new FileWriter("data/MasterWorker/scale_out_MW_myLaptop_12x10e7.csv", true)) {
            csvWriter.write("Error,Ntotal,Nworkers,Time\n");
            //create worker's socket
            for (int i = 0; i < numWorkers; i++) {
                sockets[i] = new Socket(ip, tab_port[i]);
                System.out.println("SOCKET = " + sockets[i]);

                reader[i] = new BufferedReader(new InputStreamReader(sockets[i].getInputStream()));
                writer[i] = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sockets[i].getOutputStream())), true);
            }

            String message_to_send = String.valueOf(totalCount);
            String message_repeat = "y";

            long stopTime, startTime;

            while (message_repeat.equals("y")) {

                startTime = System.currentTimeMillis();
                total = 0;
                // initialize workers
                for (int i = 0; i < numWorkers; i++) {
                    writer[i].println(message_to_send);          // send a message to each worker
                }
                //listen to workers's message
                for (int i = 0; i < numWorkers; i++) {
                    tab_total_workers[i] = reader[i].readLine();      // read message from server
                    System.out.println("Client sent: " + tab_total_workers[i]);
//                tab_total_workers[i] = reader[i].readLine();      // read message from server
//                System.out.println("Worker " + i + " sent: " + tab_total_workers[i]);
//                total += Integer.parseInt(tab_total_workers[i]);  // Add to total
                }

                // compute PI with the result of each workers
                for (int i = 0; i < numWorkers; i++) {
                    total += Double.parseDouble(tab_total_workers[i]);
                }
//            pi = 4.0 * total / totalCount / numWorkers; //base
//            pi = total / numWorkers; //moi
                pi = 4.0 * total / (totalCount * numWorkers); // Pi je crois


                stopTime = System.currentTimeMillis();


                String result = (Math.abs((pi - Math.PI)) / Math.PI) + "," + totalCount * numWorkers + "," +  numWorkers + "," + (stopTime - startTime);
                csvWriter.write(result + "\n");

                System.out.println("\nPi : " + pi);
                System.out.println("Error: " + (Math.abs((pi - Math.PI)) / Math.PI) + "\n");

                System.out.println("Ntot: " + totalCount * numWorkers);
                System.out.println("Available processors: " + numWorkers);
                System.out.println("Time Duration (ms): " + (stopTime - startTime) + "\n");

                System.out.println(result);

                System.out.println("\n Repeat computation (y/N): ");
                try {
                    message_repeat = bufferRead.readLine();
                    System.out.println(message_repeat);
                } catch (IOException ioE) {
                    ioE.printStackTrace();
                }
            }

            for (int i = 0; i < numWorkers; i++) {
                System.out.println("END");     // Send ending message
                writer[i].println("END");
                reader[i].close();
                writer[i].close();
                sockets[i].close();
            }
        }
    }
}