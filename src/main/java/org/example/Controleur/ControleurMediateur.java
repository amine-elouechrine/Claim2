package org.example.Controleur;

import org.example.IA.IA;
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
import java.io.FileNotFoundException;
import java.io.IOException;

public class ControleurMediateur implements CollecteurEvenements {

    Jeu jeu;
    InterfaceUtilisateur vue;
    Card carteLeader;
    Sequence<Animation> animations;
    int dureePause;
    int iterations;
    Animation mouvement;
    boolean animationsSupportees, animationsActives, pause;
    boolean gagneFinished;
    IA iaJeu;
    boolean jouable = true;
    Card carteIA;

    public ControleurMediateur(Jeu j, IA ia) {
        jeu = j;
        animations = new SequenceListe<>();
        dureePause = 1600;
        iterations = 60;
        animationsSupportees = false;
        animationsActives = false;
        if (ia != null) {
            iaJeu = ia;
        }
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
        return (getJoueurCourant().equals(jeu.getJoueur1()));
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

    public Player getJoueurCourant() {
        return jeu.getJoueurCourant();
    }

    public int[][] getCarteJouable() {
        if (carteLeader != null) {
            return jeu.getCarteJouable(carteLeader, getHandCourant());
        } else {
            return getMainJoueurCourant();
        }
    }

    public String getNomJoueurCourant() {
        return jeu.getNomJoueur(getJoueurCourant());
    }

    public int getCarteJoueur1V() {
        return jeu.getCarteJoueur1V();
    }

    public Player getJoueurGagnant() {
        return jeu.getJoueurGagnant();
    }

    /* Methodes qui modifient le jeu */

    @Override
    public void annuler() throws IOException {
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

        if (jeu.getPlateau().getCarteJoueur1() != null) {
            carteLeader = jeu.getPlateau().getCarteJoueur1();
        } else if (jeu.getPlateau().getCarteJoueur2() != null) {
            carteLeader = jeu.getPlateau().getCarteJoueur2();
        } else carteLeader = null;
        System.out.println("carte leader " + carteLeader);
        for (Card carte : jeu.getPlateau().getJoueurCourant().getHandScndPhase().getAllCards()) {
            System.out.println(carte);
        }
        jeu.metAJour();
    }

    @Override
    public void nouvellePartie() {
        jeu.getPlateau().initialiserJeu();
        jeu.setCarteJouer();
        jeu.getPlateau().setPhase(true);
        carteLeader = null;
        jeu.metAJour();
        startDistributionAnimation(iterations);
    }

    public boolean estFinPartie() {
        return jeu.estFinPartie();
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

    public void tourIA() {
        if (estFinPartie()) {
            // Calcul des scores
            System.out.println("La partie est terminée\n");
        } else {
            if (getPhase())
                carteIA = iaJeu.jouerCoupPhase1(jeu.getPlateau());
            else
                carteIA = iaJeu.jouerCoupPhase2(jeu.getPlateau());

            jouerCarteIA(carteIA);
        }
    }

    public void joueTour(int index) {

        if (estFinPartie()) {
            // Calcul des scores
            System.out.println("La partie est terminée\n");
        } else {
            // Application des règles de jeu pour la selection de carte
            if (carteLeader != null) {
                jouable = jeu.estCarteJouable(carteLeader, index);
            }
            if (jouable) {
                jeu.addAction();
                jouerCarte(index);
            }
            if (iaJeu != null)
                while (jeu.getJoueur2() == jeu.getJoueurCourant()) {
                    tourIA();
                }
        }
    }

    private void jouerCarteIA(Card carte) {
        jeu.getPlateau().jouerCarte(carte);
        // jeu.getJoueur2().getHand().removeCard(carte);
        if (jeu.estCarteJoueJ1() && jeu.estCarteJoueJ2()) {
            jeu.playTrick();
            jeu.setCarteJouer();
            carteLeader = null;
        } else {
            carteLeader = carte;
            jeu.switchJoueur();
        }
    }


    private void jouerCarte(int index) {
        Card carteJoue = jeu.getPlateau().jouerCarte(index);
        if (jeu.estCarteJoueJ1() && jeu.estCarteJoueJ2()) {
            pause = true;
            Player gagnant = getJoueurGagnant();
            int card1Faction = getCarteJoueur1F();
            int card2Faction = getCarteJoueur2F();
            startAnimationGagne(iterations, gagnant);
            startAnimationDefausse(iterations, card1Faction, card2Faction);


            Timer timer = new Timer(200 + dureePause, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jeu.playTrick();
                    jeu.setCarteJouer();

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
            if (estFinPartie())
                System.exit(0);
        }
    }


    public void ajouteInterfaceUtilisateur(InterfaceUtilisateur v) {
        vue = v;
    }


    public void distribuer() {
        vue.distribuer();
    }

    public void distribuerGagne() {
        vue.distribuerGagne();
    }

    public void distribuerDefausse() {
        vue.distribuerDefausse();
    }


    public void startDistributionAnimation(int totalIterations) {
        vue.initializeAnimationDistribuer(totalIterations);
        mouvement = new AnimationDistribuer(totalIterations, this);
        animations.insereQueue(mouvement);
    }

    public void startAnimationGagne(int totalIterations, Player gagnant) {
        int joueur;
        if (gagnant == jeu.getJoueur1()) {
            joueur = 1;
        } else {
            joueur = 2;
        }
        vue.initializeAnimationGagne(totalIterations, joueur);
        mouvement = new AnimationGagne(totalIterations, this);
        animations.insereQueue(mouvement);
    }

    public void startAnimationDefausse(int totalIterations, int card1Faction, int card2Faction) {
        vue.initializeAnimationDefausse(totalIterations, card1Faction, card2Faction);
        mouvement = new AnimationDefausse(totalIterations, this);
        animations.insereQueue(mouvement);
    }
}
