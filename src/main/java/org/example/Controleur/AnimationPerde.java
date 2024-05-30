package org.example.Controleur;

public class AnimationPerde extends Animation{
    int duration;
    int initialDelay;
    boolean delayCompleted;


    public AnimationPerde(int d, ControleurMediateur control) {
        super(1, control);
        initialDelay = 140;
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
            control.distribuerPerde();
        }


    }

    @Override
    public boolean estTerminee() {
        return duration <= 0;
    }

}
