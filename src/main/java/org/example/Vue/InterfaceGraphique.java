package org.example.Vue;

import org.example.Modele.Jeu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
public class InterfaceGraphique implements Runnable {

    JFrame fenetre;

    public static void demarrer() {
        SwingUtilities.invokeLater(new InterfaceGraphique());
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
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(500, 500);
        fenetre.setVisible(true);
    }
}
