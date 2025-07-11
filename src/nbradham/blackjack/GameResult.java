package nbradham.blackjack;

record GameResult(State state, int payout) {
	static enum State {
		BLACKJACK, BUST, LOSE, SURRENDER, TIE, WIN
	};
}