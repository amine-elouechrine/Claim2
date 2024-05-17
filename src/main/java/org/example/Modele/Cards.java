package org.example.Modele;
import java.util.Collections;
import java.util.List;

public class Cards extends CardCollection{

    // Constructeur
    public Cards() {
        super();
    }



    // Méthode pour créer les cartes Gobelin
    public void addAllCards(){
        createGobelinCards();
        createKnightCards();
        createUndeadCards();
        createDwarveCards();
        createDoppelgangerCards();
        shuffle();
    }

    private void createGobelinCards() {
        for (int i = 0; i < 5; i++) {
            addCard(new Card(0, "Goblins"));
        }
        for (int i = 1; i < 10; i++) {
            addCard(new Card(i , "Goblins"));
        }
    }

    // Méthode pour créer les cartes Knight
    private void createKnightCards() {
        for (int i = 2; i < 10; i++) {
            addCard(new Card(i , "Knight"));
        }
    }

    // Méthode pour créer les cartes Undead
    private void createUndeadCards() {
        for (int i = 0; i < 10; i++) {
            addCard(new Card(i, "Undead"));
        }
    }

    // Méthode pour créer les cartes Dwarve
    private void createDwarveCards() {
        for (int i = 0; i < 10; i++) {
            addCard(new Card(i, "Dwarves"));
        }
    }

    // Méthode pour créer les cartes Doppelganger
    private void createDoppelgangerCards() {
        for (int i = 0; i < 10; i++) {
            addCard(new Card(i , "Doppelganger"));
        }
    }


    public Card max_valeur() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("La pile de cartes est vide.");
        }

        Card maxCard = cards.get(0);
        for (Card card : cards) {
            if (card.getValeur() > maxCard.getValeur()) {
                maxCard = card;
            }
        }

        return maxCard;
    }
    public Cards getCardsOfSameFactionAs(Card opponentCard) {
        if (cards.isEmpty()) {
            throw new IllegalStateException("La pile de cartes est vide.");
        }

        Cards cardsOfSameFaction = new Cards();
        Class<?> opponentCardClass = opponentCard.getClass();

        for (Card card : cards) {
            if (card.getClass() == opponentCardClass) {
                cardsOfSameFaction.setCard(card);
            }
        }

        return cardsOfSameFaction;
    }
    // Méthode pour obtenir la carte la plus grande qui est plus petite que la carte jouée par l'adversaire
    public Card getHighestCardSmallerThan(Card opponentCard) {
        if (cards.isEmpty()) {
            throw new IllegalStateException("La pile de cartes est vide.");
        }

        Card highestSmallerCard = null;
        int opponentValue = opponentCard.getValeur();

        for (Card card : cards) {
            int cardValue = card.getValeur();
            if (cardValue < opponentValue && (highestSmallerCard == null || cardValue > highestSmallerCard.getValeur())) {
                highestSmallerCard = card;
            }
        }

        return highestSmallerCard;
    }

    public Card min_valeur() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("La pile de cartes est vide.");
        }

        Card minCard = cards.get(0);
        for (Card card : cards) {
            // Si la valeur de la carte actuelle est inférieure à la valeur de la carte minimale
            // ou si la valeur est égale mais la faction a un score plus bas, mettre à jour la carte minimale
            if (card.getValeur() < minCard.getValeur() ||
                    (card.getValeur() == minCard.getValeur() && card.getFactionScore() < minCard.getFactionScore())) {
                minCard = card;
            }
        }

        return minCard;
    }


    // Méthode pour mélanger les cartes dans la pile
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // Méthode pour obtenir une carte de la pile
    public Card getCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("La pile de cartes est vide.");
        }
        return removeCard(0);
    }

    // Méthode pour obtenir toutes les cartes de la pile
    public List<Card> getCards() {
        return cards;
    }

    public boolean isEmpty(){
        return cards.isEmpty();
    }
    public void setCard(Card c){
        addCard(c);
    }

    // Méthode pour obtenir une main de 13 cartes à partir de la pile
    public Hand getHandOf13Cards() {
        if (cards.size() < 13) {
            throw new IllegalStateException("La pile de cartes contient moins de 13 cartes.");
        }
        Hand hand = new Hand();
        for (int i = 0; i < 13; i++) {
            hand.addCard(getCard());
        }
        return hand;
    }

    public void removeCard() {
        pile.pop();
    }
}
