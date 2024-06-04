package org.example.Modele;

//import javax.smartcardio.Card;

import java.util.List;
import java.util.Objects;

/**
 * La classe Plateau représente le plateau de jeu dans lequel les cartes sont placées pendant la partie.
 * Elle contient les informations essentielles au déroulement du jeu,
 * telles que les cartes des joueurs, la pioche, la défausse,
 * les joueurs eux-mêmes et la carte affichée sur le plateau.
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
    public Plateau() {
        this.defausse = new Defausse();
        this.phase = true; // commancer a la phase 1
    }

    /**
     * Constructeur de la classe Plateau pour l'intelligence artificielle pour puisse faire une copie du plateau.
     * @param
     */
    // Constructeur de copie phase 2
    public Plateau(Plateau other) {
        this.carteJoueur1 = other.carteJoueur1 != null ? new Card(other.carteJoueur1) : null;
        this.carteJoueur2 = other.carteJoueur2 != null ? new Card(other.carteJoueur2) : null;
        this.joueur1 = other.joueur1 != null ? new Player(other.joueur1) : null;
        this.joueur2 = other.joueur2 != null ? new Player(other.joueur2) : null;
        if (other.joueurCourant.getName().equals(other.joueur1.getName())) {
            this.joueurCourant = this.joueur1;
        } else {
            this.joueurCourant = this.joueur2;
        }
        this.defausse = other.defausse != null ? new Defausse(other.defausse) : null;
        this.pioche = other.pioche != null ? new Cards(other.pioche) : null;
        this.phase = other.phase;
    }

    public Plateau clone() {
        return new Plateau(this);
    }


    // Restaure un état précédemment sauvegardé
    public void restoreState(PlateauState state) {
        this.carteJoueur1 = state.getCarteJoueur1();
        this.carteJoueur2 = state.getCarteJoueur2();
        this.joueur1 = state.getJoueur1();
        this.joueur2 = state.getJoueur2();
        if (state.getJoueurCourant() == state.getJoueur1()) {
            this.joueurCourant = this.joueur1;
        }else {
            this.joueurCourant = this.joueur2;
        }
        this.defausse = state.getDefausse();
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

    public Card getAdversaireCard(){
        if(joueurCourant == joueur1){
            return carteJoueur2;
        }else{
            return carteJoueur1;
        }
    }

    public boolean estTourIa(){
        if(joueurCourant.getName().equals("MinMax")){
            return true;
        }else{
            return false;
        }
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
    public boolean estFinPartie() {
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
    public void initialiserJeu(boolean ia, String nameJ1, String nameJ2) {
        //creation des cartes de jeu et shuffle (pioche)
        pioche = new Cards();
        pioche.addAllCards();

        pioche.shuffle();
        //creation & initialiser les mains
        Hand mainJoueur1 = pioche.getHandOf13Cards();
        Hand mainJoueur2 = pioche.getHandOf13Cards();

        // creation des joueurs
        if (Objects.equals(nameJ1, ""))
            joueur1 = new Player("Joueur 1");
        else
            joueur1 = new Player(nameJ1);

        if (Objects.equals(nameJ2, ""))
            joueur2 = new Player("Joueur 2");
        else
            joueur2 = new Player(nameJ2);

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

        if(joueurCourant.getName().equals(joueur1.getName())) {
            setCarteJoueur1(carteJoue);
        }
        else if (joueurCourant.getName().equals(joueur2.getName()))  {
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

    /**
     * Permet de changer le joueur courant en passant au joueur suivant.
     */
    public void switchJoueur() {
        if (joueurCourant == joueur1) {
            joueurCourant = joueur2;
        } else {
            joueurCourant = joueur1;
        }
    }


    /**
     * Attribue les cartes aux joueurs après la fin d'un tour lors de la première phase (découverte des cartes).
     * applique les regles de jeu de la 1er phase pour determiner la gagne de chaque joueur
     *
     * @param winningCard La carte gagnante de la phase.
     * @param r L'instance des règles du jeu.
     */
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


    /**
     * Attribue les cartes aux joueurs après la fin d'un tour lors de la deuxième phase (bataille).
     * applique les regles de jeu de la 2eme phase pour determiner la gagne de chaque joueur
     *
     * @param winningCard La carte gagnante de la phase.
     * @param r L'instance des règles du jeu.
     */
    public void attribuerCarteSecondPhase(Card winningCard, ReglesDeJeu r) {// on doit changer la fonction ApplyDwarveRule:c'est fait
        // fait dans carte gagnante
        if (r.carteEgaux(carteJoueur1, carteJoueur2)) {
            // determiner le leader
            if (joueurCourant.getName().equals(joueur1.getName())) {
                r.applySecondPhaseRules(joueur2, joueur1, carteJoueur2, carteJoueur1);
                joueurCourant = joueur2;
            } else {
                r.applySecondPhaseRules(joueur1, joueur2, carteJoueur1, carteJoueur2);
                joueurCourant = joueur1;
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

    /**
     * Vérifie si une carte pré-sélectionnée est jouable dans la main du joueur.
     *
     * @param preselected La liste des cartes pré-sélectionnées.
     * @param indice L'indice de la carte dans la main du joueur.
     * @param hand La main du joueur.
     * @return True si la carte est jouable, False sinon.
     */
    public boolean coupJouable(List<Card> preselected, int indice, Hand hand) {
        return preselected.contains(hand.getCard(indice));
    }

    /**
     * Vérifie si la partie est dans la première phase (découverte des cartes).
     *
     * @return True si la partie est dans la première phase, False sinon.
     */
    public Boolean estPhase1() {
        return !(joueur1.getHand().isEmpty() && joueur2.getHand().isEmpty());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Plateau plateau = (Plateau) o;

        // sa suffit pas de comparer la carte joueur1 et carte joueur2 seulement parce qu'il y a plusieurs gobelins
        // Compare relevant fields to determine equality
        return  Objects.equals(carteJoueur1, plateau.carteJoueur1) &&
                Objects.equals(carteJoueur2, plateau.carteJoueur2) &&
                Objects.equals(joueur1.pileDeScore, plateau.joueur1.pileDeScore) &&
                Objects.equals(joueur2.pileDeScore, plateau.joueur2.pileDeScore) &&
                Objects.equals(joueurCourant.getName(), plateau.joueurCourant.getName()) &&
                Objects.equals(phase, plateau.phase);
    }

    @Override
    public int hashCode() {
        // Generate hash code based on relevant fields
        return Objects.hash(carteJoueur1, carteJoueur2, joueur1.pileDeScore, joueur2.pileDeScore, joueurCourant.getName(), phase);
    }


    public Boolean estPhase1_2() {
        return phase;
    }

    public void setHand(Hand hand1, Hand hand2) {
        joueur1.setHand(hand1);
        joueur2.setHand(hand2);

    }

}