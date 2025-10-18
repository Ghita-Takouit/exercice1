package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommande;
import ma.projet.classes.Produit;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.List;

public class CommandeService implements IDao<Commande> {

    @Override
    public boolean create(Commande commande) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(commande);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Commande commande) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(commande);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Commande commande) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(session.contains(commande) ? commande : session.merge(commande));
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Commande findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Commande commande = session.createQuery(
                "SELECT c FROM Commande c LEFT JOIN FETCH c.lignesCommande WHERE c.id = :id",
                Commande.class)
                .setParameter("id", id)
                .uniqueResult();
            return commande;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Commande> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Commande", Commande.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void afficherDetailsCommande(Long commandeId) {
        Commande commande = findById(commandeId);

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
}
