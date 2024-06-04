package org.example.IA;
import org.example.Modele.*;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class IAFirstPhase {
    HashMap<String ,Integer> diversity;
    public IAFirstPhase(){
        diversity=new HashMap<>();
        diversity.put("Goblins",0);
        diversity.put("Dwarves",0);
        diversity.put("Knight",0);
        diversity.put("Doppelganger",0);
        diversity.put("Undead",0);

    }
    public boolean ShouldTakeCard(String faction ){
        if(!diversity.containsKey(faction))
            return true;
        int minCount = 11 ;
        for(int count : diversity.values()){
            if(count <minCount)
                minCount=count ;
        }
        return diversity.get(faction)==minCount ;
    }
    public int moyenneDuPoids(Plateau plateau){
        int mean =0 ;
        for (Card carte : plateau.getPioche().getCards()){
            mean += carte.getWeight(carte);
        }
        for (Card carte : plateau.getJoueur1().getHand().getAllCards()){//somme du poids des cartes de l'adversaire
            mean += carte.getWeight(carte);
        }
        int taille = plateau.getPioche().getSize() + plateau.getJoueur1().getHand().size();
        if (taille!=0)
            return mean/taille;
        else
            return 0;
    }

    public Card getCardIa (Hand IAhand ){
        int maxCard = -1;
        Card card=null ;
        for (Card carte : IAhand.getCards()){
            if(carte.getValeur()>maxCard){
                card = carte ;
                maxCard = card.getValeur();
            }
        }
        //IAhand.removeCard(card);
        return card;
    }

    public Card getMinCard (Hand IAhand){
        int minCArd  = 100 ;
        Card card =null ;
        for (Card carte : IAhand.getCards()){
            if (carte.getWeight(carte)<minCArd){
                card = carte ;
                minCArd = carte.getWeight(carte);
            }
        }
        //IAhand.removeCard(card);
        return card;
    }


    public Card getMinCardIfNotLeader(Hand IAhand , List<Card> cartesJouables){
        int minval = 100;
        Card mincard =null;
        for(Card carte : cartesJouables){
            if (carte.getWeight(carte)<minval){
                minval=carte.getWeight(carte);
                mincard = carte;
            }
        }
        //IAhand.removeCard(mincard);
        return mincard;
    }

    public Card minMaxCard(Hand IAhand,Card carteAdversaire) {
        Card highestSmallerCard = null;
        int opponentValue = carteAdversaire.getValeur();
        List<Card> carteJouables = ReglesDeJeu.cartesJouables(carteAdversaire,IAhand);
        boolean in =false ;
        for (Card carte : carteJouables) {
            if (carte.getFaction().equals(carteAdversaire.getFaction())) {
                in = true;
            }
        }
        if(in) {
            for (Card card : carteJouables) {
                int cardValue = card.getValeur();
                if (cardValue > opponentValue && (highestSmallerCard == null || cardValue < highestSmallerCard.getValeur())) {
                    highestSmallerCard = card;
                }
            }
            if (highestSmallerCard == null) {
                return getMinCardIfNotLeader(IAhand, carteJouables);
            }
            return highestSmallerCard;
        }
        else{
            return getMinCard(IAhand);
        }
    }

    public Card getCardIAIfLeader(Hand IAhand ,Plateau p,int mean){
        if(p.getCarteAffichee().getWeight(p.getCarteAffichee())>mean || ShouldTakeCard(p.getCarteAffichee().getFaction())){
            return getCardIa(IAhand);
        }
        else {
            return getMinCard(IAhand);
        }
    }
    public Card getCardIAIfNotLeader (Hand IAhand , Plateau p , int mean,Card carteJoue){
        if (p.getCarteAffichee().getWeight(p.getCarteAffichee())>mean ||  (ShouldTakeCard(p.getCarteAffichee().getFaction()) && p.getJoueurCourant().getHand().size()<9)){
            return minMaxCard(p.getJoueurCourant().getHand(),carteJoue) ;
        }
        else{
            List<Card> list = ReglesDeJeu.cartesJouables(carteJoue,p.getJoueurCourant().getHand());
            return getMinCardIfNotLeader(p.getJoueurCourant().getHand(),list);
        }
    }

    public Card jouerCarteIA(Plateau p)
    {
        int mean =  moyenneDuPoids(p);
        Card carteIA;
        if (p.getCarteJoueur1()==null && p.getCarteJoueur2() ==null){
            carteIA = getCardIAIfLeader(p.getJoueurCourant().getHand(),p,mean);

        }
        else{
            carteIA = getCardIAIfNotLeader(p.getJoueurCourant().getHand(),p,mean,p.getCardAdversaire());
        }
        diversity.put(carteIA.getFaction(),diversity.get(carteIA.getFaction())+1);
        return carteIA ;
    }
}