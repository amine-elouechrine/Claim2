package org.example.TestModel;
import java.util.ArrayList;
import java.util.List;

import org.example.Modele.Card;
import org.example.Modele.Cards;
import org.example.Modele.Hand;

public class CardsTest {


    public static void main(String[] args) {
        testShuffle();
        testGetHandOf13Cards();
    }

    public static void testShuffle() {
        Cards cards = new Cards();
        List<Card> cardsBeforeShuffle = new ArrayList<>(cards.getCards());

        cards.shuffle();

        List<Card> cardsAfterShuffle = cards.getCards();

        // Vérifier que les cartes ont été mélangées en comparant les deux listes
        boolean isShuffled = !cardsBeforeShuffle.equals(cardsAfterShuffle);
        if (isShuffled) {
            System.out.println("Shuffle test passed. : Les cartes ont été mélangées avec succès.");
        } else {
            System.out.println("Shuffle test failed. : Les cartes n'ont pas été mélangées.");
        }
    }

    public static void testGetHandOf13Cards() {
        Cards cards = new Cards();
        Hand hand = cards.getHandOf13Cards();
        if (hand.size() == 13) {
            System.out.println("GetHandOf13Cards test passed.");
        } else {
            System.out.println("GetHandOf13Cards test failed.");
        }
    }

}
