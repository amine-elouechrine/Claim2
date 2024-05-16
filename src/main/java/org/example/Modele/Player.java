package org.example.Modele;

public class Player extends GeneralPlayer{
    String Name ;
    int score ;
    Hand hand;
    Hand handScndPhase;
    PileDeScore pileDeScore;

    public Player(String Name) {
        super(Name);
    }

    // il faut faire overide de jouer carte et implementer la logique de jouer une carte pr n joueur humain

}
