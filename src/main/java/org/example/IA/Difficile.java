package org.example.IA;

import org.example.Modele.Card;
import org.example.Modele.Hand;

public class Difficile extends IA {
    public Difficile(String Name) {
        super(Name);
    }

    @Override
    public Card jouerCoupPhase1(Hand mainIA, boolean suivre_faction, Card carte_adversaire) {
        return null;
    }

    @Override
    public Card jouerCoupPhase2(Hand mainIA, boolean suivre_faction, Card carte_adversaire) {
        return null;
    }

    public void generer_arbre_minimax_phase2(Hand mainIaPhase2, Hand mainAdversairePhase2) {
       //Node root = new Node(mainIaPhase2, mainAdversairePhase2);

    }

    @Override
    public Card jouerCarte(int indexCard) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'jouerCarte'");
    }

}

