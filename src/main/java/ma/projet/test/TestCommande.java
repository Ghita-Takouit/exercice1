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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TestCommande {

    public static void main(String[] args) {
        ProduitService produitService = new ProduitService();
        CategorieService categorieService = new CategorieService();
        CommandeService commandeService = new CommandeService();
        LigneCommandeService ligneCommandeService = new LigneCommandeService();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {

            Categorie categorie = new Categorie("ELEC", "Électronique");
            categorieService.create(categorie);

            Produit p1 = new Produit("Ordinateur Portable", "HP", 120, "ES12", new Date());
            p1.setCategorie(categorie);
            produitService.create(p1);

            Produit p2 = new Produit("Imprimante Laser", "Canon", 100, "ZR85", new Date());
            p2.setCategorie(categorie);
            produitService.create(p2);

            Produit p3 = new Produit("Tablette Android", "Samsung", 200, "EE85", new Date());
            p3.setCategorie(categorie);
            produitService.create(p3);

            Produit p4 = new Produit("Smartphone", "iPhone", 850, "IP14", new Date());
            p4.setCategorie(categorie);
            produitService.create(p4);

            Produit p5 = new Produit("Écran LED", "LG", 95, "LG24", new Date());
            p5.setCategorie(categorie);
            produitService.create(p5);

            System.out.println("5 produits crees\n");

            System.out.println("--- Test 1 : Creation de commandes ---");

            Calendar cal = Calendar.getInstance();

            cal.set(2013, Calendar.MARCH, 14);
            Commande cmd1 = new Commande(cal.getTime());
            commandeService.create(cmd1);
            ligneCommandeService.create(new LigneCommande(cmd1, p1, 7));
            ligneCommandeService.create(new LigneCommande(cmd1, p2, 14));
            ligneCommandeService.create(new LigneCommande(cmd1, p3, 5));
            System.out.println("Commande 1 creee : " + dateFormat.format(cmd1.getDateCommande()));

            cal.set(2024, Calendar.JANUARY, 20);
            Commande cmd2 = new Commande(cal.getTime());
            commandeService.create(cmd2);
            ligneCommandeService.create(new LigneCommande(cmd2, p4, 3));
            ligneCommandeService.create(new LigneCommande(cmd2, p5, 10));
            System.out.println("Commande 2 creee : " + dateFormat.format(cmd2.getDateCommande()));

            cal.set(2024, Calendar.JUNE, 15);
            Commande cmd3 = new Commande(cal.getTime());
            commandeService.create(cmd3);
            ligneCommandeService.create(new LigneCommande(cmd3, p1, 2));
            ligneCommandeService.create(new LigneCommande(cmd3, p3, 8));
            ligneCommandeService.create(new LigneCommande(cmd3, p4, 1));
            System.out.println("Commande 3 creee : " + dateFormat.format(cmd3.getDateCommande()));

            System.out.println("\n--- Test 2 : Affichage des details de la commande ---\n");
            commandeService.afficherDetailsCommande(cmd1.getId());

            System.out.println("--- Test 3 : Produits commandes entre deux dates ---");
            cal.set(2024, Calendar.JANUARY, 1);
            Date dateDebut = cal.getTime();
            cal.set(2024, Calendar.DECEMBER, 31);
            Date dateFin = cal.getTime();

            System.out.println("Periode : " + dateFormat.format(dateDebut) + " - " + dateFormat.format(dateFin));
            List<Produit> produitsEntre2024 = produitService.findProduitsCommandesEntreDates(dateDebut, dateFin);
            System.out.println("Nombre de produits commandes : " + produitsEntre2024.size());
            for (Produit p : produitsEntre2024) {
                System.out.println("  - " + p.getNom() + " (Ref: " + p.getReference() + ")");
            }

            System.out.println("\n--- Test 4 : Produits de la commande " + cmd3.getId() + " ---");
            List<Produit> produitsCmd3 = produitService.findProduitsParCommande(cmd3.getId());
            System.out.println("Nombre de produits dans cette commande : " + produitsCmd3.size());
            for (Produit p : produitsCmd3) {
                System.out.println("  - " + p.getNom() + " - " + p.getPrix() + " DH");
            }

            System.out.println("\n--- Test 5 : Liste de toutes les commandes ---");
            List<Commande> toutesCommandes = commandeService.findAll();
            System.out.println("Nombre total de commandes : " + toutesCommandes.size());
            for (Commande cmd : toutesCommandes) {
                System.out.println("  - Commande #" + cmd.getId() + " : " + dateFormat.format(cmd.getDateCommande()));
            }

        } catch (Exception e) {
            System.err.println("Erreur lors des tests : " + e.getMessage());
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
