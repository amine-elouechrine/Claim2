package org.example.Modele;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class PileDeScore {
    private Map<String, List<Card>> cartesGagnees;

    public PileDeScore() {
        cartesGagnees = new HashMap<>();
    }

    public List<Card> getAllCards() {
        List<Card> allCards = new ArrayList<>();
        for (List<Card> cards : cartesGagnees.values()) {
            allCards.addAll(cards);
        }
        return allCards;
    }

    public Map<String, List<Card>> getCartesGagnees() {
        return cartesGagnees;
    }
    public Map<String, List<Card>> getPileDeScore() {
        return cartesGagnees;
    }
    // getCardFaction(String faction) : List<Card> return tous les cartes de cette faction dans cartes gagnées
    // Récupère toutes les cartes d'une faction donnée
    public List<Card> getCardFaction(String faction) {
        return cartesGagnees.getOrDefault(faction, new ArrayList<>());
    }

    // Retourner la plus grand valeur de card d'une faction
    public int maxValueOfFaction(String faction){
        List<Card> factionCards = getCardFaction(faction);
        if (factionCards.isEmpty()) {
            return Integer.MIN_VALUE;
        }
        int maxValue = factionCards.get(0).getValeur();
        for (Card card : factionCards) {
            if (card.getValeur() > maxValue) {
                maxValue = card.getValeur();
            }
        }
        return maxValue;
    }
    // addCard(Card carte) : void ajouter une carte dans cartes gagnées dans la faction corrspondante 
    // Ajoute une carte à la faction correspondante
    public void addCard(Card carte) {
        String faction = carte.getFaction();
        if (cartesGagnees.containsKey(faction)) {
            cartesGagnees.get(faction).add(carte);
        } else {
            List<Card> cartes = new ArrayList<>();
            cartes.add(carte);
            cartesGagnees.put(faction, cartes);
        }
    }

}
