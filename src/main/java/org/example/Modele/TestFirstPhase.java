package org.example.Modele;
import java.util.List;
import java.util.Scanner;

public class TestFirstPhase {

    TestFirstPhase(Plateau plateau, ReglesDeJeu r,Cards cards){



        plateau.joueurCourant = plateau.joueur1;
        Scanner s  = new Scanner(System.in);
        System.out.println("bienvenue dans la premiere phase");
        while (plateau.joueur1.hand.size()>0 && plateau.joueur2.hand.size()>0){
            System.out.println("c'est le tour du joueur "+ plateau.joueurCourant.getName());
            Card playingCard = cards.getCard();
            System.out.println("on va jouer sur la carte : " + playingCard);
            System.out.println("voici les cartes du joueur : " + plateau.joueurCourant.getName());
            for(Card card : plateau.joueurCourant.getHand().getAllCards()){
                System.out.println(card);
            }
            int numCard = s.nextInt();
            Card choosenCard = plateau.joueurCourant.getHand().getAllCards().remove(numCard);
            System.out.println("le joueur "+plateau.joueurCourant.getName()+" a choisi la carte : "+choosenCard);
            r.switchJoueur(plateau);
            System.out.println("c'est le tour du joueur "+plateau.joueurCourant.getName());
            List<Card> preselectedCards = r.cartesJouables(choosenCard, plateau.joueurCourant.hand);
            System.out.println("le joueur "+plateau.joueurCourant.getName()+" peux chosir une de ces cartes :" );
            for(Card card : preselectedCards){
                System.out.println(card);
            }
            int choosenContreCardNumber = s.nextInt();

            Card conterCard = preselectedCards.get(choosenContreCardNumber);
            plateau.joueurCourant.getHand().getAllCards().remove(conterCard);
            System.out.println("le joueur "+plateau.joueurCourant.getName() + " a choisi la carte : "+conterCard );
            Card winningCard = r.carteGagnante(choosenCard, conterCard);
            System.out.println("la carte gagnante est "+ winningCard);
            if (winningCard==conterCard){//si le joueur courant a gagné le coup
                if (choosenCard.getFaction().equals("Dwarve")){//on ajoute les cartes dans la pile de score du joueur gagnant si elles sont de type dwarve
                    plateau.joueurCourant.getPileDeScore().addCard(choosenCard);
                }
                if (conterCard.getFaction().equals("Dwarve")){
                    plateau.joueurCourant.getPileDeScore().addCard(conterCard);
                }
                plateau.joueurCourant.handScndPhase.addCard(playingCard);
                r.switchJoueur(plateau);
                plateau.joueurCourant.handScndPhase.addCard(cards.getCard());
                r.switchJoueur(plateau);

            }
            else{
                if (choosenCard.getFaction().equals("Dwarve")){
                    plateau.joueurCourant.getPileDeScore().addCard(choosenCard);
                }
                if (conterCard.getFaction().equals("Dwarve")){
                    plateau.joueurCourant.getPileDeScore().addCard(conterCard);
                }
                plateau.joueurCourant.handScndPhase.addCard(cards.getCard());
                r.switchJoueur(plateau);
                plateau.joueurCourant.handScndPhase.addCard(playingCard);
            }


        }


    }

    /*public static void main(String[] args) {
        Cards cards = new Cards();
        cards.shuffle();
        Hand firstHand =cards.getHandOf13Cards();
        Hand secondHand =cards.getHandOf13Cards();
        Player joueur1 = new Player("joueur1", firstHand);
        Player joueur2 = new Player("joueur2", secondHand);
        Plateau plateau = new Plateau(joueur1,joueur2,cards);
        ReglesDeJeu r = new ReglesDeJeu();
        TestFirstPhase TestFirstPhase = new TestFirstPhase(plateau,r,cards);
    }*/
}

