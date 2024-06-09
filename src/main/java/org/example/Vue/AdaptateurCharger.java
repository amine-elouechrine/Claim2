package org.example.Vue;

import org.example.Modele.Jeu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AdaptateurCharger implements ActionListener {
    CollecteurEvenements control;
    JTextField fichier;
    Jeu jeu;

    AdaptateurCharger(CollecteurEvenements c, JTextField f, Jeu j) {
        control = c;
        fichier = f;
        jeu = j;
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
            InterfaceGraphique.demarrer(jeu, control);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        System.out.println("Clique sur le bouton 1er charger");
    }
}
