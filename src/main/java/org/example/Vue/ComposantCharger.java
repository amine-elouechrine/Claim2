package org.example.Vue;

import org.example.Modele.Jeu;

import javax.swing.*;
import java.awt.*;

public class ComposantCharger extends Box {

    CollecteurEvenements control;

    ComposantCharger(int axis, CollecteurEvenements c) {
        super(axis);
        control = c;

        // Charger et sauver
        Box box = Box.createHorizontalBox();

        // Espace pour ecrire un nom de fichier de sauvegarde
        add(new JLabel("Nom fichier :"));
        JTextField fichier = new JTextField();
        fichier.setMaximumSize(new Dimension(
                fichier.getMaximumSize().width, fichier.getMinimumSize().height));
        add(fichier);

        box.add(createGlue());

        // Ajout du bouton charger
        RoundedButton charger = new RoundedButton("Charger");
        charger.addActionListener(new AdaptateurCharger(control, fichier, new Jeu(true, "", "")));

        box.add(charger);
        box.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(box);
    }
}
