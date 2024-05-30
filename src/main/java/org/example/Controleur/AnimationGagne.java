package org.example.Controleur;

public class AnimationGagne extends Animation{
    int duration;
    int initialDelay;
    boolean delayCompleted;


    public AnimationGagne(int d, ControleurMediateur control) {
        super(1, control);
        initialDelay = 70;
        duration = d;
        control.setPause(true);
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
            control.distribuerGagne();
        }


    }

    @Override
    public boolean estTerminee() {
        return duration <= 0;
    }

}
