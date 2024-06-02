package org.example.Vue;

import javax.swing.*;
import java.awt.*;

public class ComposantAnnulerRefaire extends Box {
    ComposantAnnulerRefaire(int axis, CollecteurEvenements c) {
        super(axis);

        Box box = Box.createHorizontalBox();

        RoundedButton annuler = new RoundedButton("Annuler");
        annuler.addActionListener(new AdaptateurAnnuler(c));

        RoundedButton refaire = new RoundedButton("Refaire");
        refaire.addActionListener(new AdaptateurRefaire(c));

        box.add(annuler);
        box.add(refaire);
        add(box);
    }
}
