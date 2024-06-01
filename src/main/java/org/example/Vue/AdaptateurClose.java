package org.example.Vue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurClose implements ActionListener {
    private JFrame fenetre;

    public AdaptateurClose(JFrame fenetre) {
        this.fenetre = fenetre;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fenetre.dispose(); // Close the JFrame
    }
}
