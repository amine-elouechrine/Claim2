
/**
 * Cette classe représente une carte dans le jeu. Chaque carte a une valeur numérique et une faction associée.
 */

public abstract class Card {
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
     * Getter pour la valeur de la carte.
     * @return La valeur numérique de la carte.
     */
    public int getValeur() {
        return value;
    }

    /**
     * Setter pour la valeur de la carte.
     * @param value La nouvelle valeur numérique de la carte.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Getter pour la faction de la carte.
     * @return La faction à laquelle appartient la carte.
     */
    public String getFaction() {
        return faction;
    }
}