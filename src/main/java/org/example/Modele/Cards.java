package org.example.Modele;
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
            pile.push(new Card(0, "Goblins"));
        }
        for (int i = 1; i < 10; i++) {
            pile.push(new Card(i , "Goblins"));
        }
    }

    // Méthode pour créer les cartes Knight
    private void createKnightCards() {
        for (int i = 2; i < 10; i++) {
            pile.push(new Card(i , "Knight"));
        }
    }

    // Méthode pour créer les cartes Undead
    private void createUndeadCards() {
        for (int i = 0; i < 10; i++) {
            pile.push(new Card(i, "Undead"));
        }
    }

    // Méthode pour créer les cartes Dwarve
    private void createDwarveCards() {
        for (int i = 0; i < 10; i++) {
            pile.push(new Card(i, "Dwarves"));
        }
    }

    // Méthode pour créer les cartes Doppelganger
    private void createDoppelgangerCards() {
        for (int i = 0; i < 10; i++) {
            pile.push(new Card(i , "Doppelganger"));
        }
    }
    
    
    /*public Cards getCardsOfSameFactionAs(Card opponentCard) {
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
    }*/


    // Méthode pour mélanger les cartes dans la pile
    public void shuffle() {
        Collections.shuffle(pile);
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
