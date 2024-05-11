package org.example.Vue;

import org.example.Modele.Jeu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class InterfaceGraphique implements Runnable {

    Jeu j;
    JFrame fenetre;
    CollecteurEvenements control;


    InterfaceGraphique(Jeu jeu, CollecteurEvenements c) {
        j = jeu;
        control = c;
    }

    public static void demarrer(Jeu jeu, CollecteurEvenements control) {
        SwingUtilities.invokeLater(new InterfaceGraphique(jeu, control));
    }

    @Override
    public void run() {
        // Nom de la fenêtre
        fenetre = new JFrame("Claim incroyable jeu de carte");

        // Change l'icone de la fenetre principale
        try {
            fenetre.setIconImage(ImageIO.read(new File("src/main/resources/Claim.png")));
        } catch (IOException exc) {
            System.out.println("Erreur de chargement de l'icone");
        }

        // BarreHaute
        ComposantBarreHaute bh = new ComposantBarreHaute(BoxLayout.X_AXIS, control, j);

        // Dessin du NiveauGraphique
        NiveauGraphique niv = new NiveauGraphique(j, control);
        niv.setFocusable(true);
        niv.requestFocusInWindow();


        // Fenetre InterfaceGraphique
        fenetre.add(niv);
        // fenetre.add(barreHaute, BorderLayout.PAGE_START);
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(Color.WHITE);
        JToggleButton menu = new JToggleButton("Menu");
        menuPanel.add(menu, BorderLayout.NORTH);
        ComposantMenuPartie menuPartie = new ComposantMenuPartie(BoxLayout.PAGE_AXIS, control, j);
        menu.addActionListener(new AdaptateurOuvreMenu(menu, menuPartie));
        fenetre.add(menuPanel, BorderLayout.EAST);
        fenetre.add(bh, BorderLayout.NORTH);

        fenetre.pack();
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(1280, 960);
        fenetre.getContentPane().setBackground(Color.WHITE);
        fenetre.setVisible(true);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*
        // Aligning the vertical panel to the right side
        JPanel verticalPanel = new JPanel();
        verticalPanel.setLayout(new BoxLayout(verticalPanel, BoxLayout.Y_AXIS));
        verticalPanel.add(new ComposantMenuPartie(BoxLayout.Y_AXIS, control, j));
        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        wrapperPanel.add(verticalPanel);
        fenetre.add(wrapperPanel, BorderLayout.CENTER);
        */
    }
}
