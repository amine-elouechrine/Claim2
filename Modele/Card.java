 

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
    @Override
    public String toString() {
        return "Card{" + "faction=" + faction + ", value=" + value + '}';
    }
}