public class Consommateur extends Thread {
    private BAL boiteAuLettre;
    private String LettreRecue;

    public Consommateur(BAL bal) {
        boiteAuLettre = bal;
    }
    public void run() {
        try {
            while (true) {
                Thread.sleep(1000);

                String lettre = boiteAuLettre.retirer();

                if (lettre == null) {
                    System.out.println("Consommateur : Aucune lettre dans la BAL");
                } else if (lettre.contains("*")) {
                    System.out.println("Consommateur : On arrête de prendre des lettres, c'était la dernière");
                } else {
                    System.out.println("Consommateur : A récupéré la lettre " + lettre + " | Il reste " + boiteAuLettre.getStock() + " lettre");
                }
            }
        } catch (InterruptedException e) {
            System.out.println("le consommateur à eu un probleme lorqsu'il a recupéré la lettre");
        }
    }
}
