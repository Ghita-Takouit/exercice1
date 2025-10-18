package ma.projet.test;

import ma.projet.classes.Produit;
import ma.projet.classes.Categorie;
import ma.projet.service.ProduitService;
import ma.projet.service.CategorieService;
import ma.projet.util.HibernateUtil;

import java.util.Date;
import java.util.List;

public class TestProduit {

    public static void main(String[] args) {
        ProduitService produitService = new ProduitService();
        CategorieService categorieService = new CategorieService();

        try {
            System.out.println("========================================");
            System.out.println("TEST DES OPERATIONS CRUD SUR PRODUIT");
            System.out.println("========================================\n");

            Categorie categorie = new Categorie("INFO", "Informatique");
            categorieService.create(categorie);
            System.out.println("Categorie creee : " + categorie);

            System.out.println("\n--- Test 1 : Creation de produits ---");
            Produit p1 = new Produit("MacBook Pro", "Apple", 15000, "MB001", new Date());
            p1.setCategorie(categorie);
            produitService.create(p1);
            System.out.println("Produit cree : " + p1);

            Produit p2 = new Produit("Dell XPS", "Dell", 8500, "DX002", new Date());
            p2.setCategorie(categorie);
            produitService.create(p2);
            System.out.println("Produit cree : " + p2);

            Produit p3 = new Produit("Souris Logitech", "Logitech", 85, "LG003", new Date());
            p3.setCategorie(categorie);
            produitService.create(p3);
            System.out.println("Produit cree : " + p3);

            Produit p4 = new Produit("Clavier Mécanique", "Razer", 150, "RZ004", new Date());
            p4.setCategorie(categorie);
            produitService.create(p4);
            System.out.println("Produit cree : " + p4);

            System.out.println("\n--- Test 2 : Affichage de tous les produits ---");
            List<Produit> tousLesProduits = produitService.findAll();
            System.out.println("Nombre de produits : " + tousLesProduits.size());
            for (Produit p : tousLesProduits) {
                System.out.println("  - " + p);
            }

            System.out.println("\n--- Test 3 : Recherche par ID ---");
            Produit produitTrouve = produitService.findById(p1.getId());
            if (produitTrouve != null) {
                System.out.println("Produit trouve : " + produitTrouve);
            } else {
                System.out.println("Produit non trouve");
            }

            System.out.println("\n--- Test 4 : Modification d'un produit ---");
            System.out.println("Avant modification : " + p1);
            p1.setPrix(14500);
            p1.setMarque("Apple Inc.");
            produitService.update(p1);
            Produit produitModifie = produitService.findById(p1.getId());
            System.out.println("Apres modification : " + produitModifie);

            System.out.println("\n--- Test 5 : Produits avec prix > 100 DH (Requete Nommee) ---");
            List<Produit> produitsChers = produitService.findProduitsSuperieurA100();
            System.out.println("Nombre de produits > 100 DH : " + produitsChers.size());
            for (Produit p : produitsChers) {
                System.out.printf("  - %s : %.2f DH%n", p.getNom(), p.getPrix());
            }

            System.out.println("\n--- Test 6 : Produits par categorie ---");
            List<Produit> produitsCategorie = produitService.findByCategorie(categorie);
            System.out.println("Produits de la categorie '" + categorie.getLibelle() + "' : " + produitsCategorie.size());
            for (Produit p : produitsCategorie) {
                System.out.println("  - " + p.getNom());
            }

            System.out.println("\n--- Test 7 : Suppression d'un produit ---");
            System.out.println("Suppression du produit : " + p3.getNom());
            produitService.delete(p3);
            List<Produit> produitsApresSupression = produitService.findAll();
            System.out.println("Nombre de produits apres suppression : " + produitsApresSupression.size());

            System.out.println("\n========================================");
            System.out.println("TOUS LES TESTS SONT TERMINES !");
            System.out.println("========================================");

        } catch (Exception e) {
            System.err.println("Erreur lors des tests : " + e.getMessage());
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
