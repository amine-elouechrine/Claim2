package org.example.Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurNouvellePartie implements ActionListener {
    CollecteurEvenements control;

    AdaptateurNouvellePartie(CollecteurEvenements c) {
        control = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // control.nouvellePartie();
        System.out.println("Clique sur le bouton Nouvelle Partie");
    }
}
