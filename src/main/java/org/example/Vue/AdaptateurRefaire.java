package org.example.Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurRefaire implements ActionListener {
    CollecteurEvenements control;

    AdaptateurRefaire(CollecteurEvenements c) {
        control = c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // control.refaire();
        System.out.println("Clique sur le bouton refaire");
    }
}
