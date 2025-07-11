package nbradham.blackjack;

import nbradham.blackjack.Game.Action;

abstract class Player {

	int credits = 10;

	abstract int getBet();

	abstract void setGame(Game game);

	abstract Action getAction(Action[] options);

	abstract void onEnd(GameResult gameResult);
}