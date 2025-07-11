package nbradham.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Stack;

import nbradham.blackjack.Card.Rank;
import nbradham.blackjack.Card.Suit;
import nbradham.blackjack.GameResult.State;

final class Game {

	private static final byte TARGET = 21;

	static enum Action {
		DOUBLE, HIT, STAND, SPLIT, SURRENDER;
	}

	private final Stack<Card> deck = new Stack<>();
	private final ArrayList<ArrayList<PlacedCard>> playerHands = new ArrayList<>();
	private final ArrayList<PlacedCard> dealHand = new ArrayList<>();
	private final Player player;
	private final HashSet<Action> validActs = new HashSet<>();
	private int bet;

	Game(final Player setPlayer) {
		(player = setPlayer).setGame(this);
		for (Suit s : Suit.values())
			for (Rank r : Rank.values())
				deck.push(new Card(s, r));
		Collections.shuffle(deck);
	}

	final GameResult start() {
		while ((bet = player.getBet()) < 1 || bet > player.credits)
			;
		player.credits -= bet;
		final ArrayList<PlacedCard> firstHand = new ArrayList<>();
		playerHands.add(firstHand);
		dealCard(firstHand);
		addPlaced(dealHand, new PlacedCard(deck.pop(), false));
		dealCard(firstHand);
		dealDeal();
		if (getTotal(firstHand) == TARGET) {
			revealDeal();
			return getDealHandTot() == TARGET ? new GameResult(State.TIE, bet)
					: new GameResult(State.BLACKJACK, bet * 3 / 2);
		} else {
			boolean canDouble = player.credits >= bet;
			if (canDouble)
				validActs.add(Action.DOUBLE);
			validActs.add(Action.HIT);
			if (firstHand.get(0).getCard().rank() == firstHand.get(1).getCard().rank() && canDouble)
				validActs.add(Action.SPLIT);
			validActs.add(Action.STAND);
			validActs.add(Action.SURRENDER);
			Action act = getValidPlayerAct();
			validActs.remove(Action.DOUBLE);
			validActs.remove(Action.SPLIT);
			validActs.remove(Action.SURRENDER);
			for (byte i = 0; i < playerHands.size(); ++i) {
				final ArrayList<PlacedCard> hand = playerHands.get(i);
				switch (act) {
				case DOUBLE:
					doubleBet();
					dealCard(hand);
					return dealerTurn(hand);
				case HIT:
					dealCard(hand);
					if (getTotal(hand) >= TARGET)
						return dealerTurn(hand);
					act = getValidPlayerAct();
					break;
				case SPLIT:
					doubleBet();
					final ArrayList<PlacedCard> secondHand = new ArrayList<>();
					playerHands.add(secondHand);
					secondHand.add(firstHand.remove(1));
					// TODO Implement.
					break;
				case STAND:
					return dealerTurn(hand);
				case SURRENDER:
					return new GameResult(State.SURRENDER, bet / 2);
				}
			}
			return null;
		}
	}

	final PlacedCard[] getDealerHand() {
		return dealHand.parallelStream().map(pc -> pc.revealed ? pc : new PlacedCard(null, false))
				.toArray(l -> new PlacedCard[l]);
	}

	final PlacedCard[][] getPlayerHand() {
		final PlacedCard[][] hands = new PlacedCard[playerHands.size()][];
		byte i = -1;
		for (ArrayList<PlacedCard> h : playerHands)
			hands[++i] = h.toArray(new PlacedCard[h.size()]);
		return hands;
	}

	private final void dealDeal() {
		dealCard(dealHand);
	}

	private final void dealCard(ArrayList<PlacedCard> dest) {
		addPlaced(dest, new PlacedCard(deck.pop()));
	}

	private final void addPlaced(ArrayList<PlacedCard> dest, PlacedCard card) {
		dest.add(card);
	}

	private final byte getDealHandTot() {
		return getTotal(dealHand);
	}

	private final Action getValidPlayerAct() {
		final Action[] availableActs = validActs.toArray(new Action[validActs.size()]);
		Action act;
		while (!validActs.contains(act = player.getAction(availableActs)))
			;
		return act;
	}

	private final GameResult dealerTurn(ArrayList<PlacedCard> hand) {
		byte playTot = getTotal(hand);
		if (playTot > TARGET)
			return new GameResult(State.BUST, 0);
		revealDeal();
		byte dealTot;
		while ((dealTot = getDealHandTot()) < 16 || dealTot < playTot)
			dealDeal();
		return dealTot > TARGET ? new GameResult(State.WIN, bet * 2)
				: dealTot > playTot ? new GameResult(State.LOSE, 0) : new GameResult(State.TIE, bet);
	}

	private final void revealDeal() {
		dealHand.get(0).revealed = true;
	}

	private final void doubleBet() {
		player.credits -= bet;
		bet *= 2;
	}

	private static final byte getTotal(ArrayList<PlacedCard> hand) {
		byte tot = 0, playAces = 0;
		for (PlacedCard pc : hand) {
			final Rank r = pc.getCard().rank();
			tot += r.value;
			if (r == Rank.ACE)
				++playAces;
		}
		while (--playAces != -1 && (tot -= 10) > 21)
			;
		return tot;
	}
}