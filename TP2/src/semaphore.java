public abstract class semaphore {

    protected int valeur=0;

    protected semaphore (int valeurInitiale){
	valeur = valeurInitiale>0 ? valeurInitiale:0;
    }

    public synchronized void syncWait(){
	try {
	    while(valeur<=0){
		wait();
        }
	    valeur--;
	} catch(InterruptedException e){
        e.printStackTrace();
    }
    }

    public synchronized void syncSignal(){
	if(++valeur > 0) notifyAll();
    }
}
