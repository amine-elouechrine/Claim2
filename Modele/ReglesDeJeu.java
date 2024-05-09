import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class ReglesDeJeu {

    // Méthode pour déterminer quelle carte l'emporte entre deux cartes selon les règles du jeu
    public Card carteGagnante(Card carte1, Card carte2) {
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
    public  Player determinerGagnantManche(Player joueur1, Player joueur2, Card carteJoueur1, Card carteJoueur2) {
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
    public  List<Card> cartesJouables(Card carteAdversaire, Hand mainJoueur) {
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

    // Méthode pour déterminer le gagnant d'une partie
    public  String determinerGagnantPartie(Player joueur1, Player joueur2) {
        PileDeScore pileDeScoreJoueur1 = joueur1.getPileDeScore();
        PileDeScore pileDeScoreJoueur2 = joueur2.getPileDeScore();
        
        // Initialiser un compteur pour chaque joueur pour suivre le nombre de factions qu'ils ont gagnées
        int factionsGagneesJoueur1 = 0;
        int factionsGagneesJoueur2 = 0;

        List<String> factions = Arrays.asList("Goblins", "Knights", "Undead", "Dwarves", "Doppelgangers");
        // Calcul du nombre de factions gagnées par chaque joueur
        for (String faction : factions) {
            List<Card> cartesJoueur1 = pileDeScoreJoueur1.getCardFaction(faction);
            List<Card> cartesJoueur2 = pileDeScoreJoueur2.getCardFaction(faction);

            if (cartesJoueur1.size() > cartesJoueur2.size()) {
                factionsGagneesJoueur1++;
            } else if (cartesJoueur2.size() > cartesJoueur1.size()) {
                factionsGagneesJoueur2++;
            } else {
                // Si les deux joueurs ont le même nombre de cartes, comparez les valeurs des cartes
                int valeurMaxJoueur1 = getMaxCardValue(cartesJoueur1);
                int valeurMaxJoueur2 = getMaxCardValue(cartesJoueur2);
                
                if (valeurMaxJoueur1 > valeurMaxJoueur2) {
                    factionsGagneesJoueur1++;
                } else if (valeurMaxJoueur2 > valeurMaxJoueur1) {
                    factionsGagneesJoueur2++;
                }
            }
        }

        // Déterminer le gagnant de la partie en fonction du nombre de factions remportées
        if(factionsGagneesJoueur1 > factionsGagneesJoueur2){
            return joueur1.getName();
        } else{
            return joueur2.getName();
        }
    }

    // Fonction pour obtenir la valeur maximale d'une liste de cartes
     int getMaxCardValue(List<Card> cartes) {
        int max = Integer.MIN_VALUE;
        for (Card carte : cartes) {
            if (carte.getValeur() > max) {
                max = carte.getValeur();
            }
        }
        return max;
    }

    public void applyUndeadRule(Player trickWinner, Plateau plateau) {
        // Vérifier si la carte jouée est de la faction Undead
        if (plateau.getCarteJoueur1().getFaction().equals("Undead")) {
            // Ajouter la carte à la pile de score du joueur qui a remporté le tour
            trickWinner.getPileDeScore().addCard(plateau.getCarteJoueur1());
        }
        if (plateau.getCarteJoueur2().getFaction().equals("Undead")) {
            // Ajouter la carte à la pile de score du joueur qui a remporté le tour
            trickWinner.getPileDeScore().addCard(plateau.getCarteJoueur2());
        }
    }

    public void ApplyDwarvesRules(Player trickwinner , Plateau plateau){
        // à verifier les regles des nains
    }
}