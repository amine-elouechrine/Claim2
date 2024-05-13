import org.example.Modele.*;

public class jeu {
    Plateau plateau;
    Player joueur1 ;
    Player joueur2 ;
    Cards cards ;
    jeu(){
        cards = new Cards();
        cards.shuffle();
        Hand firstHand =cards.getHandOf13Cards();
        Hand secondHand =cards.getHandOf13Cards();
        joueur1 = new Player("joueur1", firstHand);
        joueur2 = new Player("joueur2", secondHand);
        plateau = new Plateau(joueur1,joueur2,cards);
        ReglesDeJeu r = new ReglesDeJeu();
        firstPhase firstPhase = new firstPhase();
        firstPhase.playFirstPhase(r,plateau);
        secondPhase secondPhase = new secondPhase();
        secondPhase.playSecondPhase(r,plateau);
        String Gagnant = r.determinerGagnantPartie(plateau.getJoueur1(),plateau.getJoueur2());
        System.out.println(Gagnant);


    }
}
