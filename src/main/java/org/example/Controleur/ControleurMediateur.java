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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    boolean IAreste, jouerCarteFini;
    Player gagnant;


    public ControleurMediateur(Jeu j, IA ia) {
        jeu = j;
        animations = new SequenceListe<>();
        dureePause = 4500;
        iterations = 60;
        animationsSupportees = false;
        animationsActives = false;
        if (ia != null) {
            iaJeu = ia;
        }
    }

    @Override
    public List<Card> getCardsFromPileScoreJ1(String factionName) {
        List<Card> List = jeu.getPlateau().getJoueur1().pileDeScore.getCardsOfFunction(factionName);
        if (List != null) {
            return List;
        } else {
            List = new ArrayList<>();
            return List;
        }
    }


    public String help(){
        return jeu.help();
    }


    public List<Card> getCardsFromPileScoreJ2(String factionName) {
        List<Card> List = jeu.getPlateau().getJoueur2().pileDeScore.getCardsOfFunction(factionName);
        if (List != null) {
            return List;
        } else {
            List = new ArrayList<>();
            return List;
        }
    }


    /* Getteurs pour la communication entre interface et moteur */
    public boolean getPhase() {
        return jeu.getPhase();
    }

    public boolean estIA() {
        return iaJeu != null;
    }

    public boolean IAestJoueurCourant() {
        return Objects.equals(jeu.getJoueurCourant().getName(), iaJeu.getName());
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

    public boolean estCarteJoueJ1() {
        return jeu.estCarteJoueJ1();
    }

    public boolean estCarteJoueJ2() {
        return jeu.estCarteJoueJ2();
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

    public String getNomJoueur1() {
        return jeu.getJoueur1().getName();
    }

    public String getNomJoueur2() {
        return jeu.getJoueur2().getName();
    }

    public int[][] getCarteJouable() {
        if (carteLeader != null) {
            return jeu.getCarteJouable(carteLeader, getHandCourant());
        } else {
            return getMainJoueurCourant();
        }
    }

    public int[][] getCarteGagnante() {
        if (carteLeader != null) {
            return jeu.getCarteJouableGagnante(carteLeader, getHandCourant());
        } else {
            return getMainJoueurCourant();
        }
    }

    public int[][] getCartePerdante() {
        if (carteLeader != null) {
            return jeu.getCarteJouablePerdante(carteLeader, getHandCourant());
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
    public void annuler() {
        if (pause) {
            return;
        }
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
        if (pause) {
            return;
        }
        jeu.refaireCoup();
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
        jeu.metAJour();
    }

    @Override
    public void nouvellePartie() {
        if (pause) {
            return;
        }
        //dureePause = 4500;
        jeu.clearStackAnnule();
        jeu.clearStackRefaire();
        jeu.getPlateau().initialiserJeu(jeu.estIA(), jeu.getNomJoueur(jeu.getJoueur1()), jeu.getNomJoueur(jeu.getJoueur2()));
        jeu.setCarteJouer();
        jeu.getPlateau().setPhase(true);

        carteLeader = null;
        jeu.metAJour();
        startDistributionAnimation(iterations);
    }


    public boolean isAnimationEnded() {
        if (mouvement != null)
            return mouvement.estTerminee();
        else
            return false;
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

    public void joueTour(int index) {
        pause = true;
        IAreste = false;
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
                jouerCarte(index, () -> {
                    if (iaJeu != null) {
                        if (jeu.getJoueur2() == jeu.getJoueurCourant() || jeu.getJoueur2() == gagnant) {
                            tourIA(() -> {
                                if (jeu.getJoueur2() == jeu.getJoueurCourant()) {
                                    tourIA(() -> {
                                    });
                                }
                            });
                        }
                    }
                });
            }
        }

        if (!(jeu.estCarteJoueJ1() && jeu.estCarteJoueJ2())) {
            pause = false;
        }

    }


    public void tourIA(Runnable callback) {

        dureePause = 4500;
        if (estFinPartie()) {
            // Calcul des scores
            System.out.println("La partie est terminée\n");
            callback.run();
        } else {
            if (getPhase())
                carteIA = iaJeu.jouerCoupPhase1(jeu.getPlateau());
            else
                carteIA = iaJeu.jouerCoupPhase2(jeu.getPlateau());

            Timer timer1 = new Timer(dureePause / 50, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jeu.getPlateau().jouerCarte(carteIA);
                    if (jeu.estCarteJoueJ1() && jeu.estCarteJoueJ2()) {
                        Player IAgagnant = getJoueurGagnant();
                        int card1Faction = getCarteJoueur1F();
                        int card2Faction = getCarteJoueur2F();
                        if (getPhase()) {
                            startAnimationGagne(iterations, IAgagnant);
                            startAnimationPerde(iterations, IAgagnant);
                        }
                        startAnimationDefausse(iterations, card1Faction, card2Faction, IAgagnant);

                        if ((jeu.getJoueur1().getHand().size() + jeu.getJoueur2().getHand().size() == 0) && getPhase()) {
                            startTransition();
                            dureePause = 6000;
                        }

                        if (!getPhase()) {
                            dureePause = 2000;
                        }

                        Timer timer = new Timer(dureePause, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                jeu.playTrick();
                                jeu.setCarteJouer();
                                startDistributionAnimation(iterations);
                                callback.run();
                            }
                        });
                        timer.setRepeats(false);
                        timer.start();
                        carteLeader = null;
                    } else {
                        carteLeader = carteIA;
                        jeu.switchJoueur();
                        callback.run();
                    }
                }
            });
            timer1.setRepeats(false);
            timer1.start();


        }
    }


    private void jouerCarte(int index, Runnable callback) {
        dureePause = 4500;
        Card carteJoue = jeu.getPlateau().jouerCarte(index);
        if (jeu.estCarteJoueJ1() && jeu.estCarteJoueJ2()) {
            gagnant = getJoueurGagnant();
            int card1Faction = getCarteJoueur1F();
            int card2Faction = getCarteJoueur2F();
            if (getPhase()) {
                startAnimationGagne(iterations, gagnant);
                startAnimationPerde(iterations, gagnant);
            }
            startAnimationDefausse(iterations, card1Faction, card2Faction, gagnant);

            if ((jeu.getJoueur1().getHand().size() + jeu.getJoueur2().getHand().size() == 0) && getPhase()) {
                startTransition();
                dureePause = 6000;
            }

            if (!getPhase()) {
                dureePause = 2000;
            }

            Timer timer = new Timer(dureePause, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jeu.playTrick();
                    jeu.setCarteJouer();
                    startDistributionAnimation(iterations);
                    callback.run();
                }
            });
            timer.setRepeats(false);
            timer.start();
            carteLeader = null;
        } else {
            carteLeader = carteJoue;
            jeu.switchJoueur();
            callback.run();
        }
    }

    /*/fonction qui me retourne dans les cartes dans la pile de score du joueur 1 d'une faction donnee
    public int[] getCardsFromPileScore(String factionName,Player player) {
        return jeu.getCardsFromPileScore(factionName);
    }*/


    @Override
    public void tictac() {
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
                        //testFin();
                        mouvement = null;
                    }
                    it.supprime();
                }
            }
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

    public void distribuerPerde() {
        vue.distribuerPerde();
    }


    public void distribuerDefausse() {
        vue.distribuerDefausse();
    }

    public void transition() {
        vue.transition();
    }


    public void startTransition() {
        vue.initializeAnimationTransition();
        mouvement = new AnimationTransition(this);
        animations.insereQueue(mouvement);
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

        String nomGagnant = gagnant.getName();
        vue.initializeAnimationGagne(totalIterations, joueur, nomGagnant);
        mouvement = new AnimationGagne(totalIterations, this);
        animations.insereQueue(mouvement);
    }

    public void startAnimationPerde(int totalIterations, Player gagnant) {
        int joueur;
        if (gagnant == jeu.getJoueur1()) {
            joueur = 1;
        } else {
            joueur = 2;
        }
        vue.initializeAnimationPerde(totalIterations, joueur);
        mouvement = new AnimationPerde(totalIterations, this);
        animations.insereQueue(mouvement);
    }

    public void startAnimationDefausse(int totalIterations, int card1Faction, int card2Faction, Player gagnant) {
        int delay = 210;
        if (!jeu.getPhase()) {
            delay = 30;
        }
        int joueur;
        if (gagnant == jeu.getJoueur1()) {
            joueur = 1;
        } else {
            joueur = 2;
        }
        vue.initializeAnimationDefausse(totalIterations, card1Faction, card2Faction, joueur);
        mouvement = new AnimationDefausse(totalIterations, this, delay);
        animations.insereQueue(mouvement);
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean getPause() {
        return pause;
    }

    public String getJoueurNomGagnant() {
        return jeu.getJoueurNomGagnant();
    }
}
