package org.example.IA;

import java.util.Arrays;
import java.util.List;
import org.example.Modele.*;

import static org.example.Modele.ReglesDeJeu.carteGagnante;

public abstract class IA extends GeneralPlayer{

    boolean amoi=false;
    public IA(String Name) {
        super(Name);
    }
    //public abstract Card jouer_coup_phase1(Hand mainIA, boolean suivre_faction, Card carte_adversaire);
    public abstract Card jouerCoupPhase1(Hand mainIA, boolean suivre_faction, Card carte_adversaire);
    //public abstract Card jouer_coup_phase2(Hand mainIA, boolean suivre_faction, Card carte_adversaire);
    public abstract Card jouerCoupPhase2(Hand mainIA, boolean suivre_faction, Card carte_adversaire);

    
    /**
     * renvoie les cartes de la meme faction que la carte passée en paramètre (opponentCard)
     * @param opponentCard
     * @return la liste de cartes de la meme faction que la carte passée en paramètre (opponentCard)
     */
    public Hand getCardsOfSameFaction(String faction) {
        if (hand == null || hand.isEmpty()) {
            return null;
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

    public static IA determinerGagnantMancheIA(IA joueur1, IA joueur2, Card carteJoueur1, Card carteJoueur2) {
        Card carteGagnante = carteGagnante(carteJoueur1, carteJoueur2);

        if (carteGagnante == carteJoueur1) {
            return joueur1;
        } else   {
            return joueur2;
        }
    }

    /**
     * renvoie une carte plus grand que ladversaire mais pas forcement la plus grande carte dans le hand
     * @param card
     * @return la carte plus grand que ladversaire mais pas forcement la plus grande carte dans le hand
     */
    public Card getSmallestHigherCard(Card card, List<Card> CarteSameFaction) {

        if (CarteSameFaction == null || CarteSameFaction.isEmpty()) {
            throw new IllegalArgumentException("La liste de cartes de même faction est vide ou nulle.");
        }

        Card smallestHigherCard = null;
        int smallestHigherValue = Integer.MAX_VALUE;

        for (Card handCard : CarteSameFaction) {
            // Vérifier si la carte est plus grande que celle passée en paramètre
            if (handCard.getValeur() > card.getValeur() && handCard.getValeur() < smallestHigherValue) {
                smallestHigherCard = handCard;
                smallestHigherValue = handCard.getValeur();
            }
        }

        return smallestHigherCard;
    }
    public static IA determinerGagnantPartieIA(IA joueur1, IA joueur2) {
        PileDeScore pileDeScoreJoueur1 = joueur1.pileDeScore;
        PileDeScore pileDeScoreJoueur2 = joueur2.pileDeScore;

        // Initialiser un compteur pour chaque joueur pour suivre le nombre de factions qu'ils ont gagnées
        int factionsGagneesJoueur1 = 0;
        int factionsGagneesJoueur2 = 0;

        List<String> factions = Arrays.asList("Goblins", "Knights", "Undead", "Dwarves", "Doppelgangers");
        // Calcul du nombre de factions gagnées par chaque joueur
        for (String faction : factions) {
            List<Card> cartesJoueur1 = pileDeScoreJoueur1.getCardFaction(faction);
            List<Card> cartesJoueur2 = pileDeScoreJoueur2.getCardFaction(faction);

            if (cartesJoueur1.size() > cartesJoueur2.size()) {
                factionsGagneesJoueur1++;
            } else if (cartesJoueur2.size() > cartesJoueur1.size()) {
                factionsGagneesJoueur2++;
            } else {
                // Si les deux joueurs ont le même nombre de cartes, comparez les valeurs des cartes
                int valeurMaxJoueur1 = ReglesDeJeu.getMaxCardValue(cartesJoueur1);
                int valeurMaxJoueur2 = ReglesDeJeu.getMaxCardValue(cartesJoueur2);

                if (valeurMaxJoueur1 > valeurMaxJoueur2) {
                    factionsGagneesJoueur1++;
                } else if (valeurMaxJoueur2 > valeurMaxJoueur1) {
                    factionsGagneesJoueur2++;
                }
            }
        }
        //add to pile de score


        // Déterminer le gagnant de la partie en fonction du nombre de factions remportées
        if(factionsGagneesJoueur1 > factionsGagneesJoueur2){
            return joueur1;
        } else{
            return joueur2;
        }
    }

    public void addPileDeScore(Card card) {
        pileDeScore.addCard(card);
    }
    public static Card determinerCarteGagnanteIA(Card carte1, Card carte2) {
        if(carte1.getFaction().equals(carte2.getFaction())) {
            if (carte1.getValeur() > carte2.getValeur()) {
                return carte1;
            } else if (carte1.getValeur() < carte2.getValeur()) {
                return carte2;
            } else {
                return null;
            }
        }else{
            return carte1;
        }
    }




    public Card getLowestValueCard(List<Card> hand) {
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
    public Card getHighestCardSmallerThan(Card opponentCard , List<Card> carteJouable) {
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


    public boolean containsKnight() {
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
