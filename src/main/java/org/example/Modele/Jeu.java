package org.example.Modele;
import org.example.Vue.NiveauGraphique;
import org.example.Patternes.Observable;

public class Jeu extends Observable {
    Plateau plateau;
    Player joueur1;
    Player joueur2;
    Cards cards;
    boolean phase1;

    public Jeu() {
        phase1 = true;
        cards = new Cards();
        cards.shuffle();
        Hand firstHand = cards.getHandOf13Cards();
        Hand secondHand = cards.getHandOf13Cards();

        joueur1 = new Player("joueur1", firstHand);
        joueur2 = new Player("joueur2", secondHand);
        plateau = new Plateau(joueur1, joueur2, cards);
        ReglesDeJeu r = new ReglesDeJeu();
        FirstPhase firstPhase = new FirstPhase();
        //firstPhase.playFirstPhase(r, plateau);
        secondPhase secondPhase = new secondPhase();
        //secondPhase.playSecondPhase(r, plateau);
        String Gagnant = r.determinerGagnantPartie(plateau.getJoueur1(), plateau.getJoueur2());
        System.out.println(Gagnant);


    }
    public int getPhase() {
        if(phase1) {
            return 1;
        }
        return 2;
    }

    public void switchPhase() {
        phase1 = !phase1;
    }

    public int getLignes() {
        return 4;
    }

    public int getColonnes() {
        return 6;
    }
}
