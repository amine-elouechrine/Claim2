package org.example.IA;

import org.example.Modele.Card;
import org.example.Modele.Cards;
import org.example.Modele.Hand;
import org.example.Modele.Player;

import java.util.Random;

public class Facile extends IA {
    Player joueur1;
    Player IA;
    Random rand;

    public Facile() {
        super("Facile");
        rand = new Random();
    }

    public Card jouer_coup_phase1_F(Hand mainIA, boolean suivre_faction, Card carte_adversaire) {
        System.out.println(";;;''''''"+hand.size());
        if(suivre_faction){
            Hand carteJouable;
            carteJouable = hand.getCardsOfSameFaction(carte_adversaire);
            if(carteJouable.isEmpty()){
                //je peux pas suivre la faction de l'adversaire
                int index = rand.nextInt(hand.getAllCards().size());
                Card carte = hand.getAllCards().get(index);
                return carte;
                //System.out.println("jouer carte : " + carte.getFaction() + " " + carte.getValeur());
            }else{
                int index = rand.nextInt(carteJouable.getAllCards().size());
                Card carte = carteJouable.getAllCards().get(index);
                //System.out.println("jouer carte : " + carte.getFaction() + " " + carte.getValeur());
                return carte;
            }
        }else{
            int index = rand.nextInt(hand.getAllCards().size());
            Card carte = hand.getAllCards().get(index);
            //System.out.println("jouer carte par facile: " + carte.getFaction() + " " + carte.getValeur());
            return carte;
        }
    }
    public Card jouer_coup_phase2_F(Hand mainIA, boolean suivre_faction, Card carte_adversaire) {
        System.out.println(";;;''''''"+handScndPhase.size());
        if(suivre_faction){
            Hand carteJouable;
            carteJouable = handScndPhase.getCardsOfSameFaction(carte_adversaire);
            if(carteJouable.isEmpty()){
                //je peux pas suivre la faction de l'adversaire
                int index = rand.nextInt(handScndPhase.getAllCards().size());
                Card carte = handScndPhase.getAllCards().get(index);
                return carte;
                //System.out.println("jouer carte : " + carte.getFaction() + " " + carte.getValeur());
            }else{
                int index = rand.nextInt(carteJouable.getAllCards().size());
                Card carte = carteJouable.getAllCards().get(index);
                //System.out.println("jouer carte : " + carte.getFaction() + " " + carte.getValeur());
                return carte;
            }
        }else{
            int index = rand.nextInt(handScndPhase.getAllCards().size());
            Card carte = handScndPhase.getAllCards().get(index);
            //System.out.println("jouer carte par facile: " + carte.getFaction() + " " + carte.getValeur());
            return carte;
        }
    }

    @Override
    public Card jouer_coup_phase1(Hand mainIA, boolean suivre_faction, Card carte_adversaire) {
        return jouer_coup_phase1_F(mainIA, suivre_faction, carte_adversaire);
    }

    @Override
    public Card jouer_coup_phase2(Hand mainIA, boolean suivre_faction, Card carte_adversaire) {
        return jouer_coup_phase2_F(mainIA, suivre_faction, carte_adversaire);
    }
}
