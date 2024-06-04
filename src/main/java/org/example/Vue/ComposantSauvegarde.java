package org.example.Vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class ComposantSauvegarde extends JFrame {

    JPanel panel;
    JFrame frame;

    public ComposantSauvegarde(CollecteurEvenements control) {
        // Nom de le fenêtre
        this.setTitle("Sauvegarder ?");

        frame = this;

        // Change l'icone de la fenetre principale
        try {
            this.setIconImage(ImageIO.read(getClass().getResource("/Claim.png")));
        } catch (IOException exc) {
            System.out.println("Erreur de chargement de l'icone");
        }

        setSize(200, 150); // Augmentez la hauteur pour inclure les nouveaux champs de texte
        setLocationRelativeTo(null);

        // Composant pour sauver ou charger une partie
        ComposantSauverCharger sc = (new ComposantSauverCharger(BoxLayout.Y_AXIS, control));
        add(sc);
        setVisible(false);
    }
}
