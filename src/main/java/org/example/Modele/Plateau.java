package org.example.Modele;

//import javax.smartcardio.Card;

import java.util.List;
import java.util.Objects;

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

    /**
     * Constructeur de la classe Plateau.
     * Initialise la défausse .
     */

    // fait la creation de defausse ailleur et une fonction pour set defausse
    public Plateau() {
        this.defausse = new Defausse();
        this.phase = true; // commancer a la phase 1
    }

    /**
     * Constructeur de la classe Plateau pour l'intelligence artificielle pour puisse faire une copie du plateau.
     * @param
     */
    // Constructeur de copie phase 2
    // ajouter la defausse et la pioche pour la phase 2
    public Plateau(Plateau other) {
        this.carteJoueur1 = other.carteJoueur1 != null ? new Card(other.carteJoueur1) : null;
        this.carteJoueur2 = other.carteJoueur2 != null ? new Card(other.carteJoueur2) : null;
        this.joueur1 = other.joueur1 != null ? new Player(other.joueur1) : null;
        this.joueur2 = other.joueur2 != null ? new Player(other.joueur2) : null;
        if (other.joueurCourant == other.joueur1) {
            this.joueurCourant = this.joueur1;
        } else {
            this.joueurCourant = this.joueur2;
        }
        this.pioche = other.pioche != null ? new Cards(other.pioche) : null;
        this.defausse = other.defausse != null ? new Defausse(other.defausse) : null;
        this.phase = false;
    }

    public Plateau clone() {
        return new Plateau(this);
    }

    // Sauvegarde l'état actuel du plateau
    /*public PlateauState saveState() {
    /*public PlateauState saveState() {

        // Cloner les cartes des joueurs
        if(carteJoueur1 == null){
            carteJoueur1 = new Card();
        }else{
            carteJoueur1 = carteJoueur1.clone();
        }
        if(carteJoueur2 == null){
            carteJoueur2 = new Card();
        }else{
            carteJoueur2 = carteJoueur2.clone();
        }


        // Cloner les joueurs
        Player clonedJoueur1 = joueur1.clone();
        Player clonedJoueur2 = joueur2.clone();

        Player CurrentPlayer ;
        if(joueur1 == joueurCourant){
            CurrentPlayer = clonedJoueur1 ;
        }else{
            CurrentPlayer = clonedJoueur2 ;
        }
        return new PlateauState(clonedCarteJoueur1, clonedCarteJoueur2, clonedJoueur1, clonedJoueur2, CurrentPlayer);
    }*/

    // Restaure un état précédemment sauvegardé
    public void restoreState(PlateauState state) {
        this.carteJoueur1 = state.getCarteJoueur1();
        this.carteJoueur2 = state.getCarteJoueur2();
        this.joueur1 = state.getJoueur1();
        this.joueur2 = state.getJoueur2();
        if (state.getJoueurCourant() == state.getJoueur1())
            this.joueurCourant = this.joueur1 ;
        else
            this.joueurCourant = this.joueur2;
        this.phase = false;
    }


    /**
     * constructeur de la classe Plateau pour les jeux de test.
     */
    public Plateau(Card carteJoeur1, Card carteJoueur2) {
        this.carteJoueur1 = carteJoeur1;
        this.carteJoueur2 = carteJoueur2;
    }


    public Card getCardAdversaire() {
        if (joueurCourant == joueur1) {
            return carteJoueur2;
        } else {
            return carteJoueur1;
        }
    }

    public Hand getHandAdversaire() {
        if (joueurCourant == joueur1) {
            return joueur2.getHand();
        } else {
            return joueur1.getHand();
        }
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

    public GeneralPlayer getAdversaire(){
        if(joueurCourant == joueur1){
            return joueur2;
        }else{
            return joueur1;
        }
    }

    /**
     * Génère une chaîne de caractères représentant l'état actuel du plateau.
     * L'état inclut les mains des deux joueurs pour la seconde phase et les cartes jouées par chaque joueur.
     * Si une carte est nulle, elle est représentée par "null" dans la chaîne générée.
     *
     * @return une chaîne de caractères représentant l'état actuel du plateau.
     */
    public String generateState() {
        StringBuilder sb = new StringBuilder();

        // Ajouter les mains des deux joueurs pour la seconde phase
        sb.append(getJoueur1().getHandScndPhase().toString());
        sb.append(getJoueur2().getHandScndPhase().toString());

        // Ajouter la carte jouée par le joueur 1 ou "null" si la carte est nulle
        if (getCarteJoueur1() == null) {
            sb.append("null");
        } else {
            sb.append(getCarteJoueur1().toString());
        }

        // Ajouter la carte jouée par le joueur 2 ou "null" si la carte est nulle
        if (getCarteJoueur2() == null) {
            sb.append("null");
        } else {
            sb.append(getCarteJoueur2().toString());
        }

        return sb.toString();
    }



    public boolean getPhase() {
        return phase;
    }

    /**
     * Changer la phase du jeu.
     *
     * @param val
     */
    public void setPhase(boolean val) {
        phase = val;
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
        return !phase && getJoueur1().getHand().isEmpty() && getJoueur2().getHand().isEmpty();
    }

    /**
     * verifier si c'est la fin du jeu
     *
     * @return true si c'est la fin du jeu, false sinon
     */
    /*public boolean isEndOfGame() {
        if (phase == false) { // si on est dans la 2eme phase
            return estFinPhase(getPhase());
        }
        return false;
    }*/


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

    public Player getJoueurNonCourant() {
        if (joueurCourant == joueur1) {
            return joueur2;
        } else {
            return joueur1;
        }
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

    public void setPioche() {
        Cards pile = new Cards();
        pile = new Cards();
        this.pioche = pile;

    }

    /**
     *
     */
    public void initialiserJeu() {
        //creation des cartes de jeu et shuffle (pioche)
        pioche = new Cards();
        pioche.addAllCards();

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

        // Init joueur courant
        joueurCourant = joueur1;

        //initialiser la carte affichee
        carteAffichee = pioche.getCard();

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
        if (getPhase())
            carteJoue = getJoueurCourant().jouerCarte(indexCard);
        else
            carteJoue = getJoueurCourant().jouerCarte2(indexCard);
        if (joueurCourant == joueur1) {
            setCarteJoueur1(carteJoue);
        } else if (joueurCourant == joueur2) {
            setCarteJoueur2(carteJoue);
        }
        return carteJoue;
    }

    public Card jouerCarte(Card card) {
        Card carteJoue;
        if(getPhase())
            carteJoue = joueurCourant.jouerCarte(card);
        else
            carteJoue = joueurCourant.jouerCarte2(card);

        if(joueurCourant == joueur1) {
            setCarteJoueur1(carteJoue);
        }
        else if (joueurCourant == joueur2) {
            setCarteJoueur2(carteJoue);
        }
        return carteJoue;
    }


    public void setCardAffiche(Card card) {
        carteAffichee = card;
    }

    public boolean estLeader2() {
        return getCardAdversaire() == null;
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

    public void switchJoueur() {
        if (joueurCourant == joueur1) {
            joueurCourant = joueur2;
        } else {
            joueurCourant = joueur1;
        }
    }

    // use apply phirst phase rule function
    public void attribuerCarteFirstPhase(Card winningCard, ReglesDeJeu r) {
        if (r.carteEgaux(carteJoueur1, carteJoueur2)) {
            // determiner le leader
            if (joueurCourant.getName() == joueur2.getName()) { // si le joueur 1 est le leader
                r.applyUndeadRule(joueur1, carteJoueur1, carteJoueur2, defausse);
                joueur1.getHandScndPhase().addCard(carteAffichee);
                joueur2.getHandScndPhase().addCard(pioche.getCard());
                joueurCourant = joueur1;
            } else {
                r.applyUndeadRule(joueur2, carteJoueur2, carteJoueur1, defausse);
                joueur2.getHandScndPhase().addCard(carteAffichee);
                joueur1.getHandScndPhase().addCard(pioche.getCard());
                joueurCourant = joueur2;
            }
        } else {
            if (winningCard == carteJoueur1) {
                joueur1.getHandScndPhase().addCard(carteAffichee);
                joueur2.getHandScndPhase().addCard(pioche.getCard());
                r.applyFirstPhaseRules(joueur1, carteJoueur1, carteJoueur2, defausse);
                joueurCourant = joueur1;
            } else if (winningCard == carteJoueur2) {
                joueur2.getHandScndPhase().addCard(carteAffichee);
                joueur1.getHandScndPhase().addCard(pioche.getCard());
                r.applyFirstPhaseRules(joueur2, carteJoueur2, carteJoueur1, defausse);
                joueurCourant = joueur2;
            }
        }
        
    }




    
    // use applay sndphaserule function 
    public void attribuerCarteSecondPhase(Card winningCard, ReglesDeJeu r) {// on doit changer la fonction ApplyDwarveRule:c'est fait
        if (r.carteEgaux(carteJoueur1, carteJoueur2)) {
            // determiner le leader
            if (joueurCourant.getName().equals(joueur2.getName())) { // si le joueur 1 est le leader
                r.applySecondPhaseRules(joueur1, joueur2, carteJoueur1, carteJoueur2);
                joueurCourant = joueur1;
            } else {
                r.applySecondPhaseRules(joueur2, joueur1, carteJoueur2, carteJoueur1);
                joueurCourant = joueur2;
            }
        } else {
            if (winningCard == carteJoueur1) {
                r.applySecondPhaseRules(joueur1, joueur2, carteJoueur1, carteJoueur2);
                joueurCourant = joueur1;
            } else { // winningCard == carteJoueur2
                r.applySecondPhaseRules(joueur2, joueur1, carteJoueur2, carteJoueur1);
                joueurCourant = joueur2;
            }
        }
    }

    public boolean coupJouable(List<Card> preselected, int indice, Hand hand) {
        return preselected.contains(hand.getCard(indice));
    }

    public Boolean estPhase1() {
        return !(joueur1.getHand().isEmpty() && joueur2.getHand().isEmpty());
    }

    @Override
    public int hashCode() {
        // Generate hash code based on relevant fields
        return Objects.hash(carteJoueur1, carteJoueur2, joueur1.pileDeScore, joueur2.pileDeScore, joueurCourant.getName(), phase);
    }

    public void setPlateau(boolean phase, Card carteAffichee, Card carteJoueur1, Card carteJoueur2, Defausse defausse, Player joueur1, Player joueur2, Cards pioche, String nameCurrentPlayer, Hand mainJ1, Hand mainJ2) {
        setPhase(phase);
        setCarteAffichee(carteAffichee);
        setCarteJoueur1(carteJoueur1);
        setCarteJoueur2(carteJoueur2);
        setDefausse(defausse);
        setJoueur1(joueur1);
        setJoueur2(joueur2);
        setPioche();
        getJoueur1().setHand(mainJ1);
        getJoueur2().setHand(mainJ2);
        if (nameCurrentPlayer.equals(getJoueur1().getName())) {
            joueurCourant = getJoueur1();
        } else {
            joueurCourant = getJoueur2();
        }
    }

    public Boolean estPhase1_2() {
        return phase;
    }

    public void setHand(Hand hand1, Hand hand2) {
        joueur1.setHand(hand1);
        joueur2.setHand(hand2);

    }

    public boolean estPartieTerminer() {
        return !phase && getJoueur1().getHandScndPhase().isEmpty() && getJoueur2().getHandScndPhase().isEmpty();
    }

}