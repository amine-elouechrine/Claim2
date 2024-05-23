package org.example.IA;

import org.example.Modele.Card;
import org.example.Modele.Plateau;
import java.util.ArrayList;
import java.util.List;
import java.io.PrintWriter;

public class Node {

    private List<Node> enfants;
    public int score;
    //public boolean IsIaTurn;
    public Plateau plateau;


    public Node() {
        enfants=new ArrayList<>();
    }

    public Node (Plateau plateau){
        this.plateau=plateau;
        enfants=new ArrayList<>();
    }

    // Constructeur de copie
    public Node(Node other) {
        this.score = other.score;
        this.plateau = other.plateau != null ? new Plateau(other.plateau) : null;
        this.enfants = new ArrayList<>();
        for (Node enfant : other.enfants) {
            this.enfants.add(new Node(enfant));
        }
    }

    public Node clone() {
        return new Node(this);
    }

    // l'ia doit etre toujours le joueur1 et l'adversaire le joueur2
    public Card getCarteAI() {
        return plateau.getCarteJoueur1();
    }



    // Méthode pour rechercher l'enfant avec une certaine évaluation
    public Node getEnfantAvecEvaluation(int eval) {
        for (Node enfant : enfants) {
            if (enfant.getScore() == eval) {
                return enfant;
            }
        }
        return null; // Retourne null si aucun enfant avec cette évaluation n'est trouvé
    }

    public void setEnfants(List<Node> enfants) {
        this.enfants = enfants;
    }

    //ajouter un enfant
    public void addEnfant(Node enfant){
        enfants.add(enfant);
    }

    public List<Node> getEnfants(){
        return enfants;
    }

    public Plateau getPlateau(){
        return this.plateau ;
    }


    /*public void afficherArbre(String prefix) {
        System.out.println(prefix + (IsIaTurn ? "IA" : "Adversaire") + " - Score: " + score );
        for (Node enfant : enfants) {
            enfant.afficherArbre(prefix + "  ");
        }
    }*/

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score=score;
    }


    public void afficherArbreProfondeur(PrintWriter writer, String prefix, int profondeur) {
        if (profondeur < 0) {
            return; // Si la profondeur est négative, on ne fait rien
        }
        writer.println("--------------------------------------------");
        writer.println(prefix +plateau.getJoueurCourant().getName());
        writer.println(prefix +plateau.getJoueur1().getName());
        writer.println(prefix +plateau.getCarteJoueur1());
        writer.println(prefix +plateau.getJoueur2().getName());
        writer.println(prefix +plateau.getCarteJoueur2());
        if (profondeur > 0) {
            for (Node enfant : enfants) {
                enfant.afficherArbreProfondeur(writer, prefix + "       ", profondeur - 1);
            }
        }
    }
}

