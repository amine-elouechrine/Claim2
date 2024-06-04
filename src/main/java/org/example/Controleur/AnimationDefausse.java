package org.example.Controleur;


import org.example.Vue.NiveauGraphique;

public class AnimationDefausse extends Animation {
    int duration;
    int initialDelay;
    boolean delayCompleted;


    public AnimationDefausse(int d, ControleurMediateur control,int delay) {
        super(1, control);
        initialDelay = delay;
        duration = d;
        delayCompleted = false;
        control.setPause(true);
    }

    @Override
    public void miseAJour() {
        if (!delayCompleted) {
            initialDelay--;
            if (initialDelay <= 0) {
                delayCompleted = true;
            }
        } else {
            duration--;
            control.distribuerDefausse();
        }

    }

    @Override
    public boolean estTerminee() {
        return duration <= 0;
    }
}
