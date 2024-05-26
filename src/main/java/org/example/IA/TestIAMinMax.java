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

        // Boucle principale de jeu
        /*boolean partieTerminee = false;
        Scanner scanner = new Scanner(System.in);
        while (!partieTerminee) {
            // Tour de l'IA
            System.out.println("Tour de l'IA...");
            Node racine = new Node(plateauInitial);
            int meilleurCoup = iaMinMax.minimax(racine, 13, true, Integer.MIN_VALUE , Integer.MAX_VALUE); // Profondeur de recherche = 3
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

        // Créer un noeud racine avec le plateau initial
        Node racine = new Node(plateauInitial);

        // Réinitialiser le compteur de nodes visités
        //iaMinMax.nodeCount = 0;


        // Appel de l'algorithme Minimax pour évaluer le meilleur coup
        // au depart il faut pas commancer avec Carte J1 = une valeur et Carte J2 = une valeur quelconque
        IAMinMax.Result result = iaMinMax.minimax(racine, 55, true, Integer.MIN_VALUE , Integer.MAX_VALUE ); // Profondeur de recherche = 3


        // Afficher le score et la carte choisie par l'IA
        System.out.println("Score: " + result.score);
        System.out.println("Carte choisie par l'IA: " + result.coup);

        // Afficher le nombre de nodes visités
        System.out.println("Nombre de nodes: " + iaMinMax.getNodeCount());

        // afficher la table de memeorisation pour voir les configurations deja calculer
        // Afficher le contenu de la memo
        //iaMinMax.afficherMemo();

        System.out.println("-------------------------------------------------");

        /*Plateau plateauInitial1 = createInitialPlateau();
        Node racine1 = new Node(plateauInitial1);
        IAMinMax iaMinMax2 = new IAMinMax();
        Card meilleurCoup = iaMinMax2.carteJouerIa(racine);
        System.out.println("Nombre de nodes: " + iaMinMax2.getNodeCount());
        System.out.println("Carte choisie par l'IA: " + meilleurCoup);*/


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
        plateau.setJoueurCourant(joueur1); // on commande par l'ia

        // Initialisation des cartes des joueurs à une valeur quelconque
        Card c1 = new Card(-1 , " ");
        Card c2 = new Card(-1 , " ");
        plateau.setCarteJoueur1(c1);
        plateau.setCarteJoueur2(c2);

        return plateau;
    }
}
