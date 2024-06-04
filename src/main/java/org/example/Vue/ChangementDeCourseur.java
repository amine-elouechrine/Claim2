package org.example.Vue;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class ChangementDeCourseur implements MouseMotionListener {
    CollecteurEvenements control;
    NiveauGraphique niv;
    public ChangementDeCourseur(CollecteurEvenements c,NiveauGraphique n) {
        control = c;
        niv = n;
    }
    @Override
    public void mouseDragged(MouseEvent e) {


    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (niv.isMouseOverFollowerDeck(e.getX(), e.getY())) {
            niv.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }else{
            niv.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

        }
        if(niv.isCoordinateInPlayer1Hand(e.getX(), e.getY())){
            niv.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }
}
