package org.example.Vue;

import org.example.Modele.Jeu;
import org.example.Patternes.Observateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ComposantFinPartie extends JFrame implements Observateur {

    CollecteurEvenements c;
    Jeu jeu;
    String JoueurGagnant = "";
    JLabel messageLabel;

    public ComposantFinPartie(CollecteurEvenements control,Jeu jeu) {
        this.c = control;
        this.setVisible(false);
        // Setting up the frame
        this.jeu = jeu;
        this.setTitle("Fin de la partie");

        // Change l'icone de la fenetre principale
        try {
            this.setIconImage(ImageIO.read(new File("src/main/resources/Claim.png")));
        } catch (IOException exc) {
            System.out.println("Erreur de chargement de l'icone");
        }

        JPanel panel = new JPanel();
        // panel.add(new JLabel(new ImageIcon("src/main/resources/Claim.png")));
        panel.setPreferredSize(new java.awt.Dimension(300, 150));
        messageLabel = new JLabel("Le Joueur " + JoueurGagnant + " a gagné");
        panel.add(messageLabel);

        JButton ok = new JButton("OK");
        ok.addActionListener(new AdaptateurClose(this));
        panel.add(ok);
        this.setContentPane(panel);
        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        this.pack();
        this.setLocationRelativeTo(null);
        // this.add(panel);
    }

    @Override
    public void miseAJour() {
        JoueurGagnant = jeu.getJoueurNomGagnant();
        messageLabel.setText("Le Joueur " + JoueurGagnant + " a gagné"); // TODO : Ajouter la fonction du joueur qui a gagné la partie dans ControleurMédiateur et l'appeler ici
    }
}
