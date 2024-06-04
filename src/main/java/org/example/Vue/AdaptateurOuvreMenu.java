package org.example.Vue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurOuvreMenu implements ActionListener {

    JToggleButton toggle;
    JFrame fenetre;
    NiveauGraphique niv;


    AdaptateurOuvreMenu(JToggleButton t, JFrame f, NiveauGraphique n) {
        toggle = t;
        fenetre = f;
        niv = n;
        f.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (toggle.isSelected()) {
            fenetre.setVisible(true);
        } else {
            fenetre.setVisible(false);
            niv.requestFocus();
        }
    }
}
