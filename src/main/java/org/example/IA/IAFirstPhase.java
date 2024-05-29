package org.example.IA;
import org.example.Modele.*;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class IAFirstPhase {
    ReglesDeJeu r = new ReglesDeJeu();
    Player IA;
    Player adversaire ;
    public int moyenneDuPoids(Plateau plateau){
        int mean =0 ;
        for (Card carte : plateau.getPioche().getCards()){
            mean += carte.getWeight(carte);
        }
        for (Card carte : adversaire.getHand().getAllCards()){
            mean += carte.getWeight(carte);
        }
        int taille = plateau.getPioche().getSize() + adversaire.getHand().size();
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
        IAhand.removeCard(card);
        return card;
    }
    public Card getMinCard (Hand IAhand){
        int minCArd  = 11 ;
        Card card =null ;
        for (Card carte : IAhand.getCards()){
            if (carte.getValeur()<minCArd){
                card = carte ;
                minCArd = carte.getValeur();
            }
        }
        IAhand.removeCard(card);
        return card;
    }




    /*public Card getCardAdversaire(Plateau p ){
        Scanner s = new Scanner(InputStream.nullInputStream()) ;
        if (p.estLeader()){
            System.out.println("vous pouvez choisir une de ses cartes ");
            for(Card carte : p.getJoueurCourant().getHand().getCards()){
                System.out.println(carte);
            }
            int x=s.nextInt();
            Card cartechosie = p.getJoueurCourant().getHand().removeCard(x);
            System.out.println("vous avez chosie la carte " +cartechosie);
            return cartechosie ;
        }
        else{
             List<Card> cartesJouables = r.cartesJouables(p.getCardAdversaire(), p.getJoueurCourant().getHand());
                System.out.println("vous pouvez jouer une de ses cartes ");
                for (Card carte : cartesJouables){
                    System.out.println(carte);
                }
                int x = s.nextInt();
                Card cartechosie = cartesJouables.get(x);
                p.getJoueurCourant().getHand().removeCard(cartechosie);
                System.out.println("vous avez choisie la carte " + cartechosie);
                return cartechosie;

        }
    }*/
    public Card getMinCardIfNotLeader(Hand IAhand , List<Card> cartesJouables){
        int minval = 10;
        Card mincard =null;
        for(Card carte : cartesJouables){
            if (carte.getValeur()<minval){
                minval=carte.getValeur();
                mincard = carte;
            }
        }
        IAhand.removeCard(mincard);
        return mincard;
    }
    public Card minMaxCard(Hand IAhand,Card carteAdversaire) {
        Card highestSmallerCard = null;
        int opponentValue = carteAdversaire.getValeur();
        List<Card> carteJouables = r.cartesJouables(carteAdversaire,IAhand);
        boolean in =false ;
        if (in==true) {
            for (Card carte : carteJouables) {
                if (carte.getFaction().equals(carteAdversaire.getFaction())||(carte.getFaction().equals("Doppelganger")) || (carte.getFaction().equals("Knight") && carteAdversaire.getFaction().equals("Goblins"))) {
                    in = true;
                }
            }
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
        if(p.getCarteAffichee().getWeight(p.getCarteAffichee())>mean){
            return getCardIa(IAhand);
        }
        else {
            return getMinCard(IAhand);
        }
    }
    public Card getCardIAIfNotLeader (Hand IAhand , Plateau p , int mean,Card carteJoue){
        if (p.getCarteAffichee().getWeight(p.getCarteAffichee())>mean){
            return minMaxCard(p.getJoueurCourant().getHand(),carteJoue) ;
        }
        else{
            List<Card> list = r.cartesJouables(carteJoue,p.getJoueur1().getHand());
            return getMinCardIfNotLeader(p.getJoueurCourant().getHand(),list);
        }
    }
    public Card getCardAdversaireIfLeader(Plateau p){
        Scanner s = new Scanner(System.in) ;
        System.out.println("vous pouvez choisir une de ses cartes ");
        for(Card carte : p.getJoueurCourant().getHand().getCards()){
            System.out.println(carte);
        }
        int x=s.nextInt();
        Card cartechosie = p.getJoueurCourant().getHand().removeCard(x);
        System.out.println("vous avez chosie la carte " +cartechosie);
        return cartechosie ;
    }
    public Card getCardAdversaireIfNotLeader(Plateau p ,Card carteJoue){
        Scanner s = new Scanner(System.in) ;
        if (carteJoue == null)
            System.out.println("carte chosie par l'IA est null");
        List<Card> cartesJouables = r.cartesJouables(carteJoue, p.getJoueurCourant().getHand());
        System.out.println("vous pouvez jouer une de ses cartes ");
        for (Card carte : cartesJouables){
            System.out.println(carte);
        }
        int x = s.nextInt();
        Card cartechosie = cartesJouables.get(x);
        p.getJoueurCourant().getHand().removeCard(cartechosie);
        System.out.println("vous avez choisie la carte " + cartechosie);
        return cartechosie;
    }

    public Card jouerCarteIA(Plateau p ){
        // si l'ia est leader
        // getCardIAIfLeader
        // sinon
        // getCardIAIfNotLeader
        int mean =  moyenneDuPoids(p);
        Card carteIA;
        if (p.getCarteJoueur1()==null && p.getCarteJoueur2() ==null){
            carteIA = getCardIAIfLeader(p.getJoueur1().getHand(),p,mean);

        }
        else{
            carteIA = getCardIAIfNotLeader(p.getJoueur1().getHand(),p,mean,p.getCarteJoueur2());
        }
        return carteIA ;
    }

    /*public Card getCardIA(Plateau p , int mean ){
        if (p.estLeader()){
            if(p.getCarteAffichee().getWeight(p.getCarteAffichee())>mean){
                return getCardIa(p.getJoueurCourant().getHand());
            }
            else {
                return getMinCard(p.getJoueurCourant().getHand());

            }
        }
        else{
            if (p.getCarteAffichee().getWeight(p.getCarteAffichee())>mean){
                return minMaxCard(p.getJoueurCourant().getHand(),p.getCardAdversaire()) ;
            }
            else{
                return getMinCard(p.getJoueurCourant().getHand());
            }
        }
    }*/
    public IAFirstPhase(ReglesDeJeu r){
        Plateau p = new Plateau();
        IA = new Player("IA");
        adversaire = new Player("joueur1");
        Cards pioche = new Cards();
        pioche.addAllCards();
        pioche.shuffle();
        IA.setHand(pioche.getHandOf13Cards());
        adversaire.setHand(pioche.getHandOf13Cards());
        p.setPioche(pioche);
        p.setJoueur1(IA);
        p.setJoueur2(adversaire);
        p.setJoueurCourant(IA);
        p.setPhase(true);
        int mean =  moyenneDuPoids(p);

        while(!(p.getJoueur1().getHand().isEmpty())&&!(p.getJoueur1().getHand().isEmpty())) {
            p.setCardAffiche(p.getPioche().getCard());
            Card premiereCarte ;
            System.out.println("on va jouer sur la carte "+ p.getCarteAffichee());
            if (p.getJoueurCourant().getName().equals("IA")){
                premiereCarte = jouerCarteIA(p);
                p.setCarteJoueur1(premiereCarte);
                System.out.println("L'IA a choisie la carte " + premiereCarte);
            }
            else{
                premiereCarte = getCardAdversaireIfLeader(p);
                p.setCarteJoueur2(premiereCarte);
            }
            p.switchJoueur();
            Card deuxiemeCarte ;
            if (p.getJoueurCourant().getName().equals("IA")){
                deuxiemeCarte = jouerCarteIA(p);
                System.out.println("L'IA a choisie la carte " + deuxiemeCarte);
                p.setCarteJoueur1(deuxiemeCarte);
            }
            else{
                deuxiemeCarte = getCardAdversaireIfNotLeader(p,premiereCarte);
                p.setCarteJoueur2(deuxiemeCarte);
            }
            Card WinningCard = r.carteGagnante(p.getCarteJoueur1(),p.getCarteJoueur2(),p);
            if (WinningCard == p.getCarteJoueur1()){
                    p.getJoueur1().getHandScndPhase().addCard(p.getCarteAffichee());
                    p.getJoueur2().getHandScndPhase().addCard(p.getPioche().getCard());
                    p.setJoueurCourant(p.getJoueur1());
            }
            else{
                    p.getJoueur2().getHandScndPhase().addCard(p.getCarteAffichee());
                    p.getJoueur1().getHandScndPhase().addCard(p.getPioche().getCard());
                    p.setJoueurCourant(p.getJoueur2());
            }
            mean=moyenneDuPoids(p);
        }
        System.out.println("voici la main de seconde phase de L'IA");
        for (Card carte :p.getJoueur1().getHandScndPhase().getCards()){
            System.out.println(carte);
        }
    }
    public static void main (String [] args){
        ReglesDeJeu r = new ReglesDeJeu();;
        new IAFirstPhase(r);

    }
}