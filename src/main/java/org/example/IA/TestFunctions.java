package org.example.IA;

import org.example.Modele.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TestFunctions {

    private static Hand creatWinnerHand(Cards pioche) {
        Hand hand = new Hand();
        List<Card> highValueCards = new ArrayList<>();

        // Filtrer les cartes de valeur >= 4
        for (Card card : pioche.getCards()) {
            if (card.getValeur() >= 4) {
                highValueCards.add(card);
            }
        }

        // Mélanger les cartes pour assurer l'aléatoire
        Collections.shuffle(highValueCards, new Random());

        // Sélectionner les 10 cartes
        for (int i = 0; i < 10 && !highValueCards.isEmpty(); i++) {
            hand.addCard(highValueCards.remove(0));
        }

        return hand;
    }

    private static Hand creatLoserHand(Cards pioche) {
        Hand hand = new Hand();
        List<Card> lowValueCards = new ArrayList<>();

        // Filtrer les cartes de valeur < 4
        for (Card card : pioche.getCards()) {
            if (card.getValeur() < 4) {
                lowValueCards.add(card);
            }
        }

        // Mélanger les cartes pour assurer l'aléatoire
        Collections.shuffle(lowValueCards, new Random());

        // Sélectionner les 10 cartes
        for (int i = 0; i < 10 && !lowValueCards.isEmpty(); i++) {
            hand.addCard(lowValueCards.remove(0));
        }

        return hand;
    }

    public static Plateau createInitialPlateau(){
        // creation de plateau
        Plateau p = new Plateau();

        //creation des joueurs
        Player IA = new Player("MinMax");
        Player adversaire = new Player("Facile");

        // creation de la pioche
        Cards pioche = new Cards();
        pioche.addAllCards();
        pioche.shuffle();

        // attribution des hand des joueurs
        IA.setHand(pioche.getHandOf13Cards());
        adversaire.setHand(pioche.getHandOf13Cards());

        p.setPioche(pioche);
        p.setJoueur1(IA);
        p.setJoueur2(adversaire);
        p.setJoueurCourant(IA);
        p.setPhase(true);

        return p; // retourner le plteau apres construction
    }


    // faire un plateau avec configuration qui fait perdre l'ia forcement
    private static Plateau configurationPerdante(){
        Player joueur1 = new Player("MinMax");
        Player joueur2 = new Player("Facile");
        // creation des mains des joueurs
        Cards pioche = new Cards();
        pioche.addAllCards();

        // Donner une main à l'IA qui lui fait perdre
        // 5 * Goblins 0
        // Dwarves 0, Dwarves 1, Dwarves 2
        // Knight 2, Knight 3
        // Undead 0, Undead 1, Undead 2
        // Donner une main à l'IA qui lui fait perdre
        joueur1.getHandScndPhase().addCard(new Card(0, "Goblin"));
        joueur1.getHandScndPhase().addCard(new Card(0, "Goblin"));
        joueur1.getHandScndPhase().addCard(new Card(0, "Goblin"));
        joueur1.getHandScndPhase().addCard(new Card(0, "Goblin"));
        joueur1.getHandScndPhase().addCard(new Card(0, "Goblin"));
        joueur1.getHandScndPhase().addCard(new Card(0, "Dwarves"));
        joueur1.getHandScndPhase().addCard(new Card(1, "Dwarves"));
        joueur1.getHandScndPhase().addCard(new Card(2, "Dwarves"));
        joueur1.getHandScndPhase().addCard(new Card(2, "Knight"));
        joueur1.getHandScndPhase().addCard(new Card(3, "Knight"));
        joueur1.getHandScndPhase().addCard(new Card(0, "Undead"));
        joueur1.getHandScndPhase().addCard(new Card(1, "Undead"));
        joueur1.getHandScndPhase().addCard(new Card(2, "Undead"));

        // donner au joueur 2 les cartes qui lui font gagner
        joueur2.getHandScndPhase().addCard(new Card(3, "Goblin"));
        joueur2.getHandScndPhase().addCard(new Card(4, "Goblin"));
        joueur2.getHandScndPhase().addCard(new Card(5, "Goblin"));
        joueur2.getHandScndPhase().addCard(new Card(6, "Goblin"));
        joueur2.getHandScndPhase().addCard(new Card(7, "Goblin"));
        joueur2.getHandScndPhase().addCard(new Card(9, "Dwarves"));
        joueur2.getHandScndPhase().addCard(new Card(5, "Dwarves"));
        joueur2.getHandScndPhase().addCard(new Card(6, "Dwarves"));
        joueur2.getHandScndPhase().addCard(new Card(6, "Knight"));
        joueur2.getHandScndPhase().addCard(new Card(7, "Knight"));
        joueur2.getHandScndPhase().addCard(new Card(5, "Undead"));
        joueur2.getHandScndPhase().addCard(new Card(4, "Undead"));
        joueur2.getHandScndPhase().addCard(new Card(9, "Undead"));

        // Initialisation du plateau avec les joueurs
        Plateau plateau = new Plateau();
        plateau.setPhase(false);//phase 2

        // configuration du plateau
        plateau.setJoueur1(joueur1);  // ia
        plateau.setJoueur2(joueur2); // adversaire
        plateau.setJoueurCourant(joueur1); // on commande par l'ia

        return plateau;

    }

    public static Plateau setPlateauPhase2() {
        // Création des joueurs
        Player joueur1 = new Player("MinMax");
        Player joueur2 = new Player("Facile");

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

        return plateau;
    }

    public static Plateau setPlateauSameHand(){
        // Création des joueurs
        Player joueur1 = new Player("MinMax");
        Player joueur2 = new Player("Facile");

        Cards pioche = new Cards();
        pioche.addAllCards();
        joueur1.setHandScndPhase(pioche.getHandOf13Cards());//ia

        Hand handAdversaire = joueur1.getHandScndPhase().clone();
        joueur2.setHandScndPhase(handAdversaire);//adversaire

        // Initialisation du plateau avec les joueurs
        Plateau plateau = new Plateau();
        plateau.setPhase(false);//phase 2

        // configuration du plateau
        plateau.setJoueur1(joueur1);  // ia
        plateau.setJoueur2(joueur2); // adversaire
        plateau.setJoueurCourant(joueur1); // on commande par l'ia

        return plateau;

    }




}
