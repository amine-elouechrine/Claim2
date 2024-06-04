package org.example.Modele;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class Cards extends CardCollection {

    // Constructeur
    public Cards() {
        super();
    }



    // Constructeur de copie
    public Cards(Cards other) {
        this.cards = new ArrayList<>();
        for (Card card : other.cards) {
            this.cards.add(new Card(card));
        }
    }

    public Cards clone() {
        return new Cards(this);
    }

    // Méthode pour créer les cartes Gobelin
    public void addAllCards() {
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
            addCard(new Card(i, "Goblins"));
        }
    }

    public static int getNbCarteGobelins(){
        return 14;
    }

    public static int getNbCarteKnights(){
        return 8;
    }

    public static int getNbCarteUndeads(){
        return 10;
    }

    public static int getNbCarteDwarves(){
        return 10;
    }

    public static int getNbCarteDoppelgangers(){
        return 10;
    }

    public static int getNbCarteFaction(String faction){
        switch (faction) {
            case "Goblins":
                return getNbCarteGobelins();
            case "Knight":
                return getNbCarteKnights();
            case "Undead":
                return getNbCarteUndeads();
            case "Dwarves":
                return getNbCarteDwarves();
            case "Doppelganger":
                return getNbCarteDoppelgangers();
            default:
                return 0;
        }
    }



    // Méthode pour créer les cartes Knight
    private void createKnightCards() {
        for (int i = 2; i < 10; i++) {
            addCard(new Card(i, "Knight"));
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
            addCard(new Card(i, "Doppelganger"));
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

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public void setCard(Card c) {
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
        hand = ranger(hand);
        shuffle();
        return hand;
    }

    // Méthode pour ranger la main par ordre de faction puis valeurs
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

    public void removeCard() {
        cards.remove(0);
    }
}
