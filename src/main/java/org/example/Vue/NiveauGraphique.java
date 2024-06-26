package org.example.Vue;


import org.example.Modele.Jeu;
import org.example.Patternes.Observateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    int startHandXJ1;
    int startHandXJ2;
    int posHandYJ1;
    int posHandYJ2;
    int totalWidthJ1;
    int totalWidthJ2;
    int totalHeight;
    int nbCardHandJ1;
    int nbCardHandJ2;

    int x, y;
    int fontSize_1;
    int fontSize_2;
    int fontSize_3;
    int panelWidth;
    int panelHeight;
    int positionCarteJoueJ1X, positionCarteJoueJ1Y;
    int positionCarteJoueJ2X, positionCarteJoueJ2Y;
    int positionCarteAfficheeX, positionCarteAfficheeY;
    int positionFollower1X, positionFollower1Y;
    int positionFollower2X, positionFollower2Y;
    int positionDefausseX, positionDefausseY;
    int positionDeckX, positionDeckY;
    int positionPileScoreJ1X;
    int positionPileScoreJ1Y;
    int positionPileScoreJ2X;
    int positionPileScoreJ2Y;

    double currentCarteJoueX, currentCarteJoueY;
    double deltaX, deltaY, deltaGagneX, deltaGagneY;
    double deltaDefausse1X, deltaDefausse1Y, deltaDefausse2X, deltaDefausse2Y;
    double deltaPerdeX, deltaPerdeY;
    int deltaTransparence;

    //double deltaX, deltaY;
    int totalIterations;
    double currentCarteaganeeX, currentCarteaganeeY;
    double currentCarteJoue1X, currentCarteJoue1Y;
    double currentCarteJoue2X, currentCarteJoue2Y;
    double currentCartePerdeX, currentCartePerdeY;
    int currentTransparence;

    // Variables pour l'affichage du score
    int numRows;
    int cellHeight;
    int imageX, imageY, textX, textY;
    int lineY;
    String faction;
    String messageGagnant;
    int score;
    int val;
    FontMetrics fm;
    int textWidth;
    int textHeight;
    boolean fini;
    // private static final double RECTANGLE_SCALE_FACTOR = 0.05; // Adjust this value for scaling

    // Boolean pour les cartes jouables
    boolean isPlayable;
    DrawCheck drawC;

    // Main sous forme de couples d'entier des cartes dans la main d'un joueur.
    int[][] main;
    int[][] mainJ2;

    // Font pour le texte
    Font font;
    Font font_1;
    Font font_2;
    Font font_3;

    // Couleur du background
    Color bgColor;
    Color textColor;
    int transparence;
    // Chargement des assets images pour l'affichage
    BufferedImage image;
    BufferedImage grayImage;
    Image icon_goblin;
    Image icon_knight;
    Image icon_undead;
    Image icon_dwarve;
    Image icon_doppleganger;

    // nom des imagers pour les charger
    String strImage = "";

    // Valeur des cartes jouée par les 2 joueurs (-1 si aucune carte n'est actuellement joué)
    int carteJ1V = -1, carteJ1F = -1;
    int carteJ2V = -1, carteJ2F = -1;

    Jeu jeu;
    ComposantRejouer rec;
    ComposantFinPartie fin;


    /* Load assets */
    Map<String, BufferedImage> imageMap = new HashMap<>();

    public NiveauGraphique(Jeu j, CollecteurEvenements c, ComposantRejouer rejouer, ComposantFinPartie finPartie, DrawCheck drawCheck) throws IOException {
        control = c;
        jeu = j;
        jeu.ajouteObservateur(this);
        rec = rejouer;
        drawC = drawCheck;
        fin = finPartie;
        // Load images
        String contenu = ResourceManager.readTextFile("/fileNames.txt");
        String[] lignes = contenu.split("\n");
        for (String ligne : lignes) {
            acceptFile(new File(ligne));
        }
        loadIcon();
    }

    public void loadImages() {
        acceptFile(new File("icon_goblin.png"));
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

    private void paintGameBoard(Graphics2D g) {
        g.drawImage(imageMap.get("backBlue"),0, 0, getWidth(), getHeight(), this);
        // Set bigger font size
        font = g.getFont().deriveFont(Font.BOLD, largeur() / 25f); // Adjust font size based on panel width
        g.setFont(font);

        panelWidth = getWidth();
        panelHeight = getHeight();

        // Taille de la main
        nbCardHandJ1 = control.getNbCardsJ1P1(); // Nombre de carte dans la main du joueur 1 à la phase 1
        // HandJ1P2 = control.getNbCardsJ1P2(); // Nombre de carte dans la main du joueur 1 à la phase 2
        nbCardHandJ2 = control.getNbCardsJ2P1(); // Nombre de carte dans la main du joueur 2 à la phase 1
        // HandJ2P2 = control.getNbCardsJ2P2(); // Nombre de carte dans la main du joueur 2 à la phase 2

        // Si une carte est jouée :
        carteJ1V = control.getCarteJoueur1V();
        carteJ1F = control.getCarteJoueur1F();
        carteJ2V = control.getCarteJoueur2V();
        carteJ2F = control.getCarteJoueur2F();

        // Definition of font
        fontSize_1 = Math.min((int) (panelWidth * 0.02), (int) (panelHeight * 0.035));
        font_1 = new Font("Arial", Font.PLAIN, fontSize_1);

        fontSize_2 = fontSize_1 * 3 / 4;
        font_2 = new Font("Arial", Font.PLAIN, fontSize_2);

        fontSize_3 = fontSize_1 * 2;
        font_2 = new Font("Arial", Font.PLAIN, fontSize_2);

        // Calculate rectangle dimensions based on panel size
        rectWidth = (int) (panelWidth * 0.06);
        rectHeight = Math.max(rectWidth, (panelHeight * 4) / 30); // Ensure height is always greater than width

        // Calculate spacing between rectangles
        spacing = 0;

        // Calculate total width of all rectangles and spacing
        totalWidthJ1 = nbCardHandJ1 * rectWidth + (nbCardHandJ1 - 1) * spacing;
        totalWidthJ2 = nbCardHandJ2 * rectWidth + (nbCardHandJ2 - 1) * spacing;
        // totalWidthJ1P2 = HandJ1P2 * rectWidth + (HandJ1P2 - 1) * spacing;
        // totalWidthJ2P2 = HandJ2P2 * rectWidth + (HandJ2P2 - 1) * spacing;

        // Calculate starting x position to center the rectangles
        startHandXJ1 = (panelWidth - totalWidthJ1) / 2;
        startHandXJ2 = (panelWidth - totalWidthJ2) / 2;
        // startXJ1P2 = (panelWidth - totalWidthJ1P2) / 2;
        // startXJ2P2 = (panelWidth - totalWidthJ2P2) / 2;

        posHandYJ1 = hauteur() - rectHeight - 10;
        posHandYJ2 = 10;
        totalHeight = rectHeight;


        if (control.estFinPartie()) {
            fin.JoueurGagnant = jeu.getJoueurNomGagnant();
            fin.messageLabel.setText("Le Joueur " + fin.JoueurGagnant + " a gagné");
            fin.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    rec.setVisible(true);
                }
            });
            fin.ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fin.setVisible(false);
                    rec.setVisible(true);
                }
            });
            if (!control.getPause()) {
                fin.setVisible(true);
            }
        }

        /* Phase 1 */
        if (control.getPhase()) {


            y = hauteur() - rectHeight - 10;
            main = control.getHandJ1P1();
            // Dessin des cartes de la main du joueur 1

            if (drawC.isDrawHandJ1Toggle()) {
                drawHiddenHand(g, nbCardHandJ1);
            } else
                for (int i = 0; i < nbCardHandJ1; i++) {
                    x = startHandXJ1 + i * (rectWidth + spacing);
                    drawHand(g, i, main, control.getNomJoueur2());
                }

            // Ajouter "À toi de jouer" pour le joueur 1
            if (control.isJoueurCourantJoueur1()) {
                g.setFont(font_2);
                g.setColor(Color.RED); // Utiliser la couleur actuelle
                g.drawString("À toi de jouer", positionPileScoreJ1X - 30, posHandYJ1 - 23);
            }

            y = 10;
            mainJ2 = control.getHandJ2P1();
            drawHandJ2(g);


            drawJoueurGagnant(g);
            // Dessine les decks de followers
//            positionFollower1X = panelWidth / 9;
//            positionFollower2X = panelWidth / 9;
//            positionFollower1Y = hauteur() - rectHeight - 20;
//            positionFollower2Y = 20;
            drawFollowerDeck(g);

            // Draw carte a gagne
            positionCarteAfficheeX = rectWidth * 5 / 2 + largeur() / 2;
            positionCarteAfficheeY = hauteur() / 2 - rectHeight / 2;
            if (control.getPause())
                drawCardToWinDistribuer(g);
            else
                drawCardToWin(g);

            // Draw deck
            drawDeck(g);

            // Animation perdre
            drawAnimationPerde(g);

            // Draw defausse
            // positionDefausseX = largeur() - largeur() / 8;
            // positionDefausseY = hauteur() / 2 + rectHeight / 4;
            drawDefausse(g);

            // Draw score pile
            x = rectWidth;
            y = hauteur() / 2 - rectHeight;
            positionPileScoreJ1X = x * 3;
            positionPileScoreJ1Y = y + rectHeight;
            positionPileScoreJ2X = positionPileScoreJ1X;
            positionPileScoreJ2Y = y;
            // Pile de score J1
            g.drawString("Pile de score J1", x * 3, y + rectHeight * 2 + 20);
            g.drawImage(imageMap.get("carte_score"), x * 3, y + rectHeight, rectWidth, rectHeight, this);
            // Pile de score J2
            g.drawString("Pile de score J2", x * 3, y - 20);
            g.drawImage(imageMap.get("carte_score"), x * 3, y, rectWidth, rectHeight, this);
            if (drawC.isDrawScorePileToggle())
                drawScorePile(g);

            transparence = 0;
            drawTransitionAnimation(g);

            /* Phase 2 */
        } else if (!control.getPhase()) {

            // Ajouter "À toi de jouer" pour le joueur 1
            if (control.isJoueurCourantJoueur1()) {
                g.setFont(font_2);
                g.setColor(Color.RED); // Utiliser la couleur actuelle
                g.drawString("À toi de jouer", positionPileScoreJ1X - 30, posHandYJ1 - 23);
            }
            y = hauteur() - rectHeight - 10;
            main = control.getHandJ1P2();

            // Dessin des cartes de la main du joueur 1
            if (drawC.isDrawHandJ1Toggle()) {
                drawHiddenHand(g, nbCardHandJ1);
            } else
                for (int i = 0; i < nbCardHandJ1; i++) {
                    x = startHandXJ1 + i * (rectWidth + spacing);
                    drawHand(g, i, main, control.getNomJoueur2());
                }

            y = 10;
            mainJ2 = control.getHandJ2P2();
            drawHandJ2(g);
            drawJoueurGagnant(g);
            // Draw score pile
            x = rectWidth;
            y = hauteur() / 2 - rectHeight;
            positionPileScoreJ1X = x * 3;
            positionPileScoreJ1Y = y + rectHeight;
            positionPileScoreJ2X = positionPileScoreJ1X;
            positionPileScoreJ2Y = y;
            // Pile de score J1
            g.drawImage(imageMap.get("carte_score"), x * 3, y + rectHeight, rectWidth, rectHeight, this);
            // Pile de score J2
            g.drawImage(imageMap.get("carte_score"), x * 3, y, rectWidth, rectHeight, this);
            if (drawC.isDrawScorePileToggle())
                drawScorePile(g);
        }

        // Dessins des cartes jouées par chaque joueur
        positionCarteJoueJ1X = totalWidthJ1 / 2 + startHandXJ1;
        positionCarteJoueJ1Y = panelHeight - totalHeight * 5 / 2 - 10;
        positionCarteJoueJ2X = totalWidthJ2 / 2 + startHandXJ2;
        positionCarteJoueJ2Y = totalHeight * 3 / 2 + 10;


        drawCarteJoue(g, carteJ1F, carteJ1V, positionCarteJoueJ1X, positionCarteJoueJ1Y, currentCarteJoue1X, currentCarteJoue1Y);
        drawCarteJoue(g, carteJ2F, carteJ2V, positionCarteJoueJ2X, positionCarteJoueJ2Y, currentCarteJoue2X, currentCarteJoue2Y);
    }

    private void drawHiddenHand(Graphics2D g, int nbCardHand) {
        for (int i = 0; i < nbCardHand; i++) {
            x = startHandXJ2 + i * (rectWidth + spacing);
            g.setColor(Color.GRAY);
            image = imageMap.get("backside");
            g.drawImage(image, x, y, rectWidth, rectHeight, this);
        }
    }

    private void drawJoueurGagnant(Graphics2D g) {
        g.setFont(font_2);
        g.setColor(Color.RED);

        if (messageGagnant != null) {
            g.drawString(messageGagnant, positionCarteAfficheeX + rectHeight, posHandYJ1 - rectHeight);
        } else {
            g.drawString("", positionCarteAfficheeX + rectHeight, posHandYJ1 - rectHeight);
        }

    }

    private void drawHandJ2(Graphics2D g) {
        if (!drawC.isDrawHandToggle()) {
            // Dessin de la main face caché du joueur 2 si il est une IA
            drawHiddenHand(g, nbCardHandJ2);
        } else {
            // Dessin de la main du joueur 2
            for (int i = 0; i < nbCardHandJ2; i++) {
                x = startHandXJ2 + i * (rectWidth + spacing);
                drawHand(g, i, mainJ2, control.getNomJoueur1());
            }
        }
    }

    /* Dessine la carte à gagner dans la phase 1 */
    private void drawCardToWin(Graphics g) {

        getStrImage(control.getCarteAfficheeFactionScore());
        strImage += "_" + control.getCarteAfficheeValeur();
        image = imageMap.get(strImage);
        if (currentCarteaganeeX == 0 && currentCarteaganeeY == 0) {
            g.drawImage(image, positionCarteAfficheeX, positionCarteAfficheeY, rectWidth, rectHeight, this);
        } else if (currentCarteaganeeX < positionCarteAfficheeX || currentCarteaganeeX > positionDeckX) {
            g.drawImage(image, positionCarteAfficheeX, positionCarteAfficheeY, rectWidth, rectHeight, this);
        } else if (currentCarteaganeeY > positionCarteAfficheeY || currentCarteaganeeY < positionDeckY) {
            g.drawImage(image, positionCarteAfficheeX, positionCarteAfficheeY, rectWidth, rectHeight, this);
        } else {
            g.drawImage(image, (int) currentCarteaganeeX, (int) currentCarteaganeeY, rectWidth, rectHeight, this);
        }
    }

    private void drawCardToWinDistribuer(Graphics g) {
        getStrImage(control.getCarteAfficheeFactionScore());
        strImage += "_" + control.getCarteAfficheeValeur();
        image = imageMap.get(strImage);
        if (currentCarteaganeeX == 0 && currentCarteaganeeY == 0) {
            g.drawImage(image, positionCarteAfficheeX, positionCarteAfficheeY, rectWidth, rectHeight, this);
        } else {
            g.drawImage(image, (int) currentCarteaganeeX, (int) currentCarteaganeeY, rectWidth, rectHeight, this);
        }
    }

    public Point getPositionScorePile() {
        // Calcul des coordonnées X et Y de la pile de score
        x = rectWidth; // Position X de la pile de score
        y = getHeight() / 2 - rectHeight; // Position Y de la pile de score (centrée verticalement)

        return new Point(x, y);
    }

    public int getLigneCliquee(int mouseY) {
        // Récupérer les coordonnées de la pile de score
        Point positionScorePile = getPositionScorePile();
        int scorePileY = positionScorePile.y;

        // Calculer la ligne sur laquelle vous avez cliqué
        return (mouseY - scorePileY) / (rectHeight * 2 / 6);
    }

    public boolean estDansPileDeScore(int x, int y) {
        // Récupérer les coordonnées de la pile de score
        Point positionScorePile = getPositionScorePile();
        int scorePileX = positionScorePile.x;
        int scorePileY = positionScorePile.y;

        // Vérifier si les coordonnées (x, y) sont dans la pile de score

        return x >= scorePileX && x <= scorePileX + rectWidth * 2 &&
                y >= scorePileY && y <= scorePileY + rectHeight * 2;
    }

    /* Dessine la pile de score */
    private void drawScorePile(Graphics g) {
        x = rectWidth;
        y = hauteur() / 2 - rectHeight;

        // Dessin de l'emplacement de la pile de score
        g.drawRect(x, y, rectWidth * 2, rectHeight * 2);

        numRows = 6;
        cellHeight = rectHeight * 2 / numRows;

        for (int i = 0; i < numRows; i++) {
            lineY = y + i * cellHeight;
            faction = assignFactionToNumber(i);
            // Calcul de score
            calculScore(g, i);
        }
    }

    public String assignFactionToNumber(int indice) {
        String fact = "";
        switch (indice) {
            case 1:
                fact = "Goblins";
                break;
            case 2:
                fact = "Dwarves";
                break;
            case 3:
                fact = "Knight";
                break;
            case 4:
                fact = "Doppelganger";
                break;
            case 5:
                fact = "Undead";
                break;
        }
        return fact;
    }

    /* Dessine la défausse pour la phase */
    private void drawDefausse(Graphics g) {
        g.setColor(Color.ORANGE);
        x = positionDeckX;
        y = positionDeckY - rectHeight - 20;
        positionDefausseX = x;
        positionDefausseY = y;

        // g.fillRect(x, y, rectHeight, rectWidth); // Rectangle latéral
        g.drawString("Défausse", x, y - 10);
        g.drawImage(imageMap.get("carte_placement_defausse"), x, y, rectWidth, rectHeight, this);
    }

    /* Dessine la pioche pour la phase 1 */
    private void drawDeck(Graphics g) {
        g.setColor(Color.ORANGE);
        positionDeckX = largeur() - largeur() / 8;
        positionDeckY = hauteur() / 2;
        // g.fillRect(x, y, rectHeight, rectWidth); // Rectangle latéral
        g.drawString("Pioche", positionDeckX, positionDeckY + rectHeight + 20);
        g.drawImage(imageMap.get("carte_deck"), positionDeckX, positionDeckY, rectWidth, rectHeight, this);
    }

    private void drawAnimationPerde(Graphics g) {
        if (jeu.estCarteJoueJ1() && jeu.estCarteJoueJ2()) {
            g.drawImage(imageMap.get("backside"), (int) currentCartePerdeX, (int) currentCartePerdeY, rectWidth, rectHeight, this);
        }
    }

    /* Dessine la main selon un couple d'entier */
    private void drawHand(Graphics2D g, int i, int[][] main, String Joueur) {
        // jeu.getPlateau().getJoueur1().getHand().printHand();
        getStrImage(main[i][1]);
        strImage += "_" + main[i][0];
        image = imageMap.get(strImage);
        isPlayable = false;
        for (int[] carteJouable : control.getCarteJouable()) {
            if (main[i][0] == carteJouable[0] && main[i][1] == carteJouable[1]) {
                isPlayable = true;
                break;
            }
        }
        if (control.getPause()) {
            isPlayable = false;
        }
        if (isPlayable) {
            if (strImage.equals("goblin_0") && control.getNomJoueurCourant().equals(Joueur)) {
                grayImage = toGrayScale(image);
                g.drawImage(grayImage, x, y, rectWidth, rectHeight, this);
            } else {
                g.setStroke(new BasicStroke(4));
                if (control.estCarteJoueJ2() || control.estCarteJoueJ1()) {
                    for (int[] carteGagnante : control.getCarteGagnante()) {
                        if (main[i][0] == carteGagnante[0] && main[i][1] == carteGagnante[1]) {
                            g.setColor(Color.GREEN);
                            DrawWinLoseCards(g);
                        }
                    }
                    for (int[] cartePerdante : control.getCartePerdante()) {
                        if (main[i][0] == cartePerdante[0] && main[i][1] == cartePerdante[1]) {
                            g.setColor(Color.RED);
                            DrawWinLoseCards(g);
                        }
                    }
                } else {
                    if (control.isJoueurCourantJoueur1()) {
                        // Dessiner rectangle vert de stroke de taille large
                        g.drawImage(image, x, y - 20, rectWidth, rectHeight, this);
                    } else {
                        // Dessiner rectangle vert de stroke de taille large
                        g.drawImage(image, x, y + 20, rectWidth, rectHeight, this);
                    }
                }
            }
        } else {
            grayImage = toGrayScale(image);
            g.drawImage(grayImage, x, y, rectWidth, rectHeight, this);
        }
        g.setStroke(new BasicStroke(2));
    }

    private void DrawWinLoseCards(Graphics2D g) {
        if (control.isJoueurCourantJoueur1()) {
            // Dessiner rectangle vert de stroke de taille large
            g.drawImage(image, x, y - 20, rectWidth, rectHeight, this);
            g.drawRect(x, y - 20, rectWidth, rectHeight);
        } else {
            // Dessiner rectangle vert de stroke de taille large
            g.drawImage(image, x, y + 20, rectWidth, rectHeight, this);
            g.drawRect(x, y + 20, rectWidth, rectHeight);
        }
    }

    private void drawCarteGagnante(Graphics g) {

    }

    /* Permet de trouver le nom de l'image voulu pour la charger */
    private void getStrImage(int indiceFaction) {
        switch (indiceFaction) {
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
    }

    /* Dessine une carte selon son couple d'entier (valeur, faction) et sa position (X, Y) */
    private void drawCarteJoue(Graphics g, int carteJF, int carteJV, int positionCarteJoueJX, int positionCarteJoueJY, double currentCarteJoueX, double currentCarteJoueY) {
        g.drawImage(imageMap.get("carte_placement"), positionCarteJoueJX, positionCarteJoueJY, rectWidth, rectHeight, this);
        if (carteJF != -1 && carteJV != -1) {
            // Dessin de la carte jouée
            getStrImage(carteJF);
            strImage += "_" + carteJV;
            image = imageMap.get(strImage);
            if (currentCarteJoueX == 0 && currentCarteJoueY == 0) {
                g.drawImage(image, positionCarteJoueJX, positionCarteJoueJY, rectWidth, rectHeight, this);
            } else if (!(jeu.estCarteJoueJ1() && jeu.estCarteJoueJ2())) {
                g.drawImage(image, positionCarteJoueJX, positionCarteJoueJY, rectWidth, rectHeight, this);
            } else {
                g.drawImage(image, (int) currentCarteJoueX, (int) currentCarteJoueY, rectWidth, rectHeight, this);
            }
        }
    }

    /* Dessine une icon selon une image pour la pile de score */
    private void drawIcon(Graphics g, Image icon) {
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

    private void calculScore(Graphics g, int i) {

        score = control.getNbCardFactionFromPileScoreJ1(faction) - control.getNbCardFactionFromPileScoreJ2(faction);
        val = control.getMaxValueFromPileScore(faction);
        strImage = val >= 0 ? "carte_" + val : "carte_blanc";

        image = imageMap.get(strImage);

        if (i > 0) {
            if (score > 0) {
                bgColor = Color.GREEN;
            } else if (score == 0) {
                if ((control.isJoueur1WinningFactionOnEquality(faction))) {
                    bgColor = Color.GREEN;
                } else if (control.isJoueur2WinningFactionOnEquality(faction)) {
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
        int cellWidth = 100;
        if (i == 0) {
            // Ecris score dans la première ligne
            fm = g.getFontMetrics(font_1);
            textWidth = fm.stringWidth("SCORE");
            textHeight = fm.getHeight();

            textX = x + (rectWidth * 2 - textWidth) / 2;
            textY = lineY + (cellHeight + textHeight) / 2;
            g.setFont(font_1);
            g.drawString("SCORE", textX, textY);


        }
        // Draw icon goblin
        if (i == 1) {
            drawIcon(g, icon_goblin);
        }
        // Draw icon dwarve
        if (i == 2) drawIcon(g, icon_dwarve);
        //Draw icon knight
        if (i == 3) drawIcon(g, icon_knight);
        // Draw icon dopplegagner
        if (i == 4) drawIcon(g, icon_doppleganger);
        // Draw icon undead
        if (i == 5) drawIcon(g, icon_undead);
    }

    private BufferedImage toGrayScale(BufferedImage originalImage) {
        // Crée une nouvelle image en niveaux de gris de la même taille que l'image originale
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        Graphics g = grayImage.getGraphics();
        g.drawImage(originalImage, 0, 0, null);
        g.dispose();

        return grayImage;
    }

    // Dessine les decks de followers
    private void drawFollowerDeck(Graphics g) {
        g.setColor(Color.ORANGE);
        x = (panelWidth - rectWidth * 2) + 20;
        // Draw follower deck Joueur 1
        // x = startHandXJ1 + totalWidthJ1 + 20;
        y = hauteur() - rectHeight - 20;
        // g.fillRect(x, y, rectWidth, rectHeight);
        positionFollower1X = x;
        positionFollower1Y = y;
        g.drawString("Seconde Main J1", x - 30, y - 20);
        g.drawImage(imageMap.get("carte_placement_follower"), x, y, rectWidth, rectHeight, this);

        // Draw follower deck Joueur 2
        // x = startHandXJ2 + totalWidthJ2 + 20;
        y = 20;
        // g.fillRect(x, y, rectWidth, rectHeight);
        positionFollower2X = x;
        positionFollower2Y = y;
        g.drawString("Seconde Main J2", x - 30, y + rectHeight + 20);
        g.drawImage(imageMap.get("carte_placement_follower"), x, y, rectWidth, rectHeight, this);
    }

    private void drawTransitionAnimation(Graphics g) {
        g.setFont(new Font("Arial", Font.PLAIN, fontSize_3));

        if (!(jeu.getJoueur1().getHand().size() + jeu.getJoueur2().getHand().size() == 0)) {
            currentTransparence = 0;
        }
        textColor = new Color(192, 192, 192, currentTransparence);

        g.setColor(textColor);

        FontMetrics metrics = g.getFontMetrics();
        int lineHeight = metrics.getHeight();
        int stringWidth1 = metrics.stringWidth("Fin de la phase un");
        int x = (getWidth() - stringWidth1) / 2;
        int y1 = (getHeight() - 2 * lineHeight) / 2 + lineHeight;
        int y2 = y1 + lineHeight;

        g.drawString("Fin de la phase 1", x, y1);
        g.drawString("Début de la phase 2! ", x, y2);

    }


    // Vérifie si un clic est sur le deck des followers
    public boolean isClickOnFollowerDeck(int clickX, int clickY) {
        // Vérifie si le clic est sur le deck du Joueur 1
        if (clickX >= positionFollower1X && clickX <= positionFollower1X + rectWidth &&
                clickY >= positionFollower1Y && clickY <= positionFollower1Y + rectHeight) {
            return true;
        }
        // Si le clic n'est pas sur l'un des decks
        return false;
    }

    public boolean isMouseOverFollowerDeck(int mouseX, int mouseY) {
        // Vérifie si la souris est au-dessus du deck du Joueur 1
        return mouseX >= positionFollower1X && mouseX <= positionFollower1X + rectWidth &&
                mouseY >= positionFollower1Y && mouseY <= positionFollower1Y + rectHeight;
    }

    /* Getteurs */
    private int largeur() {
        return getWidth();
    }

    private int hauteur() {
        return getHeight();
    }

    // Public getteur pour niveauGraphique
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
        return posHandYJ1;
    }

    public int posYMainJ2() {
        return posHandYJ2;
    }

    public int posXMainJ1() {
        return startHandXJ1;
    }

    public int posXMainJ2() {
        return startHandXJ2;
    }

    public int getLargeurMainJ1() {
        return totalWidthJ1;
    }

    public int getLargeurMainJ2() {
        return totalWidthJ2;
    }

    public int getHandJ1() {
        return nbCardHandJ1;
    }

    public int getNbCardHandJ2() {
        return nbCardHandJ2;
    }

    public int getHauteurMain() {
        return totalHeight;
    }

    public int getPositionFollowersPileJ1X() {
        return positionFollower1X;
    }

    public int getPositionFollowersPileJ1Y() {
        return positionFollower1Y;
    }

    public int getPositionFollowersPileJ2X() {
        return positionFollower2X;
    }

    public int getPositionFollowersPileJ2Y() {
        return positionFollower2Y;
    }

    @Override
    public void miseAJour() {
        repaint();
    }

    public void initializeAnimationDistribuer(int totalIterations) {
        this.totalIterations = totalIterations;

        this.deltaX = (positionDeckX - positionCarteAfficheeX) / (double) totalIterations;
        this.deltaY = (positionDeckY - positionCarteAfficheeY) / (double) totalIterations;

        this.currentCarteaganeeX = positionDeckX;
        this.currentCarteaganeeY = positionDeckY;

    }

    public void distribuer() {
        if (currentCarteaganeeX != positionDeckX) {
            messageGagnant = "";
        }
        currentCarteaganeeX -= deltaX;
        currentCarteaganeeY -= deltaY;
        miseAJour();
    }

    public void initializeAnimationGagne(int totalIterations, int joueur, String nomGagnant) {
        this.totalIterations = totalIterations;
        if (joueur == 1) {
            this.deltaGagneX = (positionCarteAfficheeX - positionFollower1X) / (double) totalIterations;
            this.deltaGagneY = (positionCarteAfficheeY - positionFollower1Y) / (double) totalIterations;
        } else {
            this.deltaGagneX = (positionCarteAfficheeX - positionFollower2X) / (double) totalIterations;
            this.deltaGagneY = (positionCarteAfficheeY - positionFollower2Y) / (double) totalIterations;
        }

        this.currentCarteaganeeX = positionCarteAfficheeX;
        this.currentCarteaganeeY = positionCarteAfficheeY;

        this.messageGagnant = nomGagnant + " a gagné le tour";
    }

    public void distribuerGagne() {
        currentCarteaganeeX -= deltaGagneX;
        currentCarteaganeeY -= deltaGagneY;

        miseAJour();
    }

    public void initializeAnimationPerde(int totalIterations, int joueur) {
        this.totalIterations = totalIterations;
        if (joueur == 1) {
            this.deltaPerdeX = (positionDeckX - positionFollower2X) / (double) totalIterations;
            this.deltaPerdeY = (positionDeckY - positionFollower2Y) / (double) totalIterations;
        } else {
            this.deltaPerdeX = (positionDeckX - positionFollower1X) / (double) totalIterations;
            this.deltaPerdeY = (positionDeckY - positionFollower1Y) / (double) totalIterations;
        }

        this.currentCartePerdeX = positionDeckX;
        this.currentCartePerdeY = positionDeckY;
    }

    public void distribuerPerde() {
        currentCartePerdeX -= deltaPerdeX;
        currentCartePerdeY -= deltaPerdeY;
        miseAJour();
    }

    public void initializeAnimationDefausse(int totalIterations, int card1Faction, int card2Faction, int joueur) {
        this.totalIterations = totalIterations;
        this.deltaDefausse1X = (positionCarteJoueJ1X - positionDefausseX) / (double) totalIterations;
        this.deltaDefausse1Y = (positionCarteJoueJ1Y - positionDefausseY) / (double) totalIterations;
        this.deltaDefausse2X = (positionCarteJoueJ2X - positionDefausseX) / (double) totalIterations;
        this.deltaDefausse2Y = (positionCarteJoueJ2Y - positionDefausseY) / (double) totalIterations;

        if (card1Faction == 5 && (jeu.getPhase())) {
            if (joueur == 1) {
                this.deltaDefausse1X = (positionCarteJoueJ1X - positionPileScoreJ1X) / (double) totalIterations;
                this.deltaDefausse1Y = (positionCarteJoueJ1Y - positionPileScoreJ1Y) / (double) totalIterations;
            } else {
                this.deltaDefausse1X = (positionCarteJoueJ1X - positionPileScoreJ2X) / (double) totalIterations;
                this.deltaDefausse1Y = (positionCarteJoueJ1Y - positionPileScoreJ2Y) / (double) totalIterations;

            }
        }

        if (card2Faction == 5 && (jeu.getPhase())) {
            if (joueur == 1) {
                this.deltaDefausse2X = (positionCarteJoueJ2X - positionPileScoreJ1X) / (double) totalIterations;
                this.deltaDefausse2Y = (positionCarteJoueJ2Y - positionPileScoreJ1Y) / (double) totalIterations;
            } else {
                this.deltaDefausse2X = (positionCarteJoueJ2X - positionPileScoreJ2X) / (double) totalIterations;
                this.deltaDefausse2Y = (positionCarteJoueJ2Y - positionPileScoreJ2Y) / (double) totalIterations;

            }
        }

        if (!jeu.getPhase()) {
            if (joueur == 1) {
                this.deltaDefausse1X = (positionCarteJoueJ1X - positionPileScoreJ1X) / (double) totalIterations;
                this.deltaDefausse1Y = (positionCarteJoueJ1Y - positionPileScoreJ1Y) / (double) totalIterations;
                this.deltaDefausse2X = (positionCarteJoueJ2X - positionPileScoreJ1X) / (double) totalIterations;
                this.deltaDefausse2Y = (positionCarteJoueJ2Y - positionPileScoreJ1Y) / (double) totalIterations;
                if (card1Faction == 2) {
                    this.deltaDefausse1X = (positionCarteJoueJ1X - positionPileScoreJ2X) / (double) totalIterations;
                    this.deltaDefausse1Y = (positionCarteJoueJ1Y - positionPileScoreJ2Y) / (double) totalIterations;
                }
                if (card2Faction == 2) {
                    this.deltaDefausse2X = (positionCarteJoueJ2X - positionPileScoreJ2X) / (double) totalIterations;
                    this.deltaDefausse2Y = (positionCarteJoueJ2Y - positionPileScoreJ2Y) / (double) totalIterations;
                }
            } else {
                this.deltaDefausse1X = (positionCarteJoueJ1X - positionPileScoreJ2X) / (double) totalIterations;
                this.deltaDefausse1Y = (positionCarteJoueJ1Y - positionPileScoreJ2Y) / (double) totalIterations;
                this.deltaDefausse2X = (positionCarteJoueJ2X - positionPileScoreJ2X) / (double) totalIterations;
                this.deltaDefausse2Y = (positionCarteJoueJ2Y - positionPileScoreJ2Y) / (double) totalIterations;
                if (card1Faction == 2) {
                    this.deltaDefausse1X = (positionCarteJoueJ1X - positionPileScoreJ1X) / (double) totalIterations;
                    this.deltaDefausse1Y = (positionCarteJoueJ1Y - positionPileScoreJ1Y) / (double) totalIterations;
                }
                if (card2Faction == 2) {
                    this.deltaDefausse2X = (positionCarteJoueJ2X - positionPileScoreJ1X) / (double) totalIterations;
                    this.deltaDefausse2Y = (positionCarteJoueJ2Y - positionPileScoreJ1Y) / (double) totalIterations;
                }
            }
        }

        this.currentCarteJoue1X = positionCarteJoueJ1X;
        this.currentCarteJoue1Y = positionCarteJoueJ1Y;
        this.currentCarteJoue2X = positionCarteJoueJ2X;
        this.currentCarteJoue2Y = positionCarteJoueJ2Y;

    }

    public void distribuerDefausse() {
        currentCarteJoue1X -= deltaDefausse1X;
        currentCarteJoue1Y -= deltaDefausse1Y;
        currentCarteJoue2X -= deltaDefausse2X;
        currentCarteJoue2Y -= deltaDefausse2Y;
        miseAJour();
    }

    public void initializeAnimationTransition() {
        this.deltaTransparence = 3;
        this.currentTransparence = transparence;
    }

    public void transition() {
        currentTransparence += deltaTransparence;

        if (currentTransparence > 170) {
            currentCarteJoue1X = -999;
            currentCarteJoue1Y = -999;
            currentCarteJoue2X = -999;
            currentCarteJoue2Y = -999;
            currentCarteaganeeX = -999;
            currentCarteaganeeY = -999;
            currentCartePerdeX = -999;
            currentCartePerdeY = -999;
        }
        miseAJour();
    }

    public static BufferedImage imageToBufferedImage(Image image) {
        // Crée un BufferedImage avec le type ARGB (avec canal alpha) de la même taille que l'image
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Obtient le contexte graphique du BufferedImage
        Graphics2D g2d = bufferedImage.createGraphics();

        // Dessine l'image sur le BufferedImage
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return bufferedImage;
    }


    // Pour charger les images dans le hashMap
    private void acceptFile(File file) {
        String fileName = file.getName();
        if (fileName.endsWith(".png")) {
            String imageName = fileName.substring(0, fileName.lastIndexOf("."));
            Image image3 = null;
            try {
                String filenameModified = "/" + fileName;
                java.net.URL imageURL = getClass().getResource(filenameModified);
                if (imageURL != null) {
                    image3 = ImageIO.read(imageURL);
                } else {
                    throw new IOException("Image not found");
                }
                imageMap.put(imageName, imageToBufferedImage(image3));
            } catch (IOException e) {
                System.out.println("Error loading image: " + e.getMessage());
            }
        }
    }

    public void loadIcon() {
        icon_goblin = imageMap.get("icon_goblin");
        icon_knight = imageMap.get("icon_knight");
        icon_undead = imageMap.get("icon_undead");
        icon_dwarve = imageMap.get("icon_dwarve");
        icon_doppleganger = imageMap.get("icon_doppleganger");
    }
}

