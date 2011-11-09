package fgl.cards;

import java.util.HashMap;
import android.content.Context;
import android.graphics.Color;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;

/**
 * The <code>CardBank</code> class represents a collection of {@link Card Cards} that are used in the game application. This
 * class extracts images from the {@link R.drawable Android R.drawable} class and assigns them to appropriate card.
 * <p>
 * The cards are stored in a hashmap and appropriate accessor methods are provided. The {@link Controller Controller}
 * class collects necessary buttons from an instance this class in a fashion that promotes recyling of the cards.
 * 
 * <p><i>Copyright (c) 1998, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under t he 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution.</i></p>
 * 
 * @author Fumbani Chibaka
 * @version 1.0, 10/28/2011
 * @see <a href="http:chibaka.com">Fumba Game Lab</a>
 */

public class TextViewBank {

	/** Collection of all text views used in this application **/
	private HashMap <Integer, TextView> textViewMap;

	/** Android interface that provides the global information of the application. **/
	private Context context;

	/** Key for the text view that displays the current player **/
	public static final int CURRENT_PLAYER = 1;

	/** Key for the text view that displays the number of cards in the current players hands **/
	public static final int CARDS_IN_HAND = 2;

	/** Key for the text view that displays the number of cards remaining on the main stack **/
	public static final int DECK_STATUS  = 3;

	/** Key for the text view that shows when a new player gets the device and is ready to make a move **/
	public static final int PLAYER_TRANSITION  = 4;
	
	/** The gameboard layout object **/
	private GameBoardLayout layout;

	/**
	 * Initiates sound bank and populates it with sounds from applications resources
	 * @param context application-specific resources and classes
	 * @param controller can be visualiased as the person in control of the card deck
	 * @see <a href="http://developer.android.com/reference/android/content/Context.html">Context (Android API) </a>
	 */
	TextViewBank(Context context, GameBoardLayout layout)
	{
		this.context = context;
		this.textViewMap = new HashMap <Integer, TextView>();
		this.layout = layout;
		this.initialise();
	}

	/**
	 * Makes the textview objects that are ready to be used in the application (Controller)
	 * @see R.drawable
	 */
	private void initialise() {

		//add textview which will display the current player text
		TextView currPlayerTextView = new TextView(context);
		currPlayerTextView.setId(TextViewBank.CURRENT_PLAYER);
		currPlayerTextView.setBackgroundColor(Color.YELLOW);
		currPlayerTextView.setTextColor(Color.BLUE);
		currPlayerTextView.setWidth(PlayingCardsActivity.width / 3); //width of the text view
		currPlayerTextView.setPadding(5, 0, 5, 0);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT );
		currPlayerTextView.setLayoutParams(params);
		this.textViewMap.put(TextViewBank.CURRENT_PLAYER, currPlayerTextView);

		//add textview which will display the number of cards in the players hands
		TextView handCardsTextView = new TextView(context);
		handCardsTextView.setId( TextViewBank.CARDS_IN_HAND);
		handCardsTextView.setBackgroundColor(Color.WHITE);
		handCardsTextView.setTextColor(Color.BLACK);
		handCardsTextView.setWidth(PlayingCardsActivity.width / 3);
		handCardsTextView.setHeight(30);
		handCardsTextView.setPadding(5, 0, 5, 0);
		RelativeLayout.LayoutParams params2 =new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT );
		params2.addRule(RelativeLayout.RIGHT_OF, currPlayerTextView.getId());
		handCardsTextView.setLayoutParams(params2);
		this.textViewMap.put(TextViewBank.CARDS_IN_HAND, handCardsTextView);

		//add textview which will display the number of cards remaining on the main deck
		TextView deckStatus = new TextView(context);
		deckStatus.setId( TextViewBank.DECK_STATUS);
		deckStatus.setBackgroundColor(Color.GREEN);
		deckStatus.setTextColor(Color.RED);
		deckStatus.setWidth(PlayingCardsActivity.width / 3);
		deckStatus.setHeight(30);
		deckStatus.setPadding(5, 0, 5, 0);
		RelativeLayout.LayoutParams params3 =new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT );
		params3.addRule(RelativeLayout.RIGHT_OF, handCardsTextView.getId());
		deckStatus.setLayoutParams(params3);
		this.textViewMap.put(TextViewBank.DECK_STATUS, deckStatus);

		//add text view that shows message when a player gets the device and is ready to make his/her move
		TextView blankScreenMsg = new TextView(context);
		blankScreenMsg.setId(TextViewBank.PLAYER_TRANSITION);
		blankScreenMsg.setPadding(5, 0, 5, 0);
		RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT );
		params4.setMargins(100, 200, 10, 10); //left,top,right, bottom
		params4.addRule(RelativeLayout.BELOW, currPlayerTextView.getId());
		blankScreenMsg.setLayoutParams(params4);
		this.textViewMap.put(TextViewBank.PLAYER_TRANSITION, blankScreenMsg);
		
		this.layout.addView(currPlayerTextView);
		this.layout.addView(handCardsTextView);
		this.layout.addView(deckStatus);
		this.layout.addView(blankScreenMsg);
	}

	/**
	 * Retrieves the textview that displays the name of the current player on the applications screen
	 * @return TextView
	 */
	public TextView getCurrentPlayerTextView() {
		return this.textViewMap.get(TextViewBank.CURRENT_PLAYER);
	}

	/**
	 * Retrieves the text view that displays the message when the players are transitioning
	 * @return TextView
	 */
	public TextView getPlayerTransitionTextView() {
		return this.textViewMap.get(TextViewBank.PLAYER_TRANSITION);
	}

	/**
	 * Retrieves the text view that displays number of cards remaining on the non played card deck
	 * @return TextView
	 */
	public TextView getDeckStatusTextView() {
		return this.textViewMap.get(TextViewBank.DECK_STATUS);
	}

	/**
	 * Resets all text view values to default
	 */
	public void reset() {
		this.getCurrentPlayerTextView().setText("");
		this.getDeckStatusTextView().setText("");
		this.getHandStatusTextView().setText("");
		this.getPlayerTransitionTextView().setText("");
	}

	/**
	 * Retrieves the text view that shows the number of cards currently in the players hands 
	 * @return TextView
	 */
	public TextView getHandStatusTextView() {
		return this.textViewMap.get(TextViewBank.CARDS_IN_HAND);
	}



}
