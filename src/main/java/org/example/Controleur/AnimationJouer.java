package org.example.Controleur;


import org.example.Vue.NiveauGraphique;

public class AnimationJouer extends Animation {
    int duration;

    public AnimationJouer(int d, ControleurMediateur control) {
        super(1, control);
        duration = d;
        control.setPause(true);

    }

    @Override
    public void miseAJour() {
        duration--;
        control.jouer();
        if (estTerminee()) {
            control.setPause(false);
        }

    }

    @Override
    public boolean estTerminee() {
        return duration <= 0;
    }
}
