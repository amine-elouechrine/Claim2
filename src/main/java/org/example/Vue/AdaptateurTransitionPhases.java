package org.example.Vue;

import org.example.Modele.Jeu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurTransitionPhases implements ActionListener {
    JFrame fenetre;
    CollecteurEvenements control;

    public AdaptateurTransitionPhases(CollecteurEvenements c) {
        this.fenetre = fenetre;
        control = c;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        fenetre.setVisible(true);

        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fenetre.dispose();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}
