package org.example.Vue;

import org.example.Modele.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class GestionClicPileScore implements MouseListener {

    private NiveauGraphique niveauGraphique;
    private  CollecteurEvenements control;

    public GestionClicPileScore(NiveauGraphique niveauGraphique, CollecteurEvenements control) {
        this.niveauGraphique = niveauGraphique;
        this.control = control;
    }

    public String getFactionForLine(int ligne) {
        // Obtenir le nom de la faction pour la ligne cliquée
        String factionCliquee = null;
        if (ligne == 1) {
            factionCliquee = "Goblins";
        } else if (ligne == 2) {
            factionCliquee = "Dwarves";
        } else if (ligne == 3) {
            factionCliquee = "Knight";
        }else if(ligne == 4){
            factionCliquee = "Undead";}
        else if(ligne == 5){
            factionCliquee = "Doppelganger";
        }
        return factionCliquee;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (niveauGraphique.estDansPileDeScore(e.getX(), e.getY())) {
            int ligneCliquee = niveauGraphique.getLigneCliquee(e.getY());

            // Obtenir le nom de la faction pour la ligne cliquée
            String factionCliquee = getFactionForLine(ligneCliquee);
            List<Card> PDSJ1 = control.getCardsFromPileScoreJ1(factionCliquee);
            List<Card> PDSJ2= control.getCardsFromPileScoreJ2(factionCliquee);
            // Afficher la fenêtre modale avec les cartes de la faction pour les deux joueurs
            Frame owner = (Frame) SwingUtilities.getWindowAncestor(niveauGraphique);

                // Obtenir le propriétaire de la fenêtre
                if(!PDSJ1.isEmpty() || !PDSJ2.isEmpty()){
                    FicheFactionDialog dialog = new FicheFactionDialog(owner, factionCliquee, PDSJ1, PDSJ2);
                    dialog.setVisible(true);

                }


        }



    }

    // D'autres méthodes de l'interface MouseListener
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
