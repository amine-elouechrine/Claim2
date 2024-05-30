package org.example.IA;

import org.example.Modele.Card;
import org.example.Modele.Cards;
import org.example.Modele.Hand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TestFunctions {

    private static Hand creatWinnerHand(Cards pioche) {
        Hand hand = new Hand();
        List<Card> highValueCards = new ArrayList<>();

        // Filtrer les cartes de valeur >= 4
        for (Card card : pioche.getCards()) {
            if (card.getValeur() >= 4) {
                highValueCards.add(card);
            }
        }

        // Mélanger les cartes pour assurer l'aléatoire
        Collections.shuffle(highValueCards, new Random());

        // Sélectionner les 10 cartes
        for (int i = 0; i < 10 && !highValueCards.isEmpty(); i++) {
            hand.addCard(highValueCards.remove(0));
        }

        return hand;
    }

    private static Hand creatLoserHand(Cards pioche) {
        Hand hand = new Hand();
        List<Card> lowValueCards = new ArrayList<>();

        // Filtrer les cartes de valeur < 4
        for (Card card : pioche.getCards()) {
            if (card.getValeur() < 4) {
                lowValueCards.add(card);
            }
        }

        // Mélanger les cartes pour assurer l'aléatoire
        Collections.shuffle(lowValueCards, new Random());

        // Sélectionner les 10 cartes
        for (int i = 0; i < 10 && !lowValueCards.isEmpty(); i++) {
            hand.addCard(lowValueCards.remove(0));
        }

        return hand;
    }


}
