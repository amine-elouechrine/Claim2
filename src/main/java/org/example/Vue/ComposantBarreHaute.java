package org.example.Vue;

import org.example.Modele.Jeu;

import javax.swing.*;
import java.awt.*;

public class ComposantBarreHaute extends Box {

    ComposantBarreHaute(int axis, CollecteurEvenements control, JFrame menu, Jeu j) {
        super(axis);
        add(Box.createGlue());

        // Affichage Joueur courant
        add(new ComposantJoueurCourant(axis, j));
        add(Box.createGlue());
        add(new JLabel("a: annuler | r: refaire         "));

        // Bouton Menu Deroulant
        setBackground(Color.ORANGE);
        setOpaque(true);

        JToggleButton ouvreMenu = new JToggleButton("Menu");
        add(ouvreMenu, BorderLayout.LINE_END);
        ouvreMenu.addActionListener(new AdaptateurOuvreMenu(ouvreMenu, menu));
    }
}
