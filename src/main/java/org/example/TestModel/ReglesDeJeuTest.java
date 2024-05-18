package org.example.TestModel;
import java.util.List;
import java.util.Scanner;

import org.example.Modele.Card;
import org.example.Modele.Cards;
import org.example.Modele.Hand;
import org.example.Modele.Player;
import org.example.Modele.ReglesDeJeu;

public class ReglesDeJeuTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tests des règles de jeu : ");
        System.out.println("1. Test de la méthode carteGagnante");
        System.out.println("2. Test de la méthode determinerGagnantManche");
        System.out.println("3. Test de la méthode cartesJouables");
        System.out.println("4. Test de la méthode determinerGagnantPartie");

        System.out.print("Entrez le numéro du test à exécuter : ");
        String test = scanner.nextLine();
        switch (test) {
            case "1":
                testCarteGagnante();
                break;
            case "2":
                testDeterminerGagnantManche();
                break;
            case "3":
                // testCartesJouables();
                break;
            case "4":
                testDeterminerGagnantPartie();
                break;
            default:
                System.out.println("Test non reconnu.");
                break;
        }
        scanner.close();  
    }

    static void testCarteGagnante() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Entrez : 1 pour test manuel || 2 pour test automatique : ");
        String test = scanner.nextLine();

        Card carte1 = new Card();
        Card carte2 = new Card();

        if (test.equals("1")) {
            // Récupérer le nom et la valeur de la première carte depuis la ligne de commande
            System.out.print("Entrez le nom de la première carte : ");
            String nomCarte1 = scanner.nextLine();
            System.out.print("Entrez la valeur de la première carte : ");
            int valeurCarte1 = Integer.parseInt(scanner.nextLine());
            
            // Récupérer le nom et la valeur de la deuxième carte depuis la ligne de commande
            System.out.print("Entrez le nom de la deuxième carte : ");
            String nomCarte2 = scanner.nextLine();
            System.out.print("Entrez la valeur de la deuxième carte : ");
            int valeurCarte2 = Integer.parseInt(scanner.nextLine());
        
            scanner.close();

            // Créer les objets Card à partir des données entrées par l'utilisateur
            carte1.setFaction(nomCarte1);
            carte1.setValue(valeurCarte1);
            carte2.setFaction(nomCarte2);
            carte2.setValue(valeurCarte2);

        } else {
            Cards cards = new Cards();
            cards.shuffle();
            carte1 = cards.getCards().get(0);
            carte2 = cards.getCards().get(1);
            System.out.println("Carte 1 : " + carte1.getFaction() + " de valeur " + carte1.getValeur());
            System.out.println("Carte 2 : " + carte2.getFaction() + " de valeur " + carte2.getValeur());
        }

        // Vérifier quelle carte gagne selon les règles spécifiées
        Card carteGagnante = ReglesDeJeu.carteGagnante(carte1, carte2);
    
        System.out.println("La carte gagnante est : " + carteGagnante.getFaction() + " de valeur " + carteGagnante.getValeur());
    }
    

    static void testDeterminerGagnantManche() {
        // Initialisation des joueurs et des cartes pour le test
        Player joueur1 = new Player("Alice");
        Player joueur2 = new Player("Bob");
        Cards cards = new Cards();
        cards.shuffle();
        Card carteJoueur1 = new Card();
        Card carteJoueur2 = new Card();
        carteJoueur1 = cards.getCards().get(0);
        carteJoueur2 = cards.getCards().get(1);

        System.out.println("joueur1 : " + joueur1.getName() + " carte : " + carteJoueur1.getFaction() + " de valeur " + carteJoueur1.getValeur());
        System.out.println("joueur2 : " + joueur2.getName() + " carte : " + carteJoueur2.getFaction() + " de valeur " + carteJoueur2.getValeur());
        Card carteGagnante = ReglesDeJeu.carteGagnante(carteJoueur1, carteJoueur2);
        System.out.println("La carte gagnante est : " + carteGagnante.getFaction() + " de valeur " + carteGagnante.getValeur());
        Player gagnant = ReglesDeJeu.determinerGagnantManche(joueur1, joueur2, carteJoueur1, carteJoueur2);
        System.out.println("Le gagnant de la manche est : " + gagnant.getName());
        
        String winner ;

        if (carteGagnante.getFaction().equals(carteJoueur1.getFaction()) && carteGagnante.getValeur() == carteJoueur1.getValeur()) {
            winner = joueur1.getName();
        } else {
            winner = joueur2.getName();
        }

        if (gagnant.getName().equals(winner)) {
            System.out.println("Test determinerGagnantManche réussi.");
        } else {
            System.out.println("Test determinerGagnantManche échoué.");
        }
    }

    /*
    static void testCartesJouables() {
        Cards cards = new Cards();
        cards.shuffle();
        Hand mainJoueur =cards.getHandOf13Cards();
        Card carteAdversaire = cards.getCards().get(0);
        List<Card> cartesJouables = ReglesDeJeu.cartesJouables(carteAdversaire, mainJoueur);
        System.out.println("carteAdversaire : " + carteAdversaire.getFaction() + " de valeur " + carteAdversaire.getValeur());
        System.out.println("Cartes jouables : " + cartesJouables.toString());
    }

     */

    static void testDeterminerGagnantPartie() {
        Player joueur1 = new Player("Alice");
        Player joueur2 = new Player("Bob");
        // Ajoutez des cartes à la pile de score pour chaque joueur pour simuler une partie
        String gagnant = ReglesDeJeu.determinerGagnantPartie(joueur1, joueur2);

        String winner ;
        if (joueur1.getScore() > joueur2.getScore()) {
            winner = joueur1.getName();
        } else {
            winner = joueur2.getName();
        }

        if (gagnant.equals(winner)) {
            System.out.println("Test determinerGagnantPartie réussi.");
        } else {
            System.out.println("Test determinerGagnantPartie échoué.");
        }
    }
}
