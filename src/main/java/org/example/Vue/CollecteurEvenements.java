package org.example.Vue;

import org.example.Patternes.Observateur;

public interface CollecteurEvenements {
    boolean getPhase();

    int getNbCardsJ1P1();

    int getNbCardsJ1P2();

    int getNbCardsJ2P1();

    int getNbCardsJ2P2();

    void clicSouris(int index);
    void clicSourisJ2(int index);
}
