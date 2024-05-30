package org.example.Vue;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class AdaptateurClavier extends KeyAdapter implements KeyListener {

    CollecteurEvenements control;
    ComposantSauvegarde sc;

    AdaptateurClavier(CollecteurEvenements control, ComposantSauvegarde sauvegarde) {
        this.control = control;
        sc = sauvegarde;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_A) {
            try {
                control.annuler();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_R) {
            control.refaire();
            System.out.println("Clique sur le bouton refaire");
        } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S) {
            //control.sauvegarder(fichier);
            sc.setVisible(true);
        } else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N) {
            control.nouvellePartie();
        } else {
            //pas d'action pour les autres clés
        }
    }
}