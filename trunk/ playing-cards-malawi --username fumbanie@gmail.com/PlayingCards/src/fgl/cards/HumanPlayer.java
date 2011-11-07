package fgl.cards;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;

/**
 * 
 * Copyright (c) 2011 FUMBA GAME LAB. All rights reserved
 * 
 * Malawi Playing Cards: HumanPlayer.java
 * Represents the human player.
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

public class HumanPlayer implements Player {


	private Controller controller;


	/**
	 * Name of the human player
	 */
	private String name;

	/**
	 * List of cards in hands
	 */
	private List<Card> cards = new ArrayList<Card>();

	/**
	 * Constructs human player 
	 * @param name Name of the player
	 */
	public HumanPlayer(String name, Controller controller){
		this.name = name;
		this.controller = controller;
	}

	/**
	 * Send move to the the game controller
	 */
	public boolean makeMove(Card card) {
		//Check if move is valid
		return Rules.validMove(card);
	}

	/**
	 * Gets the name of the player
	 * @return String representing name of the player
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * Check to see if this class is human
	 */
	public Boolean isHuman() {
		return true;
	}

	/**
	 * Makes card available to player.
	 */
	public void addCard(Card card) {
		this.cards.add(card);
		this.recalculatePositions();
	}

	/**
	 * Gets the list of cards in the players hands
	 */
	public List<Card> getCardsInHand() {
		return this.cards;
	}

	/** 
	 * Puts back cards to the original card storage space 
	 **/
	public void returnCards() {
		for (Card card: this.cards){
			this.controller.getCardBank().putBackCard(card);
		}
		this.cards.clear();
	}

	/** Beings the card to the top- when player selects it **/
	public void bringCardToFront(Card selectedCard) {
		for(int i = 0; i < this.getCardsInHand().size(); i++ )
		{
			Card card = this.getCardsInHand().get(i);
			if ( card.getName().equalsIgnoreCase( selectedCard.getName()))
			{
				this.cards.remove(i);
				this.cards.add(card);
			}
		}//end for loop
	}

	/**
	 * Returns a card object from the collection of served cards
	 * @param	cardName The name of the card to be extracted
	 * @return	The specified card. Returns null if no cards could be identified by the specified name
	 */	
	public Card getCard(String activeCard) {
		for( Card card: this.cards)
		{
			if (card.getName().equalsIgnoreCase(activeCard))
				return card;
		}
		return null;
	}

	/** Add the selected card to the played cards list **/
	public void playCard(Card card) {
		Point point = this.controller.getTopCard().getPosition();
		card.setCurrentPosition(point);
		this.controller.updatePlayedCards( card );
		this.cards.remove(card);
		this.recalculatePositions();
	}

	/** This method sets new card positions whenever a card is added or removed from the players hands **/
	public void recalculatePositions() {
		Tools.printDebug("Num in hands : " + this.cards.size());
		for (int i = 1; i <= this.cards.size(); i++ )
		{
			Card card = this.cards.get(i - 1);
			double initial_x = 1.0 / (this.cards.size() + 2);
			double x = initial_x * i;
			double y = 0.3;
			GameBoardLayout.setPosition(card, x, y);
			card.setDefaultPosition( card.getPosition());
		}
	}

	/** Pick random card from the card stack
	 * @return Card
	 */
	public Card pickCard() {
		return this.controller.getCardBank().pickRandomCard();
	}
}
