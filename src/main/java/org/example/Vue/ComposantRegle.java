package org.example.Vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ComposantRegle extends JScrollPane {

    private double zoomFactor = 0.75; // Initialiser avec un facteur de zoom plus petit
    private JLabel imageLabel;
    private ImageIcon imageIcon;
    private BufferedImage originalImage;

    public ComposantRegle() {
        // Chemin de l'image dans les ressources
        // String imagePath = Objects.requireNonNull(getClass().getResource("/rulebook.png")).getPath();

        // Chargement de l'image
        // imageIcon = new ImageIcon(imagePath);
        imageIcon = loadImageIcon("/rulebook.png");
        imageLabel = new JLabel(imageIcon);

        // Convertir ImageIcon en BufferedImage pour redimensionnement
        originalImage = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = originalImage.createGraphics();
        imageIcon.paintIcon(null, g, 0, 0);
        g.dispose();

        // Appliquer le facteur de zoom initial
        zoomImage(zoomFactor);

        // Ajout de l'image à un JScrollPane
        JScrollPane scrollPane = new JScrollPane(imageLabel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        // Définir la taille préférée pour le JScrollPane pour s'assurer qu'il est plus petit que l'image
        scrollPane.setPreferredSize(new java.awt.Dimension(700, 500));

        // Créer des boutons de zoom et dézoom
        JButton zoomInButton = new JButton("Zoom +");
        JButton zoomOutButton = new JButton("Zoom -");

        // Ajouter des action listeners aux boutons
        zoomInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zoomImage(1.1);
            }
        });

        zoomOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zoomImage(0.9);
            }
        });

        // Ajouter les boutons à un panneau
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(zoomInButton);
        buttonPanel.add(zoomOutButton);

        // Créer un panneau principal pour contenir les boutons et le JScrollPane
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Affichage du panneau principal dans une boîte de dialogue
        JOptionPane.showMessageDialog(null, mainPanel, "Règles du jeu", JOptionPane.INFORMATION_MESSAGE);
    }

    private void zoomImage(double factor) {
        zoomFactor *= factor;
        int width = (int) (originalImage.getWidth() * zoomFactor);
        int height = (int) (originalImage.getHeight() * zoomFactor);
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        imageIcon.setImage(scaledImage);
        imageLabel.revalidate();
        imageLabel.repaint();
    }

    public static BufferedImage imageToBufferedImage(Image image) {
        // Crée un BufferedImage avec le type ARGB (avec canal alpha) de la même taille que l'image
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Obtient le contexte graphique du BufferedImage
        Graphics2D g2d = bufferedImage.createGraphics();

        // Dessine l'image sur le BufferedImage
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return bufferedImage;
    }

    private BufferedImage loadImage(String path) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) {
                throw new IOException("Image not found: " + path);
            }
            return ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private ImageIcon loadImageIcon(String path) {
        try (InputStream is = getClass().getResourceAsStream(path)) {
            if (is == null) {
                throw new IOException("Image not found: " + path);
            }
            BufferedImage image = ImageIO.read(is);
            return new ImageIcon(image);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
