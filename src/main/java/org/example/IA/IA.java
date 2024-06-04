package org.example.IA;

import org.example.Modele.*;

import java.util.Arrays;
import java.util.List;

import static org.example.IA.IA_intermediareVSIA_Facile.plateau;
import static org.example.Modele.ReglesDeJeu.carteGagnante;

public abstract class IA extends GeneralPlayer {

    public IA(String Name) {
        super(Name);
    }

    public static Card determinerCarteGagnanteIA(Card carte1, Card carte2) {
        if (carte1.getFaction().equals(carte2.getFaction())) {
            if (carte1.getValeur() > carte2.getValeur()) {
                return carte1;
            } else if (carte1.getValeur() < carte2.getValeur()) {
                return carte2;
            } else {
                return null;
            }
        } else {
            return carte1;
        }
    }

    // public abstract Card jouer_coup_phase1(Hand mainIA, boolean suivre_faction, Card carte_adversaire);
    public Card jouerCoupPhase1(Plateau plateau) {
        return null;
    }

    // public abstract Card jouer_coup_phase2(Hand mainIA, boolean suivre_faction, Card carte_adversaire);
    public abstract Card jouerCoupPhase2(Plateau plateau);

    /**
     * renvoie les cartes de la meme faction que la carte passée en paramètre (opponentCard)
     *
     * @param plateau
     * @return la liste de cartes de la meme faction que la carte passée en paramètre (opponentCard)
     */
    public Hand getCardsOfSameFaction(Plateau plateau) {
        Hand hand = plateau.getJoueurCourant().getHand();
        String faction = plateau.getCardAdversaire().getFaction();
        if (hand == null || hand.isEmpty()) {
            return new Hand();
        }

        Hand cardsOfSameFaction = new Hand();

        for (Card handCard : hand.getAllCards()) {
            if (handCard.getFaction().equals(faction)) {
                cardsOfSameFaction.addCard(handCard);
            }
        }

        return cardsOfSameFaction;
    }

    public Hand getCardsOfSameFaction2(Hand hand, String faction) {
        if (hand == null || hand.isEmpty()) {
            return new Hand();
        }

        Hand cardsOfSameFaction = new Hand();

        for (Card handCard : hand.getAllCards()) {
            if (handCard.getFaction().equals(faction)) {
                cardsOfSameFaction.addCard(handCard);
            }
        }

        return cardsOfSameFaction;
    }

    public void addHandScndPhase(Card card) {
        handScndPhase.addCard(card);
    }

    /**
     * renvoie une carte plus grand que ladversaire mais pas forcement la plus grande carte dans le hand
     *
     * @param card
     * @return la carte plus grand que ladversaire mais pas forcement la plus grande carte dans le hand
     */
    public static Card getSmallestHigherCard(Card card, List<Card> CarteSameFaction) {

        if (CarteSameFaction == null || CarteSameFaction.isEmpty()) {
            throw new IllegalArgumentException("La liste de cartes de même faction est vide ou nulle.");
        }

        Card smallestHigherCard = CarteSameFaction.get(0);
        int smallestHigherValue = Integer.MAX_VALUE;

        for (int i = 1; i < CarteSameFaction.size(); i++) {
            // Vérifier si la carte est plus grande que celle passée en paramètre
            if (CarteSameFaction.get(i).getValeur() > card.getValeur() && CarteSameFaction.get(i).getValeur() < smallestHigherValue) {
                smallestHigherCard = CarteSameFaction.get(i);
                smallestHigherValue = CarteSameFaction.get(i).getValeur();
            }
        }

        return smallestHigherCard;
    }

    public void addPileDeScore(Card card) {

        pileDeScore.addCard(card);
    }

    public static Card getLowestValueCard(List<Card> hand) {
        if (hand == null || hand.isEmpty()) {
            throw new IllegalStateException("La liste de cartes est vide ou nulle.");
        }

        Card lowestCard = hand.get(0);
        int lowestValue = lowestCard.getValeur();

        for (Card card : hand) {
            int cardValue = card.getValeur();
            if (cardValue < lowestValue) {
                lowestCard = card;
                lowestValue = cardValue;
            }
        }

        return lowestCard;
    }

    public Card getHighestValueCard(List<Card> hand) {
        if (hand == null || hand.isEmpty()) {
            throw new IllegalStateException("La liste de cartes est vide ou nulle.");
        }

        Card highestCard = hand.get(0);
        int highestValue = highestCard.getValeur();

        for (Card card : hand) {
            int cardValue = card.getValeur();
            if (cardValue > highestValue) {
                highestCard = card;
                highestValue = cardValue;
            }
        }

        return highestCard;
    }

    // Méthode pour obtenir la carte la plus grande qui est plus petite que la carte jouée par l'adversaire
    public Card getHighestCardSmallerThan(Card opponentCard, List<Card> carteJouable) {
        if (carteJouable.isEmpty()) {
            throw new IllegalStateException("La liste des cartes jouable est vide !");
        }

        Card highestSmallerCard = null;
        int opponentValue = opponentCard.getValeur();

        for (Card card : carteJouable) {
            int cardValue = card.getValeur();
            if (cardValue < opponentValue && (highestSmallerCard == null || cardValue > highestSmallerCard.getValeur())) {
                highestSmallerCard = card;
            }
        }

        return highestSmallerCard;
    }

    public static boolean containsKnight(Plateau plaateau) {
        Hand hand = plateau.getJoueurCourant().getHand();
        if (hand == null || hand.getAllCards().isEmpty()) {
            return false;
        }

        for (Card card : hand.getAllCards()) {
            if (card.getFaction().equals("Chevalier")) {
                return true;
            }
        }

        return false;
    }

}
