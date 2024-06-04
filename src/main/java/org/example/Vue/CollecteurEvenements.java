package org.example.Vue;


import org.example.Modele.Card;
import org.example.Modele.PileDeScore;

import org.example.Modele.Player;

import org.example.Patternes.Observateur;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface CollecteurEvenements {

    /* Getteurs */
    boolean getPhase();

    int getNbCardsJ1P1();

    int getNbCardsJ1P2();

    int getNbCardsJ2P1();

    int getNbCardsJ2P2();

    int[][] getHandJ1P1();

    int[][] getHandJ2P1();

    int[][] getHandJ1P2();

    int[][] getHandJ2P2();

    String getNomJoueurCourant();
    String getNomJoueur1();
    String getNomJoueur2();

    int[][] getCarteJouable();

    int getCarteJoueur1V();

    int getCarteJoueur1F();

    int getCarteJoueur2V();

    int getCarteJoueur2F();

    int getCarteAfficheeFactionScore();

    int getCarteAfficheeValeur();

    int getNbCardFactionFromPileScoreJ1(String factionName);

    int getNbCardFactionFromPileScoreJ2(String factionName);

    int getMaxValueoOfFactionFromPileScoreJ1(String factionName);

    int getMaxValueoOfFactionFromPileScoreJ2(String factionName);

    int getMaxValueFromPileScore(String factionName);

    public int[][] getCarteGagnante();

    public int[][] getCartePerdante();
  
    Player getJoueurGagnant();

    /* State check */
    boolean isJoueur1WinningFactionOnEquality(String factionName);

    boolean isJoueur2WinningFactionOnEquality(String factionName);

    boolean isJoueurCourantJoueur1();
  
    boolean estCarteJoueJ1();
  
    boolean estCarteJoueJ2();
  
    boolean estFinPartie();

    boolean isAnimationEnded();

    boolean getPause();

    /* Adaptateurs */
    void refaire();
  
    void annuler() ;
  
    void sauve(String filename) throws FileNotFoundException;
  
    void restaure(String filename) throws IOException;
  
    void nouvellePartie();

    void clicSouris(int index);

    void clicSourisJ2(int index);

    void tictac();

    void ajouteInterfaceUtilisateur(InterfaceUtilisateur vue);

    void startDistributionAnimation(int totalIterations);

    List<Card> getCardsFromPileScoreJ1(String factionName);

    List<Card> getCardsFromPileScoreJ2(String factionName);

    String getJoueurNomGagnant();

    String help();
}
