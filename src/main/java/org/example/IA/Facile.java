package org.example.IA;

import org.example.Modele.Card;
import org.example.Modele.Cards;
import org.example.Modele.Hand;
import org.example.Modele.Player;

import java.util.Random;

public class Facile {
    Player joueur1;
    Player IA;
    Random rand;

    public Facile(Player j1, Player ia) {
        joueur1 = j1;
        IA = ia;
        Cards pioche = new Cards();
        pioche.shuffle();
        IA.setHand(pioche.getHandOf13Cards());
        j1.setHand(pioche.getHandOf13Cards());
        rand = new Random();
    }

    public void jouer_coup(Hand mainIA, boolean suivre_faction, Card carte_adversaire) {
        if(suivre_faction){
            Hand carteJouable;
            carteJouable = mainIA.getCardsOfSameFaction(carte_adversaire);
            if(carteJouable.isEmpty()){
                //je peux pas suivre la faction de l'adversaire
                int index = rand.nextInt(mainIA.getAllCards().size());
                Card carte = mainIA.getAllCards().get(index);
                System.out.println("jouer carte : " + carte.getFaction() + " " + carte.getValeur());
            }else{
                int index = rand.nextInt(carteJouable.getAllCards().size());
                Card carte = carteJouable.getAllCards().get(index);
                System.out.println("jouer carte : " + carte.getFaction() + " " + carte.getValeur());
            }
        }else{
            int index = rand.nextInt(mainIA.getAllCards().size());
            Card carte = mainIA.getAllCards().get(index);
            System.out.println("jouer carte : " + carte.getFaction() + " " + carte.getValeur());
        }
    }

}
