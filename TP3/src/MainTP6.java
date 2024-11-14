public class MainTP6 {
    public static void main(String[] args) {
        BAL BoiteAuLettre = new BAL();

        Producteur producteur = new Producteur(BoiteAuLettre);
        Consommateur consommateur = new Consommateur(BoiteAuLettre);

        producteur.start();
        consommateur.start();
    }
}
