package nbradham.blackjack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;

import nbradham.blackjack.Game.Action;

final class TerminalPlayer implements Player {

	private static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

	private Game game;

	@Override
	public final void setGame(Game setGame) {
		game = setGame;
	}

	@Override
	public final byte getBet() {
		byte bet = 0;
		do {
			System.out.print("Enter bet: ");
			try {
				bet = Byte.parseByte(in.readLine());
				if (bet < 1)
					System.out.printf("Bet must be in range [1, %d].%n", Byte.MAX_VALUE);
			} catch (NumberFormatException | IOException e) {
				System.out.println("Could not parse bet.");
			}
		} while (bet == 0);
		return bet;
	}

	@Override
	public Action getAction(Action[] options) {
		StringBuilder sb = new StringBuilder("Available actions: ");
		HashSet<Action> acts = new HashSet<>();
		for (Action a : options) {
			acts.add(a);
			switch (a) {
			case Action.Double_Down -> sb.append("(D)ouble Down, ");
			case Action.Hit -> sb.append("(H)it, ");
			case Action.Split -> sb.append("(S)plit, ");
			case Action.Stand -> sb.append("S(t)and, ");
			case Action.Surrender -> sb.append("S(u)rrender, ");
			}
		}
		sb.setLength(sb.length() - 2);
		System.out.printf("Dealer: %s%nPlayer: %s%n%s", Arrays.toString(game.getDealerHand()),
				Arrays.toString(game.getPlayerHand()), sb.append("\nSelect action: "));
		while (true)
			try {
				switch (in.readLine().toLowerCase()) {
				case "d":
					if (acts.contains(Action.Double_Down))
						return Action.Double_Down;
					break;
				case "h":
					if (acts.contains(Action.Hit))
						return Action.Hit;
					break;
				case "s":
					if (acts.contains(Action.Split))
						return Action.Split;
					break;
				case "t":
					if (acts.contains(Action.Stand))
						return Action.Stand;
					break;
				case "u":
					if (acts.contains(Action.Surrender))
						return Action.Surrender;
					break;
				}
				System.out.printf("%s", sb);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
}