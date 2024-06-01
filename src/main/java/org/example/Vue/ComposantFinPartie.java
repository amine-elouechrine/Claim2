package org.example.Vue;

import org.example.Modele.Jeu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ComposantFinPartie extends JFrame {

    CollecteurEvenements c;

    public ComposantFinPartie(CollecteurEvenements control) {
        this.c = control;
        this.setVisible(false);
        // Setting up the frame
        this.setTitle("Fin de la partie");

        // Change l'icone de la fenetre principale
        try {
            this.setIconImage(ImageIO.read(new File("src/main/resources/Claim.png")));
        } catch (IOException exc) {
            System.out.println("Erreur de chargement de l'icone");
        }

        JPanel panel = new JPanel();
        // panel.add(new JLabel(new ImageIcon("src/main/resources/Claim.png")));
        String JoueurGagnant = "";
        JoueurGagnant = "test"; // TODO : Ajouter la fonction du joueur qui a gagné la partie dans ControleurMédiateur et l'appeler ici
        panel.add(new JLabel("Le Joueur " + JoueurGagnant + " a gagné"));
        JButton ok = new JButton("OK");
        ok.addActionListener(new AdaptateurClose(this));
        panel.add(ok);
        this.setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        // this.add(panel);
    }
}
