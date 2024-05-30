package org.example.IA;

import org.example.Modele.*;

public class TestMinMaxVsIntermediaire {
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

