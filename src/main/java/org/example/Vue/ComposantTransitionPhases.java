package org.example.Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ComposantTransitionPhases extends JPanel {

    private JLabel textLabel;
    private Timer timer;
    private int opacity = 0;
    private int fadeDuration = 2000;

    public ComposantTransitionPhases() {
        setLayout(new BorderLayout());
        add(new JLabel("Centered Text", SwingConstants.CENTER), BorderLayout.CENTER);
        setBackground(Color.DARK_GRAY);

        textLabel = new JLabel();
        textLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(textLabel, BorderLayout.CENTER);

        timer = new Timer(fadeDuration / 10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (opacity < 255) {
                    opacity += 25;
                    if (opacity > 255) {
                        opacity = 255;
                    }
                    textLabel.setForeground(new Color(0, 0, 0, opacity));
                } else {
                    ((Timer)e.getSource()).stop();
                    startFadeOut();
                }
            }
        });
        startFadeIn();

        setVisible(true);
    }

    private void startFadeIn() {
        opacity = 0;
        textLabel.setText("PHASE 1 END ");
        textLabel.setForeground(new Color(0, 0, 0, opacity));
        timer.start();
    }

    private void startFadeOut() {
        opacity = 255;
        textLabel.setText("PHASE 2 BEGIN");
        textLabel.setForeground(new Color(0, 0, 0, opacity));
        timer.setInitialDelay(fadeDuration * 2);
        timer.start();
    }
}
