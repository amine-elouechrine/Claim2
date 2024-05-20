package org.example.Vue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AdaptateurCharger implements ActionListener {
    CollecteurEvenements control;
    JTextField fichier;

    AdaptateurCharger(CollecteurEvenements c, JTextField f) {
        control = c;
        fichier = f;
    }

    /*
     * Une taille a été entrée. On la convertie en entier et on envoie un évènement.
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        String filename = fichier.getText();
        if (filename.isEmpty())
            filename = "save";
        try {
            control.restaure(filename);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        System.out.println("Clique sur le bouton 1er charger");
    }
}
