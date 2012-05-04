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
		this.gamePanel = panel;
		this.currentMove = new Move();
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
		this.gamePanel.recalculatePositions(this);
	}

	@Override
	/**
	 * 
	 */
	public String getLocation() {
		return this.location;
	}

	@Override
	public Card pickCard() {
		Card card = this.gamePanel.getCardBank().pickRandomCard();
		card.activate();
		this.addCard(card);
		return card;
	}

	@Override
	void addCard(Card card) {
		this.cards.add(card);
		this.gamePanel.recalculatePositions(this);
	}

}