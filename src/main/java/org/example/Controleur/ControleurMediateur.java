package org.example.Controleur;

import org.example.Vue.CollecteurEvenements;
import org.example.Modele.Jeu;
import org.example.Patternes.Observable;

public class ControleurMediateur implements CollecteurEvenements {

    Jeu jeu;

    public ControleurMediateur(Jeu j) {
        jeu = j;
    }

    public int getPhase() {
        return jeu.getPhase();
    }
}
