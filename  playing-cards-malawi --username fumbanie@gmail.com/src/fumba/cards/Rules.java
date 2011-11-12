package fumba.cards;

/**
 * The <code>Rules</code> class defines the rules for playing the game. Game users are allowed to customize the game rules according to their liking or
 * familiarity since this game has variations.
 * 
 * <p><i>Copyright (c) 1998, 2011 Oracle. All rights reserved. 
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution.</i></p>
 * 
 * @author Fumbani Chibaka
 * @version 1.0, 10/28/2011
 * @see <a href="http:chibaka.com">Fumba Game Lab</a>
 */

public abstract class Rules {

	
	/**
	 * Checks if a move is valid
	 * @return Boolean
	 */
	public static Move checkMove(Card card)
	{
		Move move = new Move();

		Controller controller = card.getController();

		char currCardSuite = card.getSuite();
		char currCardNum = card.getNumber();

		char topCardSuite = controller.getTopCard().getSuite();
		char topCardNum = controller.getTopCard().getNumber();

		if (currCardSuite == topCardSuite || currCardNum == topCardNum )
		{
			move.setValidity(true);
			move.setDone(true);
			move.setCard(card);
		}
		return move; 
	}

	/**
	 * Check if there are any valid moves 
	 * @return Boolean (true if no moves are available from the cards currently in the players hands)
	 */
	public static Boolean pickCard(Controller controller) {
		for (Card card: controller.getCurrentPlayer().getCardsInHand()){
			if (Rules.checkMove(card).getValidity())
				return false;
		}
		return true;
	}

}
