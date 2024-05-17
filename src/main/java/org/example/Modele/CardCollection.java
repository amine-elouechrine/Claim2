package org.example.Modele;

import java.util.ArrayList;
import java.util.List;

public class CardCollection {
    public List<Card> cards;

    public CardCollection() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public Card removeCard(int index) {
        return cards.remove(index);
    }

    public Card getCard(int index) {
        return cards.get(index);
    }

    public int getSize() {
        return cards.size();
    }

    public List<Card> getCards() {
        return cards;
    }



}
