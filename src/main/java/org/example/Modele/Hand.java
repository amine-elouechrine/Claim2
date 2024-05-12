package org.example.Modele;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hand {
    public List<Card> cards;

    // Constructeur
    public Hand() {

        cards = new ArrayList<>();
    }
    public Card getSmallestHigherCard(Card card) {
        if (cards == null || cards.isEmpty()) {
            throw new IllegalStateException("La main est vide ou nulle.");
        }

        Card smallestHigherCard = card;
        int smallestHigherValue = Integer.MAX_VALUE;

        for (Card handCard : cards) {
            // Vérifier si la carte est plus grande que celle passée en paramètre
            if (handCard.getValeur() > card.getValeur() && handCard.getValeur() < smallestHigherValue) {
                smallestHigherCard = handCard;
                smallestHigherValue = handCard.getValeur();
            }
        }

        return smallestHigherCard;
    }



    public void setHand(List<Card> cards) {
        this.cards = cards;
    }

    // Ajouter une carte à la main
    public void addCard(Card card) {
        cards.add(card);
    }

    // Retirer une carte de la main
    public void removeCard(Card card) {
        cards.remove(card);
    }

    // Obtenir les cartes de la même faction qu'une carte donnée
    public Hand getCardsOfSameFaction(Card card) {
        if (cards == null || cards.isEmpty()) {
            return null;
        }

        Hand cardsOfSameFaction = new Hand();
        Class<?> cardClass = card.getClass();

        for (Card handCard : cards) {
            if (handCard.getClass() == cardClass) {
                cardsOfSameFaction.addCard(handCard);
            }
        }

        return cardsOfSameFaction;
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
    public Card getMin() {
        if (cards == null || cards.isEmpty()) {
            throw new IllegalStateException("La main est vide ou nulle.");
        }

        Card minCard = cards.get(0);

        for (Card handCard : cards) {
            if (handCard.getValeur() < minCard.getValeur()) {
                minCard = handCard;
            }
        }
        return minCard;
    }


    public void printHand() {
        if (cards == null || cards.isEmpty()) {
            System.out.println("La main est vide.");
        } else {
            System.out.println("Cartes dans la main :");
            for (Card card : cards) {
                System.out.println(card.getFaction()+" "+card.getValeur());
            }
        }
    }
    // Obtenir toutes les cartes de la main
    public List<Card> getAllCards() {
        return cards;
    }

    public Card getRandomCard() {
        if (cards == null || cards.isEmpty()) {
            throw new IllegalStateException("La main est vide ou nulle.");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(cards.size());
        Card randomCard = cards.get(randomIndex);
        cards.remove(randomIndex);
        return randomCard;
    }
    // Vider la main
    public void clear() {
        cards.clear();
    }
    public boolean containsKnight() {
        if (cards == null || cards.isEmpty()) {
            return false;
        }

        for (Card card : cards) {
            if (card instanceof Knight) {
                return true;
            }
        }

        return false;
    }
    // Obtenir le nombre de cartes dans la main
    public int size() {
        return cards.size();
    }
    public Card getLowestValueCard() {
        if (cards == null || cards.isEmpty()) {
            throw new IllegalStateException("La liste de cartes est vide ou nulle.");
        }

        Card lowestCard = cards.get(0);
        int lowestValue = lowestCard.getValeur();

        for (Card card : cards) {
            int cardValue = card.getValeur();
            if (cardValue < lowestValue) {
                lowestCard = card;
                lowestValue = cardValue;
            }
        }

        return lowestCard;
    }
}
