public class Consommateur extends Thread {
    private BAL boiteAuLettre;
    private String lettreRecue;

    public Consommateur(BAL bal) {
        boiteAuLettre = bal;
    }
    public void run() {
        try {
            while (true) {
                Thread.sleep(20); // délai ajustable a souhait afin de voir les différents comportements de la BAL

                lettreRecue = boiteAuLettre.retirer();

                if (lettreRecue == null) {
                    System.out.println("Consommateur : Aucune lettre dans la BAL");
                } else if (lettreRecue.contains("*")) {
                    System.out.println("Consommateur : On arrête de prendre des lettres, c'était la dernière");
                } else {
                    System.out.println("Consommateur : A récupéré la lettre " + lettreRecue + " | Il reste " + boiteAuLettre.getStock() + " lettre");
                }
            }
        } catch (InterruptedException e) {
            System.out.println("le consommateur à eu un probleme lorqsu'il a recupéré la lettre");
        }
    }
}
