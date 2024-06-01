package org.example.Vue;

public class DrawCheck {
    private boolean drawScorePileToggle;
    private boolean drawHandToggle;

    // Getter method
    public boolean isDrawHandToggle() {
        return drawHandToggle;
    }

    public boolean isDrawScorePileToggle() {
        return drawScorePileToggle;
    }

    // Setter method
    public void setDrawHandToggle(boolean drawHandToggle) {
        this.drawHandToggle = drawHandToggle;
    }
    public void setDrawScorePileToggle(boolean drawScorePileToggle) {
        this.drawScorePileToggle = drawScorePileToggle;
    }
}
