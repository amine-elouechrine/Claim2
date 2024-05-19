package org.example.Vue;

import org.example.Patternes.Observateur;

import java.io.IOException;

public interface CollecteurEvenements {
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

    void refaire();
    void annuler();
    void sauve(String filename);
    void restaure(String filename ) throws IOException;
    void nouvellePartie();

    void clicSouris(int index);
    void clicSourisJ2(int index);


}
