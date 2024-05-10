import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * La classe Cards représente l'ensemble de 52 cartes.
 * Elle permet de créer, mélanger et distribuer des cartes aux joueurs.
 */
public class Cards {
    private Stack<Card> pile;

    /**
     * Constructeur de la classe Cards.
     * Initialise une pile de cartes vide.
     * Les cartes sont ajoutées à la pile lors de la création de l'objet.
     */
    public Cards() {
        pile = new Stack<>();
        createGobelinCards();
        createKnightCards();
        createUndeadCards();
        createDwarveCards();
        createDoppelgangerCards();
    }

    /**
     * Méthode pour créer les cartes de faction Gobelin et les ajouter à la pile.
     */
    private void createGobelinCards() {
        for (int i = 0; i < 5; i++) {
            pile.push(new Gobelin(0));
        }
        for (int i = 1; i < 10; i++) {
            pile.push(new Gobelin(i));
        }
    }

    /**
     * Méthode pour créer les cartes de faction Knight et les ajouter à la pile.
     */
    private void createKnightCards() {
        for (int i = 2; i < 10; i++) {
            pile.push(new Knight(i));
        }
    }

    /**
     * Méthode pour créer les cartes de faction Undead et les ajouter à la pile.
     */
    private void createUndeadCards() {
        for (int i = 0; i < 10; i++) {
            pile.push(new Undead(i));
        }
    }

    /**
     * Méthode pour créer les cartes de faction Dwarve et les ajouter à la pile.
     */
    private void createDwarveCards() {
        for (int i = 0; i < 10; i++) {
            pile.push(new Dwarve(i));
        }
    }

    /**
     * Méthode pour créer les cartes de faction Doppelganger et les ajouter à la pile.
     */
    private void createDoppelgangerCards() {
        for (int i = 0; i < 10; i++) {
            pile.push(new Doppelganger(i));
        }
    }

    /**
     * Méthode pour mélanger les cartes dans la pile.
     */
    public void shuffle() {
        Collections.shuffle(pile);
    }

    /**
     * Méthode pour obtenir une carte de la pile.
     * @return La carte retirée de la pile.
     * @throws IllegalStateException si la pile est vide.
     */
    public Card getCard() {
        if (pile.isEmpty()) {
            throw new IllegalStateException("La pile de cartes est vide.");
        }
        return pile.pop();
    }

    /**
     * Méthode pour obtenir toutes les cartes de la pile.
     * @return La liste de toutes les cartes dans la pile.
     */
    public List<Card> getCards() {
        return pile;
    }

    /**
     * Méthode pour obtenir une main de 13 cartes à partir de la pile.
     * @return La main de 13 cartes.
     * @throws IllegalStateException si la pile contient moins de 13 cartes.
     */
    public List<Card> getHandOf13Cards() {
        if (pile.size() < 13) {
            throw new IllegalStateException("La pile de cartes contient moins de 13 cartes.");
        }
        List<Card> hand = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            hand.add(getCard());
        }
        return hand;
    }
}
