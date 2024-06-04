package org.example.Modele;
/**
 * Cette classe représente une carte dans le jeu. Chaque carte a une valeur numérique et une faction associée.
 */


public class Card {
    private int value;// La valeur de la carte
    private String faction; // La faction à laquelle appartient la carte

    /**
     * Constructeur de la classe Card.
     * @param value La valeur numérique de la carte.
     * @param faction La faction à laquelle appartient la carte.
     */
    public Card(int value, String faction) {
        this.value = value;
        this.faction = faction;
    }


    public int getWeight(Card carte) {
        int valCarte ;
        if (carte.getValeur()>4){
            valCarte=carte.getValeur()+4;
        }
        else
            valCarte=carte.getValeur()+2;
        switch (carte.getFaction()) {
            case "Goblins":
                return valCarte + 2;
            case "Knight":
                return valCarte+ 3;
            case "Doppelganger":
                return valCarte + 4;
            case "Dwarves":
                return valCarte + 3;
            case "Undead":
                return valCarte + 3;
        }
        return 0;
    }

    public Card() {
        // Constructeur vide pour les tests
    }

    public Card(Card card) {
        this.value = card.value;
        this.faction = card.faction;
    }


    public Card clone() {
        // Assuming Card has a copy constructor
        return new Card(this);
    }

    /**
     * Getter pour la valeur de la carte.
     * @return La valeur numérique de la carte.
     */
    public int getValeur() {
        return value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (value != card.value) return false;
        return faction.equals(card.faction);
    }

    /**
     * Setter pour la valeur de la carte.
     * @param value La nouvelle valeur numérique de la carte.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Setter pour la faction de la carte.
     * @param faction
     */
    public void setFaction(String faction) {
        this.faction = faction;
    }

    /**
     * Getter pour la faction de la carte.
     * @return La faction à laquelle appartient la carte.
     */
    public String getFaction() {
        return faction;
    }

    public int getFactionScore() {
        switch (faction) {
            case "Undead":
                return 5;
            case "Doppelganger":
                return 4;
            case "Knight":
                return 3;
            case "Dwarves":
                return 2;
            case "Goblins":
                return 1;
            default:
                return 0;
        }}
    @Override
    public String toString() {
        return "Card{" + "faction=" + faction + ", value=" + value + '}';
    }
}