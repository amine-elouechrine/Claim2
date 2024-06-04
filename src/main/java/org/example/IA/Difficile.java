package org.example.IA;

import org.example.Modele.Card;
import org.example.Modele.Plateau;

public class Difficile extends IA{

    IAFirstPhase ia;
    public Difficile() {
        super("MinMax");
    }

    @Override
    public Card jouerCoupPhase1(Plateau plateau) {
        return ia.jouerCarteIA(plateau);
    }

    @Override
    public Card jouerCoupPhase2(Plateau plateau) {
        return IAMinMax.carteJouerIa(plateau);
    }
}
