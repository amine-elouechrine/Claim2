import java.util.ArrayList;
import java.util.List;

public class ReglesDeJeu {

    // Méthode pour déterminer quelle carte l'emporte entre deux cartes selon les règles du jeu
    public static Card carteGagnante(Card carte1, Card carte2) {
        String faction1 = carte1.getFaction();
        String faction2 = carte2.getFaction();

        // Règle spéciale pour les Gobelins et les Chevaliers
        if (faction1.equals("Goblins") && faction2.equals("Knights")) {
            return carte2; // Le Chevalier bat toujours le Gobelin
        } else if (faction1.equals("Knights") && faction2.equals("Goblins")) {
            return carte1; // Le Chevalier bat toujours le Gobelin
        }

        // Comparaison basée sur les valeurs des cartes si les factions ne sont pas Goblins ou Knights
        if (carte1.getValeur() > carte2.getValeur()) {
            return carte1;
        } else if (carte1.getValeur() < carte2.getValeur()) {
            return carte2;
        } else {
            // En cas d'égalité, aucune carte ne l'emporte
            return null;
        }
    }

    // Méthode pour déterminer le gagnant d'une manche
    public static Player determinerGagnantManche(Player joueur1, Player joueur2, Card carteJoueur1, Card carteJoueur2) {
        Card carteGagnante = carteGagnante(carteJoueur1, carteJoueur2);
        
        if (carteGagnante == carteJoueur1) {
            return joueur1;
        } else if (carteGagnante == carteJoueur2) {
            return joueur2;
        } else {
            return null; // En cas d'égalité
        }
    }

    // Méthode pour déterminer quelles cartes le deuxième joueur peut jouer en fonction de la carte jouée par le premier joueur
    public static List<Card> cartesJouables(Card carteAdversaire, Hand mainJoueur) {
        List<Card> cartesJouables = new ArrayList<>();

        // Vérifier si le joueur possède une carte de la même faction que celle jouée par l'adversaire
        for (Card carte : mainJoueur.getAllCards()) {
            if (carte.getFaction().equals(carteAdversaire.getFaction())) {
                cartesJouables.add(carte);
            }
        }

        // Si le joueur n'a pas de carte de la même faction, il peut jouer n'importe quelle carte
        if (cartesJouables.isEmpty()) {
            cartesJouables.addAll(mainJoueur.getAllCards());
        }

        return cartesJouables;
    }



}
