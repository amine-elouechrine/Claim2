package org.example.Modele;

public abstract class GeneralPlayer {
    public String Name ;
    public int score ;
    public Hand hand;
    public Hand handScndPhase;
    public PileDeScore pileDeScore;

    public GeneralPlayer (String name){
        this.score = 0 ;
        this.Name = name; // initialiser le nom du joueur
        this.hand = new Hand(); // initialiser de hand vide
        this.pileDeScore = new PileDeScore(); // initialiser la pile de score vide
        this.handScndPhase = new Hand(); // initialiser la main de la seconde phase vide
    }
    
    public Hand getHand() {
        return hand;
    }

    public void setHand(Hand main){
        this.hand = main ;
    }

    public Hand getHandScndPhase() {
        return handScndPhase;
    }

    /**
     * Renvoie le nom du joueur.
     * @return Le nom du joueur.
     */
    public String getName() {
        return Name;
    }

    public int getScore() {
        return score;
    }

    /**
     * Renvoie la pile de score du joueur.
     * @return La pile de score du joueur.
     */
    public PileDeScore getPileDeScore() {
        return pileDeScore;
    }

    /**
     * Ajoute une carte à la pile de score du joueur.
     * @param carte
     */
    public void addPileDeScore(Card carte){
        pileDeScore.addCard(carte);
    }

    /**
     * verifie si la main du joueur est vide selon la phase
     * si on est dans la 1er phase est verifie hand sinon handScndPhase
     * @param phase
     * @return true si la main est vide, false sinon
     */
    public boolean isHandEmpty(boolean phase){
        if(phase == true ){
            return hand.isEmpty();
        }else{
            return handScndPhase.isEmpty();
        }
    }

    /**
     * incremente le score du joueur
     * @param points
     */
    public void updateScore(int points) {
        score += points;
    }

    /**
     * Joue une carte de la main du joueur et la retire de sa main.
     * @param indexCard carte à jouer.
     * @return La carte jouée, ou null si la carte n'est pas dans la main du joueur.
     */
    abstract public Card jouerCarte(int indexCard);


}
