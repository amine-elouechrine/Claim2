package org.example.Vue;


public interface InterfaceUtilisateur {
    void distribuer();

    void distribuerGagne();

    void distribuerDefausse();

    void initializeAnimationDistribuer(int totalIterations);

    void initializeAnimationGagne(int totalIterations, int joueur);

    void initializeAnimationDefausse(int totalIterations, int card1Faction, int card2Faction);
}
