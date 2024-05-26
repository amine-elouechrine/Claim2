package org.example.Controleur;

import org.example.Modele.Card;
import org.example.Modele.Hand;
import org.example.Modele.Jeu;
import org.example.Modele.Player;
import org.example.Vue.CollecteurEvenements;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ControleurMediateur implements CollecteurEvenements {

    Jeu jeu;
    Card carteLeader;
    boolean jouable = true;

    public ControleurMediateur(Jeu j) {
        jeu = j;
    }

    /* Getteurs pour la communication entre interface et moteur */
    public boolean getPhase() {
        return jeu.getPhase();
    }

    public int getNbCardsJ1P1() {
        return jeu.getHandJ1P1().size();
    }

    public int getNbCardsJ2P1() {
        return jeu.getHandJ2P1().size();
    }

    public int getNbCardsJ1P2() {
        return jeu.getHandJ1P2().size();
    }

    public int getNbCardsJ2P2() {
        return jeu.getHandJ2P2().size();
    }

    public int[][] getHandJ1P1() {
        return jeu.getMainJoueur1Phase1();
    }

    public int[][] getHandJ2P1() {
        return jeu.getMainJoueur2Phase1();
    }

    public int[][] getHandJ1P2() {
        return jeu.getMainJoueur1Phase2();
    }

    public int[][] getHandJ2P2() {
        return jeu.getMainJoueur2Phase2();
    }

    public int[][] getMainJoueurCourant() {
        return jeu.getHand(jeu.getPlateau().getJoueurCourant().getHand());
    }

    public int getCarteAfficheeFactionScore() {
        return jeu.getCarteAfficheeFactionScore();
    }

    public int getCarteAfficheeValeur() {
        return jeu.getCarteAfficheeValeur();
    }

    public boolean isJoueurCourantJoueur1() {
        return (getPlayerCourant().Name.equals("Joueur 1"));
    }

    public int getNbCardFactionFromPileScoreJ1(String factionName) {
        return jeu.getNbCardFactionFromPileScoreJ1(factionName);
    }

    public int getNbCardFactionFromPileScoreJ2(String factionName) {
        return jeu.getNbCardFactionFromPileScoreJ2(factionName);
    }

    public int getMaxValueoOfFactionFromPileScoreJ1(String factionName) {
        return jeu.getMaxValueoOfFactionFromPileScoreJ1(factionName);
    }

    public int getMaxValueoOfFactionFromPileScoreJ2(String factionName) {
        return jeu.getMaxValueoOfFactionFromPileScoreJ2(factionName);
    }

    public int getMaxValueFromPileScore(String factionName) {
        return Math.max(getMaxValueoOfFactionFromPileScoreJ1(factionName), getMaxValueoOfFactionFromPileScoreJ2(factionName));
    }

    public boolean isJoueur1WinningFactionOnEquality(String factionName) {
        return (getMaxValueoOfFactionFromPileScoreJ1(factionName) > getMaxValueoOfFactionFromPileScoreJ2(factionName));
    }

    public boolean isJoueur2WinningFactionOnEquality(String factionName) {
        return (getMaxValueoOfFactionFromPileScoreJ1(factionName) < getMaxValueoOfFactionFromPileScoreJ2(factionName));
    }

    public Hand getHandCourant() {
        return jeu.getPlateau().getJoueurCourant().getHand();
    }

    public Player getPlayerCourant() {
        return jeu.getPlateau().getJoueurCourant();
    }

    public int[][] getCarteJouable() {
        if (carteLeader != null) {
            return jeu.getCarteJouable(carteLeader, getHandCourant());
        } else {
            return getMainJoueurCourant();
        }
    }

    public String getNomJoueurCourant() {
        return jeu.getNomJoueur(getPlayerCourant());
    }

    public int getCarteJoueur1V() {
        return jeu.getCarteJoueur1V();
    }

    @Override
    public void annuler() {
        jeu.annulerCoup();
        if (jeu.getPlateau().getCarteJoueur1() == null && jeu.getPlateau().getCarteJoueur2() != null) {
            carteLeader = jeu.getPlateau().getCarteJoueur2();
        } else if (jeu.getPlateau().getCarteJoueur1() != null && jeu.getPlateau().getCarteJoueur2() == null) {
            carteLeader = jeu.getPlateau().getCarteJoueur1();
        } else {
            carteLeader = null;
        }
        jeu.metAJour();
    }

    @Override
    public void refaire() {
        jeu.refaireCoup();
        System.out.println("carte Leader " + carteLeader);
        /*if (jeu.getPlateau().getCarteJoueur1() != null ||jeu.getPlateau().getCarteJoueur2() != null ) {
            carteLeader=null;
        }
        else{
            if (jeu.getPlateau().getJoueurCourant() == jeu.getPlateau().getJoueur1()) {
                carteLeader=jeu.getPlateau().getCarteJoueur1();
            }
            else{
                carteLeader=jeu.getPlateau().getCarteJoueur2();
            }
        }*/
        jeu.metAJour();
    }

    @Override
    public void sauve(String filename) throws FileNotFoundException {
        jeu.sauve(filename);
        jeu.metAJour();
    }

    @Override
    public void restaure(String filename) throws IOException {
        jeu.restaure(filename);
        if(jeu.getPlateau().getCarteJoueur1()!=null){
            carteLeader=jeu.getPlateau().getCarteJoueur1();
        }
        else if (jeu.getPlateau().getCarteJoueur2()!=null) {
            carteLeader=jeu.getPlateau().getCarteJoueur2();
        }else carteLeader=null;

        jeu.metAJour();
    }

    @Override
    public void nouvellePartie() {
        jeu.getPlateau().initialiserJeu();
        jeu.setCarteJouer();
        carteLeader=null;
        jeu.metAJour();
    }

    public int getCarteJoueur1F() {
        return jeu.getCarteJoueur1F();
    }

    public int getCarteJoueur2V() {
        return jeu.getCarteJoueur2V();
    }

    public int getCarteJoueur2F() {
        return jeu.getCarteJoueur2F();
    }

    /* Récupération d'un clique de souris pour un tour de jeu */
    public void clicSouris(int index) {
        if (index == -1) {
            System.out.println("Clic ailleurs que sur une carte\n");
        } else {
            joueTour(index);
        }
        jeu.metAJour();
    }

    public void clicSourisJ2(int index) {
        if (index == -1) {
            System.out.println("Clic ailleurs que sur une carte\n");
        } else {
            joueTour(index);
        }
        jeu.metAJour();
    }

    public void joueTour(int index) {

        if (jeu.estFinPartie()) {
            // Calcul des scores
            System.out.println("La partie est terminée\n");
        }

        // Application des règles de jeu pour la selection de carte
        if (carteLeader != null) {
            jouable = jeu.estCarteJouable(carteLeader, index);
        }

        if (jouable) {
            jeu.addAction();
            jouerCarte(index);
        }
        // Ajouter temporisation / animation

        // L'IA joue une carte
        // IA.joue() ?

        // Ajouter temporisation / animation pour la carte jouer par l'IA

    }

    private void jouerCarte(int index) {
        Card carteJoue = jeu.getPlateau().jouerCarte(index);
        if (jeu.estCarteJoueJ1() && jeu.estCarteJoueJ2()) {
            jeu.playTrick();
            // On joue le plie
            // Ajouter temporisation / Animation pour la bataille et l'attribution des cartes après le plie
            jeu.setCarteJouer();
            carteLeader = null;
        } else {
            carteLeader = carteJoue;
            jeu.switchJoueur();
        }
    }
}
