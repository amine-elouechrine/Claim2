package org.example.Vue;


public interface InterfaceUtilisateur {
    void distribuer();

    void distribuerGagne();

    void distribuerPerde();

    void distribuerDefausse();

    void jouer();


    void initializeAnimationDistribuer(int totalIterations);

    void initializeAnimationGagne(int totalIterations, int joueur);

    void initializeAnimationPerde(int totalIterations, int joueur);

    void initializeAnimationDefausse(int totalIterations, int card1Faction, int card2Faction, int joueur);

    void initializeAnimationJouer(int totalIterations, int index, int joueur);

}
