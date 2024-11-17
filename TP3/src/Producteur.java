public class Producteur extends Thread {
    private BAL boiteAuLettre;
    private String[] lettresADeposer = "ABCDEFGHIJKLMNOPQRSTUVWXYZ*".split("");

    public Producteur(BAL bal) {
        this.boiteAuLettre = bal;
    }

    public void run() {
        try {
            for (String lettreADeposer: lettresADeposer) {
                boolean isDelivered = false;
                while (!isDelivered) {
                    Thread.sleep(200);
                    isDelivered = boiteAuLettre.deposer(lettreADeposer);
                }
                System.out.println("Producteur a déposé la lettre " + lettreADeposer);
            }
            System.out.println("Producteur a fini de tout déposer");
        } catch (InterruptedException e) {
            System.out.println("Producteur interrompu");
        }
    }
}
