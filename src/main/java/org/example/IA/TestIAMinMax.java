package org.example.IA;

import java.util.Scanner;

import org.example.Modele.Card;
import org.example.Modele.Cards;
import org.example.Modele.Plateau;
import org.example.Modele.Player;

import java.util.ArrayList;
import java.util.List;

public class TestIAMinMax {
    public static void main(String[] args) {
        // Création d'un plateau de jeu initial
        Plateau plateauInitial = createInitialPlateau();

        // Création de l'instance de l'IA Minimax
        IAMinMax iaMinMax = new IAMinMax();

        /*// Boucle principale de jeu
        boolean partieTerminee = false;
        Scanner scanner = new Scanner(System.in);
        while (!partieTerminee) {
            // Tour de l'IA
            System.out.println("Tour de l'IA...");
            Node racine = new Node(plateauInitial);
            int meilleurCoup = iaMinMax.minimax(racine, 9, true, Integer.MIN_VALUE , Integer.MAX_VALUE); // Profondeur de recherche = 3
            // Récupération de l'enfant correspondant au meilleur coup
            Node meilleurNoeud = racine.getEnfantAvecEvaluation(meilleurCoup);
            // Récupération de la carte à jouer dans ce node
            // reccuperer la carte a jouer par l'ia
            Card carteAI = meilleurNoeud.getCarteAI();
            // Jouer la carte
            plateauInitial.jouerCarte2(carteAI); //plateau jouerCarte(index) il faut une fonction qui reccupere l'indexe de la carte a jouer dans la main de l'ia

            // Vérification de la fin de la partie
            if (plateauInitial.isEndOfGame()) {
                System.out.println("Fin de la partie. Victoire de l'IA !");
                partieTerminee = true;
                break;
            }

            // Tour de l'adversaire humain
            System.out.println("Tour de l'adversaire humain. Entrez votre coup :");
            int coupAdversaire = scanner.nextInt();

            // Jouer le coup de l'adversaire
            plateauInitial.jouerCarte(coupAdversaire);

            // Vérification de la fin de la partie
            if (plateauInitial.isEndOfGame()) {
                System.out.println("Fin de la partie. Victoire de l'adversaire humain !");
                partieTerminee = true;
            }

        }*/

        // Appel de l'algorithme Minimax pour évaluer le meilleur coup
        Node racine = new Node(plateauInitial);

        int meilleurCoup = iaMinMax.minimax(racine, 9, true, Integer.MIN_VALUE , Integer.MAX_VALUE); // Profondeur de recherche = 3

        // Affichage du meilleur coup calculé
        System.out.println("Meilleur coup calculé par l'algorithme Minimax : " + meilleurCoup);
    }

    private static Plateau createInitialPlateau() {
        // Création des joueurs
        Player joueur1 = new Player("IA");
        Player joueur2 = new Player("Adversaire");

        // creation des mains des joueurs
        Cards pioche = new Cards();
        pioche.addAllCards();
        joueur1.setHandScndPhase(pioche.getHandOf13Cards());//ia
        joueur2.setHandScndPhase(pioche.getHandOf13Cards());//adversaire

        // Initialisation du plateau avec les joueurs
        Plateau plateau = new Plateau();
        plateau.setPhase(false);//phase 2

        // configuration du plateau
        plateau.setJoueur1(joueur1);  // ia
        plateau.setJoueur2(joueur2); // adversaire
        plateau.setJoueurCourant(joueur1); // on commande par l'i

        return plateau;
    }
}
