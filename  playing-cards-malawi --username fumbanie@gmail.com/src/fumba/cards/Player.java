package fumba.cards;

import java.util.ArrayList;
import java.util.List;

/**
 * The <code>Player</code> interface defines a player object. A player can
 * either be a human or CPU. In order to implement an artificially intelligent
 * player, the abstract methods in this class need to be implemented.
 * 
 * <p>
 * <i>Copyright (c) 1998, 2011 Oracle. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 and Eclipse Distribution License v. 1.0 which accompanies
 * this distribution.</i>
 * </p>
 * 
 * @author Fumbani Chibaka
 * @version 1.0, 10/28/2011
 * @see <a href="http:chibaka.com">Fumba Game Lab</a>
 */

public abstract class Player {

	/** List of cards in players hands */
	protected List<Card> cards = new ArrayList<Card>();

	/** Game panel **/
	protected GamePanel gamePanel;

	/** Name of the current player **/
	protected String name;

	/** The players current move **/
	protected Move currentMove;

	/**
	 * Returns the name of the player
	 */
	protected String getName() {
		return this.name;
	}

	/**
	 * Check to see if the player is human
	 */
	public abstract Boolean isHuman();

	/**
	 * Makes card available to player and determine the position that the card
	 * is to be placed on the players hand
	 */
	protected void addCard(Card card) {
		this.cards.add(card);
		this.recalculatePositions();
	}

	/**
	 * Gets the list of cards in the players hands
	 */
	protected List<Card> getCardsInHand() {
		return this.cards;
	}

	/**
	 * Adds the specified card to the played cards list
	 */
	public abstract void makeMove(Move move);

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Checks to see if the player has valid moves
	 */
	public boolean hasValidMoves() {
		return Rules.hasValidMoves(this);
	}

	/**
	 * Returns a card object from the collection of served cards
	 * 
	 * @param cardName
	 *            The name of the card to be extracted
	 * @return The specified card. Returns null if no cards could be identified
	 *         by the specified name
	 */
	protected Card getCard(String activeCard) {
		for (Card card : this.cards) {
			if (card.getName().equalsIgnoreCase(activeCard))
				return card;
		}
		return null;
	}

	/**
	 * This method sets new card positions whenever a card is added or removed
	 * from the players hands
	 **/
	protected void recalculatePositions() {

		// TODO Move this method to the GamePanel class
		for (int i = 1; i <= this.cards.size(); i++) {
			Card card = this.cards.get(i - 1);
			double initial_x = 1.0 / (this.cards.size() + 2);
			double x = initial_x * i;
			double y = 0.3;
			this.gamePanel.getLayout().setPosition(card, x, y);
			card.setDefaultPosition(card.getPosition());
		}
	}

	/**
	 * Pick random card from the card stack
	 * 
	 * @return Card
	 */
	protected void pickCard() {
		Card card = this.gamePanel.getCardBank().pickRandomCard();
		card.activate();
		this.addCard(card);
	}

	/**
	 * String representation of the player object
	 */
	public String toString() {
		return this.getName();
	}

	/** Gets the players current move **/
	protected Move getCurrentMove() {
		return this.currentMove;
	}

	/** Sets the players current move **/
	protected void setCurrentMove(Move currentMove) {
		this.currentMove = currentMove;
	}

	/** Beings the card to the top- when player selects it **/
	protected void bringCardToFront(Card selectedCard) {
		for (int i = 0; i < this.getCardsInHand().size(); i++) {
			Card card = this.getCardsInHand().get(i);
			if (card.getName().equalsIgnoreCase(selectedCard.getName())) {
				this.cards.remove(i);
				this.cards.add(card);
			}
		}// end for loop
	}

	/**
	 * Puts back cards to the original card storage space
	 **/
	protected void returnCards() {
		for (Card card : this.cards) {
			this.gamePanel.getCardBank().putBackCard(card);
		}
		this.cards.clear();
	}

	/**
	 * Counts the number of cards in the players hands
	 * 
	 * @return integer
	 */
	protected int countCardsInHands() {
		return this.cards.size();
	}
}
