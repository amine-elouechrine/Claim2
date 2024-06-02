package org.example.Vue;

import org.example.Modele.Jeu;
import org.example.Patternes.Observateur;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ComposantRecommencer extends Box implements Observateur {
    Jeu jeu;
    private JLabel gagnant;
    private JFrame parentFrame;

    CollecteurEvenements control;

    ComposantRecommencer(int axis, CollecteurEvenements c, Jeu jeu, JFrame parentFrame) {
        super(axis);
        this.jeu = jeu;
        this.jeu.ajouteObservateur(this);
        control = c;

        gagnant = new JLabel("");
        RoundedButton recommencer = new RoundedButton("Recommencer");
        recommencer.addActionListener(new AdaptateurNouvellePartie(c));

        JButton quitter = new JButton("Quitter");
        quitter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                parentFrame.setVisible(false);
                // System.exit(0);
            }
        });

        // Change l'icone de la fenetre principale
        try {
            parentFrame.setIconImage(ImageIO.read(new File("src/main/resources/assets/gaufre.png")));
        } catch (
                IOException exc) {
            System.out.println("Erreur de chargement de l'icone");
        }

        add(gagnant);
        add(recommencer);
        add(quitter);
    }

    @Override
    public void miseAJour() {
        if (control.estFinPartie()) {
            JDialog dialog = new JDialog(parentFrame, "Jeu Terminé", true);
            dialog.setLayout(new BorderLayout());
            dialog.add(this, BorderLayout.CENTER);

            gagnant.setText("Le gagnant est : " + jeu.getJoueurCourant().getName());
            dialog.pack();
            dialog.setLocationRelativeTo(parentFrame);
            dialog.setVisible(true);
        }
    }

}
