package org.example.Vue;

import java.util.SplittableRandom;

public class DrawCheck {
    private boolean drawScorePileToggle;
    private boolean drawHandToggle;

    private boolean drawHandJ1Toggle;

    // Getter method
    public boolean isDrawHandToggle() {
        return drawHandToggle;
    }

    public boolean isDrawHandJ1Toggle() {
        return drawHandJ1Toggle;
    }

    public boolean isDrawScorePileToggle() {
        return drawScorePileToggle;
    }

    // Setter method
    public void setDrawHandToggle(boolean drawHandToggle) {
        this.drawHandToggle = drawHandToggle;
    }
    public void setDrawHandJ1Toggle(boolean drawHandJ1Toggle) {
        this.drawHandJ1Toggle = drawHandJ1Toggle;
    }
    public void setDrawScorePileToggle(boolean drawScorePileToggle) {
        this.drawScorePileToggle = drawScorePileToggle;
    }
}
