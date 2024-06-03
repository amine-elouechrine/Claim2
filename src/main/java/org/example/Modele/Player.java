package org.example.Modele;

public class Player extends GeneralPlayer {

    public Player(String Name) {
        super(Name);
    }


    // Constructeur de copie
    public Player(Player other) {
        super(other.Name);
        this.handScndPhase = new Hand(other.handScndPhase);
        this.hand = new Hand(other.hand);
        this.pileDeScore = new PileDeScore(other.pileDeScore);
    }

    @Override
    public Player clone() {
        return new Player(this);
    }

}
