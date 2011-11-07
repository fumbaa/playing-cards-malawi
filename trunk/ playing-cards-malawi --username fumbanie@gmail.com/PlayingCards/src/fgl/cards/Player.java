package fgl.cards;

import java.util.List;

/**
 * 
 * Copyright (c) 2011 FUMBA GAME LAB. All rights reserved
 * 
 * Malawi Playing Cards: Player.java
 * Player interface class
 * 
 * @author Fumbani Chibaka
 * @version 1.0
 * @since 0.0
 * 
 * 
 * /*******************************************************************************
 * Copyright (c) 1998, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution. 
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 ******************************************************************************
 *
 */
public interface Player {

	/**
	 * Sends a move to the Controller class
	 */
	public boolean makeMove(Card card);

	/**
	 * Returns the name of the player
	 */
	public String getName();

	/**
	 * Check to see if the player is human
	 */
	public Boolean isHuman();

	/**
	 * Puts card to player hand
	 * @param card
	 */
	public void addCard(Card card);

	/** This method sets new card positions whenever a card is added or removed from the players hands **/
	public void recalculatePositions();
	
	/**
	 * Gets list of cards in the players hands
	 * @return ArrayList of cards
	 */
	public List<Card> getCardsInHand();

	/** Return all the cards to the main deck **/
	public void returnCards();

	/** Brings the current card that player has selected to the top of all the others **/
	public void bringCardToFront(Card card);

	/**
	 * Gets the specific card from the players hand
	 * @param activeCard the selected card
	 * @return a card object
	 */
	public Card getCard(String activeCard);

	/**
	 * Adds the specified card to the played cards list
	 */
	public void playCard(Card card);

	/** Pick random card from the card deck **/
	public Card pickCard();

}
