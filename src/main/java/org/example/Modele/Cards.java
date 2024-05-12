package org.example.Modele;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Cards {
    private Stack<Card> pile;

    // Constructeur
    public Cards() {
        pile = new Stack<>();
        createGobelinCards();
        createKnightCards();
        createUndeadCards();
        createDwarveCards();
        createDoppelgangerCards();
    }

    // Méthode pour créer les cartes Gobelin
    private void createGobelinCards() {
        for (int i = 0; i < 5; i++) {
            pile.push(new Gobelin(0));
        }
        for (int i = 1; i < 10; i++) {
            pile.push(new Gobelin(i));
        }
    }

    // Méthode pour créer les cartes Knight
    private void createKnightCards() {
        for (int i = 2; i < 10; i++) {
            pile.push(new Knight(i));
        }
    }

    // Méthode pour créer les cartes Undead
    private void createUndeadCards() {
        for (int i = 0; i < 10; i++) {
            pile.push(new Undead(i));
        }
    }

    // Méthode pour créer les cartes Dwarve
    private void createDwarveCards() {
        for (int i = 0; i < 10; i++) {
            pile.push(new Dwarve(i));
        }
    }

    // Méthode pour créer les cartes Doppelganger
    private void createDoppelgangerCards() {
        for (int i = 0; i < 10; i++) {
            pile.push(new Doppelganger(i));
        }
    }
    public Card max_valeur() {
        if (pile.isEmpty()) {
            throw new IllegalStateException("La pile de cartes est vide.");
        }

        Card maxCard = pile.firstElement();
        for (Card card : pile) {
            if (card.getValeur() > maxCard.getValeur()) {
                maxCard = card;
            }
        }

        return maxCard;
    }
    public Cards getCardsOfSameFactionAs(Card opponentCard) {
        if (pile.isEmpty()) {
            throw new IllegalStateException("La pile de cartes est vide.");
        }

        Cards cardsOfSameFaction = new Cards();
        Class<?> opponentCardClass = opponentCard.getClass();

        for (Card card : pile) {
            if (card.getClass() == opponentCardClass) {
                cardsOfSameFaction.setCard(card);
            }
        }

        return cardsOfSameFaction;
    }
    // Méthode pour obtenir la carte la plus grande qui est plus petite que la carte jouée par l'adversaire
    public Card getHighestCardSmallerThan(Card opponentCard) {
        if (pile.isEmpty()) {
            throw new IllegalStateException("La pile de cartes est vide.");
        }

        Card highestSmallerCard = null;
        int opponentValue = opponentCard.getValeur();

        for (Card card : pile) {
            int cardValue = card.getValeur();
            if (cardValue < opponentValue && (highestSmallerCard == null || cardValue > highestSmallerCard.getValeur())) {
                highestSmallerCard = card;
            }
        }

        return highestSmallerCard;
    }

    public Card min_valeur() {
        if (pile.isEmpty()) {
            throw new IllegalStateException("La pile de cartes est vide.");
        }

        Card minCard = pile.firstElement();
        for (Card card : pile) {
            // Si la valeur de la carte actuelle est inférieure à la valeur de la carte minimale
            // ou si la valeur est égale mais la faction a un score plus bas, mettre à jour la carte minimale
            if (card.getValeur() < minCard.getValeur() ||
                    (card.getValeur() == minCard.getValeur() && getFactionScore(card) < getFactionScore(minCard))) {
                minCard = card;
            }
        }

        return minCard;
    }

    // Méthode pour vérifier si la pile contient des cartes Knight
    public boolean containsKnight() {
        for (Card card : pile) {
            if (card instanceof Knight) {
                return true;
            }
        }
        return false;
    }
    public int getFactionScore(Card card) {
        switch (card.getFaction()) {
            case "Undead":
                return 5;
            case "Doppelganger":
                return 4;
            case "Chevalier":
                return 3;
            case "Nains":
                return 2;
            case "Goblins":
                return 1;
            default:
                return 0;
        }
    }
    // Méthode pour mélanger les cartes dans la pile
    public void shuffle() {
        Collections.shuffle(pile);
    }

    public Card getLowestCardWithFactionScore() {
        if (pile.isEmpty()) {
            throw new IllegalStateException("La pile de cartes est vide.");
        }

        Card lowestCard = null;
        int lowestValue = Integer.MAX_VALUE;
        int lowestFactionScore = Integer.MAX_VALUE;

        for (Card card : pile) {
            int cardValue = card.getValeur();
            int cardFactionScore = getFactionScore(card);

            if (cardValue < lowestValue) {
                lowestCard = card;
                lowestValue = cardValue;
                lowestFactionScore = cardFactionScore;
            } else if (cardValue == lowestValue && cardFactionScore < lowestFactionScore) {
                lowestCard = card;
                lowestFactionScore = cardFactionScore;
            }
        }

        return lowestCard;
    }
    // Méthode pour obtenir une carte de la pile
    public Card getCard() {
        if (pile.isEmpty()) {
            throw new IllegalStateException("La pile de cartes est vide.");
        }
        return pile.pop();
    }

    // Méthode pour obtenir toutes les cartes de la pile
    public List<Card> getCards() {
        return pile;
    }

    public boolean isEmpty(){
        return pile.isEmpty();
    }
    public void setCard(Card c){
        pile.push(c);
    }

    // Méthode pour obtenir une main de 13 cartes à partir de la pile
    public Hand getHandOf13Cards() {
        if (pile.size() < 13) {
            throw new IllegalStateException("La pile de cartes contient moins de 13 cartes.");
        }
        Hand hand = new Hand();
        for (int i = 0; i < 13; i++) {
            hand.addCard(getCard());
        }
        return hand;
    }
}
