package nbradham.blackjack;

final record Card(Suit suit, Rank rank) {
	
	public static enum Suit {
		Clubs, Diamonds, Hearts, Spades
	}

	public static enum Rank {
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
	
	@Override
	public final String toString() {
		return String.format("%s of %s", rank, suit);
	}
};