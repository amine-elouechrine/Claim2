package org.example.IA;

public class MinMaxVsIntermediaire {
    /**
     * public String MinMaxVsIntermediaire(IAMinMax iaMinMax , IA Intermediare){
     *         Card coupIa = null;
     *         Card coupAdversaire = null;
     *         Card winningCard;
     *         String winner = "";
     *         boolean partieTerminee = false;
     *
     *         // Création d'un plateau de jeu initial
     *         Plateau plateauInitial = createInitialPlateau();
     *
     *         while (!partieTerminee) {
     *             if (plateauInitial.getJoueurCourant().getName().equals("MinMax")) {
     *                 // Tour de l'IA
     *                 System.out.println("Tour de l'IA...");
     *                 coupIa = iaMinMax.carteJouerIa(plateauInitial); // Profondeur de recherche = 13
     *                 System.out.println("Carte choisie par l'IA: " + coupIa);
     *                 plateauInitial.jouerCarte(coupIa);
     *                 plateauInitial.switchJoueur();
     *             } else {
     *                 // Tour de l'adversaire
     *                 System.out.println("Tour de l'adversaire...");
     *                 coupAdversaire = Intermediare.jouerCoupPhase2(plateauInitial);
     *                 System.out.println("Carte jouée par l'adversaire : " + coupAdversaire);
     *                 plateauInitial.jouerCarte(coupAdversaire);
     *             }
     *
     *             if (plateauInitial.getJoueurCourant().getName().equals("Intermediaire")) {
     *                 System.out.println("Tour de l'adversaire...");
     *                 coupAdversaire = Intermediare.jouerCoupPhase2(plateauInitial);
     *                 System.out.println("Carte jouée par l'adversaire : " + coupAdversaire);
     *                 plateauInitial.jouerCarte(coupAdversaire);
     *             } else {
     *                 System.out.println("Tour de l'IA...");
     *                 coupIa = iaMinMax.carteJouerIa(plateauInitial); // Profondeur de recherche = 13
     *                 if (coupIa == null) {
     *                     coupIa = plateauInitial.getJoueur1().getHandScndPhase().get(0);
     *                 }
     *                 System.out.println("Carte choisie par l'IA: " + coupIa);
     *                 plateauInitial.jouerCarte(coupIa);
     *             }
     *
     *             winningCard = ReglesDeJeu.determinerCarteGagnante(coupIa, coupAdversaire, plateauInitial);
     *             System.out.println("Carte gagnante : " + winningCard);
     *             plateauInitial.attribuerCarteSecondPhase(winningCard, new ReglesDeJeu());
     *
     *             if (plateauInitial.isEndOfGame()) {
     *                 winner = ReglesDeJeu.determinerGagnantPartie(plateauInitial.getJoueur1(), plateauInitial.getJoueur2());
     *                 System.out.println("Fin de la partie. Victoire de " + winner);
     *                 partieTerminee = true;
     *             }
     *         }
     *
     *         return winner;
     *         }
     *
     *     }
     */
}
