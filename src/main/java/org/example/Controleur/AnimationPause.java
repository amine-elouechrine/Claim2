package org.example.Controleur;


public class AnimationPause extends Animation {
    int duration;
    public AnimationPause(int d, ControleurMediateur control) {
        super(1, control);
        duration = d;
        //System.out.println("AnimationPause initialized with duration: " + duration);
    }

    @Override
    public void miseAJour() {
        duration--;
        //control.pause();
        //System.out.println("AnimationPause miseAJour called, remaining duration: " + duration);
    }

    @Override
    public boolean estTerminee() {
        return duration <= 0;
    }
}
