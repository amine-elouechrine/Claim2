package org.example.Vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ComposantRejouer extends JFrame {

    JPanel panel;

    JFrame fenetre;

    public ComposantRejouer(CollecteurEvenements control) {
        // Nom de le fenêtre
        this.setTitle("Recommencer ?");

        fenetre = this;


        // Change l'icone de la fenetre principale
        try {
            this.setIconImage(ImageIO.read(getClass().getResource("/Claim.png")));
        } catch (IOException exc) {
            System.out.println("Erreur de chargement de l'icone");
        }


        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200); // Augmentez la hauteur pour inclure les nouveaux champs de texte
        setLocationRelativeTo(null);

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 5));

        // texte de rejouer partie
        panel.add(new JLabel("Voulez-vous rejouer une partie ?"));

        // Bouton Oui
        JButton oui = new JButton("Oui");
        oui.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fenetre.setVisible(false);
                control.nouvellePartie();
            }
        });

        // Bouton non
        JButton non = new JButton("Non");
        non.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fenetre.setVisible(false);
            }
        });

        panel.add(oui);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(non);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(panel);
        setVisible(false);
    }
}
