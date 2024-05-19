package org.example.Vue;

import org.example.Controleur.ControleurMediateur;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AdaptateurClavier extends KeyAdapter implements KeyListener {

    CollecteurEvenements control;
    ControleurMediateur controleurMediateur;

    AdaptateurClavier(CollecteurEvenements control) {
        this.control = control;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_A) {
            control.annuler();
            System.out.println("Clique sur le bouton annuler");
        } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_R) {
            control.refaire();
            System.out.println("Clique sur le bouton refaire");
        } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S) {
            //touche pour sauvegarder/charger
            //control.sauvegarder();
            //control.charger();
            System.out.println("Clique sur le bouton sauvegarder/charger");
        }else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N) {
            //touche pour nouvelle partie
            //control.nouvelle();
            System.out.println("Clique sur le bouton nouvelle partie");
        }else{
            //pas d'action pour les autres clés
        }
    }
}