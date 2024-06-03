package org.example.Vue;

import javax.swing.*;
import java.awt.*;

public class ComposantSauverCharger extends Box {

    CollecteurEvenements control;

    ComposantSauverCharger(int axis, CollecteurEvenements c) {
        super(axis);
        control = c;

        // Charger et sauver
        Box box = Box.createHorizontalBox();

        // Espace pour ecrire un nom de fichier de sauvegarde
        add(new JLabel("Nom fichier :"));
        RoundedTextField fichier = new RoundedTextField(20);
        fichier.setMaximumSize(new Dimension(
                fichier.getMaximumSize().width, fichier.getMinimumSize().height));
        add(fichier);

        box.add(createGlue());

        // Ajout du bouton sauver
        RoundedButton sauver = new RoundedButton("Sauver");
        sauver.addActionListener(new AdaptateurSauver(control, fichier));

        // Ajout du bouton charger
        RoundedButton charger = new RoundedButton("Charger");
        charger.addActionListener(new AdaptateurCharger(control, fichier));

        box.add(sauver);
        box.add(Box.createHorizontalStrut(20));
        box.add(charger);
        box.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(box);
    }
}
