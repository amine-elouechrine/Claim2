package org.example.Modele;
import java.util.List;
import java.util.Scanner;

public class TestSecondPhase {
    Plateau plateau;
    TestSecondPhase(Plateau plateau,ReglesDeJeu r) {
        Player trickWinner;
        Player trickLoser ;
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to second phase");
        int i=0 ;
        while (i<13){
            System.out.println("voici les cartes que le joueeur courant peut les jouer");
            for (Card card :plateau.joueurCourant.handScndPhase.getAllCards()){
                System.out.println(card);
            }
            System.out.println("tapez le numero de la carte que vous voulez jouer");
            int numCard = s.nextInt();
            Card choosenCard = plateau.joueurCourant.getHandScndPhase().removeCard(numCard);
            r.attributCard(plateau, choosenCard);
            r.switchJoueur(plateau);
            List<Card> preselectedCards = r.cartesJouables(choosenCard,plateau.joueurCourant.getHandScndPhase());
            System.out.println("voici les cartes de laquelle vous pouvez chosir");
            for(Card card :preselectedCards){
                System.out.println(card);
            }
            System.out.println("tapez le numero de la carte de cette liste");
            int numCounterCard = s.nextInt();
            Card counterCard = preselectedCards.get(numCounterCard);
            plateau.joueurCourant.getHandScndPhase().removeCard(counterCard);
            System.out.println("le joueur adversaire a choisi la carte "+counterCard);
            r.attributCard(plateau, counterCard);
            Card winningCard = r.carteGagnante(choosenCard,counterCard);
            System.out.println("la carte gagnante est "+winningCard);
            if (winningCard == counterCard){
                trickWinner = r.determinerGagnantManche(plateau.getJoueur1(),plateau.getJoueur2(),plateau.carteJoueur1,plateau.carteJoueur2);
                trickLoser = r.determinerPerdantManche(plateau.getJoueur1(),plateau.getJoueur2(),trickWinner);
                r.ApplyDwarvesRules(trickWinner,trickLoser,plateau);
            }
            else{
                trickLoser = plateau.joueurCourant;
                r.switchJoueur(plateau);
                trickWinner=plateau.joueurCourant;
                r.ApplyDwarvesRules(trickWinner,trickLoser,plateau);
            }
            i++;

        }
    }
    public static void main(String[] args){
        Cards cards = new Cards();
        cards.shuffle();
        Hand firstHand =cards.getHandOf13Cards();
        Hand secondHand =cards.getHandOf13Cards();
        Player joueur1 = new Player("joueur1", firstHand);
        Player joueur2 = new Player("joueur2", secondHand);
        Plateau plateau = new Plateau(joueur1,joueur2,cards);
        ReglesDeJeu r = new ReglesDeJeu();
        TestFirstPhase TestFirstPhase = new TestFirstPhase(plateau ,r,cards);
        TestSecondPhase testSecondPhase = new TestSecondPhase(plateau,r);
        String Gagnant = r.determinerGagnantPartie(plateau.getJoueur1(),plateau.getJoueur2());
        System.out.println(Gagnant);
    }
}

