package org.example.Vue;

import org.example.Modele.Card;
import org.example.Modele.Cards;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

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
            //si il a clique sur la pile de Second Hand
            if (niv.isClickOnFollowerDeck(i, j)) {
                //ouvrire une fenetre de dialogue pour afficher les carte dans le follower deck
                List<Card> cards = control.getFollowerDeckJ1();
                if(cards.isEmpty()){
                    JOptionPane.showMessageDialog(null, "Le deck est vide", "Contenu de la liste", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                StringBuilder message = new StringBuilder("Liste des cartes :\n");
                for (Card card : cards) {
                    message.append(" ").append(card.getValeur()+" de "+card.getFaction()).append("\n");
                }

                JOptionPane.showMessageDialog(null, message.toString(), "Contenu de la liste", JOptionPane.INFORMATION_MESSAGE);
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
    public void mouseMoved(MouseEvent e) {



    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
