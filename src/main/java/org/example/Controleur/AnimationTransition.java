package org.example.Controleur;


import org.example.Vue.NiveauGraphique;

public class AnimationTransition extends Animation {
    int duration;
    int initialDelay;
    boolean delayCompleted;


    public AnimationTransition(ControleurMediateur control) {
        super(1, control);
        duration = 60;
        control.setPause(true);
        initialDelay = 240;
        delayCompleted = false;
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
            control.transition();
        }
        if (estTerminee()) {
            control.setPause(false);
        }

    }

    @Override
    public boolean estTerminee() {
        return duration <= 0;
    }
}
