package nbradham.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Stack;

import nbradham.blackjack.Card.Rank;
import nbradham.blackjack.Card.Suit;

final class Game {

	static enum Action {
		Double_Down, Hit, Stand, Split, Surrender;
	}

	private final Stack<Card> deck = new Stack<>();
	private final ArrayList<PlacedCard> dealHand = new ArrayList<>(), playHand = new ArrayList<>();
	private final Player player;
	private byte bet;

	Game(final Player setPlayer) {
		(player = setPlayer).setGame(this);
		for (Suit s : Suit.values())
			for (Rank r : Rank.values())
				deck.push(new Card(s, r));
		Collections.shuffle(deck);
	}

	final void start() {
		while ((bet = player.getBet()) < 1)
			;
		dealPlayer();
		addPlaced(dealHand, new PlacedCard(deck.pop(), false));
		dealPlayer();
		dealCard(dealHand);
		HashSet<Action> acts = new HashSet<>();
		acts.add(Action.Double_Down);
		acts.add(Action.Hit);
		if (playHand.get(0).getCard().rank() == playHand.get(1).getCard().rank())
			acts.add(Action.Split);
		acts.add(Action.Stand);
		acts.add(Action.Surrender);
		Action act;
		while (!acts.contains(act = player.getAction(acts.toArray(new Action[acts.size()]))))
			;
		System.out.printf("Act: %s%n", act);
	}

	private final void dealPlayer() {
		dealCard(playHand);
	}

	private final void dealCard(ArrayList<PlacedCard> dest) {
		addPlaced(dest, new PlacedCard(deck.pop()));
	}

	private final void addPlaced(ArrayList<PlacedCard> dest, PlacedCard card) {
		dest.add(card);
	}

	final PlacedCard[] getDealerHand() {
		return dealHand.parallelStream().map(pc -> pc.isRevealed() ? pc : new PlacedCard(null, false))
				.toArray(l -> new PlacedCard[l]);
	}

	final PlacedCard[] getPlayerHand() {
		return playHand.toArray(new PlacedCard[playHand.size()]);
	}
}