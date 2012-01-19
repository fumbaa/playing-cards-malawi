package fumba.cards;

/**
 * The <code>Rules</code> class defines the rules for playing the game. Game
 * users are allowed to customize the game rules according to their liking or
 * familiarity since this game has variations.
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

public abstract class Rules {

	/**
	 * Checks if a card can be played and returns a move object with validity
	 * and continuity determined.
	 * 
	 * @return Move
	 */
	public static Move checkMove(Card card) {
		Move move = new Move();

		Controller controller = card.getController();

		char currCardSuite = card.getSuite();
		char currCardNum = card.getNumber();

		char topCardSuite = controller.getTopCard().getSuite();
		char topCardNum = controller.getTopCard().getNumber();

		if (currCardSuite == topCardSuite || currCardNum == topCardNum) {
			move.setValidity(true);
			move.setContinuity( determineContinuity(card) ); 
			move.setCard(card);
		}
		return move;
	}

	/**
	 * Check if the card being played requires for a support card. ie. the
	 * player needs to make another move
	 * 
	 * @return boolean true if the player needs to make another supporting move
	 */
	public static boolean determineContinuity(Card card) {
		String[] repeatCards = card.getController().getRepeatCards();
		if ( ArrayUtils.contains(repeatCards, card.getName()) || ArrayUtils.contains(repeatCards, "" + card.getNumber() )){
			return true;
		}
		return false;
	}

	/**
	 * Checks to see if the player has valid moves
	 * 
	 * @param player
	 *            Player object
	 * @return boolean Return true if the player can make a valid move from his
	 *         current stack
	 */
	public static boolean hasValidMoves(Player player) {
		for (Card card : player.getCardsInHand()) {
			if (Rules.checkMove(card).getValidity())
				return true;
		}
		return false;
	}

}
