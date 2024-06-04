package org.example.Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class AdaptateurHelpButton implements ActionListener {
    CollecteurEvenements c;

    public AdaptateurHelpButton(CollecteurEvenements collecteurEvenements) {
        this.c = collecteurEvenements;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String help = c.help();

/*import javax.swing.*;

public class AdaptateurHelpButton implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        // Chemin de l'image à afficher
        String imagePath = "src/main/resources/Claim.png"; // Remplacez par le chemin réel de votre image

        // Créer une icône à partir de l'image
        ImageIcon icon = new ImageIcon(imagePath);*/


        // Créer un JDialog
        JDialog dialog = new JDialog();
        dialog.setTitle("Aide");

        // Créer un JTextPane pour un meilleur rendu du texte
        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText("<html><body style='font-family: Arial, sans-serif; font-size: 14px; line-height: 1.5; padding: 10px;'>"
                + help.replace("\n", "<br>")
                + "</body></html>");
        textPane.setEditable(false);
        textPane.setBackground(new Color(240, 240, 240));  // Gris clair pour le fond

        // Ajouter le JTextPane à un JScrollPane pour permettre le défilement
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());  // Enlever la bordure du JScrollPane

        // Créer un panel principal avec une marge
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Créer un bouton "Fermer"
        RoundedButton closeButton = new RoundedButton("Fermer");
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(event -> dialog.dispose());

        // Panel pour le bouton avec alignement à droite
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Ajouter le panel principal au JDialog
        dialog.getContentPane().add(mainPanel);

        // Définir la taille et le comportement du JDialog
        dialog.setSize(350, 250);
        dialog.setLocationRelativeTo(null);  // Centrer la fenêtre
        dialog.setModal(true);  // Rendre la fenêtre modale
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Afficher le JDialog
        dialog.setVisible(true);
    }

    private void addResizeListener(JDialog dialog, JLabel label, ImageIcon icon) {
        dialog.addComponentListener(createComponentAdapter(dialog, label, icon));
    }

    private ComponentAdapter createComponentAdapter(JDialog dialog, JLabel label, ImageIcon icon) {
        return new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeImage(dialog, label, icon);
            }
        };
    }

    private void resizeImage(JDialog dialog, JLabel label, ImageIcon icon) {
        int width = dialog.getWidth();
        int height = dialog.getHeight();
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(scaledImage));
    }

}