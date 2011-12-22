package fumba.cards;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * The <code>Controller</code> manages the game application. This class controls
 * menu elements, creates the cards and distributes them to players. It also
 * listens to moves that players are making and updates the game state
 * accordingly.
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

public class Controller extends View implements MessageConstants {

	// Screen timeline
	/** Welcome screen **/
	private static final int START_SCREEN = 1;

	/** Screen with game ready to be played **/
	private static final int GAME_SCREEN = 2;

	/** Options Screen **/
	private static final int OPTIONS_SCREEN = 3;

	private static final int GAME_OVER = 4;

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

	/** Current player **/
	private Player currentPlayer;

	/** The current screen being displayed **/
	private int currentScreen;

	/** Card that is turned upside down **/
	private Card cardBack;

	/** List of players currently in the game **/
	private List<Player> players = new ArrayList<Player>();

	/** Device handed to next player show cards in hand **/
	private boolean blankScreenTransition = false;

	/** A framelayout that organizes all the graphical game elements **/
	private GameBoardLayout layout;

	/** Total number of cards available for this game application **/
	private int totalNumCards;

	/** Textviews that are used to display text for this application **/
	private TextViewBank textViews;

	/** The next player **/
	private Player nextPlayer;

	/**
	 * The number of cards to be served to the players at the beginning of the
	 * game
	 **/
	private int numOfCardsServed;

	/** Game Language **/
	private String LN;

	/**
	 * Starts the game with a welcome screen where user selects desired options
	 * and initiates the game. Card objects are also created in this
	 * constructor.
	 * 
	 * @param context
	 *            interface to global information about an application
	 *            environment
	 */
	public Controller(Context context, GameBoardLayout layout) {
		super(context);

		this.numOfCardsServed = 2;

		this.touchPoint = new Point();
		this.context = context;
		this.currentScreen = Controller.START_SCREEN;
		this.blankScreenTransition = false;
		this.layout = layout;
		this.setFocusable(true);

		// Make elements available to the controller
		this.buttons = new CustomButtonBank(this.context, this.layout);
		this.sounds = new SoundBank(this.context);
		this.cards = new CardBank(this.context, this);
		this.textViews = new TextViewBank(this.context, this.layout);

		// LOG INFORMATION FOR INITIAL VALUES (for debugging )
		this.totalNumCards = cards.countCards();

		// show main menu
		// TODO this.addMainMenu();

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
		middle_card.activate();

		for (int i = 0; i < this.numOfCardsServed; i++) {
			// distribute to all the players
			for (int j = 0; j < this.players.size(); j++) {
				Card card = this.cards.pickRandomCard();
				card.activate();
				this.players.get(j).addCard(card);
			}
		}
	}

	/**
	 * Overrides View.ondraw method. Draws images on the canvas
	 * 
	 * @param canvas
	 *            The canvas onto which the bitmaps will be drawn
	 * @see View
	 */
	@Override
	protected void onDraw(Canvas canvas) {

		// 1. START SCREEN (Draw Start Button )
		if (this.currentScreen == Controller.START_SCREEN)
			canvas.drawBitmap(this.buttons.getStartButton().getBitmap(),
					this.buttons.getStartButton().getX(), this.buttons
							.getStartButton().getY(), null);

		// 2. GAME SCREEN (Depends on the scenario)
		if (this.currentScreen == Controller.GAME_SCREEN) {
			// draw back button
			canvas.drawBitmap(this.buttons.getBackButton().getBitmap(),
					this.buttons.getBackButton().getX(), this.buttons
							.getBackButton().getY(), null);

			// Scenario 1: Show a blank screen that will be activated by next
			// player whenever they are ready to play
			if (this.blankScreenTransition) {
				this.nextPlayer = this.getNextPlayer(); // TODO: Careful when
														// more than 2 people
														// are playing (Reverse/
														// forward)
				this.textViews.getCurrentPlayerTextView().setText(
						"Current Player : " + this.nextPlayer.getName());
				this.textViews.getHandStatusTextView().setText(
						"# of Cards in Hand : "
								+ this.nextPlayer.countCardsInHands());
				String string = ", touch anywhere on the screen inorder for you to see your cards and continue playing the game.";
				this.textViews.getPlayerTransitionTextView().setText(
						this.nextPlayer.getName() + string);
			}

			// Scenario 2: allows the current player to see their current play
			// status before passing the device to the next player
			if (this.currentPlayer.getCurrentMove().isDone())
				canvas.drawBitmap(buttons.getContinueButton().getBitmap(),
						buttons.getContinueButton().getX(), buttons
								.getContinueButton().getY(), null);

			// Scenario 3: draw cards
			if (!this.blankScreenTransition) {
				canvas.drawBitmap(this.cardBack.getBitmap(),
						this.cardBack.getX(), this.cardBack.getY(), null); // unplayed
																			// cards
				for (Card card : this.playedCards) {
					canvas.drawBitmap(card.getBitmap(), card.getX(),
							card.getY(), null);
				} // played cards
				for (Card card : this.currentPlayer.getCardsInHand()) {
					canvas.drawBitmap(card.getBitmap(), card.getX(),
							card.getY(), null);
				} // cards in hands
			}

		}

		// 3. GAME OVER SCREEN (displays message if the game is over- the
		// current player wins!)
		else if (this.currentScreen == Controller.GAME_OVER) {
			this.textViews.getPlayerTransitionTextView().setText(
					"Game Over !!!!");
		}

		// Logging for debug purposes only
		this.textViews.getDeckStatusTextView().setText(
				"Used : " + this.cards.countCards() + " / "
						+ this.totalNumCards + " c[" + this.playedCards.size()
						+ "] a[" + this.countNumCardsAllPlayers() + "]");
	}

	/** Retrieves the next player **/
	private Player getNextPlayer() {
		int location = this.players.indexOf(this.currentPlayer) + 1;
		if (location == this.players.size())
			location = 0;
		return this.players.get(location);
	}

	/**
	 * Detects screen touch gestures and moves the selected card accordingly
	 * 
	 * @param event
	 *            The detected motion event
	 * @return true
	 * @see MotionEvent
	 */
	public boolean onTouchEvent(MotionEvent event) {

		int eventAction = event.getAction();
		this.touchPoint = new Point((int) event.getX(), (int) event.getY());

		// Only move cards on the game screen when the player is not waiting to
		// press (PASS TO NEXT PLAYER) button
		Boolean canMove = (this.currentScreen == Controller.GAME_SCREEN)
				&& !this.currentPlayer.getCurrentMove().isDone();

		switch (eventAction) {

		case MotionEvent.ACTION_UP:
			if (canMove)
				this.cardUp();
			break;

		case MotionEvent.ACTION_DOWN:
			this.screenDown();
			if (canMove)
				this.cardDown();
			this.buttonDown();
			break;

		case MotionEvent.ACTION_MOVE:
			if (canMove)
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
		if (this.blankScreenTransition) {
			this.blankScreenTransition = false;
			this.nextPlayer();
			this.textViews.getPlayerTransitionTextView().setText("");
		}
	}

	/**
	 * Actions to perform when a card is moved.
	 */
	private void cardMove() {
		if (this.currentScreen == Controller.GAME_SCREEN)
			if (this.activeCard != null) {
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

			// Current players turn
			if (this.currentPlayer.isHuman()) {
				Move move = Rules.checkMove(card);
				// Current Player makes valid move
				if (move.getValidity()
						&& this.topPlayedCard.isTouched(this.touchPoint) != null) {
					this.currentPlayer.playCard(move);
					this.activeCard = null;
					this.buttons.getContinueButton().activate();
					this.textViews.getHandStatusTextView().setText(
							"# of Cards in Hand : "
									+ this.currentPlayer.countCardsInHands());
					this.gameOver();
				}
				// Current Player makes invalid move
				else {
					card.resetPosition();
					if (this.topPlayedCard.isTouched(this.touchPoint) != null)
						sounds.rejectSound();
				}
			}

			// Computer or other players turn
			else {
				card.resetPosition();
				Toast.makeText(this.context, "Please wait: CPU is playing...",
						Toast.LENGTH_SHORT);
			}
		}
	}

	/**
	 * Check of the most recent move has resulted into a win. This method is
	 * called in {@link #cardUp()}.
	 */
	private void gameOver() {
		// important: check if the player is continuing with his move
		if (this.currentPlayer.getCurrentMove().isDone()) {
			this.currentScreen = Controller.GAME_OVER;
		}
	}

	/**
	 * Switch to the next player. Gives some time for the user to see what card
	 * they picked before it switches
	 */
	public void nextPlayer() {

		int location = this.players.indexOf(this.currentPlayer) + 1;
		if (location == this.players.size())
			location = 0;
		this.currentPlayer = this.players.get(location);

		this.textViews.getCurrentPlayerTextView().setText(
				"Current Player : " + this.currentPlayer.getName());
		this.textViews.getHandStatusTextView().setText(
				"# of Cards in Hand : "
						+ this.currentPlayer.countCardsInHands());
	}

	/**
	 * Actions to perform when a card is touched
	 */
	private void cardDown() {
		this.activeCard = null;// reset active card

		// if card is in hand....
		for (Card card : this.currentPlayer.getCardsInHand()) {
			Point check = card.isTouched(this.touchPoint);
			if (check != null) {
				this.activeCard = card.getName();
				this.currentPlayer.bringCardToFront(card); // Important: bring
															// the active card
															// to front
				card.magnify();
				break;
			}
		}
		// if card is on the played cards deck.....
		Card card = this.topPlayedCard;
		Point check = card.isTouched(this.touchPoint);
		if (check != null) {
			this.sounds.rejectSound();
		}

		// if card is on the deck of unplayed cards
		if (this.cardBack.isTouched(this.touchPoint) != null) {
			if (!this.currentPlayer.getCurrentMove().isDone()) { // Block
																	// players
																	// waiting
																	// to press
																	// PASS
																	// PHONE TO
																	// NEXT
																	// PLAYER
																	// button
																	// from
																	// picking
																	// cards
																	// //TODO
				this.currentPlayer.pickCard();
				this.buttons.getContinueButton().activate();

				this.currentPlayer.getCurrentMove().setDone(true);
				this.textViews.getHandStatusTextView().setText(
						"# of Cards in Hand : "
								+ this.currentPlayer.countCardsInHands());
			} else {
				String msg = FGLMessage.getMoveNotAllowed(LN);
				Toast.makeText(this.context, msg , Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	/**
	 * Performs necessary action when a button is touched
	 */
	private void buttonDown() {

		// START BUTTON (determine 1st player, put cards on table, distribute
		// cards)
		if (this.buttons.getStartButton().isTouched(this.touchPoint) != null) {
			this.buttons.getStartButton().deactivate();
			this.currentScreen = Controller.GAME_SCREEN;
			sounds.startSound();
			this.currentPlayer = players.get(0); // Randomize this maybe ?

			this.textViews.getCurrentPlayerTextView().setText(
					"Current Player : " + this.currentPlayer.getName());

			this.addCardDeck();
			this.serveCards();
			this.buttons.getBackButton().activate();
			this.textViews.getHandStatusTextView().setText(
					"# of Cards in Hand : "
							+ this.currentPlayer.countCardsInHands());
		}

		// BACK BUTTON (go back to the main screen)
		else if (this.buttons.getBackButton().isTouched(this.touchPoint) != null) {
			this.resetGame();
			this.currentScreen = Controller.START_SCREEN;
			this.buttons.getBackButton().deactivate();
			this.buttons.getStartButton().activate();
		}

		// CONTINUE BUTTON
		else if (this.buttons.getContinueButton().isTouched(this.touchPoint) != null
				&& this.currentPlayer.getCurrentMove().isDone()) {
			this.currentPlayer.setCurrentMove(new Move()); // refresh move
			this.buttons.getContinueButton().deactivate();
			this.blankScreenTransition = true;
		}

	}

	/**
	 * Show the card deck and make cards available for player to pick if
	 * necessary
	 **/
	private void addCardDeck() {
		this.cardBack = new Card(this.context, "--", R.drawable.card_back, this);
		this.cardBack.activate();
		this.layout.setPosition(cardBack, 0.9, 0.7);
	}

	/** Reset the game elements **/
	private void resetGame() {

		// return all cards that are currently in player hands
		for (int i = 0; i < this.players.size(); i++) {
			this.players.get(i).returnCards();
			this.players.get(i).setCurrentMove(new Move()); // refresh player
															// moves
		}

		// return played cards to the main card deck
		for (Card card : this.playedCards) {
			this.cards.putBackCard(card);
		}
		this.playedCards.clear();

		this.textViews.reset();
	}

	/**
	 * Get the card on the top of the played card deck
	 * 
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
	 * Gets the current Player. (Used by Rules class to retrieve a player from
	 * the controller)
	 * 
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
	 * 
	 * @return GameBoardLayout
	 */
	public GameBoardLayout getLayout() {
		return this.layout;
	}

	/**
	 * Count the total number of cards in all the players hands (Used for
	 * debugging)
	 * 
	 * @return the number of cards
	 */
	private int countNumCardsAllPlayers() {
		int sum = 0;
		for (Player player : this.players) {
			sum = sum + player.countCardsInHands();
		}
		return sum;
	}

}