package org.example.IA;

import org.example.Modele.Card;
import org.example.Modele.Plateau;
import org.example.Modele.ReglesDeJeu;

import java.util.Scanner;

import static org.example.IA.TestIAMinMax.carteAleatoire;

public class testIAFirstPhase {

    public static Plateau jouerPatie1(){
        Card coupIa = null;
        Card coupAdversaire = null;
        Card winningCard;
        String winner = "";

        // creation de l'ia MinMax 1st phase (heurestique)
        IAFirstPhase iaFirstPhase = new IAFirstPhase();
        Intermediare intermediare = new Intermediare();

        TestFunctions testFunctions = new TestFunctions();
        Plateau plateau = testFunctions.createInitialPlateau();
        boolean partieTerminee = false;

        while (!partieTerminee) {
            // set carte afficher
            Card carcarteAfficher = plateau.getPioche().getCard();
            plateau.setCardAffiche(carcarteAfficher);
            System.out.println(" carte Afficher : " + carcarteAfficher);

            if (plateau.getJoueurCourant().getName().equals("MinMax")) {
                System.out.println("Tour de l'IA MinMax...");
                coupIa = iaFirstPhase.jouerCarteIA(plateau);
                //coupIa = intermediare.jouerCoupPhase1(plateau);
                System.out.println("Carte choisie par l'IA: " + coupIa);
                plateau.jouerCarte(coupIa);
                plateau.setCarteJoueur1(coupIa);
                plateau.switchJoueur();
            } else {
                // Tour de l'adversaire
                System.out.println("Tour de l'adversaire Intermediaire...");
                coupAdversaire = intermediare.jouerCoupPhase1(plateau);
                //coupAdversaire = iaFirstPhase.jouerCarteIA(plateau);
                System.out.println("Carte jouée par l'adversaire : " + coupAdversaire);
                plateau.jouerCarte(coupAdversaire);
                plateau.setCarteJoueur2(coupAdversaire);
                plateau.switchJoueur();
            }
            //--------------------------------------------------------------------------
            if (plateau.getJoueurCourant().getName().equals("Facile")) {
                System.out.println("Tour de l'adversaire Interdmediaire...");
                coupAdversaire = intermediare.jouerCoupPhase1(plateau);
                //coupAdversaire = iaFirstPhase.jouerCarteIA(plateau);
                System.out.println("Carte jouée par l'adversaire : " + coupAdversaire);
                plateau.jouerCarte(coupAdversaire);
                plateau.setCarteJoueur2(coupAdversaire);
                // attribution des cartes
                //winningCard = ReglesDeJeu.carteGagnante(coupIa, coupAdversaire, plateau);
            } else {
                System.out.println("Tour de l'IA...");
                coupIa = iaFirstPhase.jouerCarteIA(plateau); // Profondeur de recherche = 13
                //coupIa = intermediare.jouerCoupPhase1(plateau);
                System.out.println("Carte choisie par l'IA: " + coupIa);
                plateau.jouerCarte(coupIa);
                plateau.setCarteJoueur1(coupIa);
                // attribution des cartes
                //winningCard = ReglesDeJeu.carteGagnante(coupAdversaire,coupIa, plateau);
            }
            winningCard = ReglesDeJeu.carteGagnante(coupIa,coupAdversaire, plateau);
            System.out.println("Carte gagnante : " + winningCard);
            plateau.attribuerCarteFirstPhase(winningCard, new ReglesDeJeu());

            if (plateau.estFinPhase(plateau.getPhase())) {
                partieTerminee = true;
            }
            System.out.println("\n\n\n");
        }
        // passer a la phase 2
        plateau.setPhase(false);
        return plateau;

    }


    public static String jouerPartieFacileMinMax(Plateau plateauInitial) {
        Card coupIa = null;
        Card coupAdversaire = null;
        Card winningCard;
        String winner = "";
        boolean partieTerminee = false;

        // creation de l'ia MinMax
        IAMinMax iaMinMax = new IAMinMax();
        Intermediare intermediare = new Intermediare();

        // Création d'un plateau de jeu initial

        while (!partieTerminee) {
            Card premiere;
            Card deuxieme;
            if (plateauInitial.getJoueurCourant().getName().equals("MinMax")) {
                // Tour de l'IA
                System.out.println("Tour de l'IA...");
                premiere = iaMinMax.carteJouerIa(plateauInitial); // Profondeur de recherche = 13
                System.out.println("Carte choisie par l'IA: " + premiere);
                plateauInitial.jouerCarte(premiere);
                plateauInitial.switchJoueur();
            } else {
                // Tour de l'adversaire
                System.out.println("Tour de l'adversaire...");
                //premiere = intermediare.jouerCoupPhase2(plateauInitial);
                premiere = carteAleatoire(plateauInitial);
                System.out.println("Carte jouée par l'adversaire : " + premiere);
                plateauInitial.jouerCarte(premiere);
                plateauInitial.switchJoueur();
            }

            if (plateauInitial.getJoueurCourant().getName().equals("Facile")) {
                System.out.println("Tour de l'adversaire...");
                deuxieme = carteAleatoire(plateauInitial);

                System.out.println("Carte jouée par l'adversaire : " + deuxieme);
                plateauInitial.jouerCarte(deuxieme);
                //winningCard = ReglesDeJeu.carteGagnante(coupIa, coupAdversaire, plateauInitial);
            } else {
                System.out.println("Tour de l'IA...");
                deuxieme = iaMinMax.carteJouerIa(plateauInitial); // Profondeur de recherche = 13
                if (deuxieme == null) {
                    deuxieme = plateauInitial.getJoueur1().getHandScndPhase().get(0);
                }
                //winningCard = ReglesDeJeu.carteGagnante(coupAdversaire, coupIa, plateauInitial);
                System.out.println("Carte choisie par l'IA: " + deuxieme);
                plateauInitial.jouerCarte(deuxieme);
            }

            winningCard = ReglesDeJeu.carteGagnante(premiere, deuxieme, plateauInitial);
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

        for (int i = 0; i < 1000; i++) {
            Plateau plateau;
            String winner;

             // Test d'une partie compelete
            plateau = jouerPatie1();
            System.out.println("Fin de la phase 1 \n\n\n");
            plateau.setCarteJoueur1(null);
            plateau.setCarteJoueur2(null);

            winner = jouerPartieFacileMinMax(plateau);


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
}
