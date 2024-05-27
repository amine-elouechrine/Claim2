package org.example.Controleur;

import org.example.Modele.Card;
import org.example.Modele.Hand;
import org.example.Modele.Jeu;
import org.example.Modele.Player;
import org.example.Structures.Iterateur;
import org.example.Structures.Sequence;
import org.example.Structures.SequenceListe;
import org.example.Vue.CollecteurEvenements;
import org.example.Modele.GestionAnnuleRefaire;
import org.example.Vue.CollecteurEvenements;
import org.example.Modele.Jeu;
import org.example.Patternes.Observable;
import org.example.Vue.InterfaceGraphique;
import org.example.Vue.InterfaceUtilisateur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ControleurMediateur implements CollecteurEvenements {

    Jeu jeu;
    InterfaceUtilisateur vue;
    Card carteLeader;

    Sequence<Animation> animations;
    int dureePause;
    int iterations;
    Animation mouvement;
    boolean animationsSupportees, animationsActives, pause;


    boolean jouable = true;

    public ControleurMediateur(Jeu j) {
        jeu = j;
        animations = new SequenceListe<>();
        dureePause = 1600;
        iterations = 60;
        animationsSupportees = false;
        animationsActives = false;
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
        jeu.annuler();
        jeu.metAJour();
    }

    @Override
    public void refaire() {
        jeu.refaire();
        jeu.metAJour();
    }

    @Override
    public void sauve(String filename) {
        jeu.sauve(filename);
        jeu.metAJour();
    }

    @Override
    public void restaure(String filename) throws IOException {
        jeu.restaure(filename);
        jeu.metAJour();
    }

    @Override
    public void nouvellePartie() {
        jeu.getPlateau().initialiserJeu();
        jeu.setCarteJouer();
        jeu.metAJour();
        startDistributionAnimation(iterations);
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
        if (pause) {
            return;
        }
        if (index == -1) {
            System.out.println("Clic ailleurs que sur une carte\n");
        } else {
            joueTour(index);
        }
        jeu.metAJour();
    }

    public void clicSourisJ2(int index) {
        if (pause) {
            return;
        }
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

            // On joue le plie
            // Ajouter temporisation / Animation pour la bataille et l'attribution des cartes après le plie
            // mouvement = new AnimationPause(dureePause, this);
            // animations.insereQueue(mouvement);
            pause = true;
            Timer timer = new Timer(dureePause, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jeu.playTrick();
                    jeu.setCarteJouer();
                    jeu.metAJour();
                    startDistributionAnimation(iterations);
                    pause = false;
                }
            });
            timer.setRepeats(false);
            timer.start();
            carteLeader = null;
        } else {
            carteLeader = carteJoue;
            jeu.switchJoueur();
        }

    }


    @Override
    public void tictac() {
        // On sait qu'on supporte les animations si on reçoit des évènements temporels
        if (!animationsSupportees) {
            animationsSupportees = true;
            animationsActives = true;
        }
        if (animationsActives) {
            Iterateur<Animation> it = animations.iterateur();
            while (it.aProchain()) {
                Animation a = it.prochain();
                a.tictac();
                if (a.estTerminee()) {
                    if (a == mouvement) {
                        testFin();
                        mouvement = null;
                    }
                    it.supprime();
                }
            }
        }
    }

    private void testFin() {
        if (jeu.estFinPhase1()) {
            jeu.switchPhase();
            if (jeu.estFinPartie())
                System.exit(0);
        }
    }


    public void ajouteInterfaceUtilisateur(InterfaceUtilisateur v) {
        vue = v;
    }


    public void distribuer() {
        vue.distribuer();
    }

    public void startDistributionAnimation(int totalIterations) {
        vue.initializeAnimation(totalIterations);
        mouvement = new AnimationDistribuer(totalIterations, this);
        animations.insereQueue(mouvement);
    }

}
