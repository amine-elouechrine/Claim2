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

    private static final double RECTANGLE_SCALE_FACTOR = 0.05; // Adjust this value for scaling
    /* Load assets */
    BufferedImage gaufre;
    BufferedImage claim;
    public NiveauGraphique(Jeu j, CollecteurEvenements cont) {

        control = cont;
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
        super.paintComponent(g);
        paintGameBoard(g);
    }

    private void paintGameBoard(Graphics g) {

        int lignes = jeu.getLignes();
        int colonnes = jeu.getColonnes();
        largeurCase = getWidth() / colonnes;
        hauteurCase = getHeight() / lignes;

        g.setColor(Color.BLACK);

        g.drawRect(getWidth() - largeurCase, 0, largeurCase, hauteurCase);
        // Calculate oval dimensions based on panel size
        int ovalWidth = (int) (largeur() * 0.075);
        int ovalHeight = (int) (hauteur() * 0.05);
        int ovalX = largeur() - ovalWidth - 10; // 10 pixels from the right edge
        int ovalY = 10; // 10 pixels from the top edge
        g.setColor(Color.GREEN);
        g.fillOval(ovalX, ovalY, ovalWidth, ovalHeight);

        // Set bigger font size
        Font font = g.getFont().deriveFont(Font.BOLD, largeur() / 25f); // Adjust font size based on panel width
        g.setFont(font);

        // Draw "Menu" text centered within the oval
        String text = "Menu";
        FontMetrics fontMetrics = g.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(text);
        int textHeight = fontMetrics.getHeight();
        int textX = ovalX + (ovalWidth - textWidth) / 2;
        int textY = ovalY + (ovalHeight - textHeight) / 2 + fontMetrics.getAscent();
        g.setColor(Color.BLACK);
        g.drawString(text, textX, textY);

        // Phase 1
        if(control.getPhase() == 1) {

            // Dessin des cartes de l'adversaire (IA)
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int totalRects = 13;

            // Calculate rectangle dimensions based on panel size
            int rectWidth = (int) (panelWidth * 0.05);
            int rectHeight = Math.max(2 * rectWidth, (int) (panelHeight * RECTANGLE_SCALE_FACTOR)); // Ensure height is always greater than width

            // Calculate spacing between rectangles
            int spacing = 1;

            // Calculate total width of all rectangles and spacing
            int totalWidth = totalRects * rectWidth + (totalRects - 1) * spacing;

            // Calculate starting x position to center the rectangles
            int startX = (panelWidth - totalWidth) / 2;

            // Draw rectangles
            for (int i = 0; i < totalRects; i++) {
                int x = startX + i * (rectWidth + spacing);
                int y = 10;
                g.setColor(Color.BLUE);
                g.fillRect(x, y, rectWidth, rectHeight);
            }
        }

        // Draw image at 0 0
        // g.drawImage(claim, 0, 0, largeurCase, hauteurCase, this);

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

