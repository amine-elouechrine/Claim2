package org.example.IA;

import org.example.Modele.*;

import java.util.Random;

public class Facile extends IA {

    Player joueur1;
    Player IA;
    Random rand;


    public Facile() {
        super("IA Facile");
        rand = new Random();
    }

    public static void main(String[] args) {
        Facile ia = new Facile();
        Plateau plateau = new Plateau();
        plateau.initialiserJeu(true, "", "");
        plateau.setCarteJoueur2(plateau.getJoueur2().getHand().getCard(0));
        Card card = ia.joueCoupPhase1F(plateau);
        System.out.println(card);
    }

    public Card joueCoupPhase1F(Plateau plateau) {
        if (!plateau.estLeader()) {
            return jouerAvecSuiviFaction(plateau);
        } else {
            return jouerCarteAleatoire(plateau.getJoueurCourant().getHand());
        }
    }

    private Card jouerAvecSuiviFaction(Plateau plateau) {
        Hand carteJouable;

        carteJouable = getCardsOfSameFaction2(plateau.getJoueurCourant().getHand(), plateau.getCardAdversaire().getFaction());

        if (carteJouable.isEmpty()) {
            if (plateau.estPhase1_2()) {
                return jouerCarteAleatoire(plateau.getJoueurCourant().getHand());
            } else return jouerCarteAleatoire(plateau.getJoueurCourant().getHand());

        } else {
            return choisirCarteAleatoire(carteJouable);
        }
    }

    private Card jouerCarteAleatoire(Hand main) {
        int index = rand.nextInt(main.getAllCards().size());

        Card carte = main.getAllCards().get(index);
        //System.out.println("jouer carte : " + carte.getFaction() + " " + carte.getValeur());
        return carte;
    }



    /*public Card jouer_coup_phase1_F(Plateau plateau) {
        System.out.println(";;;''''''"+hand.size());
        if(suivre_faction){
            Hand carteJouable;
            carteJouable = getCardsOfSameFaction(carte_adversaire.getFaction());
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
    }*/

    private Card choisirCarteAleatoire(Hand carteJouable) {
        int index = rand.nextInt(carteJouable.getAllCards().size());
        Card carte = carteJouable.getAllCards().get(index);
        //System.out.println("jouer carte : " + carte.getFaction() + " " + carte.getValeur());
        return carte;
    }


    /*public Card jouer_coup_phase2_F(Plateau plateau) {
        System.out.println(";;;''''''"+handScndPhase.size());
        if(suivre_faction){
            Hand carteJouable;
            carteJouable = getCardsOfSameFaction(carte_adversaire.getFaction());
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
    }*/

    // mainIA : doit etre handScndPhase
    public Card jouerCoupPhase2F(Plateau plateau) {
        //System.out.println(";;;''''''" + handScndPhase.size());
        if (!plateau.estLeader()) {
            return jouerAvecSuiviFaction(plateau);
        } else {
            return jouerCarteAleatoire(plateau.getJoueurCourant().getHand());
        }
    }

    @Override
    public Card jouerCoupPhase1(Plateau plateau) {
        return joueCoupPhase1F(plateau);
    }

    @Override
    public Card jouerCoupPhase2(Plateau plateau) {
        return jouerCoupPhase2F(plateau);
    }

    @Override
    public Card jouerCarte(int indexCard) {
        return null;
    }

    
}
