/**
 * 
 * Copyright (c) 2011 FUMBA GAME LAB. All rights reserved
 * 
 * Malawi Playing Cards: SetUpGameBoard.java
 * 
 * @author Fumbani Chibaka
 * @version 1.0
 * @since 0.0
 * 
 * 
 * /*******************************************************************************
 * Copyright (c) 1998, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution. 
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 ******************************************************************************
 *
 */
package fgl.cards;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Random;
import java.util.Set;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class SetUpGameBoard extends View {

	private Hashtable<String, Card> cardDeck = new Hashtable<String,Card>(); //collection of the cards
	private Hashtable<String, Card> servedCards = new Hashtable<String, Card>(); //cards served to player

	private Hashtable<String, Integer> cardKey = new Hashtable<String, Integer>();
	private static String TAG = "FGL";

	private final static String [] CARD_LETTERS = {"A","2","3","4","5","6","7","8","9","10", "J", "K", "Q"};
	private final static String [] CARD_SUITES = {"S","H","D","C"}; //Spade, Heart, Diamonds, Clubs

	private String activeCard; // variable to know which card is selected by the player
	private Point offset = new Point();
	
	private Context context;

	public SetUpGameBoard(Context context) 
	{
		super(context);
		setFocusable(true); //TODO: necessary for getting the touch events??

		this.context = context;
		this.makeCards();

		//Create cards and assign initial positions for them
		this.serveCards();
	}


	//Simple shuffle Cards...
	private Card pickRandomCard() {
		Random random_generator = new Random();
		Set<String> cardNames = cardDeck.keySet();
		Object [] array = cardNames.toArray();
		String cardName = (String) array[ random_generator.nextInt(54)];

		//TODO: Find out why it is already shuffled at this point
		Card temp = cardDeck.remove(cardName);
		return temp;
	}


	private void makeCards() {
		//add normal cards first
		for (int j = 0; j < CARD_SUITES.length; j++)
		{
			for (int i = 0; i < CARD_LETTERS.length; i++)
			{
				Card newCard = new Card(context, R.drawable.card , CARD_LETTERS[i], CARD_SUITES[j]);
				cardDeck.put(newCard.getName(), newCard);
			}

			//..then add jokers
			cardDeck.put( "*B", new Card(context, R.drawable.card,"*","B")); //black and white joker
			cardDeck.put( "*C", new Card(context, R.drawable.card,"*","C")); //color joker
		}
	}


	private void serveCards() {

		Point point = new Point(100,100);        	
		Card card = this.pickRandomCard();

		card.setDefaultPosition(point);
		card.setCurrentPosition(point);

		servedCards.put(card.getName(), card);
		
		
		//-----------------------------
		Point point2 = new Point(200,300);        	
		Card card2 = this.pickRandomCard();

		card2.setDefaultPosition(point2);
		card2.setCurrentPosition(point2);

		servedCards.put(card2.getName(), card2);

		//XXX: Print serve summary:
		Log.v(TAG, ""+ "Random Card picked : "+ card.getName());
		Log.v(TAG, ""+ "servedCards : "+ servedCards.size());
		Log.v(TAG, ""+ "cardDeck : "+ cardDeck.size());
	}

	// the method that draws the balls
	@Override protected void onDraw(Canvas canvas) {

		//draw the balls on the canvas
		Collection<Card> cards = servedCards.values();
		for (Card card : cards) {
			canvas.drawBitmap(card.getBitmap(), card.getX(), card.getY(), null);
		}

	}

	// events when touching the screen
	public boolean onTouchEvent(MotionEvent event) {
		int eventaction = event.getAction(); 

		Point touchPoint = new Point( (int)event.getX(),(int)event.getY());
		
		//TODO: Find out how switch case works in this case...

		switch (eventaction ) { 
		case MotionEvent.ACTION_DOWN: // touch down so check if the finger is on a ball
			activeCard = ""; //Reset active card


			Collection<Card> set = servedCards.values();
			// check if inside the bounds of the card

			for (Card card : set) 
			{
				Boolean check = (Boolean) Tools.isInRectangle(touchPoint, card).get(0);

				if ( check ){
					activeCard = card.getName();
					
					servedCards.get(activeCard).setX( touchPoint.x );
					servedCards.get(activeCard).setY( touchPoint.y );

					break;
				}
			}
			break; 


		case MotionEvent.ACTION_MOVE:   // touch drag with the ball
			// move the balls the same as the finger
			if (activeCard != "") 
			{	
				servedCards.get(activeCard).setX( touchPoint.x - offset.x);
				servedCards.get(activeCard).setY( touchPoint.y - offset.y);

				//FIXME: Try doing this by using context field
				PlayingCardsActivity.printDebug( activeCard);
			}
			break; 

			// touch drop - just do things here after dropping
		case MotionEvent.ACTION_UP: 
			//return cards to their initial position
			if (activeCard != "")  
			{
				Card droppedCard= servedCards.get(activeCard);
				droppedCard.resetPosition();
			}
			break; 
		} 
		// redraw the canvas
		invalidate(); 
		return true; 

	}
}