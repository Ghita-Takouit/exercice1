package ma.projet.test;

import ma.projet.classes.Categorie;
import ma.projet.service.CategorieService;
import ma.projet.util.HibernateUtil;

import java.util.List;

public class TestCategorie {

    public static void main(String[] args) {
        CategorieService categorieService = new CategorieService();

        try {
            System.out.println("========================================");
            System.out.println("TEST DES OPERATIONS CRUD SUR CATEGORIE");
            System.out.println("========================================\n");

            System.out.println("--- Test 1 : Creation de categories ---");
            Categorie cat1 = new Categorie("INFO", "Informatique");
            categorieService.create(cat1);
            System.out.println("Categorie creee : " + cat1);

            Categorie cat2 = new Categorie("ELEC", "Électronique");
            categorieService.create(cat2);
            System.out.println("Categorie creee : " + cat2);

            Categorie cat3 = new Categorie("MOBIL", "Mobilier");
            categorieService.create(cat3);
            System.out.println("Categorie creee : " + cat3);

            System.out.println("\n--- Test 2 : Affichage de toutes les categories ---");
            List<Categorie> toutesLesCategories = categorieService.findAll();
            System.out.println("Nombre de categories : " + toutesLesCategories.size());
            for (Categorie c : toutesLesCategories) {
                System.out.println("  - " + c);
            }

            System.out.println("\n--- Test 3 : Recherche par ID ---");
            Categorie categorieTrouvee = categorieService.findById(cat1.getId());
            if (categorieTrouvee != null) {
                System.out.println("Categorie trouvee : " + categorieTrouvee);
            } else {
                System.out.println("Categorie non trouvee");
            }

            System.out.println("\n--- Test 4 : Modification d'une categorie ---");
            System.out.println("Avant modification : " + cat2);
            cat2.setLibelle("Électronique et Électroménager");
            categorieService.update(cat2);
            Categorie categorieModifiee = categorieService.findById(cat2.getId());
            System.out.println("Apres modification : " + categorieModifiee);

            System.out.println("\n--- Test 5 : Suppression d'une categorie ---");
            System.out.println("Suppression de la categorie : " + cat3.getLibelle());
            categorieService.delete(cat3);
            List<Categorie> categoriesApresSupression = categorieService.findAll();
            System.out.println("Nombre de categories apres suppression : " + categoriesApresSupression.size());

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
