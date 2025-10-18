package ma.projet.classes;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "produits")
@NamedQueries({
    @NamedQuery(
        name = "Produit.findByPrixSuperieur",
        query = "SELECT p FROM Produit p WHERE p.prix > :prix"
    )
})
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String marque;

    @Column(nullable = false)
    private double prix;

    @Column(nullable = false)
    private String reference;

    @Temporal(TemporalType.DATE)
    private Date dateAchat;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    public Produit() {
    }

    public Produit(String nom, String marque, double prix, String reference, Date dateAchat) {
        this.nom = nom;
        this.marque = marque;
        this.prix = prix;
        this.reference = reference;
        this.dateAchat = dateAchat;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Date getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(Date dateAchat) {
        this.dateAchat = dateAchat;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", marque='" + marque + '\'' +
                ", prix=" + prix +
                ", reference='" + reference + '\'' +
                ", dateAchat=" + dateAchat +
                ", categorie=" + (categorie != null ? categorie.getId() : null) +
                '}';
    }
}
