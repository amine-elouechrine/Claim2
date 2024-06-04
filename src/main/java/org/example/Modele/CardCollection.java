package org.example.Modele;

import java.util.ArrayList;
import java.util.List;

public class CardCollection {
    public List<Card> cards;

    /**
     * @brief Constructeur par défaut. Initialise une collection vide de cartes.
     */
    public CardCollection() {
        cards = new ArrayList<>();
    }

    /**
     * @brief Ajoute une carte à la collection.
     * @param card La carte à ajouter.
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * @brief Supprime une carte de la collection.
     * @param card La carte à supprimer.
     */
    public void removeCard(Card card) {
        cards.remove(card);
    }

    /**
     * @brief Supprime une carte de la collection à l'index spécifié.
     * @param index L'index de la carte à supprimer.
     * @return La carte qui a été supprimée.
     */
    public Card removeCard(int index) {
        return cards.remove(index);
    }

    /**
     * @brief Obtient une carte de la collection à l'index spécifié.
     * @param index L'index de la carte à obtenir.
     * @return La carte à l'index spécifié.
     */
    public Card getCard(int index) {
        return cards.get(index);
    }

    /**
     * @brief Obtient la taille de la collection de cartes.
     * @return Le nombre de cartes dans la collection.
     */
    public int getSize() {
        return cards.size();
    }

    /**
     * @brief Obtient la liste des cartes dans la collection.
     * @return La liste des cartes.
     */
    public List<Card> getCards() {
        return cards;
    }



}
