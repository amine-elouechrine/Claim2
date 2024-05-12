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
        hauteurR = niv.rectHeight;
        largeurR = niv.rectWidth;
        int carte = 0;

        if (i > niv.posXMain() && i < (niv.getLargeurMain() + niv.posXMain()) && j > niv.posYMain() && j < (niv.posYMain() + niv.getHauteurMain())) {
            carte = ((i - niv.posXMain()) / niv.getTotalCards()) / 4;
        }
        System.out.println("carte : " + carte);
        System.out.println("posX : " + i + " posY : " + j + "\n posXMain : " + niv.posXMain() + " posYMain : " + niv.posYMain());
        System.out.println("Largeur Main : " + niv.getLargeurMain() + " Hauteur Main : " + (niv.posYMain() + niv.getHauteurMain()));


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
