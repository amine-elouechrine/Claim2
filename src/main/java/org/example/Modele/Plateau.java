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
    Player joueurCourant;
    Boolean phase; // true si on est dans la phase 1, false sinon
    int numberCardPlayed;

    /**
     * Constructeur de la classe Plateau.
     * Initialise la défausse .
     */
    public Plateau() {
        this.defausse = new Defausse();
        this.phase = true; // commancer a la phase 1
    }

    /**
     * Constructeur de la classe Plateau pour l'intelligence artificielle pour puisse faire une copie du plateau.
     * @param plateau
     */
    public Plateau(Plateau plateau) {
        this.carteAffichee = plateau.getCarteAffichee();
        this.carteJoueur1 = plateau.getCarteJoueur1();
        this.carteJoueur2 = plateau.getCarteJoueur2();
        this.pioche = plateau.getPioche();
        this.defausse = plateau.getDefausse();
        this.joueur1 = plateau.getJoueur1();
        this.joueur2 = plateau.getJoueur2();
        this.joueurCourant = plateau.getJoueurCourant();
        this.phase = plateau.getPhase();
    }

    /**
     * constructeur de la classe Plateau pour les jeux de test. 
     */
    public Plateau(Card carteJoeur1 , Card carteJoueur2){
        this.carteJoueur1 = carteJoeur1;
        this.carteJoueur2 = carteJoueur2;
    }

    /**
     * Changer la phase du jeu.
     * @param val
     */
    public void setPhase (boolean val){
        phase=val;
    }

    /**
     * passer a la phase 2.
     */
    public void switchPhase() {
        if (phase == true) {
            phase = !phase;
        } else {
            System.out.println("Vous etes deja dans la phase 2");
        }
    }

    public boolean getPhase() {
        return phase;
    }

    /**
     * verifie si c'est la fin de la phase ou pas
     *
     * @param phase
     * @return true si c'est la fin de la phase, false sinon
     */
    public boolean estFinPhase(boolean phase) {
        if (joueur1.isHandEmpty(phase) && joueur2.isHandEmpty(phase)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * verifier si c'est la fin du jeu
     *
     * @return true si c'est la fin du jeu, false sinon
     */
    public boolean isEndOfGame() {
        if (phase == false) { // si on est dans la 2eme phase
            return estFinPhase(getPhase());
        }
        return false;
    }

    /**
     * Renvoie la carte affichée sur le plateau.
     *
     * @return La carte affichée sur le plateau.
     */
    public Card getCarteAffichee() {
        return carteAffichee;
    }

    public void setCarteAffichee(Card carteAffichee) {
        this.carteAffichee = carteAffichee;
    }

    /**
     * Renvoie la carte du joueur 1.
     *
     * @return La carte du joueur 1.
     */
    public Card getCarteJoueur1() {
        return carteJoueur1;
    }

    public void setCarteJoueur1(Card carteJoueur1) {
        this.carteJoueur1 = carteJoueur1;
    }

    /**
     * Renvoie la carte du joueur 2.
     *
     * @return La carte du joueur 2.
     */
    public Card getCarteJoueur2() {
        return carteJoueur2;
    }

    public void setCarteJoueur2(Card carteJoueur2) {
        this.carteJoueur2 = carteJoueur2;
    }

    /**
     * Renvoie le joueur courant.
     *
     * @return Le joueur courant.
     */
    public Player getJoueurCourant() {
        return joueurCourant;
    }

    public void setJoueurCourant(Player joueurCourant) {
        this.joueurCourant = joueurCourant;
    }

    /**
     * Renvoie le joueur 1.
     *
     * @return Le joueur 1.
     */
    public Player getJoueur1() {
        return joueur1;
    }

    public void setJoueur1(Player joueur1) {
        this.joueur1 = joueur1;
    }

    /**
     * Renvoie le joueur 2.
     *
     * @return Le joueur 2.
     */
    public Player getJoueur2() {
        return joueur2;
    }

    public void setJoueur2(Player joueur2) {
        this.joueur2 = joueur2;
    }

    /**
     * Ajoute une carte à la défausse.
     *
     * @param card La carte à ajouter à la défausse.
     */
    public void addToDefausse(Card card) {
        defausse.ajouterCarte(card);
    }


    /**
     * Renvoie la liste des cartes dans la défausse.
     *
     * @return La liste des cartes dans la défausse.
     */
    public Defausse getDefausse() {
        return defausse;
    }
    public void setDefausse(Defausse defausse) {
        this.defausse = defausse;
    }

    /**
     * Renvoie la pioche.
     *
     * @return
     */
    public Cards getPioche() {
        return pioche;
    }

    public void setPioche(Cards pioche) {
        this.pioche = pioche;
    }

    /**
     *
     */
    public void initialiserJeu() {
        //creation des cartes de jeu et shuffle (pioche)
        pioche = new Cards();
        pioche.addAllCards();
        System.out.println("Nombre carte dans la pioche : " + pioche.getCards().size());

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

        System.out.println("Nombre carte dans la pioche : " + pioche.getCards().size());

        // Init joueur courant
        joueurCourant = joueur1;
        //initialiser la carte affichee
        carteAffichee = pioche.getCard();

        System.out.println("Nombre carte dans la pioche : " + pioche.getCards().size());
    }

    /**
     * Joue une carte de la main du joueur leader (qui commance le tour) et la retire de sa main.
     *
     * @param indexCard
     * @return La carte jouée.
     */
    public Card jouerCarte(int indexCard) {
        // jouer une carte quelconque de sa main
        Card carteJoue;
        if(getPhase())
            carteJoue = joueurCourant.jouerCarte(indexCard);
        else
            carteJoue = joueurCourant.jouerCarte2(indexCard);
        if(joueurCourant == joueur1) {
            setCarteJoueur1(carteJoue);
        }
        else if (joueurCourant == joueur2) {
            setCarteJoueur2(carteJoue);
        }
        return carteJoue;
    }

    public void jouerCarte(Card card){
        if(joueurCourant == joueur1) {
            setCarteJoueur1(card);
        } else if (joueurCourant == joueur2) {
            setCarteJoueur2(card);
        }
    }

    /**
     * verifierle joueur courant est le leader
     *
     * @return true si le joueur courant est le leader, false sinon
     */
    public boolean estLeader() {
        if (carteJoueur1 == null && carteJoueur2 == null) {
            return true;
        } else {
            return false;
        }
    }

    public void switchJoueur(){
        if(joueurCourant==joueur1){
            joueurCourant=joueur2;
        }
        else{
            joueurCourant=joueur1;
        }
    }

    public void attribuerCarteFirstPhase(Card winningCard, ReglesDeJeu r) {
        if (winningCard == carteJoueur1) {
            joueur1.getHandScndPhase().addCard(carteAffichee);
            joueur2.getHandScndPhase().addCard(pioche.getCard());
            r.applyUndeadRule(joueur1,carteJoueur1,carteJoueur2,defausse);
            joueurCourant = joueur1;
        } else if(winningCard == carteJoueur2) {
            joueur2.getHandScndPhase().addCard(carteAffichee);
            joueur1.getHandScndPhase().addCard(pioche.getCard());
            r.applyUndeadRule(joueur2,carteJoueur2,carteJoueur1,defausse);
            joueurCourant = joueur2;
        }
        else if (winningCard==null){
            if(joueurCourant==joueur1){
                joueur2.getHandScndPhase().addCard(carteAffichee);
                joueur1.getHandScndPhase().addCard(pioche.getCard());
                joueurCourant=joueur2;
            }
            else{
                joueur1.getHandScndPhase().addCard(carteAffichee);
                joueur2.getHandScndPhase().addCard(pioche.getCard());
                joueurCourant=joueur1;
            }
        }
    }

    public void attribuerCarteSecondPhase(Card winningCard, ReglesDeJeu r) {// on doit changer la fonction ApplyDwarveRule:c'est fait
        if (winningCard == carteJoueur1) {
            r.ApplyDwarvesRules(joueur1, joueur2, carteJoueur1, carteJoueur2);
            joueurCourant = joueur1;
        } else if(winningCard == carteJoueur2)  {
            r.ApplyDwarvesRules(joueur2, joueur1, carteJoueur2, carteJoueur1);
            joueurCourant = joueur2;
        }
        else if (winningCard==null){
            if (joueurCourant==joueur1) {
                r.ApplyDwarvesRules(joueur1,joueur2,carteJoueur1,carteJoueur2);
                joueurCourant=joueur2;
            }
            else{
                r.ApplyDwarvesRules(joueur2,joueur1,carteJoueur2,carteJoueur1);
                joueurCourant=joueur1;
            }
        }else{
            throw new IllegalArgumentException("La carte gagnante n'est pas dans la main du joueur.");
        }
    }

    public boolean coupJouable(List<Card> preselected, int indice, Hand hand) {
        return preselected.contains(hand.getCard(indice));
    }

    public Boolean estPhase1() {
        return !(joueur1.getHand().isEmpty() && joueur2.getHand().isEmpty());
    }


}