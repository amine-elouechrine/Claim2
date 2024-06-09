package org.example.Vue;

import org.example.Controleur.ControleurMediateur;

import javax.swing.*;
import java.awt.*;

public class ComposantSauvegardeReussie extends JDialog {


    public ComposantSauvegardeReussie(Frame parent) {
        super(parent, true);
        setLayout(new BorderLayout());
        JLabel lbl = new JLabel("Sauvegarde Réussie");
    }
}
