package org.example.Vue;

import org.example.Modele.Jeu;

import javax.swing.*;

public class ComposantMenuPartie extends JFrame {

    CollecteurEvenements c;

    Jeu jeu;

    ComposantMenuPartie(int axis, CollecteurEvenements control, Jeu jeu) {
        this.jeu = jeu;
        c = control;

        this.setSize(600, 400);
        JPanel panel = new JPanel();
        // Boutons Annuler et Refaire coup
        panel.add(new ComposantAnnulerRefaire(axis, control));
        panel.add(Box.createGlue());

        // Bouton Nouvelle Partie
        panel.add(new ComposantNouvellePartie(axis, control, this));
        panel.add(Box.createGlue());

        // Boutons et FieldTexts Sauver et Charger une partie
        JButton loadGameButton = new JButton("Sauvergarder ou Charger Partie");
        // loadGameButton.setPreferredSize(new Dimension(200, 50));
        loadGameButton.addActionListener(new AdaptateurSauvegarde(control, new ComposantSauvegarde(control)));
        panel.add(loadGameButton);
        panel.add(Box.createGlue());

        this.add(panel);
    }
}

