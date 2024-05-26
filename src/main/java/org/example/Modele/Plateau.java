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
     *
     * @param other
     */
    // Constructeur de copie
    public Plateau(Plateau other) {
        this.carteAffichee = other.carteAffichee != null ? new Card(other.carteAffichee) : null;
        this.carteJoueur1 = other.carteJoueur1 != null ? new Card(other.carteJoueur1) : null;
        this.carteJoueur2 = other.carteJoueur2 != null ? new Card(other.carteJoueur2) : null;
        this.pioche = other.pioche != null ? new Cards(other.pioche) : null;
        this.defausse = other.defausse != null ? new Defausse(other.defausse) : null;
        this.joueur1 = other.joueur1 != null ? new Player(other.joueur1) : null;
        this.joueur2 = other.joueur2 != null ? new Player(other.joueur2) : null;
        if (other.joueurCourant == other.joueur1) {
            this.joueurCourant = this.joueur1;
        } else if (other.joueurCourant == other.joueur2) {
            this.joueurCourant = this.joueur2;
        } else {
            this.joueurCourant = null; // Ou autre gestion d'erreur si nécessaire
        }
        this.phase = other.phase;
    }

    /**
     * constructeur de la classe Plateau pour les jeux de test.
     */
    /*public Plateau(Card carteJoeur1, Card carteJoueur2) {
        this.carteJoueur1 = carteJoeur1;
        this.carteJoueur2 = carteJoueur2;
    }*/

    public Plateau clone() {
        return new Plateau(this);

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
	 * constructeur de la classe Plateau pour les jeux de test.
	 */
	public Plateau(Card carteJoeur1, Card carteJoueur2) {
		this.carteJoueur1 = carteJoeur1;
		this.carteJoueur2 = carteJoueur2;
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

    public void setCarteJoeurCouant(Card card) {
        if (joueurCourant.equals(joueur1)) {
            carteJoueur1 = card;
        } else
            carteJoueur2 = card;
    }

    /**
     * Renvoie le joueur courant.
     *
     * @return Le joueur courant.
     */
    public Player getJoueurCourant() {
        return joueurCourant;
    }

    public Player getJoueurNonCourant() {
        if (joueurCourant == joueur1) {
            return joueur2;
        } else {
            return joueur1;
        }
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

    public void jouerCarte(Card card) {
        if (joueurCourant.equals(joueur1)) {
            setCarteJoueur1(card);
        } else if (joueurCourant.equals(joueur2)) {
            setCarteJoueur2(card);
        }
    }

	/**
	 * verifier le joueur courant est le leader
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

	public void setCardAffiche(Card card) {
		carteAffichee = card;
	}

	public boolean estLeader2() {
		return getCardAdversaire() == null;
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
            if (joueurCourant.getName().equals(joueur2.getName())) { // si le joueur 1 est le leader
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

    public void jouerCarte2(Card card) {
        // jouer une carte quelconque de sa main

        if (getJoueurCourant().getHandScndPhase().contains(card)) {
            getJoueurCourant().getHandScndPhase().removeCard(card);
        }
        if (joueurCourant == joueur1) {
            setCarteJoueur1(card);
        } else if (joueurCourant == joueur2) {
            setCarteJoueur2(card);
        }
    }

    // use applay sndphaserule function
    public void attribuerCarteSecondPhase(Card winningCard, ReglesDeJeu r) {// on doit changer la fonction ApplyDwarveRule:c'est fait
        if (r.carteEgaux(carteJoueur1, carteJoueur2)) {
            // determiner le leader
            if (joueurCourant.getName() .equals( joueur2.getName())) { // si le joueur 1 est le leader
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

    public void setHand(Hand hand1,Hand hand2){
        joueur1.setHand(hand1);
        joueur2.setHand(hand2);

    }


    public GeneralPlayer joueurCourant() {
        return null;
    }

    public void setPioche(Cards pioche) {
        this.pioche = pioche;
    }
}