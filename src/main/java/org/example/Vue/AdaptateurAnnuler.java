package org.example.Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AdaptateurAnnuler implements ActionListener {
    CollecteurEvenements control;

    AdaptateurAnnuler(CollecteurEvenements c) {
        control = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        control.annuler();
        System.out.println("Clique sur le bouton annuler");
    }
}
