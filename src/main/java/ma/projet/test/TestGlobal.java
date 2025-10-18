package ma.projet.test;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommande;
import ma.projet.classes.Produit;
import ma.projet.service.CategorieService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.service.ProduitService;
import ma.projet.util.HibernateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestGlobal {

    public static void main(String[] args) {
        ProduitService produitService = new ProduitService();
        CategorieService categorieService = new CategorieService();
        CommandeService commandeService = new CommandeService();
        LigneCommandeService ligneCommandeService = new LigneCommandeService();

        try {
            System.out.println("========================================");
            System.out.println("   TEST GLOBAL DE TOUTES LES FONCTIONNALITES   ");
            System.out.println("========================================\n");

            System.out.println("ETAPE 1 : Creation des categories");
            System.out.println("-------------------------------------");
            Categorie catElectronique = new Categorie("ELEC", "Électronique");
            categorieService.create(catElectronique);

            Categorie catInformatique = new Categorie("INFO", "Informatique");
            categorieService.create(catInformatique);

            System.out.println("2 categories creees\n");

            System.out.println("ETAPE 2 : Creation des produits");
            System.out.println("-------------------------------------");
            Produit[] produits = {
                new Produit("Ordinateur Portable", "HP", 120, "ES12", new Date()),
                new Produit("Imprimante", "Canon", 100, "ZR85", new Date()),
                new Produit("Tablette", "Samsung", 200, "EE85", new Date()),
                new Produit("Smartphone", "Apple", 850, "IP14", new Date()),
                new Produit("Souris", "Logitech", 45, "LG01", new Date()),
                new Produit("Clavier", "Razer", 150, "RZ02", new Date())
            };

            for (int i = 0; i < produits.length; i++) {
                produits[i].setCategorie(i < 3 ? catElectronique : catInformatique);
                produitService.create(produits[i]);
                System.out.println("  " + produits[i].getNom() + " - " + produits[i].getPrix() + " DH");
            }
            System.out.println();

            System.out.println("ETAPE 3 : Produits avec prix > 100 DH (Requete Nommee)");
            System.out.println("-------------------------------------");
            List<Produit> produitsChers = produitService.findProduitsSuperieurA100();
            System.out.println("Nombre de produits : " + produitsChers.size());
            for (Produit p : produitsChers) {
                System.out.printf("  %s : %.0f DH (Ref: %s)%n",
                    p.getNom(), p.getPrix(), p.getReference());
            }
            System.out.println();

            System.out.println("ETAPE 4 : Produits par categorie");
            System.out.println("-------------------------------------");
            List<Produit> produitsElec = produitService.findByCategorie(catElectronique);
            System.out.println("Categorie Electronique : " + produitsElec.size() + " produits");
            for (Produit p : produitsElec) {
                System.out.println("  " + p.getNom());
            }
            System.out.println();

            System.out.println("ETAPE 5 : Creation de commandes");
            System.out.println("-------------------------------------");
            Calendar cal = Calendar.getInstance();

            cal.set(2024, Calendar.MARCH, 14);
            Commande cmd1 = new Commande(cal.getTime());
            commandeService.create(cmd1);
            ligneCommandeService.create(new LigneCommande(cmd1, produits[0], 7));
            ligneCommandeService.create(new LigneCommande(cmd1, produits[1], 14));
            ligneCommandeService.create(new LigneCommande(cmd1, produits[2], 5));
            System.out.println("Commande 1 creee (3 produits)");

            cal.set(2024, Calendar.JUNE, 20);
            Commande cmd2 = new Commande(cal.getTime());
            commandeService.create(cmd2);
            ligneCommandeService.create(new LigneCommande(cmd2, produits[3], 2));
            ligneCommandeService.create(new LigneCommande(cmd2, produits[5], 5));
            System.out.println("Commande 2 creee (2 produits)\n");

            System.out.println("ETAPE 6 : Affichage d'une commande");
            System.out.println("-------------------------------------");
            commandeService.afficherDetailsCommande(cmd1.getId());

            System.out.println("ETAPE 7 : Produits commandes entre deux dates");
            System.out.println("-------------------------------------");
            cal.set(2024, Calendar.JANUARY, 1);
            Date debut = cal.getTime();
            cal.set(2024, Calendar.DECEMBER, 31);
            Date fin = cal.getTime();

            List<Produit> produitsCommandesEntre = produitService.findProduitsCommandesEntreDates(debut, fin);
            System.out.println("Periode : 01/01/2024 - 31/12/2024");
            System.out.println("Nombre de produits : " + produitsCommandesEntre.size());
            for (Produit p : produitsCommandesEntre) {
                System.out.println("  " + p.getNom() + " (Ref: " + p.getReference() + ")");
            }
            System.out.println();

            System.out.println("ETAPE 8 : Produits d'une commande specifique");
            System.out.println("-------------------------------------");
            List<Produit> produitsCmd = produitService.findProduitsParCommande(cmd2.getId());
            System.out.println("Commande #" + cmd2.getId() + " contient " + produitsCmd.size() + " produits :");
            for (Produit p : produitsCmd) {
                System.out.println("  " + p.getNom() + " - " + p.getPrix() + " DH");
            }
            System.out.println();

            System.out.println("========================================");
            System.out.println("            RESUME DES TESTS                    ");
            System.out.println("========================================");
            System.out.println("Categories creees    : " + categorieService.findAll().size());
            System.out.println("Produits crees       : " + produitService.findAll().size());
            System.out.println("Commandes creees     : " + commandeService.findAll().size());
            System.out.println("Produits > 100 DH    : " + produitsChers.size());
            System.out.println("Toutes les fonctionnalites testees");
            System.out.println("========================================");

        } catch (Exception e) {
            System.err.println("\nERREUR : " + e.getMessage());
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
