package org.example.Vue;


import org.example.Patternes.Observateur;
import org.example.Modele.Jeu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class NiveauGraphique extends JComponent implements Observateur {
    CollecteurEvenements control;

    int largeurCase;
    int hauteurCase;

    Jeu jeu;
    /* Load assets */
    BufferedImage gaufre;
    BufferedImage claim;
    public NiveauGraphique(Jeu j) {
        jeu = j;
        jeu.ajouteObservateur(this);

        try {
            gaufre = ImageIO.read(new File("src/main/resources/gaufre.png"));
            claim = ImageIO.read(new File("src/main/resources/Claim.png"));
        } catch (IOException exc) {
            System.out.println("Erreur de chargement des assets");
        }
    }

    /*
     * Peindre le plateau de jeu
     */
    @Override
    public void paintComponent(Graphics g) {
        // Paint the game board area
        paintGameBoard(g);

        // Paint the score area
        paintScoreArea(g);
    }

    private void paintGameBoard(Graphics g) {

        int lignes = jeu.getLignes();
        int colonnes = jeu.getColonnes();
        largeurCase = getWidth() / colonnes;
        hauteurCase = getHeight() / lignes;

        // Draw the poison image
        g.drawImage(claim, 0, 0, largeurCase, hauteurCase, this);
    }

    private void paintScoreArea(Graphics g) {
        Font scoreFont = new Font("Arial", Font.PLAIN, 20);
        g.setFont(scoreFont);
        g.setColor(Color.BLACK);
    }

    int largeur() {
        return getWidth();
    }

    int hauteur() {
        return getHeight();
    }

    public int largeurCase() {
        return largeurCase;
    }

    public int hauteurCase() {
        return hauteurCase;
    }

    @Override
    public void miseAJour() {
        repaint();
    }
}

