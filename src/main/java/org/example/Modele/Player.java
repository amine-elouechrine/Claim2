package org.example.Modele;

public class Player {
    String Name ;
    int score ;
    Hand hand;
    Hand handScndPhase;
    PileDeScore pileDeScore;

    public Player(String Name) {
        this.Name = Name; // initialiser le nom du joueur
        score = 0; // initialiser le score a 0
        this.hand = new Hand(); // initialiser de hand vide
        this.pileDeScore = new PileDeScore(); // initialiser la pile de score vide
        this.handScndPhase = new Hand(); // initialiser la main de la seconde phase vide
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    /**
     * Ajoute une carte à la pile de score du joueur.
     * @param carte
     */
    public void addPileDeScore(Card carte){
        pileDeScore.addCard(carte);
    }

    /**
     * Renvoie le nom du joueur.
     * @return Le nom du joueur.
     */
    public String getName(){
        return Name;
    }
    public void setName(String name){
        Name = name;
    }

    /**
     * Renvoie la pile de score du joueur.
     * @return La pile de score du joueur.
     */
    public PileDeScore getPileDeScore() {
        return pileDeScore;
    }
    public void setPileDeScore(PileDeScore pileDeScore) {
        this.pileDeScore = pileDeScore;
    }

    public Hand getHand() {
        return hand;
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

    public int  getScore(){
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    Hand getHandScndPhase() {
        return handScndPhase;
    }
    /*Card getCardFromHand(){
        return hand.get(hand.size()-1);
    }*/
    // passer en paramettre la carte a retirer
    public void setHandScndPhase(Hand handScndPhase) {
        this.handScndPhase = handScndPhase;
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
    public Card jouerCarte(int indexCard) {
        Card carte = hand.getCard(indexCard);
        // Vérifie si la carte est présente dans la main du joueur
        if (hand.contains(carte)) {
            // Retire la carte de la main du joueur
            hand.removeCard(carte);
            return carte;
        } else {
            System.out.println("La carte n'est pas dans la main du joueur.");
            // Si la carte n'est pas dans la main du joueur, retourne null
            return null;
        }
    }

}
