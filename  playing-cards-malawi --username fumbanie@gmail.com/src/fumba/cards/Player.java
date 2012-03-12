package fumba.cards;

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

	/**
	 * Returns the name of the player
	 */
	public abstract String getName();

	/**
	 * Check to see if the player is human
	 */
	public abstract Boolean isHuman();

	/**
	 * Puts card to player hand
	 * 
	 * @param card
	 */
	public abstract void addCard(Card card);

	/**
	 * This method sets new card positions whenever a card is added or removed
	 * from the players hands
	 **/
	public abstract void recalculatePositions();

	/**
	 * Gets list of cards in the players hands
	 * 
	 * @return ArrayList of cards
	 */
	public abstract List<Card> getCardsInHand();

	/** Return all the cards to the main deck **/
	public abstract void returnCards();

	/**
	 * Brings the current card that player has selected to the top of all the
	 * others
	 **/
	public abstract void bringCardToFront(Card card);

	/**
	 * Gets the specific card from the players hand
	 * 
	 * @param activeCard
	 *            the selected card
	 * @return a card object
	 */
	public abstract Card getCard(String activeCard);

	/**
	 * Adds the specified card to the played cards list
	 */
	public abstract void playCard(Move move);

	/** Pick random card from the card deck **/
	public abstract void pickCard();

	/** Counts the number of cards in the players hand **/
	public abstract int countCardsInHands();

	/**
	 * Extracts the players current move
	 * 
	 * @return {@link Move Move Object}
	 */
	public abstract Move getCurrentMove();

	/** Sets the players current move **/
	public abstract void setCurrentMove(Move move);

	/** Checks if player has moves that can be made **/
	public abstract boolean hasValidMoves();

}
