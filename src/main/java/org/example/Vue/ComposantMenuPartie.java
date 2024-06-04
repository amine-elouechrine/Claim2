package org.example.Vue;

import org.example.Modele.Jeu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

public class ComposantMenuPartie extends JFrame {

    CollecteurEvenements c;
    Jeu jeu;

    ComposantMenuPartie(int axis, CollecteurEvenements control, Jeu jeu, DrawCheck drawCheck) {
        this.jeu = jeu;
        c = control;

        this.setUndecorated(true);
        // Setting up the frame
        this.setTitle("Menu de Claim");
        // Change l'icone de la fenetre principale
        try {
            this.setIconImage(ImageIO.read(getClass().getResource("/Claim.png")));
        } catch (IOException exc) {
            System.out.println("Erreur de chargement de l'icone");
        }

        this.setSize(600, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Boutons et FieldTexts Sauver et Charger une partie
        Box Sauvegarde = Box.createHorizontalBox();
        RoundedButton loadGameButton = new RoundedButton("Sauvergarder ou Charger Partie");
        loadGameButton.addActionListener(new AdaptateurSauvegarde(control, new ComposantSauvegarde(control)));
        Sauvegarde.add(loadGameButton);

        Box regles = Box.createHorizontalBox();
        RoundedButton r = new RoundedButton("Règle de jeu");
        r.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ComposantRegle();
            }
        });
        regles.add(r);

        // Check box pour le dessin de la main adverse
        JLabel drawHandLabel = new JLabel("Afficher la main de l'adversaire :");
        JCheckBox drawHandCheckBox = new JCheckBox();

        drawHandCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Update the boolean state based on the checkbox status
                drawCheck.setDrawHandToggle(e.getStateChange() == ItemEvent.SELECTED);
                // Optionally, print the state to the console for debugging
                System.out.println("Checkbox Hand Draw is checked: " + drawCheck.isDrawHandToggle());
                jeu.metAJour();
            }
        });

        // Check box pour caché la main du joueur 1
        JLabel drawHandJ1Label = new JLabel("Cacher la main du Joueur 1 :");
        JCheckBox drawHandJ1CheckBox = new JCheckBox();
        drawHandJ1CheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Update the boolean state based on the checkbox status
                drawCheck.setDrawHandJ1Toggle(e.getStateChange() == ItemEvent.SELECTED);
                // Optionally, print the state to the console for debugging
                System.out.println("Checkbox Hand J1 is checked: " + drawCheck.isDrawHandJ1Toggle());
                jeu.metAJour();
            }
        });

        // Toggle de l'affichage de la pile de score
        JLabel drawScorePileLabel = new JLabel("Afficher les cartes et infos de la pile de score :");
        JCheckBox drawScorePileCheckBox = new JCheckBox();

        drawScorePileCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // Update the boolean state based on the checkbox status
                drawCheck.setDrawScorePileToggle(e.getStateChange() == ItemEvent.SELECTED);
                // Optionally, print the state to the console for debugging
                System.out.println("Checkbox Score Pile Draw is checked: " + drawCheck.isDrawScorePileToggle());
                jeu.metAJour();
            }
        });

        // Panel en plus pour les checkbox
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Boutons Annuler et Refaire coup
        panel.add(new ComposantAnnulerRefaire(axis, control));

        // Bouton Nouvelle Partie
        panel.add(new ComposantNouvellePartie(axis, control, this));

        // Bouton Sauvegarder/Charger Partie
        panel.add(Sauvegarde);

        // Bouton Règle
        panel.add(regles);

        // Ajout des checkBoxes
        panel1.add(drawHandLabel);
        panel1.add(drawHandCheckBox);
        panel2.add(drawScorePileLabel);
        panel2.add(drawScorePileCheckBox);
        panel3.add(drawHandJ1Label);
        panel3.add(drawHandJ1CheckBox);

        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);

        // this.pack();
        this.add(panel);
    }
}
