package org.example.Vue;

import org.example.Modele.Cards;

import java.awt.*;
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

        if (control.getPause())
            return;
        if(!control.getPause()) {
            // Clic sur le niveau graphique
            i = e.getX();
            j = e.getY();
            valeur_carte = 0f;
            carte = -1;
            hauteurR = niv.hauteurCarte();
            largeurR = niv.largeurCarte();
            System.out.println(niv.getPositionScorePile());
            if (niv.estDansPileDeScore(i, j)) {
                //System.out.println("Clic sur la pile de score");
                int x = niv.getLigneCliquee(j);

                if(x==1){//Gobelin
                    //Cards cardsPS=control.getCardsFromPileScoreJ1("Gobelin");
                }else if(x==2) {//Nain

                }else if(x==3){//Knight

                }else if(x==4){//Doopelganger

                }else{//Undead

                }
            }

            // Si le joueurCourant est le joueur 1
            if (control.isJoueurCourantJoueur1()) {
                if (i > niv.posXMainJ1() && i < (niv.getLargeurMainJ1() + niv.posXMainJ1())
                        && j > (niv.posYMainJ1() - 20) && j < (niv.posYMainJ1() - 20 + niv.getHauteurMain())) {
                    valeur_carte = (double) (i - niv.posXMainJ1()) / largeurR; // Il faut diviser par la taille des cartes
                    carte = (int) Math.floor(valeur_carte);
                    control.clicSouris(carte);
                }
            }

            // Si le joueurCourant est le joueur 2
            else {
                if (i > niv.posXMainJ2() && i < (niv.getLargeurMainJ2() + niv.posXMainJ2())
                        && j > (niv.posYMainJ2() + 20) && j < (niv.posYMainJ2() + 20 + niv.getHauteurMain())) {
                    valeur_carte = (double) (i - niv.posXMainJ2()) / largeurR; // Il faut diviser par la taille des cartes
                    carte = (int) Math.floor(valeur_carte);
                    control.clicSourisJ2(carte);
                }
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
