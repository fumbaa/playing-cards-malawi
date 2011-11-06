package fgl.cards;

import java.util.ArrayList;
import java.util.List;

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
		
		return Rules.validMove(card, controller);
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
	 * Makes card available to player
	 */
	public void getCard(Card card) {
		this.cards.add(card);
	}

}
