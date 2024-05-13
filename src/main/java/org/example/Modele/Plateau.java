package org.example.Modele;

//import javax.smartcardio.Card;
import java.util.List;

/**
 * Cette classe représente le plateau de jeu dans lequel les cartes sont placées pendant la partie.
 * Elle contient la carte affichée, les cartes des joueurs, la pioche, la défausse et les joueurs.
 */

public class Plateau {
    Card carteAffichee; // Carte affichée sur le plateau
    Card carteJoueur1;
    Card carteJoueur2;
    Cards pioche;
    Defausse defausse; // Pile de cartes défaussées
    Player joueur1;
    Player joueur2;
    Player joueurCourant ;

    /**
     * Constructeur de la classe Plateau.
     * Initialise les joueurs, la pioche et la défausse.
     * @param joueur1 Le joueur 1
     * @param joueur2 Le joueur 2
     * @param pioche La pioche de cartes
     */
    public Plateau() {
        this.defausse = new Defausse();
    }

    /**
     * Renvoie la carte affichée sur le plateau.
     * @return La carte affichée sur le plateau.
     */
    public Card getCarteAffichee() {
        return carteAffichee;
    }

    /**
     * Renvoie la carte du joueur 1.
     * @return La carte du joueur 1.
     */
    public Card getCarteJoueur1() {
        return carteJoueur1;
    }

    /**
     * Renvoie la carte du joueur 2.
     * @return La carte du joueur 2.
     */
    public Card getCarteJoueur2() {
        return carteJoueur2;
    }

    /**
     * Renvoie le joueur courant.
     * @return Le joueur courant.
     */
    public Player getJoueurCourant() {
        return joueurCourant;
    }

    /**
     * Renvoie le joueur 1.
     * @return Le joueur 1.
     */
    public Player getJoueur1() {
        return joueur1;
    }

    /**
     * Renvoie le joueur 2.
     * @return Le joueur 2.
     */
    public Player getJoueur2() {
        return joueur2;
    }

    /**
     * Ajoute une carte à la défausse.
     * @param card La carte à ajouter à la défausse.
     */
    public void addToDefausse(Card card) {
        defausse.ajouterCarte(card);
    }

    /**
     * Renvoie la liste des cartes dans la défausse.
     * @return La liste des cartes dans la défausse.
     */
    public Defausse getDefausse() {
        return defausse;
    }

    /**
     * Renvoie la pioche.
     * @return
     */
    public Cards getPioche() {
        return pioche;
    }

    /**
     * 
     */
    public void initialiserJeu(){
        //creation des cartes de jeu et shuffle (pioche)
        pioche = new Cards();
        pioche.shuffle();
        //creation & initialiser les mains 
        Hand mainJoueur1 = pioche.getHandOf13Cards();
        Hand mainJoueur2 = pioche.getHandOf13Cards();
        //creation des joueurs
        joueur1 = new Player("Joueur 1");
        joueur2 = new Player("Joueur 2");
        //initialiser les mains des joueurs
        joueur1.setHand(mainJoueur1);
        joueur2.setHand(mainJoueur2);
        //initialiser la carte affichee
        carteAffichee = pioche.getCard();
    }

    /**
     * Joue une carte de la main du joueur leader (qui commance le tour) et la retire de sa main.
     * @param indexCard
     * @return La carte jouée.
     */
    public Card jouerCarte(int indexCard){
        // jouer une carte quelconque de sa main
        return joueurCourant.jouerCarte(indexCard);
    }

    /**
     * verifierle joueur courant est le leader 
     * @return true si le joueur courant est le leader, false sinon
     */
    public boolean estLeader(){
        if(carteJoueur1 == null && carteJoueur2 == null){
            return true;
        }else{
            return false;
        }
    }

    public void attribuerCarteFirstPhase(Card winningCard) {
        if (winningCard==carteJoueur1){
            joueur1.getHandScndPhase().addCard(winningCard);
            joueur2.getHandScndPhase().addCard(pioche.getCard());
            joueurCourant=joueur1;
        }
        else{
            joueur2.getHandScndPhase().addCard(winningCard);
            joueur1.getHandScndPhase().addCard(pioche.getCard());
            joueurCourant=joueur2;
        }
    }
    public void attribuerCarteSecondPhase(Card winningCard,ReglesDeJeu r){// on doit changer la fonction ApplyDwarveRule:c'est fait
        if (winningCard==carteJoueur1){
            r.ApplyDwarvesRules(joueur1,joueur2, carteJoueur1,carteJoueur2);
        }
        else{
            r.ApplyDwarvesRules(joueur2,joueur1,carteJoueur2,carteJoueur1);
        }
    }
    public boolean coupJouable(List<Card> preselected , int indice,Hand hand ) {
        return preselected.contains(hand.getCard(indice));
    }
    public Boolean estPhase1(){
        return !(joueur1.getHand().isEmpty() && joueur2.getHand().isEmpty());
    }

}