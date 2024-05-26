package org.example.Vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AdaptateurSouris extends MouseAdapter implements MouseListener {

    int i, j;
    int largeurR, hauteurR;
    double valeur_carte = 0f;
    int carte = -1;
    NiveauGraphique niv;
    CollecteurEvenements control;

    AdaptateurSouris(NiveauGraphique n, CollecteurEvenements c) {
        niv = n;
        control = c;

    }

    @Override
    public void mousePressed(MouseEvent e) {

        // Clic sur le niveau graphique
        i = e.getX();
        j = e.getY();
        valeur_carte = 0f;
        carte = -1;
        hauteurR = niv.hauteurCarte();
        largeurR = niv.largeurCarte();
        System.out.println("Clic sur la carte " + carte + "\n");
        // Si le joueurCourant est le joueur 1
        if (control.isJoueurCourantJoueur1()) {
            if (i > niv.posXMainJ1() && i < (niv.getLargeurMainJ1() + niv.posXMainJ1()) && j > niv.posYMainJ1() && j < (niv.posYMainJ1() + niv.getHauteurMain())) {
                valeur_carte = (double) (i - niv.posXMainJ1()) / largeurR; // Il faut diviser par la taille des cartes
                carte = (int) Math.floor(valeur_carte);
                control.clicSouris(carte);
            }
        }

        // Si le joueurCourant est le joueur 2
        else {
            if (i > niv.posXMainJ2() && i < (niv.getLargeurMainJ2() + niv.posXMainJ2()) && j > niv.posYMainJ2() && j < (niv.posYMainJ2() + niv.getHauteurMain())) {
                valeur_carte = (double) (i - niv.posXMainJ2()) / largeurR; // Il faut diviser par la taille des cartes
                carte = (int) Math.floor(valeur_carte);
                control.clicSourisJ2(carte);
            }
        }
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
