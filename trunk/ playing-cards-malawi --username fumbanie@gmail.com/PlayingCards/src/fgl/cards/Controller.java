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

	/** interface to global information about an application environment */
	private Context context; 

	/** collection of all the cards in the deck */
	private List<Card> cardDeck = new ArrayList<Card>();

	/** collection of cards served to player */
	private List<Card> servedCards = new ArrayList<Card>();

	/** card letters */
	private final String [] CARD_LETTERS = {"A","2","3","4","5","6","7","8","9","10", "J", "K", "Q"};

	/** card suites: Spade, Heart, Diamond, Club */
	private final String [] CARD_SUITES = {"S","H","D","C"}; 

	/** currently selected card */
	private String activeCard;

	/** offset position relative to the touch point */
	private Point offset = new Point();

	/** Collection of buttons used in the game */
	private List<CustomButton> buttons = new ArrayList<CustomButton>();

	/** The coordinates of the point which is touched on the screen */
	private Point touchPoint;
	
	/** Game sound bank **/
	private SoundBank sounds;


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
		this.setFocusable(true);
		
		sounds = new SoundBank(context);
		this.makeCards();
		this.showMainMenu();
	}

	/**
	 * Prepares and shows the elements for the main menu.
	 */
	private void showMainMenu() {
		CustomButton button = new CustomButton(this.context, new Point(), "main_btn");
		Point center = button.getCenter();
		Point midpoint = new Point ( PlayingCardsActivity.width / 2 - center.x , PlayingCardsActivity.height / 2 - center.y);
		button.setPosition(midpoint);
		buttons.add(button);
	}

	/**
	 * Picks a random card from the card deck. 
	 * @return	A random card from the card deck 
	 */
	private Card pickRandomCard() {
		return this.cardDeck.remove( new Random().nextInt( this.cardDeck.size() ));
	}


	/**
	 * Makes 54 card objects that are to be reused throughout gameplay. 
	 */
	private void makeCards() {
		//Step 1: A
		for (int s = 0; s < this.CARD_SUITES.length; s++)
			for (int l = 0; l < this.CARD_LETTERS.length; l++)
				this.cardDeck.add(new Card(this.context, this.CARD_LETTERS[l] + this.CARD_SUITES[s]) );
		//Step 2: Add two jokers to the collection
		this.cardDeck.add( new Card(this.context,"*B")); //B = Black and White
		this.cardDeck.add( new Card(this.context,"*C")); //C = Color
	}


	/**
	 * Takes cards from the deck and puts them in player hands
	 */
	private void serveCards() {

		Point point1 = new Point(100,100); 
		Point point2 = new Point(200,100); 
		Point point3 = new Point(300,100);

		Card card1 = this.pickRandomCard();
		card1.setDefaultPosition(point1);
		card1.setCurrentPosition(point1);
		servedCards.add(card1);

		Card card2 = this.pickRandomCard();
		card2.setDefaultPosition(point2);
		card2.setCurrentPosition(point2);
		servedCards.add(card2);

		Card card3 = this.pickRandomCard();
		card3.setDefaultPosition(point3);
		card3.setCurrentPosition(point3);
		servedCards.add(card3);

		//Print Serving Summary to Eclipse log
		Tools.catLog( "Random Card 1  : " + card1.getName());
		Tools.catLog( "Random Card 2  : " + card2.getName());
		Tools.catLog( "Random Card 3  : " + card3.getName());
		Tools.catLog( "# Served Cards : " + this.servedCards.size());
		Tools.catLog( "# Cards in Deck: " + this.cardDeck.size());
	}

	/**
	 * Overrides View.ondraw method. Draws images on the canvas
	 * @param canvas The canvas onto which the bitmaps will be drawn            
	 * @see	View
	 */
	@Override protected void onDraw(Canvas canvas) {
		
		//draw buttons
		for (CustomButton button: this.buttons){
			canvas.drawBitmap( button.getBitmap(), button.getX(), button.getY(), null); }
		
		//draw cards
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
		case MotionEvent.ACTION_DOWN:
			this.buttonDown();
			this.cardDown();
			break; 

		case MotionEvent.ACTION_MOVE:
			this.cardMove();
			break; 

		case MotionEvent.ACTION_UP: 
			this.cardUp();
			break; 
		} 

		this.invalidate(); //Redraw the canvas
		return true; 
	}

	/**
	 * Actions to perform when a card is moved.
	 */
	private void cardMove() {
		if (this.activeCard != "")
		{
			Card card = this.getServedCard(this.activeCard);
			card.setX(touchPoint.x - card.getCenter().x);
			card.setY(touchPoint.y - card.getCenter().y);
			
			//FIXME: playing sound
			sounds.explode();
			PlayingCardsActivity.printDebug( "Card : " + card.getName() + "  Position : " + card.getX() + "," + card.getY() );
		}
	}

	/**
	 * Actions to perform when a card is dropped
	 */
	private void cardUp() {
		if (activeCard != "")  
			this.getServedCard(activeCard).resetPosition();
	}


	/**
	 * Actions to perform when a card is touched
	 */
	private void cardDown() {
		this.activeCard = "";//reset active card
		for (Card card : this.servedCards) 
		{
			Point check = card.isTouched(touchPoint);
			if ( check != null ){
				this.offset = check;
				this.activeCard = card.getName();
				this.bringToFront(card); //Important: bring the active card to front
				card.setToCenter( this.offset );
				break;
			}
		}		
	}

	/**
	 * Performs necessary action when a button is touched
	 */
	private void buttonDown() {
		//Check to see if a button was pressed
		for (CustomButton button : this.buttons) 
		{
			Point check = button.isTouched(touchPoint);
			if ( check != null){
				buttons.remove(button);
				this.invalidate();

				//Serve cards at this point
				this.serveCards();
				break;
			}
		}
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


}