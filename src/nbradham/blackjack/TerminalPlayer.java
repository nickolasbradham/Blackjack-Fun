package nbradham.blackjack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;

import nbradham.blackjack.Game.Action;

final class TerminalPlayer extends Player {

	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private Game game;

	@Override
	public final void setGame(final Game setGame) {
		game = setGame;
	}

	@Override
	public final int getBet() {
		int bet = 0;
		do {
			System.out.print("Enter bet: ");
			try {
				bet = Integer.parseInt(in.readLine());
				if (bet < 1 || bet > credits) {
					System.out.printf("Bet must be in range [1, %d].%n", credits);
					bet = 0;
				}
			} catch (NumberFormatException | IOException e) {
				System.out.println("Could not parse bet.");
			}
		} while (bet == 0);
		return bet;
	}

	@Override
	public Action getAction(final Action[] options) {
		printHands();
		final StringBuilder sb = new StringBuilder("Available actions: ");
		final HashSet<Action> acts = new HashSet<>();
		for (Action a : options) {
			acts.add(a);
			switch (a) {
			case Action.DOUBLE -> sb.append("(D)ouble Down, ");
			case Action.HIT -> sb.append("(H)it, ");
			case Action.SPLIT -> sb.append("(S)plit, ");
			case Action.STAND -> sb.append("S(t)and, ");
			case Action.SURRENDER -> sb.append("S(u)rrender, ");
			}
		}
		sb.setLength(sb.length() - 2);
		System.out.print(sb.append("\nSelect action: "));
		while (true)
			try {
				switch (in.readLine().toLowerCase()) {
				case "d":
					if (acts.contains(Action.DOUBLE))
						return Action.DOUBLE;
					break;
				case "h":
					if (acts.contains(Action.HIT))
						return Action.HIT;
					break;
				case "s":
					if (acts.contains(Action.SPLIT))
						return Action.SPLIT;
					break;
				case "t":
					if (acts.contains(Action.STAND))
						return Action.STAND;
					break;
				case "u":
					if (acts.contains(Action.SURRENDER))
						return Action.SURRENDER;
					break;
				}
				System.out.printf("%s", sb);
			} catch (final IOException e) {
				e.printStackTrace();
			}
	}

	@Override
	public void onEnd(final GameResult gameResult) {
		printHands();
		int pay = gameResult.payout();
		System.out.printf("Result: %s\tYou gain: %d", gameResult.state(), pay);
		credits += pay;
	}

	private final void printHands() {
		System.out.printf("Dealer's Hand: %s%nYour hand: %s%n", Arrays.toString(game.getDealerHand()),
				Arrays.toString(game.getPlayerHand()));
	}
}