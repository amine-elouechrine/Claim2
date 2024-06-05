package org.example.Vue;

import org.example.Modele.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

public class AdaptateurSouris extends MouseAdapter implements MouseMotionListener, MouseListener {

    int i, j;
    int largeurR, hauteurR;
    double valeur_carte = 0f;
    int carte = -1;
    NiveauGraphique niv;
    CollecteurEvenements control;

    DrawCheck drawCheck;

    AdaptateurSouris(NiveauGraphique n, CollecteurEvenements c, DrawCheck dc) {
        this.niv = n;
        this.control = c;
        this.drawCheck = dc;

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (niv.isMouseOverFollowerDeck(e.getX(), e.getY())) {
            niv.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            niv.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (control.getPause())
            return;
        if (!control.getPause()) {
            // Clic sur le niveau graphique
            i = e.getX();
            j = e.getY();
            valeur_carte = 0f;
            carte = -1;
            hauteurR = niv.hauteurCarte();
            largeurR = niv.largeurCarte();

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

        if (niv.isClickOnFollowerDeck(i, j)) {
            // ouvrire une fenetre de dialogue pour afficher les carte dans le follower deck
            List<Card> cards = control.getFollowerDeckJ1();
            if (cards.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Le deck est vide", "Contenu de la liste", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            StringBuilder message = new StringBuilder("Liste des cartes :\n");
            for (Card card : cards) {
                message.append(" ").append(card.getValeur() + " de " + card.getFaction()).append("\n");
            }

            JOptionPane.showMessageDialog(null, message.toString(), "Contenu de la liste", JOptionPane.INFORMATION_MESSAGE);
        }

        if (!drawCheck.isDrawScorePileToggle()) {
            return;
        }
        if (niv.estDansPileDeScore(e.getX(), e.getY())) {
            int ligneCliquee = niv.getLigneCliquee(e.getY());

            // Obtenir le nom de la faction pour la ligne cliquée
            String factionCliquee = niv.assignFactionToNumber(ligneCliquee);
            java.util.List<Card> PDSJ1 = control.getCardsFromPileScoreJ1(factionCliquee);
            List<Card> PDSJ2 = control.getCardsFromPileScoreJ2(factionCliquee);
            // Afficher la fenêtre modale avec les cartes de la faction pour les deux joueurs
            Frame owner = (Frame) SwingUtilities.getWindowAncestor(niv);

            // Obtenir le propriétaire de la fenêtre
            if (!PDSJ1.isEmpty() || !PDSJ2.isEmpty()) {
                FicheFactionDialog dialog = new FicheFactionDialog(owner, factionCliquee, PDSJ1, PDSJ2, control);
                dialog.setVisible(true);
            } else {
                JPopupMenu pm = new JPopupMenu("Il n'y a pas de carte dans la pile de score");
                pm.setVisible(true);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
