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
import java.util.HashMap;
import java.util.Map;

public class NiveauGraphique extends JComponent implements Observateur {
    CollecteurEvenements control;

    int largeurCase;
    int hauteurCase;

    Jeu jeu;

    private static final double RECTANGLE_SCALE_FACTOR = 0.05; // Adjust this value for scaling
    /* Load assets */
    Map<String, BufferedImage> imageMap = new HashMap<>();
    public NiveauGraphique(Jeu j, CollecteurEvenements cont) {

        control = cont;
        jeu = j;
        jeu.ajouteObservateur(this);

        String directoryPath = "src/main/resources/";
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        //Chargez l'image et le nom correspondant dans un hashmap
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    if (fileName.endsWith(".png")) {
                        String imageName = fileName.substring(0, fileName.lastIndexOf("."));
                        try {
                            BufferedImage image = ImageIO.read(file);
                            imageMap.put(imageName, image);
                        } catch (IOException e) {
                            System.out.println("Error loading image: " + e.getMessage());
                        }
                    }
                }
            }
        } else {
            System.out.println("No files found in the directory.");
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

        // Set bigger font size
        Font font = g.getFont().deriveFont(Font.BOLD, largeur() / 25f); // Adjust font size based on panel width
        g.setFont(font);

        /*
        Draw "Menu" text centered within the oval
        String text = "Menu";
        FontMetrics fontMetrics = g.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(text);
        int textHeight = fontMetrics.getHeight();
        int textX = ovalX + (ovalWidth - textWidth) / 2;
        int textY = ovalY + (ovalHeight - textHeight) / 2 + fontMetrics.getAscent();
        g.setColor(Color.BLACK);
        g.drawString(text, textX, textY);
        */


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
                BufferedImage image = imageMap.get("dwarve_9");
                g.drawImage(image, x, y, rectWidth, rectHeight, this);
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

            // Draw deck
            g.setColor(Color.ORANGE);
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
        }else if (control.getPhase() == 2) {

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
                BufferedImage image = imageMap.get("dwarve_5");
                g.drawImage(image, x, y, rectWidth, rectHeight, this);
                // g.fillRect(x, y, rectWidth, rectHeight);
            }

            int x = startX - 20 - rectWidth;
            int y = 20;

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

