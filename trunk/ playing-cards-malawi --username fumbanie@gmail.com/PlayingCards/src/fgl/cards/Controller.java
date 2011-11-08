package fgl.cards;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

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

	/** the card on the very top of the played card stack **/
	private Card topPlayedCard; 

	/** interface to global information about an application environment */
	private Context context; 

	/** collection of cards served to player */
	//TODO: private List<Card> servedCards = new ArrayList<Card>();

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

	/** collection of graphics to be displayed on current screen  */
	private List<FGLGraphic> displayGraphics = new ArrayList<FGLGraphic>();

	/** Current player **/
	private Player currentPlayer;

	/** The current screen being displayed **/
	private int screen;

	/** Card that is turned upside down **/
	private Card cardBack;



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
		this.screen = Controller.START_SCREEN;

		this.setFocusable(true);

		//make sound and graphic elements
		this.buttons	= new ButtonBank(this.context);
		this.sounds		= new SoundBank(this.context);
		this.cards		= new CardBank(this.context, this);

		//show main menu
		this.addMainMenu();

		String playerName = "Fumbani";
		Player one = new HumanPlayer(playerName, this);
		this.currentPlayer = one;

		List<Player> players = new ArrayList<Player>();
		players.add(this.currentPlayer);

	}


	/**
	 * Takes cards from the deck and puts them in player hands
	 */
	private void serveCards() {

		for (int i = 0; i < 5; i++)
		{
			this.currentPlayer.addCard( this.cards.pickRandomCard()	);
		}

		Card middle_card = this.cards.pickRandomCard();
		//Card middle_card = new Card(this.context, "ZZ", R.drawable.card_back, this);
		GameBoardLayout.setPosition(middle_card, .5, .8);
		this.updatePlayedCards(middle_card);
	}

	/**
	 * Overrides View.ondraw method. Draws images on the canvas
	 * @param canvas The canvas onto which the bitmaps will be drawn            
	 * @see	View
	 */
	@Override protected void onDraw(Canvas canvas) {

		if (this.screen == Controller.GAME_SCREEN){
			canvas.drawBitmap (this.cardBack.getBitmap(), this.cardBack.getX(), this.cardBack.getY(), null);
		}

		//draw buttons
		for (FGLGraphic graphic: this.displayGraphics){
			canvas.drawBitmap( graphic.getBitmap(), graphic.getX(), graphic.getY(), null); }

		//draw played cards
		for (Card card : this.playedCards){
			canvas.drawBitmap(card.getBitmap(), card.getX(), card.getY(), null); }

		//draw cards in hands
		for (Card card : this.currentPlayer.getCardsInHand() ){
			canvas.drawBitmap(card.getBitmap(), card.getX(), card.getY(), null); }

		//Tools.catLog(""+ this.cards.countCards());
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

		Boolean start = ( this.screen == Controller.GAME_SCREEN);

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
		if ( this.screen == Controller.GAME_SCREEN ) 
			if (this.activeCard != null)
			{
				Card card = this.currentPlayer.getCard(this.activeCard);
				card.rescale();
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
				Card card = this.currentPlayer.getCard(this.activeCard);
				Boolean validMove = this.currentPlayer.makeMove(card);

				if ( validMove && this.topPlayedCard.isTouched(this.touchPoint)!= null)
					this.currentPlayer.playCard(card);
				else
				{
					card.resetPosition();
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
		for (Card card : this.currentPlayer.getCardsInHand()) 
		{
			Point check = card.isTouched(this.touchPoint);
			if ( check != null ){
				this.offset = check;
				this.activeCard = card.getName();
				this.currentPlayer.bringCardToFront(card); //Important: bring the active card to front
				card.magnify();
				//card.setToCenter( this.offset );
				break;
			}
		}	
		//if card is on the played cards deck.....
		Card card = this.topPlayedCard;
		Point check = card.isTouched(this.touchPoint);
		if ( check != null ){
			this.sounds.rejectSound();
		}

		//if card is on the deck of unplayed cards
		card = this.cardBack;
		Boolean noValidMoves = Rules.pickCard(this);

		if (card.isTouched(this.touchPoint) != null)
		{ 
			if (noValidMoves)
			{
				Card random_card = this.currentPlayer.pickCard();
				this.currentPlayer.addCard(random_card);
			}
			else
				Toast.makeText(this.context, "You are not allowed...", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * Performs necessary action when a button is touched
	 */
	private void buttonDown() {
		//Check to see if a button was pressed
		for (FGLGraphic graphic : this.displayGraphics) 
		{
			if (graphic.isTouched( this.touchPoint) != null) {

				//Start Button
				if (graphic.equals(this.buttons.getStartButton()))
				{
					sounds.startSound();
					this.screen = Controller.GAME_SCREEN;
					this.removeMainMenu();
					this.addBackButton();
					this.addCardDeck();
					this.serveCards();
					break;
				}

				//Back button
				if (graphic.equals(this.buttons.getBackButton()))
				{
					this.resetGame();
					this.addMainMenu();
					this.screen = Controller.START_SCREEN;
					break;
				}
			}
		}//end for-loop
	}


	/**
	 * Prepares and shows the elements for the main menu.
	 */
	private void addMainMenu() {

		//add start button
		CustomButton button = this.buttons.getStartButton();
		GameBoardLayout.setPosition(button, .5, .5);
		this.displayGraphics.add(button);

		//TODO: add other graphics...

	}

	/** Remove main menu elements **/
	private void removeMainMenu()
	{
		this.displayGraphics.remove( this.buttons.getStartButton() );
	}

	/** Puts the back button on the current screen **/
	private void addBackButton() {
		CustomButton back_button = this.buttons.getBackButton();
		GameBoardLayout.setPosition(back_button, 0.9 , 0.1);
		this.displayGraphics.add(back_button);		
	}

	/** Show the card deck and make cards available for player to pick if necessary **/
	private void addCardDeck() {
		this.cardBack = new Card (this.context, "--", R.drawable.card_back, this);
		GameBoardLayout.setPosition(cardBack, 0.9, 0.7);
	}

	/** Reset the game elements **/
	private void resetGame() {
		this.displayGraphics.remove(this.buttons.getBackButton());

		//return all cards that are currently in player hands
		this.currentPlayer.returnCards();
		this.playedCards.clear();
	}

	/**
	 * Get the card on the top of the played card deck
	 * @return top played card
	 */
	public Card getTopCard() {
		return this.topPlayedCard;
	}

	/** Returns the original storage space for all the cards **/
	public CardBank getCardBank() {
		return this.cards;
	}

	/**
	 * Gets the current Player
	 * @return Player
	 */
	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}

	/** Updates the stack of cards that have already been played **/
	public void updatePlayedCards(Card card) {
		this.playedCards.add(card);
		this.topPlayedCard = card; 
	}


}