package org.example.Vue;


import org.example.Patternes.Observateur;
import org.example.Modele.Jeu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.font.ShapeGraphicAttribute;
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

    BufferedImage carte;
    BufferedImage claim;

    public NiveauGraphique(Jeu j, CollecteurEvenements cont) {

        control = cont;
        jeu = j;
        jeu.ajouteObservateur(this);

        try {
            carte = ImageIO.read(new File("src/main/resources/dwarve_0.png"));
            // for(int i =0;i<52;i++){
            //BufferedImage files[i].getname
            // }
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
    public void paintComponent(Graphics graphics) {
        // Paint the game board area
        Graphics2D g = (Graphics2D) graphics;
        super.paintComponent(g);
        paintGameBoard(g);
    }

    private void paintGameBoard(Graphics g) {

        int lignes = jeu.getLignes();
        int colonnes = jeu.getColonnes();
        largeurCase = getWidth() / colonnes;
        hauteurCase = getHeight() / lignes;

        g.setColor(Color.BLACK);

        setBackground(Color.YELLOW);

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


        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int totalCards = 13; // Nombre total de carte à recupérer du modele à chaque tour de jeu
        // Phase 1
        if (control.getPhase() == 1) {

            // Dessin des cartes de l'adversaire (IA)
            // Calculate rectangle dimensions based on panel size
            int rectWidth = (int) (panelWidth * 0.05);
            int rectHeight = Math.max(2 * rectWidth, (int) (panelHeight * RECTANGLE_SCALE_FACTOR)); // Ensure height is always greater than width

            // Calculate spacing between rectangles
            int spacing = 1;

            // Calculate total width of all rectangles and spacing
            int totalWidth = totalCards * rectWidth + (totalCards - 1) * spacing;

            // Calculate starting x position to center the rectangles
            int startX = (panelWidth - totalWidth) / 2;

            // Draw rectangles
            for (int i = 0; i < totalCards; i++) {
                int x = startX + i * (rectWidth + spacing);
                int y = 10;
                g.setColor(Color.GRAY);
                g.fillRect(x, y, rectWidth, rectHeight);
            }
            // Dessin des cartes de la main du joueur

            // Draw rectangles
            for (int i = 0; i < totalCards; i++) {
                int x = startX + i * (rectWidth + spacing);
                int y = hauteur() - rectHeight - 10;
                g.setColor(Color.BLUE);
                g.drawImage(carte, x, y, rectWidth, rectHeight, this);
                // g.fillRect(x, y, rectWidth, rectHeight);
            }

            // Draw follower deck
            int x = startX - 20 - rectWidth;
            int y = 20;
            g.fillRect(x, y, rectWidth, rectHeight);

            x = startX - 20 - rectWidth;
            y = hauteur() - rectHeight - 20;
            g.fillRect(x, y, rectWidth, rectHeight);

            // Draw carte a gagne
            x = rectWidth * 5 / 2 + largeur() / 2;
            y = hauteur() / 2 - rectHeight / 2;
            g.fillRect(x, y, rectWidth, rectHeight);

            g.setColor(Color.ORANGE);
            // Draw deck
            x = 2 * rectHeight + 20 + largeur() / 2;
            y = hauteur() / 2 - rectHeight * 3 / 4;
            g.fillRect(x, y, rectHeight, rectWidth);

            // Draw defausse
            x = 2 * rectHeight + 20 + largeur() / 2;
            y = hauteur() / 2 + rectHeight / 4;
            g.fillRect(x, y, rectHeight, rectWidth);

            // Draw Feedfoward Carte joué
            g.setColor(Color.DARK_GRAY);
            x = largeur() / 2;
            y = hauteur() - rectHeight * 11 / 5;

            // g.drawImage(gaufre, x, y, rectWidth, rectHeight, this);
            g.drawRect(x, y, rectWidth, rectHeight);
        }

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

