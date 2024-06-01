package org.example.Vue;

import javax.swing.*;
import java.awt.*;

public class ComposantNouvellePartie extends Box {
    public ComposantNouvellePartie(int axis, CollecteurEvenements control, JFrame fenetre) {
        super(axis);

        Box box = Box.createHorizontalBox();

        JButton NouvellePartie = new JButton("Nouvelle Partie customisable");
        NouvellePartie.addActionListener(new AdaptateurNouvellePartieCustom(control, fenetre));
        box.add(NouvellePartie);
        box.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(box);
    }
}
