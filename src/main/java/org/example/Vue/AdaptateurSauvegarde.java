package org.example.Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurSauvegarde implements ActionListener {
    CollecteurEvenements control;
    ComposantSauvegarde sc;

    AdaptateurSauvegarde(CollecteurEvenements c, ComposantSauvegarde sauvegarde) {
        control = c;
        sc = sauvegarde;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        sc.setVisible(true);
        System.out.println("Clique sur le bouton Charger une partie");
    }
}
