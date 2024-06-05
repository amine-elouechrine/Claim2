package org.example.Vue;

import org.example.Modele.Jeu;
import org.example.Patternes.Observateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ComposantFinPartie extends JFrame implements Observateur {

    CollecteurEvenements c;

    public void setWindowIcon(String path) {
        // Utilisez getClass().getResource pour obtenir l'URL de la ressource
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image image = icon.getImage();
            setIconImage(image); // Méthode à appeler sur JFrame pour définir l'icône
        } else {
            System.err.println("Erreur de chargement de l'icone");
        }
    }
  
    Jeu jeu;
    String JoueurGagnant = "";
    JLabel messageLabel;
    JButton ok;

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

        ok = new JButton("OK");
        ok.addActionListener(new AdaptateurClose(this));
        panel.add(ok);
        this.setContentPane(panel);
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