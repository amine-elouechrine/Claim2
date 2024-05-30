package org.example.Vue;

import org.example.Controleur.ControleurMediateur;
import org.example.Modele.Jeu;
import org.example.Patternes.Observateur;

import javax.swing.*;

public class ComposantJoueurCourant extends Box implements Observateur {

	JLabel jc;
	Jeu jeu;
	String nomJoueurCourant;
	CollecteurEvenements control;

	public ComposantJoueurCourant(int axis, Jeu jeu, CollecteurEvenements control) {
		super(axis);
		this.jeu = jeu;
		this.control = control;
		this.jeu.ajouteObservateur(this);
		nomJoueurCourant = control.getNomJoueurCourant();
		// nomJoueurCourant = jeu.getJoueurCourant().getName();

		// Affichage du joueur courant
		jc = new JLabel("Au tour du joueur : " + nomJoueurCourant + " | ");
		add(jc);
		add(Box.createGlue());
	}

	@Override
	public void miseAJour() {
		nomJoueurCourant = control.getNomJoueurCourant();
		// nomJoueurCourant = jeu.getJoueurCourant().getName();
		jc.setText("Au tour du joueur : " + nomJoueurCourant + " | ");
	}
}
