package fumba.cards;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
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

public class Controller extends RelativeLayout implements LanguageConstants,
		GeneralConstants, ScreenConstants {

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
	private String LN = CHICHEWA;

	/** Cards that need support after they are played **/
	private String[] repeatCards = REPEAT_CARDS_1;

	/**
	 * Starts the game with a welcome screen where user selects desired options
	 * and initiates the game. Card objects are also created in this
	 * constructor.
	 * 
	 * @param context
	 *            interface to global information about an application
	 *            environment
	 */
	public Controller(Context context, GameBoardLayout gameTableActivity) {
		super(context);

		this.numOfCardsServed = 2;

		this.touchPoint = new Point();
		this.context = context;
		this.currentScreen = START_SCREEN;
		this.layout = gameTableActivity;
		this.setFocusable(true);

		// Make elements available to the controller
		this.buttons = new CustomButtonBank(this.context, this);
		this.sounds = new SoundBank(this.context);
		this.cards = new CardBank(this.context, this);
		this.textViews = new TextViewBank(this.context, this);

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

		/* Application welcome Screen */
		if (this.currentScreen == START_SCREEN)
			this.buttons.drawStartButton(canvas);

		/* GamePlay Screens */
		else if (ArrayUtils.contains(GAMEPLAY_SCREENS, this.currentScreen)) {
			this.buttons.drawBackButton(canvas);
			if (this.currentScreen == TRANSITION_SCREEN) {
				this.nextPlayer = this.getNextPlayer();
				this.printTransitionScreenMessage();
				Tools.debug(CURRENT_PLAYER_SUMMARY, textViews, null, 0, null,
						0, nextPlayer);
			} else {
				this.drawCards(canvas);
				if (this.currentScreen == REVIEW_SCREEN)
					this.buttons.drawContinueButton(canvas);
			}
		}

		/* GameOver Screen */
		else if (this.currentScreen == GAME_OVER) {
			this.textViews.getPlayerTransitionTextView().setText(
					"Game Over !!!!");
			this.buttons.drawBackButton(canvas);
		}
		Tools.debug(USED_CARDS_SUMMARY, textViews, cards, totalNumCards,
				playedCards, countNumCardsAllPlayers(), null);
	}

	/**
	 * Prints the instructions for the next player to touch screen during device
	 * exchange
	 * 
	 * @param player
	 */
	private void printTransitionScreenMessage() {
		String message = FGLMessage.getTouchScreenMsg(LN);
		this.textViews.getPlayerTransitionTextView().setText(
				this.nextPlayer.getName() + ", " + message);
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

		// Only move cards on the game screen when the player is not waiting to
		// press (PASS TO NEXT PLAYER) button
		Boolean canMove = (this.currentScreen == PLAY_SCREEN)
				&& !this.currentPlayer.getCurrentMove().isContinued();

		switch (eventAction) {

		case MotionEvent.ACTION_UP:
			if (canMove && this.activeCard != null)
				this.cardReleased();
			break;

		case MotionEvent.ACTION_DOWN:
			this.screenDown();
			if (canMove)
				this.cardTouched();
			this.buttonDown();
			break;

		case MotionEvent.ACTION_MOVE:
			if (canMove && this.activeCard != null)
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
		if (this.currentScreen == TRANSITION_SCREEN) {
			this.currentScreen = PLAY_SCREEN;
			this.nextPlayer();
			this.textViews.getPlayerTransitionTextView().setText("");
		}
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
				this.textViews.getHandStatusTextView().setText(
						"# of Cards in Hand : "
								+ this.currentPlayer.countCardsInHands());
				this.currentScreen = REVIEW_SCREEN;
			} else {
				// Player has valid moves force them...
				String msg = FGLMessage.getMoveNotAllowedMsg(LN);
				Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * Actions to perform when a card is moved.
	 */
	private void cardMove() {
		if (this.currentScreen == PLAY_SCREEN)
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
					Tools.debug(CURRENT_MOVE_UPDATE, textViews, null, 0, null,
							0, currentPlayer);
					// check if game is over....
					if (this.gameOver()) {
						this.currentScreen = GAME_OVER;
						return;
					}
					// check if move is continued...
					if (!move.isContinued())
						this.currentScreen = REVIEW_SCREEN;
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
	 * Performs necessary action when a button is touched
	 */
	private void buttonDown() {

		// START BUTTON (determine 1st player, put cards on table, distribute
		// cards)
		if (this.buttons.getStartButton().isTouched(this.touchPoint) != null) {
			this.buttons.getStartButton().deactivate();
			this.currentScreen = PLAY_SCREEN;
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
			this.currentScreen = START_SCREEN;
			this.buttons.getBackButton().deactivate();
			this.buttons.getStartButton().activate();
		}

		// CONTINUE BUTTON
		else if (this.buttons.getContinueButton().isTouched(this.touchPoint) != null) {
			this.currentPlayer.setCurrentMove(new Move()); // reset move
			this.buttons.getContinueButton().deactivate();
			this.currentScreen = TRANSITION_SCREEN;
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

	/**
	 * List of cards that qualify for repeat move as determined in game menu
	 * 
	 * @return Array list of card suites/numbers
	 */
	public String[] getRepeatCards() {
		return this.repeatCards;
	}

}