package org.example.Vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AdaptateurSouris extends MouseAdapter implements MouseListener {


    NiveauGraphique niv;
    CollecteurEvenements control;

    AdaptateurSouris(NiveauGraphique n, CollecteurEvenements c) {
        niv = n;
        control = c;
    }


    @Override
    public void mousePressed(MouseEvent e) {
        // Clic sur le niveau graphique
        int i, j;
        int largeurR, hauteurR;
        i = e.getX();
        j = e.getY();
        hauteurR = niv.hauteurCarte();
        largeurR = niv.largeurCarte();
        double valeur_carte = 0f;
        int carte = -1;

        if (i > niv.posXMain() && i < (niv.getLargeurMainJ1() + niv.posXMain()) && j > niv.posYMain() && j < (niv.posYMain() + niv.getHauteurMain())) {
            valeur_carte = (double) (i - niv.posXMain()) / largeurR; // Il faut diviser par la taille des cartes
            carte = (int) Math.floor(valeur_carte);
        }

        System.out.println("double : " + valeur_carte);
        System.out.println("carte : " + carte);
        System.out.println("posX : " + i + " posY : " + j + "\n posXMain : " + niv.posXMain() + " posYMain : " + niv.posYMain());
        System.out.println("Largeur Main : " + niv.getLargeurMainJ1() + " Hauteur Main : " + (niv.posYMain() + niv.getHauteurMain()));
        System.out.println("largeur carte : " + largeurR);

        control.clicSouris(carte);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
