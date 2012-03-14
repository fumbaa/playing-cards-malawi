package fumba.cards;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * The <code>GamePanel</code> manages the game application. This class controls
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

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback,
		LanguageConstants, GeneralConstants {

	/** Thread that controls drawing of elements on the panel **/
	private GamePanelThread thread;

	/** the card on the very top of the played card stack **/
	private Card topPlayedCard;

	/** interface to global information about an application environment */
	private Context context;

	/** collection of played cards */
	private ArrayList<Card> playedCards = new ArrayList<Card>();

	/** currently selected card */
	private String activeCard;

	/** The coordinates of the point which is touched on the screen */
	private Point touchPoint;

	/** Game sound bank **/
	private SoundBank sounds;

	/** Game cards bank **/
	private CardBank cards;

	/** Current player **/
	private Player currentPlayer;

	/** Card that is turned upside down **/
	private Card cardBack;

	/** List of players currently in the game **/
	private List<Player> players = new ArrayList<Player>();

	/** A framelayout that organizes all the graphical game elements **/
	private GamePanelLayout layout;

	/** Total number of cards available for this game application **/
	@SuppressWarnings("unused")
	private int totalNumCards;

	/** Textviews that are used to display text for this application **/
	private TextViewBank textViews;

	/**
	 * The number of cards to be served to the players at the beginning of the
	 * game
	 **/
	private int numOfCardsServed;

	/** Game Language **/
	private String LN = CHICHEWA;

	/** Cards that need support after they are played **/
	private String[] repeatCards = REPEAT_CARDS_1;

	/**
	 * Collection of listeners for all the buttons that are dynamically added to
	 * the parent activity
	 **/
	private ButtonListeners gameBoardListeners;

	/** Collection of dynamic buttons **/
	private ButtonBank buttonBank;

	/** Activity that houses the Controller elements **/
	private Activity gameTableActivity;

	/** Flag to lock cards from being moved **/
	private boolean lockCards = false;

	/**
	 * Starts the game with a welcome screen where user selects desired options
	 * and initiates the game. Card objects are also created in this
	 * constructor.
	 * 
	 * @param context
	 *            interface to global information about an application
	 *            environment
	 */
	public GamePanel(GamePanelLayout layout, Activity gameTableActivity) {
		super(layout.getContext());

		// add the Panel to the SurfaceHolder for a callback
		getHolder().addCallback(this);
		this.thread = new GamePanelThread(this);

		// enable touch events to be processed on the panel
		this.setFocusable(true);

		this.gameBoardListeners = layout.getGameBoardListeners();
		this.buttonBank = layout.getButtonBank();
		this.setGameTableActivity(gameTableActivity);

		this.numOfCardsServed = 2;
		this.touchPoint = new Point();
		this.context = this.getContext();
		this.layout = layout;

		// Make elements available to the controller
		this.sounds = new SoundBank(this.context);
		this.cards = new CardBank(this.context, this);
		this.textViews = new TextViewBank(this.context, this.layout);

		// LOG INFORMATION FOR INITIAL VALUES (for debugging )
		this.totalNumCards = cards.countCards();

		// show main menu
		// TODO this.addMainMenu();

		String playerName1 = "Fumbani";
		String playerName2 = "Felix";
		// String playerName3 = "DiwiDiwi";

		Player p1 = new HumanPlayer(playerName1, this);
		Player p2 = new HumanPlayer(playerName2, this);
		// Player p3 = new HumanPlayer(playerName3, this);

		this.players.add(p1);
		this.players.add(p2);
		// this.players.add(p3);

		sounds.startSound();
		// TODO Randomize this maybe ?
		this.currentPlayer = players.get(0);

		this.addCardDeck();
		this.serveCards();
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
		this.drawCards(canvas);
		//this.showGameStatus(canvas);
	}

	/**
	 * Draw All Cards on the game board
	 * 
	 * @param canvas
	 */
	private void drawCards(Canvas canvas) {
		canvas.drawBitmap(this.cardBack.getBitmap(), this.cardBack.getX(),
				this.cardBack.getY(), null);
		
		for (Card card : this.playedCards) {
			canvas.drawBitmap(card.getBitmap(), card.getX(), card.getY(), null);
		}
		
		for (Card card : this.currentPlayer.getCardsInHand()) {
			canvas.drawBitmap(card.getBitmap(), card.getX(), card.getY(), null);
		}
	}

	/**
	 * Used for debugging: Shows the current game status
	 * 
	 * @param canvas
	 */
	private void showGameStatus(Canvas canvas) {
		/*
		 * this.textViews.getCurrentPlayerTextView().setText(
		 * "Current Player : " + this.currentPlayer.getName());
		 * this.textViews.getHandStatusTextView().setText(
		 * "# of Cards in Hand : " + this.currentPlayer.countCardsInHands());
		 */
	}

	/** Retrieves the next player **/
	private Player getNextPlayer() {
		// TODO: add reverse and forward moves
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

		synchronized (this.thread.getSurfaceHolder()) {
			if (!this.lockCards) {
				switch (eventAction) {

				case MotionEvent.ACTION_UP:
					if (this.activeCard != null)
						this.cardReleased();
					break;

				case MotionEvent.ACTION_DOWN:
					this.cardTouched();
					break;

				case MotionEvent.ACTION_MOVE:
					if (this.activeCard != null)
						this.cardMove();
					break;
				}
			}
		}
		// this.invalidate();
		return true;
	}

	/**
	 * Actions to perform when a card is touched
	 */
	private void cardTouched() {
		this.activeCard = null;// reset active card

		// if card is in hand....
		for (Card card : this.currentPlayer.getCardsInHand()) {
			Point check = card.isTouched(this.touchPoint);
			if (check != null) {
				this.activeCard = card.getName();
				this.currentPlayer.bringCardToFront(card);
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
			if (!this.currentPlayer.hasValidMoves()) {
				this.currentPlayer.pickCard();
				this.showTransitionScreen();
			} else {
				// Player has valid moves force them...
				String msg = FGLMessage.getMoveNotAllowedMsg(LN);
				Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * This method gives the current player a chance to look at their current
	 * card before they pass the device on to an opponent. Use when a player has
	 * no valid moves and picks a card instead.
	 */
	private void showTransitionScreen() {
		this.lockCards = true;
		Button button = this.buttonBank.getButtonMap().get(
				ButtonConstants.CONTINUE);
		button.setOnClickListener(this.gameBoardListeners);
		this.layout.addView(button);
		// Update current player in the Application Entry Activity
		GameTableActivity.setCurrentPlayer(this.getNextPlayer());
	}

	/**
	 * Actions to perform when a card is moved.
	 */
	private void cardMove() {
		if (this.activeCard != null) {
			Card card = this.currentPlayer.getCard(this.activeCard);
			card.rescale();
			card.setX(touchPoint.x - card.getCenter().x);
			card.setY(touchPoint.y - card.getCenter().y);
		}
	}

	/**
	 * Actions to perform when a card is dropped/ released
	 */
	private void cardReleased() {
		if (this.activeCard != null) {

			Card card = this.currentPlayer.getCard(this.activeCard);

			if (this.currentPlayer.isHuman()) {
				Move move = Rules.checkMove(card);
				/* Valid Move */
				if (move.getValidity()
						&& this.topPlayedCard.isTouched(this.touchPoint) != null) {
					this.currentPlayer.playCard(move);
					this.activeCard = null;

					// check if game is over....
					if (this.gameOver()) {
						// this.currentScreen = GAME_OVER;
						return;
					}
					// check if move is continued...
					if (!move.isContinued())
						this.showTransitionScreen();
					else
						Toast.makeText(this.context, "Its your turn again...",
								Toast.LENGTH_SHORT);
				}
				/* Invalid Move */
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
	 * called in {@link #cardReleased()}.
	 * 
	 * @return
	 */
	private boolean gameOver() {
		// important: check if the player is continuing with his move
		if (!this.currentPlayer.getCurrentMove().isContinued()
				&& this.currentPlayer.getCardsInHand().isEmpty()) {
			return true;
		}
		return false;
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
	@SuppressWarnings("unused")
	private void resetGame() {

		// return all cards that are currently in player hands
		for (int i = 0; i < this.players.size(); i++) {
			this.players.get(i).returnCards();
			// refresh player moves
			this.players.get(i).setCurrentMove(new Move());
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
	 * @return GamePanelLayout
	 */
	public GamePanelLayout getLayout() {
		return this.layout;
	}

	/**
	 * Count the total number of cards in all the players hands (Used for
	 * debugging)
	 * 
	 * @return the number of cards
	 */
	@SuppressWarnings("unused")
	private int countNumCardsAllPlayers() {
		int sum = 0;
		for (Player player : this.players) {
			sum = sum + player.countCardsInHands();
		}
		return sum;
	}

	/**
	 * List of cards that qualify for repeat move as determined in game menu
	 * 
	 * @return Array list of card suites/numbers
	 */
	public String[] getRepeatCards() {
		return this.repeatCards;
	}

	public void setGameTableActivity(Activity gameTableActivity) {
		this.gameTableActivity = gameTableActivity;
	}

	public Activity getGameTableActivity() {
		return gameTableActivity;
	}

	/**
	 * Switches to the next player
	 */
	public void switchToNextPlayer() {
		this.currentPlayer = this.getNextPlayer();
	}

	/** Update the lock status of the cards- used during player transition **/
	public void setLockCards(boolean lockStatus) {
		this.lockCards = lockStatus;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	public void surfaceCreated(SurfaceHolder holder) {
		this.thread.setRunning(true);
		this.thread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// simply copied from sample application LunarLander:
		// we have to tell thread to shut down & wait for it to finish, or else
		// it might touch the Surface after we return and explode
		boolean retry = true;
		this.thread.setRunning(false);
		while (retry) {
			try {
				this.thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// we will try it again and again...
			}
		}
	}
}
