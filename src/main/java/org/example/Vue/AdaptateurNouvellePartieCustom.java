package org.example.Vue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurNouvellePartieCustom implements ActionListener {
    CollecteurEvenements control;
    JFrame fenetreMenu;
    JFrame fenetreCustom;

    AdaptateurNouvellePartieCustom(CollecteurEvenements c, JFrame f1) {
        control = c;
        fenetreMenu = f1;
        // fenetreCustom = f2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fenetreMenu.dispose();
        InterfaceGraphique.fermer();
        InterfacePartieCustom.demarrer();
    }
}
