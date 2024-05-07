package org.example.Modele;

import org.example.Patternes.Observable;
import org.example.Vue.NiveauGraphique;

public class Jeu extends Observable {

    boolean phase1;


    public Jeu() {
        phase1 = true;
    }

    public int getPhase() {
        if(phase1) {
            return 1;
        }
        return 2;
    }

    public void switchPhase() {
        phase1 = !phase1;
    }

    public int getLignes() {
        return 4;
    }

    public int getColonnes() {
        return 6;
    }
}
