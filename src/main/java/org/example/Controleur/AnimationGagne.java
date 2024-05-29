package org.example.Controleur;


import org.example.Vue.NiveauGraphique;

public class AnimationGagne extends Animation {
    int duration;

    public AnimationGagne(int d, ControleurMediateur control) {
        super(1, control);
        duration = d;
    }

    @Override
    public void miseAJour() {
        duration--;
        control.distribuerGagne();
    }

    @Override
    public boolean estTerminee() {
        return duration <= 0;
    }
}
