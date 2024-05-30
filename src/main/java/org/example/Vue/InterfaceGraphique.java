package org.example.Vue;

import org.example.Modele.Jeu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class InterfaceGraphique implements Runnable, InterfaceUtilisateur {

    Jeu j;
    NiveauGraphique niv;
    JFrame fenetre;
    CollecteurEvenements control;
    AdaptateurClavier adaptateurClavier;
    AdaptateurTransitionPhases adaptateurTransitionPhases;

    InterfaceGraphique(Jeu jeu, CollecteurEvenements c) {
        j = jeu;
        control = c;
        adaptateurClavier = new AdaptateurClavier(control, new ComposantSauvegarde(control));
        adaptateurTransitionPhases = new AdaptateurTransitionPhases(control);
    }

    public static void demarrer(Jeu j, CollecteurEvenements c) {
        InterfaceGraphique vue = new InterfaceGraphique(j, c);
        c.ajouteInterfaceUtilisateur(vue);
        SwingUtilities.invokeLater(vue);
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

        // Recommencer partie
        ComposantRejouer rec = new ComposantRejouer(control);

        // Dessin du NiveauGraphique
        niv = new NiveauGraphique(j, control, rec);
        niv.setFocusable(true);
        niv.requestFocusInWindow();
        niv.addMouseListener(new AdaptateurSouris(niv, control));
        niv.addKeyListener(adaptateurClavier);

        // Fenetre InterfaceGraphique
        fenetre.add(niv);
        JPanel menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(Color.DARK_GRAY);
        JToggleButton menu = new JToggleButton("Menu");
        menuPanel.add(menu, BorderLayout.NORTH);
        ComposantMenuPartie menuPartie = new ComposantMenuPartie(BoxLayout.PAGE_AXIS, control, j);
        menu.addActionListener(new AdaptateurOuvreMenu(menu, menuPartie, niv));
        fenetre.add(menuPanel, BorderLayout.EAST);
        fenetre.add(bh, BorderLayout.NORTH);
        ComposantTransitionPhases transitionPhases = new ComposantTransitionPhases();
        //fenetre.add(transitionPhases);


        Timer chrono = new Timer(1, new AdaptateurTemps(control));
        chrono.start();

        fenetre.pack();
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(960, 720);
        fenetre.getContentPane().setBackground(Color.DARK_GRAY);
        fenetre.setVisible(true);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Masquer menu recommencer
        rec.setVisible(false);

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

    public void distribuer() {
        niv.distribuer();
    }

    public void distribuerGagne() {
        niv.distribuerGagne();
    }

    public void distribuerPerde() {
        niv.distribuerPerde();
    }


    @Override
    public void distribuerDefausse() {
        niv.distribuerDefausse();
    }

    @Override
    public void initializeAnimationDistribuer(int totalIterations) {
        niv.initializeAnimationDistribuer(totalIterations);
    }

    @Override
    public void initializeAnimationGagne(int totalIterations, int joueur) {
        niv.initializeAnimationGagne(totalIterations, joueur);
    }
    @Override
    public void initializeAnimationPerde(int totalIterations, int joueur) {
        niv.initializeAnimationPerde(totalIterations, joueur);
    }

    @Override
    public void initializeAnimationDefausse(int totalIterations, int card1Faction, int card2Faction) {
        niv.initializeAnimationDefausse(totalIterations, card1Faction, card2Faction);
    }
}
