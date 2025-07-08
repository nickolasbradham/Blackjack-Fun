package nbradham.blackjack;

final class PlacedCard {

	private final Card card;
	boolean revealed;

	PlacedCard(Card setCard) {
		this(setCard, true);
	}

	PlacedCard(Card setCard, boolean setRevealed) {
		card = setCard;
		revealed = setRevealed;
	}

	@Override
	public final String toString() {
		return revealed ? card.toString() : "Secret";
	}

	final Card getCard() {
		return card;
	}
}