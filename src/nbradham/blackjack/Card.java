package nbradham.blackjack;

final record Card(Suit suit, Rank rank) {

	public static enum Suit {
		CLUBS, DIAMONDS, HEARTS, SPADES
	}

	public static enum Rank {
		ACE(11), N2(2), N3(3), N4(4), N5(5), N6(6), N7(7), N8(8), N9(9), N10(10), JACK(10), QUEEN(10), KING(10);

		final int value;

		private Rank(int cardVal) {
			value = cardVal;
		}

		@Override
		public final String toString() {
			return ordinal() > N10.ordinal() || this == ACE ? super.toString() : String.valueOf(value);
		}
	}

	@Override
	public final String toString() {
		return String.format("%s%c", rank != Rank.N10 ? rank.toString().charAt(0) : "10", suit.toString().charAt(0));
	}
};