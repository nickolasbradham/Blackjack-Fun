package nbradham.blackjack;

import java.util.Collections;
import java.util.Stack;

final class Manager {

	private static final Card[] DECK = new Card[52];
	static {
		byte i = -1;
		for (Rank r : Rank.values())
			for (Suit s : Suit.values())
				DECK[++i] = new Card(s, r);
	}

	private static enum Rank {
		Ace(1), N2(2), N3(3), N4(4), N5(5), N6(6), N7(7), N8(8), N9(9), N10(10), Jack(10), Queen(10), King(10);

		private final int value;

		private Rank(int cardVal) {
			value = cardVal;
		}

		@Override
		public final String toString() {
			return ordinal() > N10.ordinal() || this == Ace ? super.toString() : String.valueOf(value);
		}
	}

	private static enum Suit {
		Clubs, Diamonds, Hearts, Spades
	}

	public static final void main(String[] args) {
		Stack<Card> shoe = new Stack<>();
		for (byte i = 0; i != 4; ++i)
			for (Card c : DECK)
				shoe.push(c);
		Collections.shuffle(shoe);
		System.out.println(shoe);
	}

	private static final record Card(Suit suit, Rank rank) {
	};
}