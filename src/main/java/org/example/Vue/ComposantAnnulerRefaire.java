package org.example.Vue;

import javax.swing.*;
import java.awt.*;

public class ComposantAnnulerRefaire extends Box {
    ComposantAnnulerRefaire(int axis, CollecteurEvenements c) {
        super(axis);

        Box box = Box.createHorizontalBox();

        JButton annuler = new JButton("Annuler");
        annuler.addActionListener(new AdaptateurAnnuler(c));

        JButton refaire = new JButton("Refaire");
        refaire.addActionListener(new AdaptateurRefaire(c));

        box.add(annuler);
        box.add(refaire);
        add(box);
    }
}
