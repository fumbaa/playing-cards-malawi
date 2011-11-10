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
 * The <code>Controller</code> manages the game application. This class controls menu elements, creates the cards and distributes them
 *  to players. It also listens to moves that players are making and updates the game state accordingly.
 * <p><i>Copyright (c) 1998, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution.</i></p>
 * 
 * @author Fumbani Chibaka
 * @version 1.0, 10/28/2011
 * @see <a href="http:chibaka.com">Fumba Game Lab</a>
 */

public class Controller extends View {

	//Sreen timeline
	/** Welcome screen **/
	private static final int START_SCREEN = 1;

	/** Screen with game ready to be played **/
	private static final int GAME_SCREEN = 2;

	/** Options Screen **/
	private static final int OPTIONS_SCREEN = 3;


	/** the card on the very top of the played card stack **/
	private Card topPlayedCard; 

	/** interface to global information about an application environment */
	private Context context;

	/** collection of played cards */
	private List<Card> playedCards = new ArrayList<Card>();

	/** currently selected card */
	private String activeCard;

	/** The coordinates of the point which is touched on the screen */
	private Point touchPoint;

	/** Game sound bank **/
	private SoundBank sounds;

	/** Game button bank **/
	private CustomButtonBank buttons;

	/** Game cards bank **/
	private CardBank cards;

	/** collection of graphics to be displayed on current screen  */
	private List<FGLGraphic> displayGraphics = new ArrayList<FGLGraphic>();

	/** Current player **/
	private Player currentPlayer;

	/** The current screen being displayed **/
	private int currentScreen;

	/** Card that is turned upside down **/
	private Card cardBack;

	/** List of players currently in the game **/
	private List<Player> players = new ArrayList<Player>();

	/** Tag to allow player to continue playing **/
	private boolean playerDone_PassToNext = false;

	/** Device handed to next player show cards in hand **/
	private boolean blankScreenTransition = false;

	/** A framelayout that organises all the graphical game elements **/
	private GameBoardLayout layout;

	/** Total number of cards available for this game application **/
	private int totalNumCards;

	/** Textviews that are used to display text for this application **/
	private TextViewBank textViews;

	/** The next player **/
	private Player nextPlayer;


	/**
	 * Starts the game with a welcome screen where user selects desired options and initiates the game.
	 * Card objects are also created in this constructor.
	 * @param context interface to global information about an application environment 
	 */
	public Controller(Context context, GameBoardLayout layout) 
	{
		super(context);
		this.touchPoint = new Point();
		this.context = context;
		this.currentScreen = Controller.START_SCREEN;
		this.layout = layout;
		this.setFocusable(true);

		//Make elements available to the controller
		this.buttons	= new CustomButtonBank(this.context);
		this.sounds		= new SoundBank(this.context);
		this.cards		= new CardBank(this.context, this);
		this.textViews	= new TextViewBank(this.context, this.layout);

		//LOG INFORMATION FOR INITIAL VALUES (for debugging )
		this.totalNumCards = cards.countCards();

		//show main menu
		this.addMainMenu();

		String playerName1 = "Fumbani";
		String playerName2 = "Felix";
		String playerName3 = "DiwiDiwi";

		Player p1 = new HumanPlayer(playerName1, this);
		Player p2 = new HumanPlayer(playerName2, this);
		Player p3 = new HumanPlayer(playerName3, this);

		this.players.add(p1);
		this.players.add(p2);
		this.players.add(p3);
	}


	/**
	 * Takes cards from the deck and puts them in player hands
	 */
	private void serveCards() {

		Card middle_card = this.cards.pickRandomCard();
		this.layout.setPosition(middle_card, .5, .8);
		this.updatePlayedCards(middle_card);

		for (int i = 0; i < 5; i++)
		{
			//distribute to all the players
			for ( int j = 0; j < this.players.size(); j++)
				this.players.get(j).addCard( this.cards.pickRandomCard()	);
		}
	}

	/**
	 * Overrides View.ondraw method. Draws images on the canvas
	 * @param canvas The canvas onto which the bitmaps will be drawn            
	 * @see	View
	 */
	@Override protected void onDraw(Canvas canvas) {

		//draw buttons
		for (FGLGraphic graphic: this.displayGraphics){
			canvas.drawBitmap( graphic.getBitmap(), graphic.getX(), graphic.getY(), null); }
		
		if (! this.blankScreenTransition){
			if (this.currentScreen == Controller.GAME_SCREEN){
				canvas.drawBitmap (this.cardBack.getBitmap(), this.cardBack.getX(), this.cardBack.getY(), null);
			}

			//draw played cards
			for (Card card : this.playedCards){
				canvas.drawBitmap(card.getBitmap(), card.getX(), card.getY(), null); }

			//draw cards in hands
			if (this.currentScreen == Controller.GAME_SCREEN)
				for (Card card : this.currentPlayer.getCardsInHand() ){
					canvas.drawBitmap(card.getBitmap(), card.getX(), card.getY(), null); }

			//allows the current player to see their current play status before passing the device to the next player
			if (this.playerDone_PassToNext)
			{
				FGLGraphic card = (buttons.getContinueButton());
				this.layout.setPosition(buttons.getContinueButton(), 0.2, 0.5);
				canvas.drawBitmap(card.getBitmap(), card.getX(), card.getY(), null);
			}
		}

		//Show a blank screen that will be activated by next player whenever they are ready to play
		else
		{
			this.nextPlayer = this.getNextPlayer(); //TODO: Careful when more than 2 people are playing (Reverse/ forward)

			this.textViews.getCurrentPlayerTextView().setText( "Current Player : " + this.nextPlayer.getName());
			this.textViews.getHandStatusTextView().setText( "# of Cards in Hand : " + this.nextPlayer.countCardsInHands());
			String string = ", touch anywhere on the screen inorder for you to see your cards and continue playing the game.";
			this.textViews.getPlayerTransitionTextView().setText( this.nextPlayer.getName() + string );
		}

		//Log used cards
		this.textViews.getDeckStatusTextView().setText("Used : " + this.cards.countCards()
				+ " / " + this.totalNumCards + " c[" + this.playedCards.size() + "] a[" + this.countNumCardsAllPlayers() + "]" );
	}


	/** Retrieves the next player **/
	private Player getNextPlayer() {
		int location = this.players.indexOf(this.currentPlayer) + 1;
		if (location == this.players.size() )
			location = 0;		
		return this.players.get(location);
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

		Boolean start = ( this.currentScreen == Controller.GAME_SCREEN);

		switch (eventAction ) {

		case MotionEvent.ACTION_UP: 
			if (start)
				this.cardUp();
			break; 

		case MotionEvent.ACTION_DOWN:
			this.screenDown();	
			if (start)
				this.cardDown();
			this.buttonDown();			
			break; 

		case MotionEvent.ACTION_MOVE:
			if (start && !playerDone_PassToNext)
				this.cardMove();
			break; 
		} 

		this.invalidate();
		return true; 
	}

	/**
	 * Actions to be performed when the screen in touched
	 */
	private void screenDown() {
		if (this.blankScreenTransition)
		{
			this.blankScreenTransition = false;
			this.nextPlayer();
			this.textViews.getPlayerTransitionTextView().setText("");
		}
	}


	/**
	 * Actions to perform when a card is moved.
	 */
	private void cardMove() {
		if ( this.currentScreen == Controller.GAME_SCREEN ) 
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

			Card card = this.currentPlayer.getCard(this.activeCard);

			//Current players turn
			if ( this.currentPlayer.isHuman() )
			{
				Boolean validMove = this.currentPlayer.makeMove(card);

				//Current Player makes valid move
				if ( validMove && this.topPlayedCard.isTouched(this.touchPoint)!= null)
				{
					this.currentPlayer.playCard(card);
					this.textViews.getHandStatusTextView().setText( "# of Cards in Hand : " + this.currentPlayer.countCardsInHands());
					this.playerDone_PassToNext = true;
				}
				//Current Player makes invalid move
				else
				{
					card.resetPosition();
					if (this.topPlayedCard.isTouched(this.touchPoint) != null)
						sounds.rejectSound();
				}
			}

			//Computer or other players turn
			else
			{
				card.resetPosition();
				Toast.makeText(this.context, "Please wait: CPU is playing...", Toast.LENGTH_SHORT);
			}
		}
	}


	/**
	 * Switch to the next player. Gives some time for the user to see what card they picked before it switches
	 */
	public void nextPlayer() {

		int location = this.players.indexOf(this.currentPlayer) + 1;
		if (location == this.players.size() )
			location = 0;
		this.currentPlayer = this.players.get(location);

		this.textViews.getCurrentPlayerTextView().setText("Current Player : " + this.currentPlayer.getName());
		this.textViews.getHandStatusTextView().setText( "# of Cards in Hand : " + this.currentPlayer.countCardsInHands());
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
				this.activeCard = card.getName();
				this.currentPlayer.bringCardToFront(card); //Important: bring the active card to front
				card.magnify();
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
		
		//TODO: Allow this? Boolean noValidMoves = Rules.pickCard(this);
		Boolean noValidMoves = true;

		if (card.isTouched(this.touchPoint) != null)
		{ 
			if (noValidMoves && !this.playerDone_PassToNext )
			{
				this.currentPlayer.pickCard();				
				this.playerDone_PassToNext = true;
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
					this.currentPlayer = players.get(0); //randomise this

					this.textViews.getCurrentPlayerTextView().setText("Current Player : " + this.currentPlayer.getName());

					this.currentScreen = Controller.GAME_SCREEN;
					this.removeMainMenu();
					this.addBackButton();
					this.addCardDeck();
					this.serveCards();

					this.textViews.getHandStatusTextView().setText( "# of Cards in Hand : " + this.currentPlayer.countCardsInHands());
					break;
				}

				//Back button
				if (graphic.equals(this.buttons.getBackButton()))
				{
					this.resetGame();
					this.addMainMenu();
					this.currentScreen = Controller.START_SCREEN;
					break;
				}
			}
		}//end for-loop


		//Continue Button
		if (this.buttons.getContinueButton().isTouched( this.touchPoint) != null && this.playerDone_PassToNext )
		{
			this.playerDone_PassToNext = false; 
			this.blankScreenTransition = true;
		}

	}


	/**
	 * Prepares and shows the elements for the main menu.
	 */
	private void addMainMenu() {

		//add start button
		CustomButton button = this.buttons.getStartButton();
		this.layout.setPosition(button, .5, .5);
		this.displayGraphics.add(button);

	}

	/** Remove main menu elements **/
	private void removeMainMenu()
	{
		this.displayGraphics.remove( this.buttons.getStartButton() );
	}

	/** Puts the back button on the current screen **/
	private void addBackButton() {
		CustomButton back_button = this.buttons.getBackButton();
		this.layout.setPosition(back_button, 0.9 , 0.1);
		this.displayGraphics.add(back_button);		
	}

	/** Show the card deck and make cards available for player to pick if necessary **/
	private void addCardDeck() {
		this.cardBack = new Card (this.context, "--", R.drawable.card_back, this);
		this.layout.setPosition(cardBack, 0.9, 0.7);
	}

	/** Reset the game elements **/
	private void resetGame() {
		this.displayGraphics.remove(this.buttons.getBackButton());
		this.playerDone_PassToNext = false; //TODO: Remove the continue button instead

		//return all cards that are currently in player hands
		for (int i = 0; i < this.players.size(); i++)
		{
			this.players.get(i).returnCards();
		}

		//return played cards to the main card deck
		for (Card card: this.playedCards){
			this.cards.putBackCard(card);
		}
		this.playedCards.clear();

		this.textViews.reset();
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
	 * Gets the current Player. (Used by Rules class to retrieve a player from the controller)
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

	/**
	 * Gets the gameboard layout object
	 * @return GameBoardLayout
	 */
	public GameBoardLayout getLayout() {
		return this.layout;
	}

	/**
	 * Count the total number of cards in all the players hands (Used for debugging)
	 * @return the number of cards
	 */
	private int countNumCardsAllPlayers(){
		int sum = 0;
		for (Player player: this.players){
			sum = sum + player.countCardsInHands();
		}
		return sum;
	}

}