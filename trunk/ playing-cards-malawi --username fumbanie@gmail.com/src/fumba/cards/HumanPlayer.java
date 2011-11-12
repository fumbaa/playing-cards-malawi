package fumba.cards;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;


public class HumanPlayer implements Player {


	private Controller controller;

	/** Name of the current player **/
	private String name;

	 /** List of cards in hands*/
	private List<Card> cards = new ArrayList<Card>();

	/** The players current move **/
	private Move currentMove;

	/**
	 * Constructs human player 
	 * @param name Name of the player
	 */
	public HumanPlayer(String name, Controller controller){
		this.name = name;
		this.controller = controller;
		this.currentMove = new Move(); //defaults to false, false for validity and continuity
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
	public void playCard(Move move) {
		
		Card card = move.getCard();
		Point point = this.controller.getTopCard().getPosition();
		card.setCurrentPosition(point);
		this.controller.getTopCard().deactivate();
		this.currentMove = move;

		this.controller.updatePlayedCards( card );
		this.cards.remove(card);
		this.recalculatePositions();
	}

	/** This method sets new card positions whenever a card is added or removed from the players hands **/
	public void recalculatePositions() {

		for (int i = 1; i <= this.cards.size(); i++ )
		{
			Card card = this.cards.get(i - 1);
			double initial_x = 1.0 / (this.cards.size() + 2);
			double x = initial_x * i;
			double y = 0.3;
			this.controller.getLayout().setPosition(card, x, y);
			card.setDefaultPosition( card.getPosition());
		}
	}

	/** Pick random card from the card stack
	 * @return Card
	 */
	public void pickCard() {
		Card card =  this.controller.getCardBank().pickRandomCard();
		card.activate();
		this.addCard( card);
	}

	/**
	 * Counts the number of cards in the players hands
	 * @return integer
	 */
	public int countCardsInHands() {
		return this.cards.size();
	}

	/**Sets the players current move **/
	public void setCurrentMove(Move currentMove)
	{
		this.currentMove = currentMove;
	}
	/** Gets the players current move **/
	public Move getCurrentMove() {
		return this.currentMove;
	}
	
}