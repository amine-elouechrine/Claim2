package org.example.IA;

import java.util.*;

import org.example.Modele.*;

import static org.example.IA.IA.determinerGagnantMancheIA;

public class TestIAMinMax {

    // fonction qui simmule le deroulement de la 1er phase Ia facile contre strategie gagnante
    public static Plateau jouerPatie1(int type){
        Card coupIa = null;
        Card coupAdversaire = null;
        Card winningCard;
        String winner = "";
        boolean partieTerminee = false;

        // appel aux fonctions de teste de l'ia
        TestFunctions testFunctions = new TestFunctions();

        // creation de l'ia MinMax 1st phase (heurestique)
        System.out.println("choix de L'heurestique 1st phase : MinMax");
        IAFirstPhase iaFirstPhase = new IAFirstPhase();
        Intermediare intermediare = new Intermediare();


        Plateau plateauInitial = TestFunctions.createInitialPlateau();
        // Création d'un plateau de jeu initial
        while (!partieTerminee) {
            // set carte afficher
            Card carcarteAfficher = plateauInitial.getPioche().getCard();
            plateauInitial.setCardAffiche(carcarteAfficher);
            System.out.println(" carte Afficher : " + carcarteAfficher);

            if (plateauInitial.getJoueurCourant().getName().equals("MinMax")) {
                System.out.println("Tour de l'IA...");
                coupIa = iaFirstPhase.jouerCarteIA(plateauInitial);
                //coupIa = intermediare.jouerCoupPhase1(plateauInitial);
                System.out.println("Carte choisie par l'IA: " + coupIa);
                plateauInitial.jouerCarte(coupIa);
                plateauInitial.setCarteJoueur1(coupIa);
                plateauInitial.switchJoueur();
            }else{
                // Tour de l'adversaire
                System.out.println("Tour de l'adversaire...");
                coupAdversaire = carteAleatoire(plateauInitial);
                System.out.println("Carte jouée par l'adversaire : " + coupAdversaire);
                plateauInitial.jouerCarte(coupAdversaire);
                plateauInitial.setCarteJoueur2(coupAdversaire);
                plateauInitial.switchJoueur();
            }
            // -------------------------------------------------------------------------
            if (plateauInitial.getJoueurCourant().getName().equals("Facile")) {
                System.out.println("Tour de l'adversaire...");
                coupAdversaire = carteAleatoire(plateauInitial);
                System.out.println("Carte jouée par l'adversaire : " + coupAdversaire);
                plateauInitial.jouerCarte(coupAdversaire);
                plateauInitial.setCarteJoueur2(coupAdversaire);
                // attribution des cartes
                //winningCard = ReglesDeJeu.carteGagnante(coupIa, coupAdversaire, plateauInitial);
            } else {
                System.out.println("Tour de l'IA...");
                coupIa = iaFirstPhase.jouerCarteIA(plateauInitial); // Profondeur de recherche = 13
                //coupIa = intermediare.jouerCoupPhase1(plateauInitial);
                System.out.println("Carte choisie par l'IA: " + coupIa);
                plateauInitial.jouerCarte(coupIa);
                plateauInitial.setCarteJoueur1(coupIa);
                // attribution des cartes
                //winningCard = ReglesDeJeu.carteGagnante(coupAdversaire,coupIa, plateauInitial);
            }

            winningCard = ReglesDeJeu.carteGagnante(coupIa, coupAdversaire, plateauInitial);
            System.out.println("Carte gagnante : " + winningCard);
            plateauInitial.attribuerCarteFirstPhase(winningCard, new ReglesDeJeu());

            if (plateauInitial.estFinPhase(plateauInitial.getPhase())) {
                partieTerminee = true;
            }
            System.out.println("\n\n\n");

        }
        // passer a la phase 2
        plateauInitial.setPhase(false);
        return plateauInitial;
    }

    /*public Card coupJouer(int type, Plateau plateau){
        IAFirstPhase iaFirstPhase = new IAFirstPhase();
        Intermediare intermediare = new Intermediare();

        Card coup = null;
        if(type == 1){
            coupIa = jouerCoupPhase1(plateau);
        }else if(type == 2){
            coupIa = jouerCoupPhase2(plateau);
        }else{
            coupIa = jouerCarteAleatoire(plateau.getJoueurCourant().getHand());
        }
        return coup;
    }*/



    public static String jouerPartieFacileMinMax(Plateau plateauInitial) {
        Card coupIa = null;
        Card coupAdversaire = null;
        Card winningCard;
        String winner = "";
        boolean partieTerminee = false;

        // creation de l'ia MinMax
        IAMinMax iaMinMax = new IAMinMax();

        // Création d'un plateau de jeu initial

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
                //winningCard = ReglesDeJeu.carteGagnante(coupAdversaire, coupIa, plateauInitial);
            } else {
                System.out.println("Tour de l'IA...");
                coupIa = iaMinMax.carteJouerIa(plateauInitial); // Profondeur de recherche = 13
                if (coupIa == null) {
                    coupIa = plateauInitial.getJoueur1().getHandScndPhase().get(0);
                }
                System.out.println("Carte choisie par l'IA: " + coupIa);
                plateauInitial.jouerCarte(coupIa);
                //winningCard = ReglesDeJeu.carteGagnante(coupIa, coupAdversaire, plateauInitial);
            }

            winningCard = ReglesDeJeu.carteGagnante(coupIa, coupAdversaire, plateauInitial);
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

        // laisser l'utilisateur choisir la strategie de l'ia
        Scanner s = new Scanner(System.in) ;
        System.out.println("pour teste la phase 2 de l'ia MinMax entrer 1: ");
        System.out.println("pour teste unne partie complete de l'ia MinMax entrer 2: ");
        int test = s.nextInt();

        System.out.println("entre 1 pour : MinMax vs Facile");
        System.out.println("entre 2 pour : MinMax vs Intermediaire");
        System.out.println("entre 3 pour : Intermediaire vs Facile");
        int type = s.nextInt();

        for (int i = 0; i < 100; i++) {
            Plateau plateau;
            String winner;

            if(test == 1) {
                plateau = TestFunctions.setPlateauPhase2();
                winner = jouerPartieFacileMinMax(plateau);

            }else{ // Test d'une partie compelete
                plateau = jouerPatie1(type);
                System.out.println("Fin de la phase 1 \n\n\n");
                plateau.setCarteJoueur1(null);
                plateau.setCarteJoueur2(null);

                winner = jouerPartieFacileMinMax(plateau);
            }

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

        Hand cartesJoueurCourant ;
        GeneralPlayer joueurCourant = plateau.getJoueurCourant();
        if(plateau.getPhase()){
            cartesJoueurCourant = joueurCourant.getHand();
        }else{
            cartesJoueurCourant = joueurCourant.getHandScndPhase();
        }
        List<Card> cartesJouable;
        if(plateau.estLeader()){
            cartesJouable = cartesJoueurCourant.getCards();
        }else{
            Card carteAdversaire = plateau.getCarteJoueur1();
            cartesJouable = ReglesDeJeu.cartesJouables(carteAdversaire , cartesJoueurCourant);
        }
        // choisir une carte aleatoire de cartes Jouables
        Random rand = new Random();
        int index = rand.nextInt(cartesJouable.size());
        return cartesJouable.get(index);
    }


}


