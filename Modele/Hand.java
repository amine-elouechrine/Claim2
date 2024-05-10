import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cards;

    // Constructeur
    public Hand() {
        cards = new ArrayList<>();
    }

    // Ajouter une carte à la main
    public void addCard(Card card) {
        cards.add(card);
    }

    // Retirer une carte de la main
    public void removeCard(Card card) {
        cards.remove(card);
    }

    // Vérifier si une carte est dans la main
    public boolean contains(Card card) {
        return cards.contains(card);
    }

    // Obtenir toutes les cartes de la main
    public List<Card> getAllCards() {
        return cards;
    }

    // Vider la main
    public void clear() {
        cards.clear();
    }

    // Obtenir le nombre de cartes dans la main
    public int size() {
        return cards.size();
    }
}
