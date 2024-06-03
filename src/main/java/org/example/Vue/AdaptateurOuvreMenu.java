package org.example.Vue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurOuvreMenu implements ActionListener {

    RoundedButton bouton;
    JFrame fenetre;
    NiveauGraphique niv;
    boolean menuVisible;

    AdaptateurOuvreMenu(RoundedButton b, JFrame f, NiveauGraphique n) {
        bouton = b;
        fenetre = f;
        niv = n;
        menuVisible = false;
        f.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Ouverture/Fermeture du menu");
        menuVisible = !menuVisible;
        if (menuVisible) {
            fenetre.setVisible(true);
            bouton.setText("Fermer Menu");
        } else {
            fenetre.setVisible(false);
            bouton.setText("Ouvrir Menu");
            niv.requestFocus();
        }
    }
}