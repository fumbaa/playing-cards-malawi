package fgl.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

/**
 * Manages the game application. This class controls menu elements, creates the cards and distributes them
 *  to players. It also listens to moves that players are making and updates the game state accordingly.
 * <p><i>Copyright (c) 1998, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution.</i></p>
 * 
 * @author Fumbani Chibaka
 * @version 1.0, 10/28/2011
 * @see <a href="http:chibaka.com">Fumba Game Lab</a>
 */

public class Controller extends View {

	/** Welcome screen **/
	private static final int START_SCREEN = 1;

	/** Screen with game ready to be played **/
	private static final int GAME_SCREEN = 2;

	private Card topPlayedCard; //TODO: For debugging purposes only (remove later)

	/** interface to global information about an application environment */
	private Context context; 

	/** collection of cards served to player */
	private List<Card> servedCards = new ArrayList<Card>();

	/** collection of played cards */
	private List<Card> playedCards = new ArrayList<Card>();


	/** currently selected card */
	private String activeCard;

	/** offset position relative to the touch point */
	private Point offset = new Point();

	/** The coordinates of the point which is touched on the screen */
	private Point touchPoint;

	/** Game sound bank **/
	private SoundBank sounds;

	/** Game button bank **/
	private ButtonBank buttons;

	/** Game cards bank **/
	private CardBank cards;

	/** collection of played cards */
	private List<CustomButton> displayButtons = new ArrayList<CustomButton>();

	/** Current player **/
	private Player currentPlayer;

	/** Start button has been pressed **/
	private boolean start;

	/** The current screen being displayed **/
	private int screen;




	/**
	 * Starts the game with a welcome screen where user selects desired options and initiates the game.
	 * Card objects are also created in this constructor.
	 * @param context interface to global information about an application environment 
	 */
	public Controller(Context context) 
	{
		super(context);
		this.touchPoint = new Point();
		this.context = context;
		this.start = false;
		this.screen = Controller.START_SCREEN;

		this.setFocusable(true);

		//make sound and graphic elements
		this.buttons	= new ButtonBank(this.context);
		this.sounds		= new SoundBank(this.context);
		this.cards		= new CardBank(this.context);

		//show main menu
		this.showMainMenu();

		String playerName = "Fumbani";
		Player one = new HumanPlayer(playerName, this);
		this.currentPlayer = one;

		List<Player> players = new ArrayList<Player>();
		players.add(this.currentPlayer);

	}

	/**
	 * Prepares and shows the elements for the main menu.
	 */
	private void showMainMenu() {
		CustomButton button = this.buttons.getStartButton();
		Point center = button.getCenter();
		Point midpoint = new Point ( PlayingCardsActivity.width / 2 - center.x , PlayingCardsActivity.height / 2 - center.y);
		button.setPosition(midpoint);

		//add button to display list
		this.displayButtons.add(button);
	}


	/**
	 * Takes cards from the deck and puts them in player hands
	 */
	private void serveCards() {

		Point point1 = new Point(100,100); 
		Point point2 = new Point(250,100); 
		Point point3 = new Point(400,100);

		Point point4 = new Point(300, 300);

		Card card1 = this.cards.pickRandomCard();
		card1.setDefaultPosition(point1);
		card1.setCurrentPosition(point1);
		servedCards.add(card1);

		Card card2 = this.cards.pickRandomCard();
		card2.setDefaultPosition(point2);
		card2.setCurrentPosition(point2);
		servedCards.add(card2);

		Card card3 = this.cards.pickRandomCard();
		card3.setDefaultPosition(point3);
		card3.setCurrentPosition(point3);
		servedCards.add(card3);


		Card card4 = this.cards.pickRandomCard();
		card4.setDefaultPosition(point4);
		card4.setCurrentPosition(point4);
		playedCards.add(card4);
		this.topPlayedCard = card4; //TODO: Remove this!!!!!

		this.currentPlayer.getCard(card1);
		this.currentPlayer.getCard(card2);
		this.currentPlayer.getCard(card3);

	}

	/**
	 * Overrides View.ondraw method. Draws images on the canvas
	 * @param canvas The canvas onto which the bitmaps will be drawn            
	 * @see	View
	 */
	@Override protected void onDraw(Canvas canvas) {

		//draw buttons
		for (CustomButton button: this.displayButtons){
			canvas.drawBitmap( button.getBitmap(), button.getX(), button.getY(), null); }

		//draw played cards
		for (Card card : this.playedCards){
			canvas.drawBitmap(card.getBitmap(), card.getX(), card.getY(), null); }

		//draw cards in hands
		for (Card card : this.servedCards){
			canvas.drawBitmap(card.getBitmap(), card.getX(), card.getY(), null); }
	}

	/**
	 * Detects screen touch gestures and moves the selected card accordingly
	 * @param	event The detected motion event
	 * @return	true           
	 * @see	MotionEvent
	 */
	public boolean onTouchEvent(MotionEvent event) {
		int eventAction = event.getAction(); 
		this.touchPoint = new Point( (int)event.getX(),(int)event.getY());

		switch (eventAction ) {

		case MotionEvent.ACTION_UP: 
			if (start)
				this.cardUp();
			break; 

		case MotionEvent.ACTION_DOWN:
			if (start)
				this.cardDown();
			this.buttonDown();
			break; 

		case MotionEvent.ACTION_MOVE:
			if (start)
				this.cardMove();
			break; 
		} 

		this.invalidate(); //Redraw the canvas
		return true; 
	}

	/**
	 * Actions to perform when a card is moved.
	 */
	private void cardMove() {
		if (start)
			if (this.activeCard != null)
			{
				Card card = this.getServedCard(this.activeCard);
				card.setX(touchPoint.x - card.getCenter().x);
				card.setY(touchPoint.y - card.getCenter().y);
			}
	}

	/**
	 * Actions to perform when a card is dropped
	 */
	private void cardUp() {
		if (this.activeCard != null) { 

			if ( this.currentPlayer.isHuman() )
			{
				Boolean validMove = this.currentPlayer.makeMove(this.getServedCard(this.activeCard));

				if ( validMove && this.topPlayedCard.isTouched(this.touchPoint)!= null)
				{
					Card card = this.getServedCard(this.activeCard);
					card.setCurrentPosition(this.topPlayedCard.getPosition());
					this.playedCards.add( card );
					this.topPlayedCard = card; //update the card on the top

					this.servedCards.remove(card); //clear it from served cards list
				}
				else
				{
					this.getServedCard(activeCard).resetPosition();

					if (this.topPlayedCard.isTouched(this.touchPoint) != null)
						sounds.rejectSound();
				}
			}
		}//end main if
	}


	/**
	 * Actions to perform when a card is touched
	 */
	private void cardDown() {
		this.activeCard = null;//reset active card

		//if card is in hand....
		for (Card card : this.servedCards) 
		{
			Point check = card.isTouched(this.touchPoint);
			if ( check != null ){
				this.offset = check;
				this.activeCard = card.getName();
				this.bringToFront(card); //Important: bring the active card to front
				card.setToCenter( this.offset );
				break;
			}
		}	
		//if card is on the played cards deck.....
		Card card = this.topPlayedCard;
		Point check = card.isTouched(this.touchPoint);
		if ( check != null ){
			this.sounds.rejectSound();
		}
	}

	/**
	 * Performs necessary action when a button is touched
	 */
	private void buttonDown() {
		//Check to see if a button was pressed
		for (CustomButton button : this.displayButtons) 
		{
			Point check = button.isTouched( this.touchPoint);
			if ( check != null){

				//Actions to be performed if this is the start screen
				if (this.screen == Controller.START_SCREEN ){

					//remove start button and add back button
					this.displayButtons.remove(button);
					CustomButton back_button = this.buttons.getBackButton();
					this.displayButtons.add(back_button);

					//FIXME:::
					back_button.setCurrentPosition(new Point(500,10));
					sounds.startSound(); 
					//Serve cards at this point
					this.serveCards();
					this.start = true;
					this.screen = Controller.GAME_SCREEN;
					break;
				}

				//Actions to be performed if this is the game screen
				if (this.screen == Controller.GAME_SCREEN)
				{
					this.displayButtons.remove(button);
					this.resetGame();
					this.showMainMenu();
					this.screen = Controller.START_SCREEN;
					break;
				}
			}
		}
	}


	/** Reset the game elements **/
	private void resetGame() {

		//return all cards that are currently in player hands
		for (Card card: this.servedCards)
		{
			this.cards.putBackCard(card);
		}
		this.servedCards.clear();

		//return all cards that have been played
		for (Card card: this.playedCards)
		{
			this.cards.putBackCard(card);
		}
		this.playedCards.clear();
	}


	/**
	 * Brings the selected card to the top of the list.
	 * @param	touchedCard The card to be brought to the front
	 */
	private void bringToFront(Card touchedCard) {

		for(int i = 0; i < this.servedCards.size(); i++ )
		{
			Card card = this.servedCards.get(i);
			if ( card.getName().equalsIgnoreCase( touchedCard.getName()))
			{
				this.servedCards.remove(i);
				this.servedCards.add(card);
				this.invalidate();
			}
		}
	}

	/**
	 * Get the card on the top of the played card deck
	 * @return top played card
	 */
	public Card getTopCard() {
		return this.topPlayedCard;
	}

	/**
	 * Returns a card object from the collection of served cards
	 * @param	cardName The name of the card to be extracted
	 * @return	The specified card. Returns null if no cards could be identified by the specified name
	 */	
	private Card getServedCard(String cardName) {
		for( Card card: this.servedCards){
			if (card.getName().equalsIgnoreCase(cardName))
				return card;
		}
		return null;
	}


}