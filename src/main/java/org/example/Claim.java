package org.example;

import org.example.Controleur.ControleurMediateur;
import org.example.Modele.Jeu;
import org.example.Vue.CollecteurEvenements;
import org.example.Vue.InterfaceGraphique;

public class Claim {
    public static void main(String[] args) {
        Jeu jeu = new Jeu();
        CollecteurEvenements control = new ControleurMediateur(jeu);
        InterfaceGraphique.demarrer(jeu, control);
    }
}
