import java.util.Scanner;
import java.util.List;

public class firstPhase {

    public void playFirstPhase(ReglesDeJeu r,Plateau plateau) {
        plateau.joueurCourant = plateau.joueur1; // on suppose que le joueur1 commence toujours le premier
        Scanner s = new Scanner(System.in); // le sanner pour taper l'indice des cartes dans la list des cartes des mains de chaque joueur
        int i = 0;//compteur pour calculer combien de partie on a joué
        while (i < 13) {
            plateau.carteAffichee = plateau.getPioche().getCard(); // on prends une carte de la pile
            int numCard = s.nextInt();//le choix de n'importe carte de la main du joueeur courant
            Card choosenCard = plateau.joueurCourant.getHand().removeCard(numCard); // on l'extrait de la list
            r.attributCard(plateau,choosenCard);// attribuer la carte a un joueur(voir ReglesdeJeux)
            r.switchJoueur(plateau); //passer au joueur adversaire
            List<Card> preselectedCards = r.cartesJouables(choosenCard, plateau.joueurCourant.getHand());//on affiche les cartes que le joueur adversaire peut les jouer
            int choosenCounterCardIndex = s.nextInt(); //choisir une carte parmi les cartes affichées par son indice
            Card conterCard = plateau.joueurCourant.getHand().removeCard(choosenCounterCardIndex); // carte jouer par le deuxieme joueur
            r.attributCard(plateau,conterCard);
            Card winningCard = r.carteGagnante(choosenCard, conterCard);//determiner la carte gagante entre les deux cartes joués
            if (winningCard == conterCard) { //si le gagnant c'est le joueur qui a joué au deuxieme tour
                r.playerWinsFirstPhaseManche(plateau, r);
            } else { // si le gagnant c'est le joueeur qui a joué au premier
                plateau.joueurCourant.handScndPhase.addCard(plateau.getPioche().getCard());//le joueur adversaire prend une carte de la pile
                r.switchJoueur(plateau);//passer au joueeur qui a joué au premier
                plateau.joueurCourant.handScndPhase.addCard(plateau.carteAffichee);// lui passer la carte affiché et c'est lui qui va commencer le prochai coup
                r.applyUndeadRule(plateau.joueurCourant,plateau);//applier la regle des Undead sur les cartes joués
            }
        i++;
        }
    }
}