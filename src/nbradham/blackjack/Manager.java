package nbradham.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

final class Manager {

	private static final ArrayList<Card> DECK = new ArrayList<>(52);
	static {
		for (Suit s : Suit.values())
			for (Rank r : Rank.values())
				DECK.add(new Card(s, r));
	}

	private static enum Rank {
		Ace(1), N2(2), N3(3), N4(4), N5(5), N6(6), N7(7), N8(8), N9(9), N10(10), Jack(10), Queen(10), King(10);

		private final int value;

		private Rank(int cardVal) {
			value = cardVal;
		}

		@Override
		public final String toString() {
			return this == Ace || ordinal() > N10.ordinal() ? super.toString() : String.valueOf(value);
		}
	}

	private static enum Suit {
		Clubs, Diamonds, Hearts, Spades
	}

	public static final void main(String[] args) {
		Stack<Card> shoe = new Stack<>();
		for (byte i = 0; i != 4; ++i) {
			Collections.shuffle(DECK);
			shoe.addAll(DECK);
		}
		System.out.println(shoe);
	}

	private static final record Card(Suit suit, Rank rank) {
	};
}