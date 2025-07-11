package nbradham.blackjack;

final class Manager {

	public static final void main(final String[] args) {
		final Player player = new TerminalPlayer();
		player.onEnd(new Game(player).start());
	}
}