package org.example.Modele;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

/**
 * @brief Cette classe représente la pioche de jeu. Elle hérite de la classe CardCollection.
 */
public class Cards extends CardCollection {

    /**
     * @brief Constructeur de la classe Cards.
     * Initialise une liste vide pour stocker les cartes.
     */
    public Cards() {
        super();
    }

    /**
     * @brief Constructeur de copie.
     * @param other La pioche à copier.
     */
    public Cards(Cards other) {
        this.cards = new ArrayList<>();
        for (Card card : other.cards) {
            this.cards.add(new Card(card));
        }
    }

    /**
     * @brief Clone la pioche actuelle.
     * @return Une nouvelle instance de Cards avec les mêmes cartes.
     */
    public Cards clone() {
        return new Cards(this);
    }

    /**
     * @brief Ajoute toutes les cartes à la pioche en créant les cartes pour chaque faction et en les melangeant.
     */
    public void addAllCards() {
        createGobelinCards();
        createKnightCards();
        createUndeadCards();
        createDwarveCards();
        createDoppelgangerCards();
        shuffle();
    }

    /**
     * @brief Prend une carte spécifique de la pioche et l'enlève de la pioche.
     * @param card La carte à prendre.
     * @return La carte prise, ou null si la carte n'est pas trouvée.
     */
    public Card getCard(Card card) {
        for (Card c : cards) {
            if (c.equals(card)) {
                cards.remove(c);
                return c;
            }
        }
        return null;
    }

    /**
     * @brief Crée les cartes de la faction Gobelins.
     */
    private void createGobelinCards() {
        for (int i = 0; i < 5; i++) {
            addCard(new Card(0, "Goblins"));
        }
        for (int i = 1; i < 10; i++) {
            addCard(new Card(i, "Goblins"));
        }
    }

    /**
     * @brief Crée les cartes de la faction Knight.
     */    private void createKnightCards() {
        for (int i = 2; i < 10; i++) {
            addCard(new Card(i, "Knight"));
        }
    }

    /**
     * @brief Crée les cartes de la faction Undead.
     */    private void createUndeadCards() {
        for (int i = 0; i < 10; i++) {
            addCard(new Card(i, "Undead"));
        }
    }

    /**
     * @brief Crée les cartes de la faction Dwarve.
     */    private void createDwarveCards() {
        for (int i = 0; i < 10; i++) {
            addCard(new Card(i, "Dwarves"));
        }
    }

    /**
     * @brief Crée les cartes de la faction Doppelgangers.
     */    private void createDoppelgangerCards() {
        for (int i = 0; i < 10; i++) {
            addCard(new Card(i, "Doppelganger"));
        }
    }


    /**
     * @brief Mélange les cartes dans la pioche.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * @brief Obtient une carte de la pioche.
     * @return La première carte de la pioche.
     * @throws IllegalStateException si la pioche est vide.
     */
    public Card getCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("La pile de cartes est vide.");
        }
        return removeCard(0);
    }

    /**
     * @brief Obtient toutes les cartes de la pioche.
     * @return La liste des cartes dans la pioche.
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * @brief Vérifie si la pioche est vide.
     * @return true si la pioche est vide, sinon false.
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * @brief Ajoute une carte à la pioche.
     * @param c La carte à ajouter.
     */
    public void setCard(Card c) {
        addCard(c);
    }

    /**
     * @brief Obtient une main de 13 cartes à partir de la pioche.
     * @return Une nouvelle instance de Hand contenant 13 cartes de la pioche.
     * @throws IllegalStateException si la pioche contient moins de 13 cartes.
     */
    public Hand getHandOf13Cards() {
        if (cards.size() < 13) {
            throw new IllegalStateException("La pile de cartes contient moins de 13 cartes.");
        }
        Hand hand = new Hand();
        for (int i = 0; i < 13; i++) {
            hand.addCard(getCard());
        }
        hand = ranger(hand);
        shuffle();
        return hand;
    }

    /**
     * @brief Trie la main par ordre de faction puis de valeur.
     * Cette méthode est utilisée pour des fins d'interface graphique, notamment
     * pour que lorsque l'adversaire va jouer, les cartes jouables soient encadrées en vert
     * et pour que l'affichage soit correct.
     * @param hand La main à trier.
     * @return Une nouvelle instance de Hand contenant les cartes triées.
     */
    public static Hand ranger(Hand hand) {
        List<Card> cards = hand.getAllCards();

        Collections.sort(cards, new Comparator<Card>() {
            @Override
            public int compare(Card c1, Card c2) {
                int factionComparison = c1.getFaction().compareTo(c2.getFaction());
                if (factionComparison != 0) {
                    return factionComparison;
                } else {
                    return Integer.compare(c1.getValeur(), c2.getValeur());
                }
            }
        });
        Hand sortedHand = new Hand();
        sortedHand.setHand(cards);
        return sortedHand;
    }

    /**
     * @brief Retire la première carte de la pioche.
     */
    public void removeCard() {
        cards.remove(0);
    }
}