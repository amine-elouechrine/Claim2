package org.example.Controleur;


public class AnimationPause extends Animation {
    private int duration;

    public AnimationPause(int duration, ControleurMediateur control) {
        super(1, control);
        this.duration = duration;
    }

    @Override
    public void miseAJour() {
        duration--;
    }

    @Override
    public boolean estTerminee() {
        return duration <= 0;
    }
}
