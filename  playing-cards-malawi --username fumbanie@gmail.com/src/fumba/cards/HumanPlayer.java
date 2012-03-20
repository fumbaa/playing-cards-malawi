package fumba.cards;

import android.graphics.Point;

public class HumanPlayer extends Player {

	/**
	 * Constructs human player
	 * 
	 * @param name
	 *            Name of the player
	 */
	public HumanPlayer(String name, GamePanel panel) {
		this.setName(name);
		this.gamePanel= panel;
		this.currentMove = new Move(); // defaults to false, false for validity
										// and continuity
	}

	/**
	 * Check to see if this class is human
	 */
	public Boolean isHuman() {
		return true;
	}

	/** Add the selected card to the played cards list **/
	public void makeMove(Move move) {

		Card card = move.getCard();
		Point point = this.gamePanel.getTopCard().getPosition();
		card.setCurrentPosition(point);
		this.gamePanel.getTopCard().deactivate();
		this.currentMove = move;

		this.gamePanel.setTopCard(card);
		this.cards.remove(card);
		this.recalculatePositions();
	}

}