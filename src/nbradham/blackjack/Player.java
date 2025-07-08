package nbradham.blackjack;

import nbradham.blackjack.Game.Action;

sealed interface Player permits TerminalPlayer {

	byte getBet();

	void setGame(Game game);

	Action getAction(Action[] options);
}