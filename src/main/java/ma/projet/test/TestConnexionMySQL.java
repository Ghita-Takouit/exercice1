package ma.projet.test;

import ma.projet.util.HibernateUtil;
import org.hibernate.SessionFactory;

public class TestConnexionMySQL {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("TEST DE CONNEXION A MYSQL");
        System.out.println("========================================\n");

        try {
            System.out.println("Tentative de connexion a MySQL...");

            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

            if (sessionFactory != null) {
                System.out.println("SUCCES ! Connexion a MySQL etablie avec succes !");
                System.out.println("La base de donnees 'exercice1_db' est accessible.");
                System.out.println("Les tables seront creees automatiquement par Hibernate.");

                HibernateUtil.shutdown();

                System.out.println("\n========================================");
                System.out.println("Vous pouvez maintenant executer les tests !");
                System.out.println("Exemple : mvn exec:java -Dexec.mainClass=\"ma.projet.test.TestCommande\"");
                System.out.println("========================================");
            } else {
                System.err.println("ERREUR : La SessionFactory est null.");
                System.err.println("Verifiez la configuration dans application.properties");
            }

        } catch (Exception e) {
            System.err.println("\nERREUR DE CONNEXION A MYSQL !");
            System.err.println("Message d'erreur : " + e.getMessage());
            System.err.println("\nSolutions possibles :");
            System.err.println("1. Verifiez que MySQL est demarre (MAMP, XAMPP, etc.)");
            System.err.println("2. Verifiez que la base de donnees 'exercice1_db' existe");
            System.err.println("3. Executez ce script SQL dans phpMyAdmin :");
            System.err.println("   GRANT ALL PRIVILEGES ON exercice1_db.* TO 'root'@'localhost';");
            System.err.println("   FLUSH PRIVILEGES;");
            System.err.println("4. Si vous avez un mot de passe MySQL, modifiez application.properties");
            System.err.println("   et ajoutez : hibernate.connection.password=VOTRE_MOT_DE_PASSE");
            e.printStackTrace();
        }
    }
}
