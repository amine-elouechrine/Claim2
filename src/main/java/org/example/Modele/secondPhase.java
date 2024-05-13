package org.example.Modele;
import java.util.List;
import java.util.Scanner;

public class secondPhase {



    public void playSecondPhase(ReglesDeJeu r,Plateau plateau){
        Player trickWinner;
        Player trickLoser;
        // la les deux joueeur ont 13 cartes chacun dans le handScndPhase
        Scanner s = new Scanner(System.in);
        int i =0;

        while (i<13){
            int numCArd = s.nextInt();
            Card choosenCard = plateau.joueurCourant.getHandScndPhase().removeCard(numCArd);
            r.attributCard(plateau, choosenCard);
            r.switchJoueur(plateau);
            List<Card> preselectedCards = r.cartesJouables(choosenCard,plateau.joueurCourant.getHandScndPhase());
            int numCounterCard = s.nextInt();
            Card counterCard = plateau.joueurCourant.getHandScndPhase().removeCard(numCounterCard);
            r.attributCard(plateau, counterCard);
            Card winningCard = r.carteGagnante(choosenCard,counterCard);
            if (winningCard == counterCard){
                trickWinner = plateau.joueurCourant;
                r.switchJoueur(plateau);
                trickLoser = plateau.joueurCourant;
                r.switchJoueur(plateau);
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
}
