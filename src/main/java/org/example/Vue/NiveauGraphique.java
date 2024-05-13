package org.example.Vue;


import org.example.Modele.Jeu;
import org.example.Patternes.Observateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NiveauGraphique extends JComponent implements Observateur {
    private static final double RECTANGLE_SCALE_FACTOR = 0.05; // Adjust this value for scaling
    CollecteurEvenements control;
    int largeurCase;
    int hauteurCase;
    int rectHeight;
    int rectWidth;
    int posX;
    int posY;
    int totalWidth;
    int totalHeight;
    int totalCards;
    Jeu jeu;
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
        totalCards = 13; // Nombre total de carte à recupérer du modele à chaque tour de jeu

        // Definition of font
        int fontSize_1 = (int) (panelWidth * 0.02);
        Font font_1 = new Font("Arial", Font.PLAIN, fontSize_1);

        int fontSize_2 = (int) (panelWidth * 0.015);
        Font font_2 = new Font("Arial", Font.PLAIN, fontSize_2);

        // Dessin des cartes de l'adversaire (IA)
        // Calculate rectangle dimensions based on panel size
        rectWidth = (int) (panelWidth * 0.05);
        rectHeight = Math.max(2 * rectWidth, (int) (panelHeight * RECTANGLE_SCALE_FACTOR)); // Ensure height is always greater than width

        // Calculate spacing between rectangles
        int spacing = 0;

        // Calculate total width of all rectangles and spacing
        totalWidth = totalCards * rectWidth + (totalCards - 1) * spacing;

        // Calculate starting x position to center the rectangles
        int startX = (panelWidth - totalWidth) / 2;

        posX = startX;
        posY = hauteur() - rectHeight - 10;
        totalHeight = rectHeight;

        // Phase 1
        if (control.getPhase() == 1) {

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
            x = largeur() - largeur() / 8;
            y = hauteur() / 2 - rectHeight * 3 / 4;
            g.fillRect(x, y, rectHeight, rectWidth); // Rectangle latéral

            // Draw defausse
            x = largeur() - largeur() / 8;
            y = hauteur() / 2 + rectHeight / 4;
            g.fillRect(x, y, rectHeight, rectWidth); // Rectangle latéral

            // Draw Feedfoward Carte joué
            g.setColor(Color.DARK_GRAY);
            x = largeur() / 2;
            y = hauteur() - rectHeight * 11 / 5;

            // g.drawImage(gaufre, x, y, rectWidth, rectHeight, this);
            g.drawRect(x, y, rectWidth, rectHeight);

            // Draw score pile
            x = rectWidth;
            y = hauteur() / 2 - rectHeight;
            g.drawRect(x, y, rectWidth * 2, rectHeight * 2);

            int numRows = 6;
            int cellHeight = rectHeight * 2 / numRows;
            int imageX, imageY, textX, textY;
            Color bgColor;
            BufferedImage icon_goblin = imageMap.get("icon_goblin");
            BufferedImage icon_knight = imageMap.get("icon_knight");
            BufferedImage icon_undead = imageMap.get("icon_undead");
            BufferedImage icon_dwarve = imageMap.get("icon_dwarve");
            BufferedImage icon_doppleganger = imageMap.get("icon_doppleganger");

            for (int i = 0; i < numRows; i++) {
                int lineY = y + i * cellHeight;
                // int score = getScoreForFaction(i);
                int score = 2;
                if (i > 0) {
                    if (score > 0) {
                        bgColor = Color.GREEN;
                    } else if (score == 0) {
                        bgColor = Color.GRAY;
                    } else {
                        bgColor = Color.RED;
                    }
                } else {
                    bgColor = Color.WHITE;
                }

                g.setColor(bgColor);
                g.fillRect(x, lineY, rectWidth * 2, cellHeight);
                g.setColor(Color.BLACK);
                g.drawLine(x, lineY, x + rectWidth * 2, lineY);

                if (i == 0) {
                    FontMetrics fm = g.getFontMetrics(font_1);
                    int textWidth = fm.stringWidth("SCORE");
                    int textHeight = fm.getHeight();

                    textX = x + (rectWidth * 2 - textWidth) / 2;
                    textY = lineY + (cellHeight + textHeight) / 2;
                    g.setFont(font_1);
                    g.drawString("SCORE", textX, textY);
                }
                if (i == 1) {
                    //Draw icon goblin
                    imageX = x + 5;
                    imageY = lineY + (cellHeight - rectWidth) / 2;
                    g.drawImage(icon_goblin, imageX, imageY, rectWidth * 5 / 8, rectWidth, this);

                    FontMetrics fm = g.getFontMetrics(font_2);
                    int textWidth = fm.stringWidth(Integer.toString(score));
                    int textHeight = fm.getHeight();

                    textX = x + rectWidth * 2 - textWidth * 4;
                    textY = lineY + (cellHeight + textHeight * 2 / 3) / 2;
                    g.setFont(font_2);
                    g.drawString(Integer.toString(score), textX, textY);
                }
                if (i == 2) {
                    //Draw icon knight
                    imageX = x + 5;
                    imageY = lineY + (cellHeight - rectWidth) / 2;
                    g.drawImage(icon_knight, imageX, imageY, rectWidth * 5 / 8, rectWidth, this);

                    FontMetrics fm = g.getFontMetrics(font_2);
                    int textWidth = fm.stringWidth(Integer.toString(score));
                    int textHeight = fm.getHeight();

                    textX = x + rectWidth * 2 - textWidth * 4;
                    textY = lineY + (cellHeight + textHeight * 2 / 3) / 2;
                    g.setFont(font_2);
                    g.drawString(Integer.toString(score), textX, textY);
                }
                if (i == 3) {
                    //Draw icon undead
                    imageX = x + 5;
                    imageY = lineY + (cellHeight - rectWidth) / 2;
                    g.drawImage(icon_undead, imageX, imageY, rectWidth * 5 / 8, rectWidth, this);

                    FontMetrics fm = g.getFontMetrics(font_2);
                    int textWidth = fm.stringWidth(Integer.toString(score));
                    int textHeight = fm.getHeight();

                    textX = x + rectWidth * 2 - textWidth * 4;
                    textY = lineY + (cellHeight + textHeight * 2 / 3) / 2;
                    g.setFont(font_2);
                    g.drawString(Integer.toString(score), textX, textY);
                }
                if (i == 4) {
                    //Draw icon dwarve
                    imageX = x + 5;
                    imageY = lineY + (cellHeight - rectWidth) / 2;
                    g.drawImage(icon_dwarve, imageX, imageY, rectWidth * 5 / 8, rectWidth, this);

                    FontMetrics fm = g.getFontMetrics(font_2);
                    int textWidth = fm.stringWidth(Integer.toString(score));
                    int textHeight = fm.getHeight();

                    textX = x + rectWidth * 2 - textWidth * 4;
                    textY = lineY + (cellHeight + textHeight * 2 / 3) / 2;
                    g.setFont(font_2);
                    g.drawString(Integer.toString(score), textX, textY);
                }
                if (i == 5) {
                    //Draw icon dopplegagner
                    imageX = x + 5;
                    imageY = lineY + (cellHeight - rectWidth) / 2;
                    g.drawImage(icon_doppleganger, imageX, imageY, rectWidth * 5 / 8, rectWidth, this);

                    FontMetrics fm = g.getFontMetrics(font_2);
                    int textWidth = fm.stringWidth(Integer.toString(score));
                    int textHeight = fm.getHeight();

                    textX = x + rectWidth * 2 - textWidth * 4;
                    textY = lineY + (cellHeight + textHeight * 2 / 3) / 2;
                    g.setFont(font_2);
                    g.drawString(Integer.toString(score), textX, textY);
                }


            }
        } else if (control.getPhase() == 2) {

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

            // Draw score pile
            x = rectWidth;
            y = hauteur() / 2 - rectHeight;
            g.drawRect(x, y, rectWidth * 2, rectHeight * 2);

            int numRows = 6;
            int cellHeight = rectHeight * 2 / numRows;
            int imageX, imageY, textX, textY;
            Color bgColor;
            BufferedImage icon_goblin = imageMap.get("icon_goblin");
            BufferedImage icon_knight = imageMap.get("icon_knight");
            BufferedImage icon_undead = imageMap.get("icon_undead");
            BufferedImage icon_dwarve = imageMap.get("icon_dwarve");
            BufferedImage icon_doppleganger = imageMap.get("icon_doppleganger");

            for (int i = 0; i < numRows; i++) {
                int lineY = y + i * cellHeight;
                // int score = getScoreForFaction(i);
                int score = 2;
                if (i > 0) {
                    if (score > 0) {
                        bgColor = Color.GREEN;
                    } else if (score == 0) {
                        bgColor = Color.GRAY;
                    } else {
                        bgColor = Color.RED;
                    }
                } else {
                    bgColor = Color.WHITE;
                }

                g.setColor(bgColor);
                g.fillRect(x, lineY, rectWidth * 2, cellHeight);
                g.setColor(Color.BLACK);
                g.drawLine(x, lineY, x + rectWidth * 2, lineY);

                if (i == 0) {
                    FontMetrics fm = g.getFontMetrics(font_1);
                    int textWidth = fm.stringWidth("SCORE");
                    int textHeight = fm.getHeight();

                    textX = x + (rectWidth * 2 - textWidth) / 2;
                    textY = lineY + (cellHeight + textHeight) / 2;
                    g.setFont(font_1);
                    g.drawString("SCORE", textX, textY);
                }
                if (i == 1) {
                    //Draw icon goblin
                    imageX = x + 5;
                    imageY = lineY + (cellHeight - rectWidth) / 2;
                    g.drawImage(icon_goblin, imageX, imageY, rectWidth * 5 / 8, rectWidth, this);

                    FontMetrics fm = g.getFontMetrics(font_2);
                    int textWidth = fm.stringWidth(Integer.toString(score));
                    int textHeight = fm.getHeight();

                    textX = x + rectWidth * 2 - textWidth * 4;
                    textY = lineY + (cellHeight + textHeight * 2 / 3) / 2;
                    g.setFont(font_2);
                    g.drawString(Integer.toString(score), textX, textY);
                }
                if (i == 2) {
                    //Draw icon knight
                    imageX = x + 5;
                    imageY = lineY + (cellHeight - rectWidth) / 2;
                    g.drawImage(icon_knight, imageX, imageY, rectWidth * 5 / 8, rectWidth, this);

                    FontMetrics fm = g.getFontMetrics(font_2);
                    int textWidth = fm.stringWidth(Integer.toString(score));
                    int textHeight = fm.getHeight();

                    textX = x + rectWidth * 2 - textWidth * 4;
                    textY = lineY + (cellHeight + textHeight * 2 / 3) / 2;
                    g.setFont(font_2);
                    g.drawString(Integer.toString(score), textX, textY);
                }
                if (i == 3) {
                    //Draw icon undead
                    imageX = x + 5;
                    imageY = lineY + (cellHeight - rectWidth) / 2;
                    g.drawImage(icon_undead, imageX, imageY, rectWidth * 5 / 8, rectWidth, this);

                    FontMetrics fm = g.getFontMetrics(font_2);
                    int textWidth = fm.stringWidth(Integer.toString(score));
                    int textHeight = fm.getHeight();

                    textX = x + rectWidth * 2 - textWidth * 4;
                    textY = lineY + (cellHeight + textHeight * 2 / 3) / 2;
                    g.setFont(font_2);
                    g.drawString(Integer.toString(score), textX, textY);
                }
                if (i == 4) {
                    //Draw icon dwarve
                    imageX = x + 5;
                    imageY = lineY + (cellHeight - rectWidth) / 2;
                    g.drawImage(icon_dwarve, imageX, imageY, rectWidth * 5 / 8, rectWidth, this);

                    FontMetrics fm = g.getFontMetrics(font_2);
                    int textWidth = fm.stringWidth(Integer.toString(score));
                    int textHeight = fm.getHeight();

                    textX = x + rectWidth * 2 - textWidth * 4;
                    textY = lineY + (cellHeight + textHeight * 2 / 3) / 2;
                    g.setFont(font_2);
                    g.drawString(Integer.toString(score), textX, textY);
                }
                if (i == 5) {
                    //Draw icon dopplegagner
                    imageX = x + 5;
                    imageY = lineY + (cellHeight - rectWidth) / 2;
                    g.drawImage(icon_doppleganger, imageX, imageY, rectWidth * 5 / 8, rectWidth, this);

                    FontMetrics fm = g.getFontMetrics(font_2);
                    int textWidth = fm.stringWidth(Integer.toString(score));
                    int textHeight = fm.getHeight();

                    textX = x + rectWidth * 2 - textWidth * 4;
                    textY = lineY + (cellHeight + textHeight * 2 / 3) / 2;
                    g.setFont(font_2);
                    g.drawString(Integer.toString(score), textX, textY);
                }
            }
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

    public int largeurCarte() {
        return rectWidth;
    }

    public int hauteurCarte() {
        return rectHeight;
    }

    public int posYMain() {
        return posY;
    }

    public int posXMain() {
        return posX;
    }

    public int getLargeurMain() {
        return totalWidth;
    }

    public int getTotalCards() {
        return totalCards;
    }

    public int getHauteurMain() {
        return totalHeight;
    }

    @Override
    public void miseAJour() {
        repaint();
    }
}

