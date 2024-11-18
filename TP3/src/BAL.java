import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BAL {

    private BlockingQueue<String> queue = new ArrayBlockingQueue<>(3);

    public synchronized boolean deposer(String lettre)  throws InterruptedException {
        return queue.offer(lettre,  200, TimeUnit.MILLISECONDS) ;
    }

    public synchronized String retirer() throws InterruptedException {
        return queue.poll(200, TimeUnit.MILLISECONDS);
    }

    public int getStock() {
        return queue.size();
    }
}