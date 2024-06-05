package org.example.Modele;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * La classe PileDeScore représente la pile de score d'un joueur dans un jeu de cartes.
 * Elle est implémentée en utilisant une HashMap où les clés sont
 * des chaînes de caractères représentant les différentes factions,
 * et les valeurs sont des listes de cartes associées à chaque faction.
 * Cette structure permet de stocker et d'organiser les cartes gagnées par
 * le joueur selon leurs factions respectives
 */
public class PileDeScore {
    private Map<String, List<Card>> cartesGagnees;

    /**
     * @brief Constructeur par défaut initialise une pile de score vide.
     */
    public PileDeScore() {
        cartesGagnees = new HashMap<>();
    }

    /**
     * @brief Constructeur de copie.
     *
     * @param other L'objet PileDeScore à copier.
     */    public PileDeScore(PileDeScore other) {
        this.cartesGagnees = new HashMap<>();
        for (Map.Entry<String, List<Card>> entry : other.cartesGagnees.entrySet()) {
            // Clone la liste de cartes pour chaque faction
            List<Card> clonedList = new ArrayList<>();
            for (Card card : entry.getValue()) {
                clonedList.add(new Card(card));
            }
            this.cartesGagnees.put(entry.getKey(), clonedList);
        }
    }

    /**
     * @brief Crée un clone de l'objet PileDeScore actuel.
     *
     * @return Un nouvel objet PileDeScore qui est une copie de l'instance actuelle.
     */
    public PileDeScore clone() {
        return new PileDeScore(this);
    }

    /**
     * @brief Récupère toutes les cartes dans la pile de score.
     *
     * @return Une liste de toutes les cartes.
     */
    public List<Card> getAllCards() {
        List<Card> allCards = new ArrayList<>();
        for (List<Card> cards : cartesGagnees.values()) {
            allCards.addAll(cards);
        }
        return allCards;
    }

    /**
     * @brief Affiche la pile de score.
     */
    public void printPileDeScore() {
        for (Map.Entry<String, List<Card>> entry : cartesGagnees.entrySet()) {
            String faction = entry.getKey();
            List<Card> cards = entry.getValue();
            System.out.println("Faction: " + faction);
            for (Card card : cards) {
                System.out.println("  - " + card.toString());
            }
        }
    }

    /**
     * @brief Récupère la pile de score.
     *
     * @return Une carte représentant la pile de score.
     */
    public Map<String, List<Card>> getPileDeScore() {
        return cartesGagnees;
    }

    /**
     * @brief Récupère toutes les cartes d'une faction donnée.
     *
     * @param faction La faction dont les cartes doivent être récupérées.
     * @return Une liste de cartes appartenant à la faction spécifiée.
     */
    public List<Card> getCardFaction(String faction) {
        return cartesGagnees.getOrDefault(faction, new ArrayList<>());
    }

    /**
     * @brief Retourne la valeur la plus élevée d'une carte d'une faction donnée.
     *
     * @param faction La faction dont la carte à la valeur la plus élevée doit être récupérée.
     * @return La valeur la plus élevée d'une carte dans la faction spécifiée, ou Integer.MIN_VALUE si la faction n'a pas de cartes.
     */    public int maxValueOfFaction(String faction){
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

    /**
     * @brief Ajoute une carte à la pile de score sous la faction appropriée.
     *
     * @param carte La carte à ajouter.
     */
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
    public List<Card> getCardsOfFunction (String Faction){
        return cartesGagnees.get(Faction);
    }

}
