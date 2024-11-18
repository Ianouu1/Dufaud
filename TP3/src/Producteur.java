public class Producteur extends Thread {
    private BAL boiteAuLettre;
    private String[] lettresADeposer = "ABCDEFGHIJKLMNOPQRSTUVWXYZ*".split("");

    public Producteur(BAL bal) {
        this.boiteAuLettre = bal;
    }

    public void run() {
        try {
            for (String lettreADeposer: lettresADeposer) {
                Thread.sleep(1000); // délai ajustable a souhait afin de voir les différents comportements de la BAL
                boolean isDelivered = boiteAuLettre.deposer(lettreADeposer);
                    if (isDelivered) {
                        System.out.println("Producteur a déposé la lettre " + lettreADeposer);
                    } else {
                        System.out.println("BAL pleine");
                    }
            }
            System.out.println("Producteur a fini de tout déposer");
        } catch (InterruptedException e) {
            System.out.println("Producteur interrompu");
        }
    }
}
