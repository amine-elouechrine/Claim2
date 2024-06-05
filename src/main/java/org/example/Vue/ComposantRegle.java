package org.example.Vue;

import javax.swing.*;
import java.util.Objects;

public class ComposantRegle extends JScrollPane {

    public ComposantRegle() {
        // Chemin de l'image dans les ressources
        String imagePath = Objects.requireNonNull(getClass().getResource("/rulebook.png")).getPath();

        // Chargement de l'image
        ImageIcon imageIcon = new ImageIcon(imagePath);
        JLabel imageLabel = new JLabel(imageIcon);

        // Ajout de l'image à un JScrollPane
        JScrollPane scrollPane = new JScrollPane(imageLabel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // Définir la taille préférée pour le JScrollPane pour s'assurer qu'il est plus petit que l'image
        scrollPane.setPreferredSize(new java.awt.Dimension(700, 500));

        // Affichage du JScrollPane dans une boîte de dialogue
        JOptionPane.showMessageDialog(null, scrollPane, "Règles du jeu", JOptionPane.INFORMATION_MESSAGE);
    }
}
