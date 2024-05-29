package org.example.Vue;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.*;

public class AdaptateurHelpButton implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
        // Chemin de l'image à afficher
        String imagePath = "src/main/resources/Claim.png"; // Remplacez par le chemin réel de votre image

        // Créer une icône à partir de l'image
        ImageIcon icon = new ImageIcon(imagePath);

        // Créer un JDialog
        JDialog dialog = new JDialog();
        dialog.setTitle("Aide");

        // Ajouter l'image à un JLabel
        JLabel label = new JLabel(icon);

        // Créer un JPanel pour contenir le JLabel
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);

        // Ajouter le panneau au JDialog
        dialog.getContentPane().add(panel);

        // Permettre le redimensionnement du JDialog
        dialog.setResizable(true);
        dialog.setSize(400, 300); // Taille initiale
        dialog.setLocationRelativeTo(null); // Centrer la boîte de dialogue sur l'écran

        // Ajouter l'écouteur de redimensionnement
        addResizeListener(dialog, label, icon);

        // Rendre le JDialog visible
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

