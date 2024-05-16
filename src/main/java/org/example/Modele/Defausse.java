package org.example.Modele;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe représente la pile de cartes défaussées dans le jeu.
 * Elle permet d'ajouter des cartes à la défausse, de récupérer la liste des cartes dans la défausse,
 * de vérifier si la défausse est vide et de le vider.
 */
public class Defausse {
    private List<Card> cartes;

    /**
     * Constructeur de la classe Defausse.
     * Initialise la liste des cartes dans la défausse.
     */
    public Defausse() {
        this.cartes = new ArrayList<>();
    }

    /**
     * Ajoute une carte à la défausse.
     * @param carte La carte à ajouter à la défausse.
     */
    public void ajouterCarte(Card carte) {
        cartes.add(carte);
    }

    /**
     * Renvoie la liste des cartes dans la défausse.
     * @return La liste des cartes dans la défausse.
     */
    public List<Card> getCartes() {
        return cartes;
    }

    public Card getCarte(int index) {
        return cartes.get(index);
    }

    public int size(){
        return cartes.size();
    }
    /**
     * Vérifie si la défausse est vide.
     * @return true si la défausse est vide, false sinon.
     */
    public boolean estVide() {
        return cartes.isEmpty();
    }

    /**
     * Vide la défausse en supprimant toutes les cartes.
     */
    public void vider() {
        cartes.clear();
    }
}
