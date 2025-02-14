import java.io.*;
import java.net.*;

/**
 * Worker is a server. It computes PI by Monte Carlo method and sends
 * the result to Master.
 */
public class WorkerSocket {
    static int port = 25545; //default port
    private static boolean isRunning = true;

    /**
     * compute PI locally by MC and sends the number of points
     * inside the disk to Master.
     */
    public static void main(String[] args) throws Exception {

        if (!("".equals(args[0]))) port = Integer.parseInt(args[0]);
        System.out.println(port);
        ServerSocket s = new ServerSocket(port);
        System.out.println("Server started on port " + port);
        Socket soc = s.accept();

        // BufferedReader bRead for reading message from Master
        BufferedReader bRead = new BufferedReader(new InputStreamReader(soc.getInputStream()));

        // PrintWriter pWrite for writing message to Master
        PrintWriter pWrite = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())), true);
        String str;
        String str2;
        while (isRunning) {
            long total = 0; // nombre de points dans un quart de cercle
            str = bRead.readLine();          // read message from Master
            if (!(str.equals("END"))) {
                System.out.println("Server receives totalCount = " + str);
                str2 = String.valueOf(1);//bRead.readLine();
                System.out.println("Server receives numWorker = " + str2);


                // compute Pi carlo
//                int totalCount = Integer.parseInt(str);
//                PiMonteCarlo piMCCalculator = new PiMonteCarlo(totalCount);
//                double piValue = piMCCalculator.getPi();
                System.out.println("--------------------");
                total = new Master().doRun(Integer.parseInt(str), Integer.parseInt(str2));
                System.out.println("total = "+ total);
                System.out.println("--------------------");


                pWrite.println(total/Integer.parseInt(str2));         // send number of points in quarter of disk
            } else {
                isRunning = false;
            }
        }
        bRead.close();
        pWrite.close();
        soc.close();
    }
}