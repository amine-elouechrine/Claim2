

public class Player {
    String Name ;
    int score ;
    int scoreInCurrentGame;
    Hand hand;
    PileDeScore pileDeScore;

    Player(String Name, Hand hand) {
        this.Name = Name;
        score = 0;
        scoreInCurrentGame = 0;
        this.hand = hand;
    }

    String getName(){
        return Name;
    }

    int  getScore(){
        return score;
    }

    int getScoreInCurrentGame(){
        return scoreInCurrentGame;
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

}
