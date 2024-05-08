import java.util.ArrayList;
import java.util.List;

public class Defausse {
    private List<Card> cartes;

    public Defausse() {
        this.cartes = new ArrayList<>();
    }

    public void ajouterCarte(Card carte) {
        cartes.add(carte);
    }

    public List<Card> getCartes() {
        return cartes;
    }

    public boolean estVide() {
        return cartes.isEmpty();
    }

    public void vider() {
        cartes.clear();
    }
}
