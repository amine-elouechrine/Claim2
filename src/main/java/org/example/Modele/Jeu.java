package org.example.Modele;

import org.example.Patternes.Observable;
import org.example.Vue.NiveauGraphique;

public class Jeu extends Observable {

    public int getLignes() {
        return 4;
    }

    public int getColonnes() {
        return 6;
    }
}
