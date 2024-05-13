package org.example.Modele;

public class Player {
    String Name ;
    int score ;
    int scoreInCurrentGame;
    Hand hand;
    Hand handScndPhase;
    PileDeScore pileDeScore;

    public Player(String Name, Hand hand) {
        this.Name = Name;
        score = 0;
        scoreInCurrentGame = 0;
        this.hand = hand;
        this.pileDeScore = new PileDeScore();
        this.handScndPhase = new Hand();
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    String getName(){
        return Name;
    }

    public PileDeScore getPileDeScore() {
        return pileDeScore;
    }

    public Hand getHand() {
        return hand;
    }

    int  getScore(){
        return score;
    }

    int getScoreInCurrentGame(){
        return scoreInCurrentGame;
    }
    Hand getHandScndPhase() {
        return handScndPhase;
    }
    /*Card getCardFromHand(){
        return hand.get(hand.size()-1);
    }*/
    // passer en paramettre la carte a retirer

    public void updateScore(int points) {
        score += points;
    }

    public void updateScoreInCurrentGame(int points) {
        scoreInCurrentGame += points;
    }

    /**
     * Joue une carte de la main du joueur et la retire de sa main.
     * @param carte carte à jouer.
     * @return La carte jouée, ou null si la carte n'est pas dans la main du joueur.
     */
    public Card jouerCarte(Card carte , Hand main) {
        // Vérifie si la carte est présente dans la main du joueur
        if (main.contains(carte)) {
            // Retire la carte de la main du joueur
            main.removeCard(carte);
            return carte;
        } else {
            System.out.println("La carte n'est pas dans la main du joueur.");
            // Si la carte n'est pas dans la main du joueur, retourne null
            return null;
        }
    }
}
