package org.example.Vue;

import org.example.Patternes.Observateur;
import java.io.IOException;

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

    /* State check */
    boolean isJoueur1WinningFactionOnEquality(String factionName);

    boolean isJoueur2WinningFactionOnEquality(String factionName);

    boolean isJoueurCourantJoueur1();

    /* Adaptateurs */
    void refaire();
  
    void annuler();
  
    void sauve(String filename);
  
    void restaure(String filename) throws IOException;
  
    void nouvellePartie();

    void clicSouris(int index);

    void clicSourisJ2(int index);


}
