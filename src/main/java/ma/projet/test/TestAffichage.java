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
import java.util.Date;
import java.util.List;

public class TestAffichage {

    public static void afficherDetailsCommande(Long commandeId) {
        CommandeService commandeService = new CommandeService();

        Commande commande = commandeService.findById(commandeId);

        if (commande == null) {
            System.out.println("Commande introuvable !");
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        String dateFormatee = dateFormat.format(commande.getDateCommande());

        System.out.println("Commande : " + commande.getId() + "\t\tDate : " + dateFormatee);
        System.out.println("Liste des produits :");
        System.out.println("Référence\tPrix\tQuantité");

        List<LigneCommande> lignesCommande = commande.getLignesCommande();

        if (lignesCommande != null && !lignesCommande.isEmpty()) {
            for (LigneCommande ligne : lignesCommande) {
                Produit produit = ligne.getProduit();
                System.out.printf("%s\t\t%.0f DH\t%d%n",
                    produit.getReference(),
                    produit.getPrix(),
                    ligne.getQuantite());
            }
        } else {
            System.out.println("Aucun produit dans cette commande.");
        }

        System.out.println();
    }

    public static void main(String[] args) {
        ProduitService produitService = new ProduitService();
        CategorieService categorieService = new CategorieService();
        CommandeService commandeService = new CommandeService();
        LigneCommandeService ligneCommandeService = new LigneCommandeService();

        try {
            Categorie categorie = new Categorie("ELEC", "Électronique");
            categorieService.create(categorie);

            Produit p1 = new Produit("Ordinateur", "HP", 120, "ES12", new Date());
            p1.setCategorie(categorie);
            produitService.create(p1);

            Produit p2 = new Produit("Imprimante", "Canon", 100, "ZR85", new Date());
            p2.setCategorie(categorie);
            produitService.create(p2);

            Produit p3 = new Produit("Tablette", "Samsung", 200, "EE85", new Date());
            p3.setCategorie(categorie);
            produitService.create(p3);

            Commande commande = new Commande(new Date());
            commandeService.create(commande);

            LigneCommande lc1 = new LigneCommande(commande, p1, 7);
            ligneCommandeService.create(lc1);

            LigneCommande lc2 = new LigneCommande(commande, p2, 14);
            ligneCommandeService.create(lc2);

            LigneCommande lc3 = new LigneCommande(commande, p3, 5);
            ligneCommandeService.create(lc3);

            System.out.println("\n=== Affichage des details de la commande ===\n");
            afficherDetailsCommande(commande.getId());

            commandeService.afficherDetailsCommande(commande.getId());

            System.out.println("=== Produits de la categorie Electronique ===");
            List<Produit> produitsCategorie = produitService.findByCategorie(categorie);
            for (Produit p : produitsCategorie) {
                System.out.println(p);
            }

            System.out.println("\n=== Produits commandes entre deux dates ===");
            Date dateDebut = new Date(System.currentTimeMillis() - 86400000);
            Date dateFin = new Date(System.currentTimeMillis() + 86400000);
            List<Produit> produitsCommandes = produitService.findProduitsCommandesEntreDates(dateDebut, dateFin);
            for (Produit p : produitsCommandes) {
                System.out.println(p);
            }

            System.out.println("\n=== Produits de la commande " + commande.getId() + " ===");
            List<Produit> produitsCommande = produitService.findProduitsParCommande(commande.getId());
            for (Produit p : produitsCommande) {
                System.out.println(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
