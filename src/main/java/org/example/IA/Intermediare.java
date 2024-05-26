package org.example.IA;

import org.example.Modele.*;

import java.util.ArrayList;
import java.util.List;

public class Intermediare extends IA {

	public Intermediare() {
		super("Intermediare");
	}

	public Card jouerCoupPhase1(Plateau plateau) {
		Card cardToPlay;

		if (!plateau.estLeader2()) {//Si l'IA n'est pas le leader elle doit suivra la faction
			cardToPlay = jouerAvecSuiviFaction(plateau);
		} else {
			cardToPlay = getHighestValueCard(plateau);
		}
		return cardToPlay;
	}

	public Card getHighestValueCard(Plateau plateau) {
		List<Card> cards = plateau.getJoueurCourant().getHand().getAllCards();
		Card highestCard = cards.get(0);
		for (Card card : cards) {
			if (card.getValeur() > highestCard.getValeur()) {
				highestCard = card;
			}
		}
		return highestCard;
	}

	public Hand getCardsOfSameFaction(Plateau plateau, String faction) {
		if (plateau.getJoueurCourant().getHand() == null || plateau.getJoueurCourant().getHand().isEmpty()) {
			return null;
		}

		Hand cardsOfSameFaction = new Hand();

		for (Card handCard : plateau.getJoueurCourant().getHand().getAllCards()) {
			if (handCard.getFaction().equals(faction)) {
				cardsOfSameFaction.addCard(handCard);
			}
		}

		return cardsOfSameFaction;
	}

	public Card jouerAvecSuiviFaction(Plateau plateau) {
		// Retrieve the list of playable cards of the same faction
		Hand cartesJouables = getCardsOfSameFaction(plateau, plateau.getCardAdversaire().getFaction());

		// Ensure cartesJouables is not null
		if (cartesJouables == null) {
			cartesJouables = new Hand();
		}

		// Check if the list of cards of the same faction is empty
		if (cartesJouables.isEmpty()) {
			// Play without cards of the same faction
			return jouerSansCarteDeMemeFaction(plateau.getJoueurCourant().getHand(), plateau.getCardAdversaire(), plateau);
		} else {
			// Play with cards of the same faction
			return jouerAvecCarteDeMemeFaction(cartesJouables.getAllCards(), plateau.getCardAdversaire());
		}
	}


	private Card jouerAvecCarteDeMemeFaction(List<Card> cartesJouables, Card carte_adversaire) {
		Card reponse = getSmallestHigherCard(carte_adversaire, cartesJouables);
		if (reponse != null && reponse.getValeur() > carte_adversaire.getValeur()) {
			return reponse;
		} else {
			return getLowestValueCard(cartesJouables);
		}
	}

	private Card jouerSansCarteDeMemeFaction(Hand mainIA, Card carte_adversaire, Plateau plateau) {
		if (carte_adversaire.getFaction().equals("Goblins") && containsKnight()) {
			return jouerChevalier(plateau);
		} else {
			return jouerDoppelgangerOuMin(plateau);
		}
	}

	private Card jouerDoppelgangerOuMin(Plateau plateau) {
		Hand doppelgangers = getCardsOfSameFaction(plateau, "Doppelganger");
		if (doppelgangers.isEmpty()) {
			return getLowestValueCard(plateau.getJoueurCourant().getHand().getAllCards());
		} else {//si l'IA a des doppelgangers
			return getSmallestHigherCard(plateau.getCardAdversaire(), doppelgangers.getAllCards());
		}
	}


	public Card jouerChevalier(Plateau plateau) {
		Hand chevaliers = getCardsOfSameFaction(plateau, "Chevalier");
		return chevaliers.getMin();
	}


	public Card jouerCoupPhase2(Plateau plateau) {
		Card cardToPlay;
		if (!plateau.estLeader2()) {
			cardToPlay = jouerAvecSuiviFactionPhase2(plateau);
		} else {
			cardToPlay = getHighestValueCard(plateau.getJoueurCourant().getHand().getAllCards());
		}
		//System.out.println("---------------Carte jouée par l'IA : " + cardToPlay.getFaction() + " " + cardToPlay.getValeur());
		return cardToPlay;
	}

	private Card jouerAvecSuiviFactionPhase2(Plateau plateau) {
		Card cardToPlay;
		if (plateau.getCardAdversaire().getFaction().equals("Nains")) {
			cardToPlay = jouerAvecNains(plateau);
		} else {
			cardToPlay = suivreFactionAdversaire(plateau);
		}
		return cardToPlay;
	}
	public Card getSmallestHigherCard(Hand hand,Card card) {

		if (hand == null || hand.isEmpty()) {
			throw new IllegalStateException("La main est vide ou nulle.");
		}

		Card smallestHigherCard = hand.getCard(0);
		int smallestHigherValue = Integer.MAX_VALUE;

		for (int i=0;i<hand.size();i++) {
			// Vérifier si la carte est plus grande que celle passée en paramètre
			if (hand.getCard(i).getValeur() > card.getValeur() && hand.getCard(i).getValeur() < smallestHigherValue) {
				smallestHigherCard = hand.getCard(i);
				smallestHigherValue = hand.getCard(i).getValeur();
			}
		}

		return smallestHigherCard;
	}


	private Card suivreFactionAdversaire(Plateau plateau) {
		Hand cartesJouables = getCardsOfSameFaction(plateau);

		if (cartesJouables.isEmpty()) {
			return getLowestValueCard((plateau.getJoueurCourant().getHand().getAllCards()));
		} else {
			return getSmallestHigherCard(cartesJouables, plateau.getCardAdversaire());
		}
	}


	private Card jouerAvecNains(Plateau plateau) {
		List<Card> carteJouable = getCardsOfSameFaction2(plateau.getJoueurCourant().getHand(), plateau.getCardAdversaire().getFaction()).getAllCards();

		if (getLowestValueCard(carteJouable).getValeur() > plateau.getCardAdversaire().getValeur()) {
			return getHighestValueCard(carteJouable);
		} else {
			return getHighestCardSmallerThan(plateau.getCardAdversaire(), carteJouable);
		}
	}

}