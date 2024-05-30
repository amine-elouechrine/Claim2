package org.example.Modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hand extends CardCollection{

    /**
     * Constructeur de la classe Hand.
     * Initialise une liste vide pour stocker les cartes.
     */
    public Hand() {
        super();
    }


    // Constructeur de copie
    public Hand(Hand other) {
        this.cards = new ArrayList<>();
        for (Card card : other.cards) {
            this.cards.add(new Card(card));
        }
    }

    @Override
    public Hand clone() {
        return new Hand(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Hand{");
        for (Card card : cards) {
            sb.append(card.toString()).append(", ");
        }
        if (!cards.isEmpty()) {
            sb.setLength(sb.length() - 2); // Enlever la dernière virgule et l'espace
        }
        sb.append("}");
        return sb.toString();
    }


    public void setHand(List<Card> cards) {
        this.cards = cards;
    }

    /**
     * Ajoute une carte à la main.
     *
     * @param card La carte à ajouter à la main.
     */
    // Ajouter une carte à la main
    public void addCard(Card card) {
        cards.add(card);
    }

    /*
     * Retire une carte de la main.
     * @param card La carte à retirer de la main.
     */
    // Retirer une carte de la main
    public Card removeCard(int i) {
        return cards.remove(i);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }
    public Card getCard(int i) {return cards.get(i);} 

    public boolean isEmpty() {
        return cards.isEmpty();
    }


    public boolean contains(Card card) {
        return cards.contains(card);
    }


    // si le hand du joueur contient une carte de la meme faction que la carte passée en paramètre
    public boolean containsCardOFaction(Card carte) {
        for (Card card : cards) {
            if (card.getFaction().equals(carte.getFaction())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Renvoie la carte la plus petit d'une main.
     * @return La carte la plus petite de la main.
     */
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

    /**
     * afficher les cartes de la main {faction valeur}
     */
    public void printHand() {
        if (cards == null || cards.isEmpty()) {
            System.out.println("La main est vide.");
        } else {
            System.out.println("Cartes dans la main :");
            for (Card card : cards) {
                System.out.println(card.getFaction() + " " + card.getValeur());
            }
        }
    }

    /**
     * Récupère toutes les cartes de la main.
     *
     * @return La liste des cartes dans la main.
     */
    // Obtenir toutes les cartes de la main
    public List<Card> getAllCards() {
        return cards;
    }


    //public Card getLargestSmallerCard(Card card) 
    //public Card getLowestCardWithFactionScore() {

    public Card getLargestSmallerCard(Card card) {
        if (cards == null || cards.isEmpty()) {
            throw new IllegalStateException("La main est vide ou nulle.");
        }

        Card largestSmallerCard = null;

        for (Card handCard : cards) {
            // Vérifier si la carte est plus grande que celle passée en paramètre
            if (handCard.getValeur() > card.getValeur()) {
                // Vérifier si la carte trouvée est plus petite que la carte actuellement retenue
                if (largestSmallerCard == null || handCard.getValeur() < largestSmallerCard.getValeur()) {
                    largestSmallerCard = handCard;
                }
            }
        }

        if (largestSmallerCard == null) {
            throw new IllegalStateException("Aucune carte plus grande mais plus petite trouvée.");
        }

        return largestSmallerCard;
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


    /**
     * Vide la main en supprimant toutes les cartes.
     */
    // Vider la main
    public void clear() {
        cards.clear();
    }


    /**
     * Renvoie le nombre de cartes dans la main.
     *
     * @return Le nombre de cartes dans la main.
     */
    // Obtenir le nombre de cartes dans la main
    public int size() {
        return cards.size();
    }


    public Card get(int i) {
        if (i < 0 || i >= cards.size()) {
            throw new IndexOutOfBoundsException("Index hors limites: " + i);
        }
        return cards.get(i);
    }
    
    
    /*public Card getRandomCard() {
        if (cards == null || cards.isEmpty()) {
            throw new IllegalStateException("La main est vide ou nulle.");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(cards.size());
        Card randomCard = cards.get(randomIndex);
        cards.remove(randomIndex);
        return randomCard;
    }*/
}
