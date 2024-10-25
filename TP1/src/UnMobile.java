import java.awt.*;
import javax.swing.*;

class UnMobile extends JPanel implements Runnable
{
    int saLargeur, saHauteur, sonDebDessin;
    final int sonPas = 10, sonTemps=50, sonCote=40;
	static semaphoreBinaire sem = new semaphoreBinaire(1);
    UnMobile(int telleLargeur, int telleHauteur)
    {
	super();
	saLargeur = telleLargeur;
	saHauteur = telleHauteur;
	setSize(telleLargeur, telleHauteur);
    }

    public void run()
    {
	for (sonDebDessin = 0; sonDebDessin < saLargeur - sonPas; sonDebDessin += sonPas)
	    {
		repaint();
		try{Thread.sleep(sonTemps);}
		catch (InterruptedException telleExcp)
		    {telleExcp.printStackTrace();}
	    }
		for (sonDebDessin = saLargeur; 0 < sonDebDessin - sonPas; sonDebDessin -= sonPas)
		{
			repaint();
			try{Thread.sleep(sonTemps);}
			catch (InterruptedException telleExcp)
			{telleExcp.printStackTrace();}
		}
		//run(); // si je veux faire en boucle
    }

    public void paintComponent(Graphics telCG)
    {
	super.paintComponent(telCG);
	telCG.fillRect(sonDebDessin, saHauteur/2, sonCote, sonCote);
    }
}