package fumba.cards;

/**
 * The <code>CPUPlayer</code> class represents an artificial intelligence
 * player.
 * 
 * <p>
 * <i>Copyright (c) 1998, 2011 Oracle. All rights reserved. This program and the
 * accompanying materials are made available under t he terms of the Eclipse
 * Public License v1.0 and Eclipse Distribution License v. 1.0 which accompanies
 * this distribution.</i>
 * </p>
 * 
 * @author Fumbani Chibaka
 * @version 1.0, 10/28/2011
 * @see <a href="http:chibaka.com">Fumba Game Lab</a>
 **/

public class CPUPlayer extends Player {

	/** Constructor **/
	public CPUPlayer(String playerName, GamePanel gamePanel) {
		this.setName(playerName);
		this.gamePanel = gamePanel;
		this.currentMove = null;
	}

	/**
	 * Calculate best move
	 */
	public void calculateMove() {
		for (Card card : this.cards) {
			Move checkedMove = Rules.checkMove(card);
			// TODO CPU Plays first valid move available
			if (checkedMove.isValid())
				this.currentMove = checkedMove;
		}

		// No move found
		if (this.currentMove == null)
			this.currentMove = new Move();
	}

	/**
	 * Player makes move
	 */
	public void makeMove(Move move) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the screen placement for the cards
	 */
	@Override
	public String getLocation() {
		return this.location;
	}

	@Override
	public Card pickCard() {
		Card card = this.gamePanel.getCardBank().pickRandomCard();
		//card.activate();
		//this.addCard(card);
		return card;
	}

}
