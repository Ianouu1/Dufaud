import javax.swing.*;

class UnMobile extends JPanel implements Runnable {
    int saLargeur, saHauteur, sonDebutDessin;
    final int sonPas = 10, sonTemps = 500, sonCote = 40;

    UnMobile (int telleLargeur, int telleHauteur){
        super();
        saLargeur = telleLargeur;
        saHauteur = telleHauteur;
        setSize(telleLargeur, telleHauteur);
        }
        //UnMobile
        public void run ()
        {
            for (sonDebutDessin = 0; sonDebutDessin < saLargeur – sonPas;
            sonDebutDessin += sonPas)
            {
                repaint();
                try {
                    (Thread.sleep(son Temps)
                }//try
                catch (InterruptedException telleExcp) {
                    telleExcp.printStackTrace();
                }//catch
            }//for
        }//run()
        public void paintComponent (Graphics telContexteGraphique)
        {
            super.paintComponent(telContexteGraphique);
            telContexteGraphique.fillRect(sonDebutDessni, saHauteur / 2, sonCote, SonCote);
        }//paintComponent()
    }//classe UnMobile