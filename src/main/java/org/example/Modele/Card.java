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

    /**
     * @brief Constructeur par défaut.
     */
    public Card() {

    }


    /**
     * @brief Calcule le poids d'une carte en fonction de sa valeur et de sa faction. utiliser pour l'heurestique de l'ia
     * @param carte La carte dont on veut calculer le poids.
     * @return Le poids de la carte.
     */
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

    /**
     * @brief Constructeur de copie.
     * @param card La carte à copier.
     */
    public Card(Card card) {
        this.value = card.value;
        this.faction = card.faction;
    }

    /**
     * @brief Clone la carte actuelle.
     * @return Une nouvelle instance de Card avec les mêmes valeurs que la carte actuelle.
     */
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


    /**
     * @brief Vérifie si deux cartes sont égales.
     * @param o L'objet à comparer avec la carte actuelle.
     * @return true si les cartes sont égales, sinon false.
     */
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

    /**
     * @brief Obtient le score associé à la faction de la carte.
     * @return Le score de la faction.
     */
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

    /**
     * @brief Représentation sous forme de chaîne de caractères de la carte.
     * @return Une chaîne de caractères représentant la carte.
     */
    @Override
    public String toString() {
        return "Card{" + "faction=" + faction + ", value=" + value + '}';
    }
}