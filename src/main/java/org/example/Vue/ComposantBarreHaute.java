package org.example.Vue;

import org.example.Modele.Jeu;

import javax.swing.*;
import java.awt.*;

public class ComposantBarreHaute extends Box {

    ComposantBarreHaute(int axis, CollecteurEvenements control, Jeu j) {
        super(axis);
        add(Box.createGlue());

        // Affichage Joueur courant
        add(new ComposantPhase(axis, j, control));
        add(Box.createGlue());
        add(new ComposantJoueurCourant(axis, j, control));
        add(Box.createGlue());
        add(new JLabel("ctrl+a : Annuler | ctrl+r : Refaire | ctrl+s : Sauvegarder/Charger la partie | ctrl+n : Nouvelle Partie "));

        // Bouton Menu Deroulant
        setBackground(Color.ORANGE);
        setOpaque(true);

        /*
        JToggleButton ouvreMenu = new JToggleButton("Menu");
        add(ouvreMenu, BorderLayout.LINE_END);
        ouvreMenu.addActionListener(new AdaptateurOuvreMenu(ouvreMenu, menu));
         */
    }
}
