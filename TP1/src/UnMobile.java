import java.awt.*;
import javax.swing.*;
import java.util.Random;

class UnMobile extends JPanel implements Runnable

{
	Random rand = new Random();
	int saLargeur, saHauteur, sonDebDessin;
    final int sonPas = rand.nextInt(16) + 10, sonTemps=50, sonCote=40;
	static semaphoreGeneral sem = new semaphoreGeneral(1);
    UnMobile(int telleLargeur, int telleHauteur)
    {
		super();
		saLargeur = telleLargeur;
		saHauteur = telleHauteur;
		setSize(telleLargeur, telleHauteur);
    }
    public void run() {
		//Aller 0 vers 1
		for (sonDebDessin = 0; sonDebDessin < saLargeur - sonPas; sonDebDessin += sonPas) {
			repaint();
			try {
				Thread.sleep(sonTemps);
			} catch (InterruptedException telleExcp) {
				telleExcp.printStackTrace();
			}
		}
		//Aller 1 vers 2
		sem.syncWait();
		for (sonDebDessin = saLargeur; sonDebDessin < 2*saLargeur - sonPas; sonDebDessin += sonPas) {
			repaint();
			try {
				Thread.sleep(sonTemps);
			} catch (InterruptedException telleExcp) {
				telleExcp.printStackTrace();
			}
		}
		sem.syncSignal();

		//Aller 2 vers 3
		for (sonDebDessin = 2*saLargeur; sonDebDessin < 3*saLargeur - sonPas; sonDebDessin += sonPas) {
			repaint();
			try {
				Thread.sleep(sonTemps);
			} catch (InterruptedException telleExcp) {
				telleExcp.printStackTrace();
			}
		}
		//Retour 3 vers 2
		for (; sonDebDessin > 2*saLargeur; sonDebDessin -= sonPas) {
			repaint();
			try {
				Thread.sleep(sonTemps);
			} catch (InterruptedException telleExcp) {
				telleExcp.printStackTrace();
			}
		}

		//Retour 2 vers 1
		sem.syncWait();
		for (; sonDebDessin > saLargeur; sonDebDessin -= sonPas) {
			repaint();
			try {
				Thread.sleep(sonTemps);
			} catch (InterruptedException telleExcp) {
				telleExcp.printStackTrace();
			}
		}
		sem.syncSignal();

		//Retour 1 vers 0
		for (; sonDebDessin > 0; sonDebDessin -= sonPas) {
			repaint();
			try {
				Thread.sleep(sonTemps);
			} catch (InterruptedException telleExcp) {
				telleExcp.printStackTrace();
			}
		}
		run(); // si besoin de relancer des aller-retours
	}
    public void paintComponent(Graphics telCG)
    {
		super.paintComponent(telCG);
		telCG.fillRect(sonDebDessin, saHauteur/2, sonCote, sonCote);
    }
}