package org.example.Controleur;


import org.example.Vue.NiveauGraphique;

public class AnimationDistribuer extends Animation {
    int duration;

    public AnimationDistribuer(int d, ControleurMediateur control) {
        super(1, control);
        duration = d;
        control.setPause(true);

    }

    @Override
    public void miseAJour() {
        duration--;
        control.distribuer();
        if (estTerminee()) {
            control.setPause(false);
        }

    }

    @Override
    public boolean estTerminee() {
        return duration <= 0;
    }
}
