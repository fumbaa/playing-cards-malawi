package fumba.cards;

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

public abstract class Rules {

	/**
	 * Check if a move is valid before it is made
	 */
	public static Boolean validMove(Card card)
	{
		Controller controller = card.getController();
		
		char currCardSuite = card.getSuite();
		char currCardNum = card.getNumber();

		char topCardSuite = controller.getTopCard().getSuite();
		char topCardNum = controller.getTopCard().getNumber();

		if (currCardSuite == topCardSuite || currCardNum == topCardNum )
			return true;
		return false;
	}

	/**
	 * Check if there are any valid moves 
	 * @return Boolean (true if no moves are available from the cards currently in the players hands)
	 */
	public static Boolean pickCard(Controller controller) {
		for (Card card: controller.getCurrentPlayer().getCardsInHand()){
			if (Rules.validMove(card))
				return false;
		}
		return true;
	}

}
