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
    //private static final double RECTANGLE_SCALE_FACTOR = 0.05; // Adjust this value for scaling
    CollecteurEvenements control;
    int largeurCase;
    int hauteurCase;
    int rectHeight;
    int rectWidth;
    int posX;
    int posYJ1;
    int posYJ2;
    int totalWidthJ1P1;
    int totalWidthJ2P1;
    int totalWidthJ1P2;
    int totalWidthJ2P2;
    int totalHeight;
    int HandJ1P1;
    int HandJ1P2;
    int HandJ2P1;
    int HandJ2P2;
    int x, y;

    int positionCarteJoueJ1X, positionCarteJoueJ1Y;
    int positionCarteJoueJ2X, positionCarteJoueJ2Y;

    Color bgColor;
    int[][] main;
    int[][] mainJ2;
    BufferedImage image;
    BufferedImage grayImage;

    String strImage = "";

    int carteJ1V = -1, carteJ1F = -1;
    int carteJ2V = -1, carteJ2F = -1;

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

        // Set bigger font size
        Font font = g.getFont().deriveFont(Font.BOLD, largeur() / 25f); // Adjust font size based on panel width
        g.setFont(font);

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Taille de la main
        HandJ1P1 = control.getNbCardsJ1P1(); // Nombre de carte dans la main du joueur 1 à la phase 1
        HandJ1P2 = control.getNbCardsJ1P2(); // Nombre de carte dans la main du joueur 1 à la phase 2
        HandJ2P1 = control.getNbCardsJ2P1(); // Nombre de carte dans la main du joueur 2 à la phase 1
        HandJ2P2 = control.getNbCardsJ2P2(); // Nombre de carte dans la main du joueur 2 à la phase 2

        // Si une carte est jouée :
        carteJ1V = jeu.getCarteJoueur1V();
        carteJ1F = jeu.getCarteJoueur1F();
        carteJ2V = jeu.getCarteJoueur2V();
        carteJ2F = jeu.getCarteJoueur2F();

        // System.out.println("Valeur carte J1 : " + carteJ1V);
        // System.out.println(carteJ1F);

        // Definition of font
        int fontSize_1 = Math.min((int) (panelWidth * 0.02), (int) (panelHeight * 0.035));
        Font font_1 = new Font("Arial", Font.PLAIN, fontSize_1);

        int fontSize_2 = fontSize_1 * 3 / 4;
        Font font_2 = new Font("Arial", Font.PLAIN, fontSize_2);

        // Chargement icons
        BufferedImage icon_goblin = imageMap.get("icon_goblin");
        BufferedImage icon_knight = imageMap.get("icon_knight");
        BufferedImage icon_undead = imageMap.get("icon_undead");
        BufferedImage icon_dwarve = imageMap.get("icon_dwarve");
        BufferedImage icon_doppleganger = imageMap.get("icon_doppleganger");

        // Dessin des cartes de l'adversaire (IA)
        // Calculate rectangle dimensions based on panel size
        rectWidth = (int) (panelWidth * 0.05);
        rectHeight = Math.max(rectWidth, (int) (panelHeight * 4 / 30)); // Ensure height is always greater than width

        // Calculate spacing between rectangles
        int spacing = 0;

        // Calculate total width of all rectangles and spacing
        totalWidthJ1P1 = HandJ1P1 * rectWidth + (HandJ1P1 - 1) * spacing;
        totalWidthJ2P1 = HandJ2P1 * rectWidth + (HandJ2P1 - 1) * spacing;
        totalWidthJ1P2 = HandJ1P2 * rectWidth;
        totalWidthJ2P2 = HandJ2P2 * rectWidth;

        // Calculate starting x position to center the rectangles
        int startXJ1P1 = (panelWidth - totalWidthJ1P1) / 2;
        int startXJ2P1 = (panelWidth - totalWidthJ2P1) / 2;
        int startXJ1P2 = (panelWidth - totalWidthJ1P2) / 2;
        int startXJ2P2 = (panelWidth - totalWidthJ2P2) / 2;
        posYJ1 = hauteur() - rectHeight - 10;
        posYJ2 = 10;
        totalHeight = rectHeight;

        /* Phase 1 */
        if (control.getPhase()) {

            positionCarteJoueJ1X = totalWidthJ1P1 / 2 + startXJ1P1;
            positionCarteJoueJ1Y = panelHeight - totalHeight * 5 / 2 - 10;
            positionCarteJoueJ2X = totalWidthJ2P1 / 2 + startXJ2P1;
            positionCarteJoueJ2Y = totalHeight * 3 / 2 + 10;

            /*
            // Dessin de la main face caché du joueur 2 si il est une IA
            for (int i = 0; i < HandJ2P1; i++) {
                int x = startXJ2 + i * (rectWidth + spacing);
                int y = 10;
                g.setColor(Color.GRAY);
                image = imageMap.get("Claim");
                g.drawImage(image, x, y, rectWidth, rectHeight, this);
            }
            */

            // Dessin des cartes de la main du joueur 1
            for (int i = 0; i < HandJ1P1; i++) {
                int x = startXJ1P1 + i * (rectWidth + spacing);
                int y = hauteur() - rectHeight - 10;
                g.setColor(Color.BLUE);
                main = jeu.getMainJoueur1Phase1();
                switch (main[i][1]) {
                    case 1:
                        strImage = "goblin";
                        break;
                    case 2:
                        strImage = "dwarve";
                        break;
                    case 3:
                        strImage = "knight";
                        break;
                    case 4:
                        strImage = "doppelganger";
                        break;
                    case 5:
                        strImage = "undead";
                        break;
                }
                strImage += "_" + main[i][0];
                image = imageMap.get(strImage);
                grayImage = toGrayScale(image);
                g.drawImage(image, x, y, rectWidth, rectHeight, this);
                // g.fillRect(x, y, rectWidth, rectHeight);
            }

            // Dessin de la main du joueur 2
            for (int i = 0; i < HandJ2P1; i++) {
                int x = startXJ2P1 + i * (rectWidth + spacing);
                int y = 10;
                g.setColor(Color.BLUE);
                mainJ2 = jeu.getMainJoueur2Phase1();
                switch (mainJ2[i][1]) {
                    case 1:
                        strImage = "goblin";
                        break;
                    case 2:
                        strImage = "dwarve";
                        break;
                    case 3:
                        strImage = "knight";
                        break;
                    case 4:
                        strImage = "doppelganger";
                        break;
                    case 5:
                        strImage = "undead";
                        break;
                }
                strImage += "_" + mainJ2[i][0];
                image = imageMap.get(strImage);
                grayImage = toGrayScale(image);
                g.drawImage(grayImage, x, y, rectWidth, rectHeight, this);
                // g.fillRect(x, y, rectWidth, rectHeight);
            }

            posX = startXJ1P1;

            // Draw follower deck Joueur 2
            // x = startXJ2 - 20 - rectWidth;
            x = panelWidth / 9;
            y = 20;
            g.fillRect(x, y, rectWidth, rectHeight);

            // Draw follower deck Joueur 1
            // x = startXJ1 - 20 - rectWidth;
            y = hauteur() - rectHeight - 20;
            g.fillRect(x, y, rectWidth, rectHeight);

            // Draw carte a gagne
            x = rectWidth * 5 / 2 + largeur() / 2;
            y = hauteur() / 2 - rectHeight / 2;
            switch (jeu.getPlateau().getCarteAffichee().getFaction()) {
                case "Goblins":
                    strImage = "goblin";
                    break;
                case "Dwarves":
                    strImage = "dwarve";
                    break;
                case "Knight":
                    strImage = "knight";
                    break;
                case "Doppelganger":
                    strImage = "doppelganger";
                    break;
                case "Undead":
                    strImage = "undead";
                    break;
            }

            strImage += "_" + jeu.getPlateau().getCarteAffichee().getValeur();
            image = imageMap.get(strImage);
            g.drawImage(image, x, y, rectWidth, rectHeight, this);

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

            for (int i = 0; i < numRows; i++) {
                int lineY = y + i * cellHeight;
                String faction = "";
                switch (i) {
                    case 1:
                        faction = "Goblins";
                        break;
                    case 2:
                        faction = "Dwarves";
                        break;
                    case 3:
                        faction = "Knight";
                        break;
                    case 4:
                        faction = "Doppelganger";
                        break;
                    case 5:
                        faction = "Undead";
                        break;
                }

                // Calcul de score
                drawScore(g, faction, i, lineY, cellHeight, font_1, icon_goblin, font_2, icon_knight, icon_undead, icon_dwarve, icon_doppleganger);
            }

        /* Phase 2 */
        } else if (!control.getPhase()) {

            positionCarteJoueJ1X = totalWidthJ1P1 / 2 + startXJ1P2;
            positionCarteJoueJ1Y = panelHeight - totalHeight * 5 / 2 - 10;
            positionCarteJoueJ2X = totalWidthJ2P1 / 2 + startXJ2P2;
            positionCarteJoueJ2Y = totalHeight * 3 / 2 + 10;

            // Dessin des cartes de la main du joueur 1
            for (int i = 0; i < HandJ1P2; i++) {
                int x = startXJ1P2 + i * (rectWidth + spacing);
                int y = hauteur() - rectHeight - 10;
                g.setColor(Color.CYAN);
                main = jeu.getMainJoueur1Phase2();
                switch (main[i][1]) {
                    case 1:
                        strImage = "goblin";
                        break;
                    case 2:
                        strImage = "dwarve";
                        break;
                    case 3:
                        strImage = "knight";
                        break;
                    case 4:
                        strImage = "doppelganger";
                        break;
                    case 5:
                        strImage = "undead";
                        break;
                }
                strImage += "_" + main[i][0];
                image = imageMap.get(strImage);
                grayImage = toGrayScale(image);
                g.drawImage(image, x, y, rectWidth, rectHeight, this);
                // g.fillRect(x, y, rectWidth, rectHeight);
            }

            // Dessin de la main du joueur 2
            for (int i = 0; i < HandJ2P2; i++) {
                int x = startXJ2P2 + i * (rectWidth + spacing);
                int y = 10;
                g.setColor(Color.BLUE);
                mainJ2 = jeu.getMainJoueur2Phase2();
                switch (mainJ2[i][1]) {
                    case 1:
                        strImage = "goblin";
                        break;
                    case 2:
                        strImage = "dwarve";
                        break;
                    case 3:
                        strImage = "knight";
                        break;
                    case 4:
                        strImage = "doppelganger";
                        break;
                    case 5:
                        strImage = "undead";
                        break;
                }
                strImage += "_" + mainJ2[i][0];
                image = imageMap.get(strImage);
                grayImage = toGrayScale(image);
                //si une carte est jouable, draw image, sinon draw grayImage
                g.drawImage(grayImage, x, y, rectWidth, rectHeight, this);
                //g.drawImage(image, x, y, rectWidth, rectHeight, this);
                // g.fillRect(x, y, rectWidth, rectHeight);
            }

            posX = startXJ1P2;

            x = startXJ1P2 - 20 - rectWidth;
            y = 20;

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

            for (int i = 0; i < numRows; i++) {
                int lineY = y + i * cellHeight;
                String faction = "";
                switch (i) {
                    case 1:
                        faction = "Goblins";
                        break;
                    case 2:
                        faction = "Dwarves";
                        break;
                    case 3:
                        faction = "Knight";
                        break;
                    case 4:
                        faction = "Doppelganger";
                        break;
                    case 5:
                        faction = "Undead";
                        break;
                }

                // Calcul de score
                // Modifier en ajoutant des getteurs dans controleurMediateur pour modifier l'appel pour score
                drawScore(g, faction, i, lineY, cellHeight, font_1, icon_goblin, font_2, icon_knight, icon_undead, icon_dwarve, icon_doppleganger);
            }
        }

        g.setColor(Color.RED);
        if (carteJ1F != -1 && carteJ1V != -1) {
            // dessin de la carte jouée
            switch (carteJ1F) {
                case 1:
                    strImage = "goblin";
                    break;
                case 2:
                    strImage = "dwarve";
                    break;
                case 3:
                    strImage = "knight";
                    break;
                case 4:
                    strImage = "doppelganger";
                    break;
                case 5:
                    strImage = "undead";
                    break;
            }
            strImage += "_" + carteJ1V;
            image = imageMap.get(strImage);
            g.drawImage(image, positionCarteJoueJ1X, positionCarteJoueJ1Y, rectWidth, rectHeight, this);
        }
        g.drawRect(positionCarteJoueJ1X, positionCarteJoueJ1Y, rectWidth, rectHeight);

        // System.out.println("Faction et valeur de carte : " + carteJ2F + " " + carteJ2V);
        if (carteJ2F != -1 && carteJ2V != -1) {
            // dessin de la carte jouée par l'IA
            switch (carteJ2F) {
                case 1:
                    strImage = "goblin";
                    break;
                case 2:
                    strImage = "dwarve";
                    break;
                case 3:
                    strImage = "knight";
                    break;
                case 4:
                    strImage = "doppelganger";
                    break;
                case 5:
                    strImage = "undead";
                    break;
            }
            strImage += "_" + carteJ2V;
            image = imageMap.get(strImage);
            g.drawImage(image, positionCarteJoueJ2X, positionCarteJoueJ2Y, rectWidth, rectHeight, this);
        }
        g.drawRect(positionCarteJoueJ2X, positionCarteJoueJ2Y, rectWidth, rectHeight);
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

    public int posYMainJ1() {
        return posYJ1;
    }

    public int posYMainJ2() {
        return posYJ2;
    }

    public int posXMain() {
        return posX;
    }

    public int getLargeurMainJ1() {
        return totalWidthJ1P1;
    }

    public int getLargeurMainJ2() {
        return totalWidthJ2P1;
    }

    public int getLargeurMainJ1P2() {
        return totalWidthJ1P2;
    }

    public int getLargeurMainJ2P2() {
        return totalWidthJ2P2;
    }

    public int getHandJ1P1() {
        return HandJ1P1;
    }

    public int getHandJ1P2() {
        return HandJ1P2;
    }

    public int getHauteurMain() {
        return totalHeight;
    }

    private void drawScore(Graphics g, String faction, int i, int lineY, int cellHeight, Font font_1, BufferedImage icon_goblin, Font font_2, BufferedImage icon_knight, BufferedImage icon_undead, BufferedImage icon_dwarve, BufferedImage icon_doppleganger) {
        int textY;
        int imageX;
        int imageY;
        int textX;
        int score = jeu.getPlateau().getJoueur1().getPileDeScore().getCardFaction(faction).size() - jeu.getPlateau().getJoueur2().getPileDeScore().getCardFaction(faction).size();
        if (i > 0) {
            if (score > 0) {
                bgColor = Color.GREEN;
            } else if (score == 0) {
                if (jeu.getPlateau().getJoueur1().getPileDeScore().maxValueOfFaction(faction) > jeu.getPlateau().getJoueur2().getPileDeScore().maxValueOfFaction(faction)) {
                    bgColor = Color.GREEN;
                } else if (jeu.getPlateau().getJoueur1().getPileDeScore().maxValueOfFaction(faction) < jeu.getPlateau().getJoueur2().getPileDeScore().maxValueOfFaction(faction)) {
                    bgColor = Color.RED;
                } else {
                    bgColor = Color.GRAY;
                }
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
    
    private BufferedImage toGrayScale(BufferedImage originalImage) {
        // Crée une nouvelle image en niveaux de gris de la même taille que l'image originale
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        Graphics g = grayImage.getGraphics();
        g.drawImage(originalImage, 0, 0, null);
        g.dispose();

        return grayImage;
    }

    @Override
    public void miseAJour() {
        repaint();
    }


}

