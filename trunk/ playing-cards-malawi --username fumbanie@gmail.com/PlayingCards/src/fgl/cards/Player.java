package fgl.cards;

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
	public void getCard(Card card);
	
}
