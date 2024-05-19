package org.example.Vue;

import org.example.Controleur.ControleurMediateur;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class AdaptateurClavier extends KeyAdapter implements KeyListener {

    CollecteurEvenements control;

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
            //control.sauvegarder(fichier);
        }else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_C) {
            //control.restaure(fichier);
        }else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N) {
            control.nouvellePartie();
        }else{
            //pas d'action pour les autres clés
        }
    }
}