package org.example.Vue;

import org.example.Modele.Jeu;

import javax.swing.*;
import java.awt.*;

public class ComposantMenuPartie extends Box {

    CollecteurEvenements c;

    Jeu jeu;

    ComposantMenuPartie(int axis, CollecteurEvenements control, Jeu jeu) {
        super(axis);
        add(Box.createGlue());
        this.jeu = jeu;
        c = control;

        // Boutons Annuler et Refaire coup
        add(new ComposantAnnulerRefaire(axis, control));
        add(Box.createGlue());

        // Bouton Nouvelle Partie
        add(new ComposantNouvellePartie(axis, control));
        add(Box.createGlue());

        // Boutons et FieldTexts Sauver et Charger une partie
        JButton loadGameButton = new JButton("Charger Partie");
        loadGameButton.setPreferredSize(new Dimension(200, 50));
        loadGameButton.addActionListener(new AdaptateurSauvegarde(control, new ComposantSauvegarde(control)));
        add(loadGameButton);
        add(Box.createGlue());
    }
}

