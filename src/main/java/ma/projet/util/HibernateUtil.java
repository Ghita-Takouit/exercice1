package ma.projet.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import ma.projet.classes.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties properties = loadProperties();

                configuration.setProperty(Environment.DRIVER, properties.getProperty("hibernate.connection.driver_class"));
                configuration.setProperty(Environment.URL, properties.getProperty("hibernate.connection.url"));
                configuration.setProperty(Environment.USER, properties.getProperty("hibernate.connection.username"));
                configuration.setProperty(Environment.PASS, properties.getProperty("hibernate.connection.password"));

                configuration.setProperty(Environment.DIALECT, properties.getProperty("hibernate.dialect"));
                configuration.setProperty(Environment.SHOW_SQL, properties.getProperty("hibernate.show_sql"));
                configuration.setProperty(Environment.FORMAT_SQL, properties.getProperty("hibernate.format_sql"));
                configuration.setProperty(Environment.HBM2DDL_AUTO, properties.getProperty("hibernate.hbm2ddl.auto"));

                configuration.setProperty(Environment.POOL_SIZE, properties.getProperty("hibernate.connection.pool_size"));

                configuration.addAnnotatedClass(Produit.class);
                configuration.addAnnotatedClass(Categorie.class);
                configuration.addAnnotatedClass(Commande.class);
                configuration.addAnnotatedClass(LigneCommande.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try {
            FileInputStream input = new FileInputStream("src/main/resources/application.properties");
            properties.load(input);
            input.close();
        } catch (IOException e) {
            System.err.println("Impossible de charger application.properties, utilisation des valeurs par défaut");
            properties.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/exercice1_db");
            properties.setProperty("hibernate.connection.username", "root");
            properties.setProperty("hibernate.connection.password", "");
            properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            properties.setProperty("hibernate.show_sql", "true");
            properties.setProperty("hibernate.format_sql", "true");
            properties.setProperty("hibernate.hbm2ddl.auto", "update");
            properties.setProperty("hibernate.connection.pool_size", "10");
        }
        return properties;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
