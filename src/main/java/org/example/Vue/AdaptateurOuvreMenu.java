package org.example.Vue;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurOuvreMenu implements ActionListener {

    JToggleButton toggle;
    JFrame fenetre;

    AdaptateurOuvreMenu(JToggleButton t, JFrame f) {
        toggle = t;
        fenetre = f;
        f.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fenetre.setVisible(toggle.isSelected());
    }
}
