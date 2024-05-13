package org.example.Modele;

import java.util.Stack;

public class GestionAnnuleRefaire {
    Plateau plateau;
    Stack<Plateau> annule;
    Stack<Plateau> refaire;

    // Constructeur par défaut de la classe GestionAnnuleRefaire 
    /**
     * Constructeur de la classe GestionAnnuleRefaire qui initialise les piles annule et refaire et commence un nouveau jeu
     * @param plateau le plateau de jeu
     */
    public GestionAnnuleRefaire(Plateau plateau) {
        this.plateau = plateau;
        annule = new Stack<>();
        refaire = new Stack<>();
    }

    /**
     * Constructeur de la classe GestionAnnuleRefaire 
     * @param fichier le fichier qui contient un jeu en cours sauvegardé
     * @throws Exception si le fichier n'existe pas
     */
    public GestionAnnuleRefaire(String fichier) throws Exception {
        annule = new Stack<>();
        refaire = new Stack<>();
    }

    /**
     * Méthode pour vérifier si on peut annuler
     * @return true si on peut annuler, false sinon
     */
    public boolean peutAnnuler() {
        return !annule.isEmpty();
    }

    /**
     * Méthode pour vérifier si on peut refaire
     * @return true si on peut refaire, false sinon
     */
    public boolean peutRefaire() {
        return !refaire.isEmpty();
    }

    /**
     * Méthode pour vider la pile refaire
     */
    public void clearStack() {
        while (!refaire.empty()) {
            refaire.pop();
        }
    }

    // il faut remetre la carte de chaque joueur dans son main
    // remettre la carte afficher dans la pioche 
    public void annuler() {
        if (peutAnnuler()) {
            refaire.push(plateau);
            plateau = annule.pop();
        }
    }

    // il faut enlever la carte afficher de la pioche
    // enlever la carte jouer de la main de chaque joueur 
    public void refaire() {
        if (peutRefaire()) {
            annule.push(plateau);
            plateau = refaire.pop();
        }
    }

    // Ajoute un plateau à la pile annule
    public void addToHistory(Plateau plateau) {
        annule.push(plateau);
        clearStack();
    }


    public void saveHand(){

    }
    public void restoreHand(){

    }
    /* pour sauve restaure il faut stocker le plateau actuell les piles de refaire & annule mais il faut aussi stocker la main de chaque joueur  */
    public void sauve(String fileName){
        try {
            System.out.println("le sauvegarde dans le fichier " + fileName + " est en cours");
        } catch (Exception e) {
            System.out.println("Erreur lors de la sauvegarde");
        }
    }

    // reccupere le plateau actuel les piles de refaire & annule mais il faut aussi reccupere la main de chaque joueur
    public void restaure(String fileName){
        try {
            System.out.println("le restauration du fichier " + fileName + " est en cours");
        } catch (Exception e) {
            System.out.println("Erreur lors de la restauration");
        }
    }
    
}
