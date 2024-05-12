package org.example.Modele;

//import javax.smartcardio.Card;
import java.util.List;

public class Plateau {  
    Card carteAffichee;
    Card carteJoueur1;
    Card carteJoueur2;
    Cards pioche;
    Defausse defausse;
    Player joueur1;
    Player joueur2;

    Plateau(Player joueur1, Player joueur2, Cards pioche,boolean ia) {
        this.joueur1 = joueur1;
        if(!ia) {
            this.joueur2 = joueur2;
        }
        this.pioche = pioche;
        this.defausse = new Defausse();
    }

    public Card getCarteAffichee() {
        return carteAffichee;
    }

    public Card getCarteJoueur1() {
        return carteJoueur1;
    }

    public Card getCarteJoueur2() {
        return carteJoueur2;
    }

    public Player getJoueur1() {
        return joueur1;
    }

    public Player getJoueur2() {
        return joueur2;
    }

    public void addToDefausse(Card card) {
        defausse.ajouterCarte(card);
    }

    public List<Card> getDefausse() {
        return defausse.getCartes();
    }

    //clearDefausse
    //isDefausseEmpty
    //shufflePioche
}