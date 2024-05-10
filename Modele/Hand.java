import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cards;


    /**
     * Constructeur de la classe Hand.
     * Initialise une liste vide pour stocker les cartes.
     */
    public Hand() {
        cards = new ArrayList<>();
    }

    /**
     * Ajoute une carte à la main.
     * @param card La carte à ajouter à la main.
     */
    // Ajouter une carte à la main
    public void addCard(Card card) {
        cards.add(card);
    }

    /*
     * Retire une carte de la main.
     * @param card La carte à retirer de la main.
     */
    // Retirer une carte de la main
    public Card removeCard(int i) {
        return cards.remove(i);
    }
    public void removeCard(Card card) {
        cards.remove(card);
    }

    /*
     * verifie si la carte est dans la main
     * @param card La carte à verifier s'il est dans la main.
     */
    public boolean contains(Card card) {
        return cards.contains(card);
    }

    /**
     * Récupère toutes les cartes de la main.
     * @return La liste des cartes dans la main.
     */
    // Obtenir toutes les cartes de la main
    public List<Card> getAllCards() {
        return cards;
    }

    /**
     * Vide la main en supprimant toutes les cartes.
     */
    // Vider la main
    public void clear() {
        cards.clear();
    }

    /**
     * Renvoie le nombre de cartes dans la main.
     * @return Le nombre de cartes dans la main.
     */
    // Obtenir le nombre de cartes dans la main
    public int size() {
        return cards.size();
    }
}