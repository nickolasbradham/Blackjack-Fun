package nbradham.blackjack;

final class Manager {

	public static final void main(final String[] args) {
		new Game(new TerminalPlayer()).start();
	}
}