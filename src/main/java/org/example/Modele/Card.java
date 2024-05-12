package org.example.Modele;

public abstract class Card {
    private int value;
    private String faction;

    // Constructeur
    public Card(int value, String faction) {
        this.value = value;
        this.faction = faction;
    }

    // Getters et setters
    public int getValeur() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getFaction() {
        return faction;
    }
    public int getFactionScore() {
        switch (faction) {
            case "Undead":
                return 5;
            case "Doppelganger":
                return 4;
            case "Chevalier":
                return 3;
            case "Nains":
                return 2;
            case "Goblins":
                return 1;
            default:
                return 0;
        }
    }
}