package org.example.IA;

import java.util.List;
import java.util.ArrayList;
import org.example.Modele.Card;
import org.example.Modele.Hand;
import org.example.Modele.PileDeScore;

public abstract class IA {
    String Name ;
    Hand hand;
    Hand handScndPhase;
    PileDeScore pileDeScore;
    public IA(String Name) {
        this.Name = Name; // initialiser le nom du joueur
        this.hand = new Hand(); // initialiser de hand vide
        this.pileDeScore = new PileDeScore(); // initialiser la pile de score vide
        this.handScndPhase = new Hand(); // initialiser la main de la seconde phase vide
        
    }

    /**
     * renvoie les cartes de la meme faction que la carte passée en paramètre (opponentCard)
     * @param opponentCard
     * @return la liste de cartes de la meme faction que la carte passée en paramètre (opponentCard)
     */
    public List<Card> getCardsOfSameFactionAs(Card opponentCard) {
        if (hand.isEmpty()) {
            throw new IllegalStateException("La pile de cartes est vide.");
        }

        List<Card> cardsOfSameFaction = new ArrayList<>();
        String opponentCardFaction = opponentCard.getFaction();

        // pour chaque carte dans la main
        for (Card card : hand.getAllCards()) {
            if (card.getFaction().equals(opponentCardFaction)) {
                cardsOfSameFaction.add(card);
            }
        }

        return cardsOfSameFaction;
    }

    /**
     * renvoie une carte plus grand que ladversaire mais pas forcement la plus grande carte dans le hand
     * @param card
     * @return la carte plus grand que ladversaire mais pas forcement la plus grande carte dans le hand
     */
    public Card getSmallestHigherCard(Card card) {
        if (hand == null || hand.isEmpty()) {
            throw new IllegalStateException("La main est vide ou nulle.");
        }

        Card smallestHigherCard = card;
        int smallestHigherValue = Integer.MAX_VALUE;

        for (Card handCard : hand.getAllCards()) {
            // Vérifier si la carte est plus grande que celle passée en paramètre
            if (handCard.getValeur() > card.getValeur() && handCard.getValeur() < smallestHigherValue) {
                smallestHigherCard = handCard;
                smallestHigherValue = handCard.getValeur();
            }
        }

        return smallestHigherCard;
    }

    public Card getLowestValueCard() {
        if (hand == null || hand.isEmpty()) {
            throw new IllegalStateException("La liste de cartes est vide ou nulle.");
        }

        Card lowestCard = hand.get(0);
        int lowestValue = lowestCard.getValeur();

        for (Card card : hand.getAllCards()) {
            int cardValue = card.getValeur();
            if (cardValue < lowestValue) {
                lowestCard = card;
                lowestValue = cardValue;
            }
        }

        return lowestCard;
    }

}
