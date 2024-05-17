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


    CollecteurEvenements control;

    // Variables pour les dimensions des dessins du niveau graphiques
    int largeurCase;
    int hauteurCase;
    int rectHeight;
    int rectWidth;
    int spacing;
    int startXJ1P1;
    int startXJ2P1;
    int startXJ1P2;
    int startXJ2P2;
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
    int fontSize_1;
    int fontSize_2;
    int panelWidth;
    int panelHeight;
    int positionCarteJoueJ1X, positionCarteJoueJ1Y;
    int positionCarteJoueJ2X, positionCarteJoueJ2Y;

    // Variables pour l'affichage du score
    int numRows;
    int cellHeight;
    int imageX, imageY, textX, textY;
    int lineY;
    String faction;
    int score;
    int val;
    FontMetrics fm;
    int textWidth;
    int textHeight;

    // private static final double RECTANGLE_SCALE_FACTOR = 0.05; // Adjust this value for scaling

    // Boolean pour les cartes jouables
    boolean isPlayable;


    // Main sous forme de couples d'entier des cartes dans la main d'un joueur.
    int[][] main;
    int[][] mainJ2;

    // Font pour le texte
    Font font;
    Font font_1;
    Font font_2;

    // Couleur du background
    Color bgColor;

    // Chargement des assets images pour l'affichage
    BufferedImage image;
    BufferedImage grayImage;
    BufferedImage icon_goblin;
    BufferedImage icon_knight;
    BufferedImage icon_undead;
    BufferedImage icon_dwarve;
    BufferedImage icon_doppleganger;

    // nom des imagers pour les chargées
    String strImage = "";

    // Valeur des cartes jouée par les 2 joueurs (-1 si aucune carte n'est actuellement joué)
    int carteJ1V = -1, carteJ1F = -1;
    int carteJ2V = -1, carteJ2F = -1;

    Jeu jeu;

    Graphics2D g;

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
        g = (Graphics2D) graphics;
        super.paintComponent(g);
        paintGameBoard();
    }

    private void paintGameBoard() {

        // Set bigger font size
        font = g.getFont().deriveFont(Font.BOLD, largeur() / 25f); // Adjust font size based on panel width
        g.setFont(font);

        panelWidth = getWidth();
        panelHeight = getHeight();

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

        // Definition of font
        fontSize_1 = Math.min((int) (panelWidth * 0.02), (int) (panelHeight * 0.035));
        font_1 = new Font("Arial", Font.PLAIN, fontSize_1);

        fontSize_2 = fontSize_1 * 3 / 4;
        font_2 = new Font("Arial", Font.PLAIN, fontSize_2);

        // Chargement icons
        icon_goblin = imageMap.get("icon_goblin");
        icon_knight = imageMap.get("icon_knight");
        icon_undead = imageMap.get("icon_undead");
        icon_dwarve = imageMap.get("icon_dwarve");
        icon_doppleganger = imageMap.get("icon_doppleganger");

        // Dessin des cartes de l'adversaire (IA)
        // Calculate rectangle dimensions based on panel size
        rectWidth = (int) (panelWidth * 0.05);
        rectHeight = Math.max(rectWidth, panelHeight * 4 / 30); // Ensure height is always greater than width

        // Calculate spacing between rectangles
        spacing = 0;

        // Calculate total width of all rectangles and spacing
        totalWidthJ1P1 = HandJ1P1 * rectWidth + (HandJ1P1 - 1) * spacing;
        totalWidthJ2P1 = HandJ2P1 * rectWidth + (HandJ2P1 - 1) * spacing;
        totalWidthJ1P2 = HandJ1P2 * rectWidth;
        totalWidthJ2P2 = HandJ2P2 * rectWidth;

        // Calculate starting x position to center the rectangles
        startXJ1P1 = (panelWidth - totalWidthJ1P1) / 2;
        startXJ2P1 = (panelWidth - totalWidthJ2P1) / 2;
        startXJ1P2 = (panelWidth - totalWidthJ1P2) / 2;
        startXJ2P2 = (panelWidth - totalWidthJ2P2) / 2;

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
                x = startXJ1P1 + i * (rectWidth + spacing);
                y = hauteur() - rectHeight - 10;
                g.setColor(Color.BLUE);
                main = jeu.getMainJoueur1Phase1();
                selectIcon(i, main);
            }

            // Dessin de la main du joueur 2
            for (int i = 0; i < HandJ2P1; i++) {
                x = startXJ2P1 + i * (rectWidth + spacing);
                y = 10;
                g.setColor(Color.BLUE);
                mainJ2 = jeu.getMainJoueur2Phase1();
                selectIcon(i, mainJ2);
            }

            // Draw follower deck Joueur 2
            x = panelWidth / 9;
            y = 20;
            g.fillRect(x, y, rectWidth, rectHeight);

            // Draw follower deck Joueur 1
            y = hauteur() - rectHeight - 20;
            g.fillRect(x, y, rectWidth, rectHeight);

            // Draw carte à gagner
            x = rectWidth * 5 / 2 + largeur() / 2;
            y = hauteur() / 2 - rectHeight / 2;
            getStrImage(jeu.getFactionScoreCarteAffiche());
            strImage += "_" + jeu.getValeurCarteAffiche();
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
            drawCarteJoue();

            // Draw score pile
            drawScorePile();


        }
        /* Phase 2 */
        else if (!control.getPhase()) {

            positionCarteJoueJ1X = totalWidthJ1P2 / 2 + startXJ1P2;
            positionCarteJoueJ1Y = panelHeight - totalHeight * 5 / 2 - 10;
            positionCarteJoueJ2X = totalWidthJ2P2 / 2 + startXJ2P2;
            positionCarteJoueJ2Y = totalHeight * 3 / 2 + 10;

            // Dessin des cartes de la main du joueur 1
            for (int i = 0; i < HandJ1P2; i++) {
                int x = startXJ1P2 + i * (rectWidth + spacing);
                int y = hauteur() - rectHeight - 10;
                g.setColor(Color.CYAN);
                main = jeu.getMainJoueur1Phase2();
                getStrImage(main[i][1]);
                strImage += "_" + main[i][0];
                image = imageMap.get(strImage);
                grayImage = toGrayScale(image);
                g.drawImage(image, x, y, rectWidth, rectHeight, this);
            }

            // Dessin de la main du joueur 2
            for (int i = 0; i < HandJ2P2; i++) {
                int x = startXJ2P2 + i * (rectWidth + spacing);
                int y = 10;
                g.setColor(Color.BLUE);
                mainJ2 = jeu.getMainJoueur2Phase2();
                getStrImage(main[i][1]);
                strImage += "_" + mainJ2[i][0];
                image = imageMap.get(strImage);
                grayImage = toGrayScale(image);

                // Si une carte est jouable, draw image, sinon draw grayImage
                g.drawImage(grayImage, x, y, rectWidth, rectHeight, this);
            }

            // Draw Feedfoward Carte joué
            drawCarteJoue();

            // Draw score pile
            drawScorePile();
        }
        // Dessin des cartes joué par les joueurs
        g.setColor(Color.RED);
        DrawCarteJoue(carteJ1F, carteJ1V, positionCarteJoueJ1X, positionCarteJoueJ1Y);
        DrawCarteJoue(carteJ2F, carteJ2V, positionCarteJoueJ2X, positionCarteJoueJ2Y);
    }

    private void DrawCarteJoue(int carteJF, int carteJV, int positionCarteJoueJX, int positionCarteJoueJY) {
        if (carteJF != -1 && carteJV != -1) {
            // dessin de la carte jouée
            getStrImage(carteJF);
            strImage += "_" + carteJV;
            image = imageMap.get(strImage);
            g.drawImage(image, positionCarteJoueJX, positionCarteJoueJY, rectWidth, rectHeight, this);
        }
        g.drawRect(positionCarteJoueJX, positionCarteJoueJY, rectWidth, rectHeight);
    }

    private void getStrImage(int factionScore) {
        switch (factionScore) {
            case 1 -> strImage = "goblin";
            case 2 -> strImage = "dwarve";
            case 3 -> strImage = "knight";
            case 4 -> strImage = "doppelganger";
            case 5 -> strImage = "undead";
        }
    }

    private void drawCarteJoue() {
        // Draw Carte
        g.setColor(Color.DARK_GRAY);
        x = largeur() / 2;
        y = hauteur() - rectHeight * 11 / 5;

        // g.drawImage(gaufre, x, y, rectWidth, rectHeight, this);
        g.drawRect(x, y, rectWidth, rectHeight);
    }

    private void drawScorePile() {
        // Draw score pile
        x = rectWidth;
        y = hauteur() / 2 - rectHeight;
        g.drawRect(x, y, rectWidth * 2, rectHeight * 2);

        numRows = 6;
        cellHeight = rectHeight * 2 / numRows;

        for (int i = 0; i < numRows; i++) {
            lineY = y + i * cellHeight;
            faction = "";
            getStrImage(i);

            // Calcul de score
            drawScore(i);
        }
    }

    private void selectIcon(int i, int[][] main) {
        getStrImage(main[i][1]);
        strImage += "_" + main[i][0];
        image = imageMap.get(strImage);
        grayImage = toGrayScale(image);
        isPlayable = false;
        for (int[] carteJouable : control.getCarteJouable()) {
            if (main[i][0] == carteJouable[0] && main[i][1] == carteJouable[1]) {
                isPlayable = true;
                break;
            }
        }

        if (isPlayable) {
            g.drawImage(image, x, y, rectWidth, rectHeight, this);
        } else {
            g.drawImage(grayImage, x, y, rectWidth, rectHeight, this);
        }
    }

    private void drawIcon(BufferedImage icon) {
        imageX = x + 5;
        imageY = lineY + (cellHeight - rectWidth) / 2;
        g.drawImage(icon, imageX, imageY, rectWidth * 5 / 8, rectWidth, this);

        fm = g.getFontMetrics(font_2);
        textWidth = fm.stringWidth(Integer.toString(score));
        textHeight = fm.getHeight();

        textX = x + (rectWidth * 2 - textWidth) / 2;
        textY = lineY + (cellHeight + textHeight * 2 / 3) / 2;
        g.setFont(font_2);
        g.drawString(Integer.toString(score), textX, textY);
        imageX = x + rectWidth * 3 / 2;
        imageY = lineY + (cellHeight - rectHeight / 4) / 2;
        // Draw la carte gagnee avec la plus grand valeur
        g.drawImage(image, imageX, imageY, rectWidth / 4, rectHeight / 4, this);
    }

    private void drawScore(int i) {
        score = jeu.getPlateau().getJoueur1().getPileDeScore().getCardFaction(faction).size() - jeu.getPlateau().getJoueur2().getPileDeScore().getCardFaction(faction).size();
        val = Math.max(jeu.getPlateau().getJoueur1().getPileDeScore().maxValueOfFaction(faction), jeu.getPlateau().getJoueur2().getPileDeScore().maxValueOfFaction(faction));

        if (val >= 0) {
            strImage= "carte_" + val;
        } else {
            strImage = "carte_blanc";
        }

        image = imageMap.get(strImage);

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
            textWidth = fm.stringWidth("SCORE");
            textHeight = fm.getHeight();

            textX = x + (rectWidth * 2 - textWidth) / 2;
            textY = lineY + (cellHeight + textHeight) / 2;
            g.setFont(font_1);
            g.drawString("SCORE", textX, textY);
        }
        if (i == 1) {
            //Draw icon goblin
            drawIcon(icon_goblin);
        }
        if (i == 3) {
            //Draw icon knight
            drawIcon(icon_knight);
        }
        if (i == 5) {
            // Draw icon undead
            drawIcon(icon_undead);
        }
        if (i == 2) {
            // Draw icon dwarve
            drawIcon(icon_dwarve);
        }
        if (i == 4) {
            // Draw icon dopplegagner
            drawIcon(icon_doppleganger);
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

    public int posYMainJ1() {
        return posYJ1;
    }

    public int posYMainJ2() {
        return posYJ2;
    }

    public int posXMainJ1() {
        return startXJ1P1;
    }

    public int posXMainJ2() {
        return startXJ2P1;
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

