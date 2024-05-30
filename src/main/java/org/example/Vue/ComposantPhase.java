package org.example.Vue;

import org.example.Controleur.ControleurMediateur;
import org.example.Modele.Jeu;
import org.example.Patternes.Observateur;

import javax.swing.*;

public class ComposantPhase extends Box implements Observateur {

    JLabel p;
    Jeu jeu;
    String phase;
    CollecteurEvenements control;

    public ComposantPhase(int axis, Jeu jeu, CollecteurEvenements control) {
        super(axis);
        this.jeu = jeu;
        this.control = control;
        this.jeu.ajouteObservateur(this);
        phase = "1";
        // Affichage du joueur courant
        p = new JLabel("PHASE ACTUELLE : " + phase + " | ");
        add(p);
        add(Box.createGlue());
    }

    @Override
    public void miseAJour() {
        if (control.getPhase())
            phase = "1";
        else
            phase = "2";
        p.setText("PHASE ACTUELLE : " + phase);
    }
}
