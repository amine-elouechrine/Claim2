package org.example.Vue;

import javax.swing.*;
import java.awt.*;

public class ComposantNouvellePartie extends Box {
    public ComposantNouvellePartie(int axis, CollecteurEvenements control) {
        super(axis);

        Box box = Box.createHorizontalBox();

        JButton NouvellePartie = new JButton("Nouvelle Partie customisable");
        NouvellePartie.addActionListener(new AdaptateurNouvellePartie(control));
        box.add(NouvellePartie);
        add(box);
    }
}
