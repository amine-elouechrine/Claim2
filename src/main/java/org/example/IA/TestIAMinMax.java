package org.example.IA;

import java.util.*;

import org.example.Modele.*;
import org.example.Modele.*;

import static org.example.IA.IA.determinerGagnantMancheIA;

public class TestIAMinMax {

    public static String MinMaxVsIntermediaire(IAMinMax iaMinMax, IA Intermediare){
        Card coupIa = null;
        Card coupAdversaire = null;
        Card winningCard;
        String winner = "";
        boolean partieTerminee = false;

        // Création d'un plateau de jeu initial
        Plateau plateauInitial = createInitialPlateau();

        while (!partieTerminee) {
            if (plateauInitial.getJoueurCourant().getName().equals("MinMax")) {
                // Tour de l'IA
                System.out.println("Tour de l'IA...");
                coupIa = iaMinMax.carteJouerIa(plateauInitial); // Profondeur de recherche = 13
                System.out.println("Carte choisie par l'IA: " + coupIa);
                plateauInitial.jouerCarte(coupIa);
                plateauInitial.switchJoueur();
            } else {
                // Tour de l'adversaire
                System.out.println("Tour de l'adversaire...");
                coupAdversaire = Intermediare.jouerCoupPhase2(plateauInitial);
                System.out.println("Carte jouée par l'adversaire : " + coupAdversaire);
                plateauInitial.jouerCarte(coupAdversaire);
            }

            if (plateauInitial.getJoueurCourant().getName().equals("Intermediaire")) {
                System.out.println("Tour de l'adversaire...");
                coupAdversaire = Intermediare.jouerCoupPhase2(plateauInitial);
                System.out.println("Carte jouée par l'adversaire : " + coupAdversaire);
                plateauInitial.jouerCarte(coupAdversaire);
            } else {
                System.out.println("Tour de l'IA...");
                coupIa = iaMinMax.carteJouerIa(plateauInitial); // Profondeur de recherche = 13
                if (coupIa == null) {
                    coupIa = plateauInitial.getJoueur1().getHandScndPhase().get(0);
                }
                System.out.println("Carte choisie par l'IA: " + coupIa);
                plateauInitial.jouerCarte(coupIa);
            }

            winningCard = ReglesDeJeu.determinerCarteGagnante(coupIa, coupAdversaire, plateauInitial);
            System.out.println("Carte gagnante : " + winningCard);
            plateauInitial.attribuerCarteSecondPhase(winningCard, new ReglesDeJeu());

            if (plateauInitial.isEndOfGame()) {
                winner = ReglesDeJeu.determinerGagnantPartie(plateauInitial.getJoueur1(), plateauInitial.getJoueur2());
                System.out.println("Fin de la partie. Victoire de " + winner);
                partieTerminee = true;
            }
        }

        return winner;
    }



    public static String jouerPartieFacileMinMax(IAMinMax iaMinMax) {
        Card coupIa = null;
        Card coupAdversaire = null;
        Card winningCard;
        String winner = "";
        boolean partieTerminee = false;

        // Création d'un plateau de jeu initial
        Plateau plateauInitial = createInitialPlateau();

        while (!partieTerminee) {
            if (plateauInitial.getJoueurCourant().getName().equals("MinMax")) {
                // Tour de l'IA
                System.out.println("Tour de l'IA...");
                coupIa = iaMinMax.carteJouerIa(plateauInitial); // Profondeur de recherche = 13
                System.out.println("Carte choisie par l'IA: " + coupIa);
                plateauInitial.jouerCarte(coupIa);
                plateauInitial.switchJoueur();
            } else {
                // Tour de l'adversaire
                System.out.println("Tour de l'adversaire...");
                coupAdversaire = carteAleatoire(plateauInitial);
                System.out.println("Carte jouée par l'adversaire : " + coupAdversaire);
                plateauInitial.jouerCarte(coupAdversaire);
                plateauInitial.switchJoueur();
            }

            if (plateauInitial.getJoueurCourant().getName().equals("Facile")) {
                System.out.println("Tour de l'adversaire...");
                coupAdversaire = carteAleatoire(plateauInitial);
                System.out.println("Carte jouée par l'adversaire : " + coupAdversaire);
                plateauInitial.jouerCarte(coupAdversaire);
            } else {
                System.out.println("Tour de l'IA...");
                coupIa = iaMinMax.carteJouerIa(plateauInitial); // Profondeur de recherche = 13
                if (coupIa == null) {
                    coupIa = plateauInitial.getJoueur1().getHandScndPhase().get(0);
                }
                System.out.println("Carte choisie par l'IA: " + coupIa);
                plateauInitial.jouerCarte(coupIa);
            }

            winningCard = ReglesDeJeu.determinerCarteGagnante(coupIa, coupAdversaire, plateauInitial);
            System.out.println("Carte gagnante : " + winningCard);
            plateauInitial.attribuerCarteSecondPhase(winningCard, new ReglesDeJeu());

            if (plateauInitial.isEndOfGame()) {
                winner = ReglesDeJeu.determinerGagnantPartie(plateauInitial.getJoueur1(), plateauInitial.getJoueur2());
                System.out.println("Fin de la partie. Victoire de " + winner);
                partieTerminee = true;
            }
        }

        return winner;
    }



    public static void main(String[] args) {
        int scoreMinMax = 0;
        int scoreFacile = 0;
        IAMinMax iaMinMax = new IAMinMax();
        //IA intermediaire = new Intermediare();

        for (int i = 0; i < 100; i++) {
            String winner = jouerPartieFacileMinMax(iaMinMax);
            //String winner = MinMaxVsIntermediaire(iaMinMax, intermediaire);

            if (winner.equals("MinMax")) {
                scoreMinMax++;
            } else {
                scoreFacile++;
            }

            System.out.println("Fin de partie num : " + i);
            System.out.println("\n\n\n");
        }

        System.out.println("Score MinMax : " + scoreMinMax);
        System.out.println("Score Facile : " + scoreFacile);
    }







    public static Card carteAleatoire(Plateau plateau){
        GeneralPlayer joueurCourant = plateau.getJoueurCourant();
        List<Card> cartesJoueurCourant = joueurCourant.getHandScndPhase().getCards();
        List<Card> cartesJouable;
        if(plateau.estLeader()){
            cartesJouable = cartesJoueurCourant;
        }else{
            cartesJouable = ReglesDeJeu.cartesJouables(plateau.getCarteJoueur1() , plateau.getJoueurCourant().getHandScndPhase());
        }
        // choisir une carte aleatoire de cartes Jouables
        Random rand = new Random();
        int index = rand.nextInt(cartesJouable.size());
        return cartesJouable.get(index);
    }


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


    // faire un plateau avec configuration qui fait perdre l'ia forcement
    private static Plateau configurationPerdante(){
        Player joueur1 = new Player("Facile");
        Player joueur2 = new Player("MinMax");
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


    private static Plateau createInitialPlateau() {
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
}


/*public static void main(String[] args) {

        Card coupIa = null ;
        Card coupAdversaire = null;
        Card winningCard;
        String winner = "";

        int scoreMinMax = 0;
        int scoreFacile = 0;

        // Création de l'instance de l'IA Minimax
        IAMinMax iaMinMax = new IAMinMax();
        //Facile facile =new Facile();

        // Boucle principale de jeu
        boolean partieTerminee = false;
        int i = 0;
        while(i<100) {
            // Création d'un plateau de jeu initial
            Plateau plateauInitial = configurationPerdante();
            partieTerminee = false;
            winner = "";
            coupIa = null;
            coupAdversaire = null;

            //System.out.println("Joueur 1 est : "+plateauInitial.getJoueur1().getName());
            //System.out.println("Joueur 2 est : "+plateauInitial.getJoueur2().getName());
            //System.out.println("Hand Joeur1 : "+plateauInitial.getJoueur1().getHandScndPhase());
            //System.out.println("Hand Joeur2 : "+plateauInitial.getJoueur2().getHandScndPhase());
            while (!partieTerminee) {
                // si c'est le tour de l'ai
                if (plateauInitial.getJoueurCourant().getName().equals("MinMax")) {
                    // Tour de l'IA
                    System.out.println("Tour de l'IA...");
                    coupIa = iaMinMax.carteJouerIa(plateauInitial); // Profondeur de recherche = 13
                    System.out.println("Carte choisie par l'IA: " + coupIa);
                    plateauInitial.jouerCarte(coupIa);
                    plateauInitial.switchJoueur();
                } else {// Tour de l'adversaire humain
                    System.out.println("Tour de l'adversaire...");
                    coupAdversaire = carteAleatoire(plateauInitial);
                    System.out.println("Carte jouée par l'adversaire : " + coupAdversaire);
                    // Jouer le coup de l'adversaire
                    plateauInitial.jouerCarte(coupAdversaire);
                    plateauInitial.switchJoueur();
                }
                // --------------------------------------------------------------------------------------------
                if (plateauInitial.getJoueurCourant().getName().equals("Facile")) {
                    System.out.println("Tour de l'adversaire...");
                    coupAdversaire = carteAleatoire(plateauInitial);
                    System.out.println("Carte jouée par l'adversaire : " + coupAdversaire);
                    // Jouer le coup de l'adversaire
                    plateauInitial.jouerCarte(coupAdversaire);

                } else {
                    System.out.println("Tour de l'IA...");
                    coupIa = iaMinMax.carteJouerIa(plateauInitial); // Profondeur de recherche = 13
                    if(coupIa == null){
                        coupIa = plateauInitial.getJoueur1().getHandScndPhase().get(0);
                    }
                    System.out.println("Carte choisie par l'IA: " + coupIa);
                    plateauInitial.jouerCarte(coupIa);
                }

                winningCard = ReglesDeJeu.determinerCarteGagnante(coupIa, coupAdversaire, plateauInitial);
                System.out.println("Carte gagnante : " + winningCard);
                plateauInitial.attribuerCarteSecondPhase(winningCard, new ReglesDeJeu());


                // Vérification de la fin de la partie
                if (plateauInitial.isEndOfGame()) {
                    winner = ReglesDeJeu.determinerGagnantPartie(plateauInitial.getJoueur1(), plateauInitial.getJoueur2());
                    System.out.println("Fin de la partie. Victoire de " + winner);
                    partieTerminee = true;
                    break;
                }


            }
            System.out.println("fin de partie num :"+ i);
            System.out.println("\n\n\n");
            if(winner.equals("MinMax")) {
                scoreMinMax++;
            }else{
                scoreFacile++;
            }
            i++;
        }

        System.out.println("Score MinMax : "+scoreMinMax);
        System.out.println("Score Facile : "+scoreFacile);

    }*/

