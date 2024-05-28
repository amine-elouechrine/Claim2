package org.example.Modele;
import java.util.List;
import java.util.ArrayList;

/**
 * Cette classe représente la pile de cartes défaussées dans le jeu.
 * Elle permet d'ajouter des cartes à la défausse, de récupérer la liste des cartes dans la défausse,
 * de vérifier si la défausse est vide et de le vider.
 */
public class Defausse extends CardCollection {

    /**
     * Constructeur de la classe Defausse.
     * Initialise la liste des cartes dans la défausse.
     */
    public Defausse() {
        super();
    }

    // Constructeur de copie
    public Defausse(Defausse other) {
        this.cards = new ArrayList<>();
        for (Card card : other.cards) {
            this.cards.add(new Card(card));
        }
    }

    public Defausse clone() {
        return new Defausse(this);
    }

    /**
     * Ajoute une carte à la défausse.
     * @param carte La carte à ajouter à la défausse.
     */
    public void ajouterCarte(Card carte) {
        addCard(carte);
    }

    /**
     * Renvoie la liste des cartes dans la défausse.
     * @return La liste des cartes dans la défausse.
     */
    public List<Card> getCartes() {
        return cards;
    }

    /**
     * Vérifie si la défausse est vide.
     * @return true si la défausse est vide, false sinon.
     */
    public boolean estVide() {
        return cards.isEmpty();
    }

    /**
     * Vide la défausse en supprimant toutes les cartes.
     */
    public void vider() {
        cards.clear();
    }
}
