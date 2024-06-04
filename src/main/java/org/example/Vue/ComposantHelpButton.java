package org.example.Vue;

import org.example.Modele.Jeu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ComposantHelpButton extends JButton {

    public ComposantHelpButton() { // on a pas besoin du controlleur ni du jeu c'est juste un pop-up qui s'affiche
        super("Help");
        // Ajouter un écouteur d'action à ce bouton
        addActionListener(new AdaptateurHelpButton());
    }

    // Méthode principale pour tester le composant
    public static void main(String[] args) {
        // Créer une fenêtre principale
        JFrame frame = new JFrame("Test Composant Help Button");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);

        // Utiliser BoxLayout pour positionner le bouton horizontalement
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS)); // Positionnement horizontal
        panel.add(Box.createHorizontalGlue()); // Espace avant le bouton
        ComposantHelpButton helpButton = new ComposantHelpButton();
        panel.add(helpButton);

        frame.getContentPane().add(panel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

}
