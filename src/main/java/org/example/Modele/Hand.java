package org.example.Modele;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @brief Cette classe représente la main d'un joueur pendant un tour de jeu. Elle hérite de la classe CardCollection.
 */
public class Hand extends CardCollection{

    /**
     * Constructeur de la classe Hand.
     * Initialise une liste vide pour stocker les cartes.
     */
    public Hand() {
        super();
    }

    /**
     * @brief Constructeur de copie.
     * @param other La main à copier.
     */
    public Hand(Hand other) {
        this.cards = new ArrayList<>();
        for (Card card : other.cards) {
            this.cards.add(new Card(card));
        }
    }

    /**
     * @brief Clone la main actuelle.
     * @return Une nouvelle instance de Hand avec les mêmes cartes.
     */
    @Override
    public Hand clone() {
        return new Hand(this);
    }

    /**
     * @brief Représentation sous forme de chaîne de caractères de la main.
     * @return Une chaîne de caractères représentant la main.
     */
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

    /**
     * @brief Définit les cartes de la main.
     * @param cards La liste des cartes à définir.
     */
    public void setHand(List<Card> cards) {
        this.cards = cards;
    }

    /**
     * @brief Ajoute une carte à la main.
     * @param card La carte à ajouter à la main.
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * @brief Retire une carte de la main à l'index spécifié.
     * @param i L'index de la carte à retirer.
     * @return La carte qui a été retirée.
     */
    public Card removeCard(int i) {
        return cards.remove(i);
    }

    /**
     * @brief Retire une carte spécifique de la main.
     * @param card La carte à retirer.
     */
    public void removeCard(Card card) {
        cards.remove(card);
    }

    /**
     * @brief Obtient une carte de la main à l'index spécifié.
     * @param i L'index de la carte à obtenir.
     * @return La carte à l'index spécifié.
     */
    public Card getCard(int i) {return cards.get(i);}

    /**
     * @brief Vérifie si la main est vide.
     * @return true si la main est vide, sinon false.
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * @brief Vérifie si la main contient une carte spécifique.
     * @param card La carte à vérifier.
     * @return true si la main contient la carte, sinon false.
     */
    public boolean contains(Card card) {
        return cards.contains(card);
    }


    /**
     * @brief Vérifie si la main contient une carte de la même faction que celle passée en paramètre.
     * @param carte La carte dont on veut vérifier la faction.
     * @return true si la main contient une carte de la même faction, sinon false.
     */
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
     * @brief Récupère toutes les cartes de la main.
     * @return La liste des cartes dans la main.
     */
    public List<Card> getAllCards() {
        return getCards();
    }


    /**
     * @brief Vide la main en supprimant toutes les cartes.
     */
    public void clear() {
        cards.clear();
    }


    /**
     * Renvoie le nombre de cartes dans la main.
     * @return Le nombre de cartes dans la main.
     */
    public int size() {
        return cards.size();
    }
}
