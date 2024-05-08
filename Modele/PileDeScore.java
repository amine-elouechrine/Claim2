import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class PileDeScore {
    private Map<String, List<Card>> cartesGagnees;

    public PileDeScore() {
        cartesGagnees = new HashMap<>();
    }

    // getCardFaction(String faction) : List<Card> return tous les cartes de cette faction dans cartes gagnées
    // Récupère toutes les cartes d'une faction donnée
    public List<Card> getCardFaction(String faction) {
        return cartesGagnees.getOrDefault(faction, new ArrayList<>());
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
