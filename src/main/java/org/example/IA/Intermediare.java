package org.example.IA;

import org.example.Modele.*;

import java.util.ArrayList;
import java.util.List;


public class Intermediare extends IA {
/*public class Intermediare {
    Player joueur1;
    Player IA;

    public Intermediare(Player j1, Player ia) {
        joueur1 = j1;
        IA = ia;
        Cards pioche = new Cards();
        pioche.shuffle();
        IA.setHand(pioche.getHandOf13Cards());
        j1.setHand(pioche.getHandOf13Cards());
    }

    public void jouer_coup_phase1(Hand mainIA, boolean suivre_faction, Card carte_adversaire) {
        if (suivre_faction) {
            //jouer la carte la plus petite plus grande que celle de l'adversaire
            Hand carteJouable;
            carteJouable = mainIA.getCardsOfSameFaction(carte_adversaire);

            if (carteJouable.isEmpty()) {
                //Regarder si la carte jouer est un Gobelin
                if (carte_adversaire.getFaction().equals("Goblins") && mainIA.containsKnight()) {
                    //verifier si on a pas de gobelin avant de jouer le chevalier
                    List<Card> Chevalier = new ArrayList<>();
                    for (Card c : mainIA.getAllCards()) {
                        if (c.getFaction().equals("Chevalier")) {
                            Chevalier.add(c);
                        }
                    }
                    Card Chevaliermin = Chevalier.get(0);
                    for (Card c : Chevalier) {
                        if (c.getValeur() < Chevaliermin.getValeur()) {
                            Chevaliermin = c;
                        }
                        System.out.println("1");
                        //JOUER LA CARTE IL MANQUE LA FONCTION JOUER CARTE
                        //il faut jouer le Chevaliermin
                    }
                } else {
                    //Regarder si on a des Doppelgängers
                    for (Card c : mainIA.getAllCards()) {
                        if (c.getFaction().equals("Doppelganger")) {
                            carteJouable.addCard(c);
                        }
                    }
                    if (!carteJouable.isEmpty()) {
                        //jouer la carte la plus petite des dopplegangers
                        Card min = carteJouable.getLowestValueCard();
                        //jouer min
                        System.out.println("jouer min ");
                    } else {
                        //Cas ou on pourrais jamais gagner la faction
                        //jouer mainIA.min_valeur() il manque la fonction jouer carte
                        System.out.println("3");
                    }
                }


            } else {
                //Cards Main_backup = carteJouable;
                Card reponse = carteJouable.getSmallestHigherCard(carte_adversaire);
                if (reponse.getValeur() > carte_adversaire.getValeur()) {
                    //gagner la faction

                    System.out.println("jouer la carte " + reponse.getValeur() + " de la faction " + reponse.getFaction() + " pour gagner la faction)");
                } else {
                    //jouer la carte la plus petite parcequ'on peux pas gagner la faction

                    System.out.println(carteJouable.getLowestValueCard().getFaction() + "-" + carteJouable.getLowestValueCard().getValeur());

                }
            }
        } else {
            //je commence le tour
            //A modifier: je joue la carte la plus null
            //jouer  mainIA.min_valeur() il manque la fonction jouer carte
            System.out.println("La carte jouer est :" + mainIA.getLowestValueCard().getValeur() + " " + mainIA.getLowestValueCard().getFaction());
        }
    }

    public void jouer_coup_phase2(Hand mainIA, boolean suivre_faction, Card carte_adversaire) {
        if (suivre_faction) {
            //l'adversaire a jouer une carte je dois jouer une carte de la meme faction
            //si la faction de la carte adversaire est un nain
            if (carte_adversaire.getFaction().equals("Nains")) {
                //je dois jouer le plus petit nains possible pour gagner la faction
                Cards carteJouable = new Cards();
                for (Card c : mainIA.getAllCards()) {
                    if (c.getFaction().equals("Nains")) {
                        carteJouable.setCard(c);
                    }
                }
                if (carteJouable.min_valeur().getValeur() > carte_adversaire.getValeur()) {
                    //jouer la carte la plus grande
                    System.out.println(carteJouable.max_valeur());
                } else {
                    //jouer la carte la plus petite mais plus grande que le nain de l'adversaire
                    System.out.println( carteJouable.getHighestCardSmallerThan(carte_adversaire));
                }
            } else {
                //suivre la faction de l'adversaire
                Hand cartesJouables = mainIA.getCardsOfSameFaction(carte_adversaire);
                if (cartesJouables.isEmpty()) {
                    //si on a pas de carte de la meme faction
                    //jouer la carte la plus petite
                    System.out.println(cartesJouables.getLowestValueCard());
                } else {
                    //jouer la carte la plus petite plus grande que celle de l'adversaire de la meme faction
                    System.out.println(cartesJouables.getLargestSmallerCard(carte_adversaire));
                }
            }
        } else {
            //je commence le tour
            //A modifier: je joue la carte la plus null
            System.out.println(mainIA.getLowestValueCard());
        }
    }

    public static void main(String[] args) {
        Cards pioche = new Cards();
        pioche.shuffle();
        Hand hand1 = pioche.getHandOf13Cards();
        Hand handIA = pioche.getHandOf13Cards();
        Intermediare i = new Intermediare(new Player("Joueur1", hand1), new Player("IA", handIA));
        handIA.printHand();
        Card cartejouer = hand1.getRandomCard();
        System.out.println("La carte jouer par le joueur 1 est : "+cartejouer.getFaction()+" "+cartejouer.getValeur());
        i.jouer_coup_phase2(handIA, false, cartejouer);
    }

}*/


	public Intermediare() {
		super("IA Intermediare");
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


	@Override
	public Card jouerCarte(int indexCard) {
		return null;
	}
}