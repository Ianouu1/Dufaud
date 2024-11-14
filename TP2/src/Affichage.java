/**
 * 
 */
import java.io.*;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;
import java.lang.String;

public class Affichage extends Thread {
	String texte;

	static semaphoreBinaire sem = new semaphoreBinaire(1);

	public Affichage(String txt) {
		texte = txt;
	}

	public void run() {

		// ❗❗❗ avec le synchronized system.out (on encadre la section critique : la boucle) ❗❗❗
//		synchronized (System.out) {
//			for (int i = 0; i < texte.length(); i++) {
//				System.out.print(texte.charAt(i));
//				try {
//					sleep(100);
//				} catch (InterruptedException e) {
//				}
//				;
//			}
//		}

		// ❗❗❗ on fait la même chose avvec le sémaphore qui encadre la section critique ❗❗❗
//		sem.syncWait();
//		for (int i=0; i<texte.length(); i++){
//		    System.out.print(texte.charAt(i));
//		    try {sleep(100);} catch(InterruptedException e){};
//		}
//		sem.syncSignal();
	}
}