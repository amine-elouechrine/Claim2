import java.util.List;
import java.util.Scanner;

public class TestFirstPhase {
    Cards cards ;
    Player joueur1;
    Player joueur2;
    Player joueurCourant;
    ReglesDeJeu reglesDeJeu;
    TestFirstPhase(){
        reglesDeJeu = new ReglesDeJeu();
        cards = new Cards();
        cards.shuffle();
        Hand firstHand =cards.getHandOf13Cards();
        Hand secondHand =cards.getHandOf13Cards();
        joueur1 = new Player("joueur1", firstHand);
        joueur2 = new Player("joueur2", secondHand);
        joueurCourant = joueur1;
        Scanner s  = new Scanner(System.in);
        while (joueur1.hand.size()>0 && joueur2.hand.size()>0){
            System.out.println("c'est le tour du joueur "+ joueurCourant.getName());
            Card playingCard = cards.getCard();
            System.out.println("on va jouer sur la carte : " + playingCard);
            System.out.println("voici les cartes du joueur : " + joueurCourant.getName());
            for(Card card : joueurCourant.getHand().getAllCards()){
                System.out.println(card);
            }
            int numCard = s.nextInt();
            Card choosenCard = joueurCourant.getHand().getAllCards().remove(numCard);
            System.out.println("le joueur "+joueurCourant.getName()+" a choisi la carte : "+choosenCard);
            switchJoueur();
            System.out.println("c'est le tour du joueur "+joueurCourant.getName());
            List<Card> preselectedCards = reglesDeJeu.cartesJouables(choosenCard, joueurCourant.hand);
            System.out.println("le joueur "+joueurCourant.getName()+" peux chosir une de ces cartes :" );
            for(Card card : preselectedCards){
                System.out.println(card);
            }
            int choosenContreCardNumber = s.nextInt();

            Card conterCard = preselectedCards.get(choosenContreCardNumber);
            joueurCourant.getHand().getAllCards().remove(conterCard);
            System.out.println("le joueur "+joueurCourant.getName() + " a choisi la carte : "+conterCard );
            Card winningCard = reglesDeJeu.carteGagnante(choosenCard, conterCard);
            System.out.println("la carte gagnante est "+ winningCard);
            if (winningCard==conterCard){//si le joueur courant a gagné le coup
                if (choosenCard.getFaction().equals("Dwarve")){//on ajoute les cartes dans la pile de score du joueur gagnant si elles sont de type dwarve
                    joueurCourant.getPileDeScore().addCard(choosenCard);
                }
                if (conterCard.getFaction().equals("Dwarve")){
                    joueurCourant.getPileDeScore().addCard(conterCard);
                }
                joueurCourant.handScndPhase.addCard(playingCard);
                switchJoueur();
                joueurCourant.handScndPhase.addCard(cards.getCard());
                switchJoueur();

            }
            else{
                if (choosenCard.getFaction().equals("Dwarve")){
                    joueurCourant.getPileDeScore().addCard(choosenCard);
                }
                if (conterCard.getFaction().equals("Dwarve")){
                    joueurCourant.getPileDeScore().addCard(conterCard);
                }
                joueurCourant.handScndPhase.addCard(cards.getCard());
                switchJoueur();
                joueurCourant.handScndPhase.addCard(playingCard);
            }


        }


        }
    public void switchJoueur(){
        if (joueurCourant==joueur1){
            joueurCourant = joueur2;
        }
        else if (joueurCourant==joueur2) {
            joueurCourant = joueur1;
        }
    }
    public static void main(String[] args) {
        TestFirstPhase TestFirstPhase = new TestFirstPhase();
    }
    }

