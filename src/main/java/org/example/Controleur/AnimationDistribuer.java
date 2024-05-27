package org.example.Controleur;


import org.example.Vue.NiveauGraphique;

public class AnimationDistribuer extends Animation {
    int duration;
    public AnimationDistribuer(int d, ControleurMediateur control) {
        super(1, control);
        duration = d;
    }

    @Override
    public void miseAJour() {
        duration--;
        control.distribuer();
        //System.out.println("AnimationPause miseAJour called, remaining duration: " + duration);
    }

    @Override
    public boolean estTerminee() {
        return duration <= 0;
    }
}
