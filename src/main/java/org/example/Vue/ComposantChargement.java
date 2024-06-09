package org.example.Vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class ComposantChargement extends JFrame {

    JPanel panel;
    JFrame frame;

    public ComposantChargement(CollecteurEvenements control) {
        // Nom de le fenêtre
        this.setTitle("Charger une partie");

        frame = this;

        // Change l'icone de la fenetre principale
        try {
            this.setIconImage(ImageIO.read(new File("/Claim.png")));
        } catch (IOException exc) {
            System.out.println("Erreur de chargement de l'icone de la fenetre de sauvegarde");
        }

        setSize(200, 150); // Augmentez la hauteur pour inclure les nouveaux champs de texte
        setLocationRelativeTo(null);

        // Composant pour sauver ou charger une partie
        ComposantCharger sc = (new ComposantCharger(BoxLayout.Y_AXIS, control));
        add(sc);
        setVisible(false);
    }
}
