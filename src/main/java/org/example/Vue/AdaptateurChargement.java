package org.example.Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurChargement implements ActionListener {
    CollecteurEvenements control;
    ComposantChargement sc;

    AdaptateurChargement(CollecteurEvenements c, ComposantChargement chargement) {
        control = c;
        sc = chargement;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        sc.setVisible(true);
    }
}
