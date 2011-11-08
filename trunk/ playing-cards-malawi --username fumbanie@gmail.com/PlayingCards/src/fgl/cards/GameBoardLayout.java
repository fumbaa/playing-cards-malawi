/**
 * 
 * Copyright (c) 2011 FUMBA GAME LAB. All rights reserved
 * 
 * Malawi Playing Cards: GameBoardLayout.java
 * This class organises the layout of the game elements
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

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameBoardLayout extends RelativeLayout {

	/** text used for printing debugging messages to device screen **/
	private TextView debugText;
	private TextView currentPlayer;
	private TextView deckStatus;

	/**
	 * Uses linear layout to arrange game elements
	 * @param context
	 */
	public GameBoardLayout(Context context)
	{
		super(context);

		//add current player text
		this.currentPlayer = new TextView(context);
		this.currentPlayer.setBackgroundColor(Color.YELLOW);
		this.currentPlayer.setTextColor(Color.BLUE);
		this.currentPlayer.setText("--");
		this.currentPlayer.setId(1);
		this.currentPlayer.setPadding(5, 0, 5, 0);
		RelativeLayout.LayoutParams para = new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT );
		para.setMargins(10, 10, 10, 10); //left,top,right, bottom

		//add debug text
		this.debugText = new TextView(context);
		this.debugText.setBackgroundColor(Color.WHITE);
		this.debugText.setTextColor(Color.BLACK);
		this.debugText.setText("Fumba Game Lab");
		this.debugText.setHeight(30);
		this.debugText.setPadding(5, 0, 5, 0);
		this.debugText.setId(2);

		RelativeLayout.LayoutParams para2 =new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT );
		para2.setMargins(10, 10, 10, 10); //left,top,right, bottom
		para2.addRule(RelativeLayout.RIGHT_OF, currentPlayer.getId());

		//add debug text
		this.deckStatus = new TextView(context);
		this.deckStatus.setBackgroundColor(Color.GREEN);
		this.deckStatus.setTextColor(Color.RED);
		this.deckStatus.setText("CardDeck Status");
		this.deckStatus.setHeight(30);
		this.deckStatus.setPadding(5, 0, 5, 0);
		this.deckStatus.setId(3);

		RelativeLayout.LayoutParams para3 =new RelativeLayout.LayoutParams( LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT );
		para3.setMargins(10, 10, 10, 10); //left,top,right, bottom
		para3.addRule(RelativeLayout.RIGHT_OF, debugText.getId());

		this.addView(currentPlayer, para );
		this.addView(debugText, para2);
		this.addView(deckStatus, para3);

		//add container for game elements (cards and buttons)
		Controller board = new Controller(context);
		this.addView(board);
	}

	/**
	 * Gets the text view for the current players name
	 * @return debugText A text view that displays the current players name
	 */
	public TextView getPlayerNameView()
	{
		return this.currentPlayer;
	}

	/**
	 * Gets the text view for the current players name
	 * @return debugText A text view that displays the current players name
	 */
	public TextView getDeckView()
	{
		return this.deckStatus;
	}

	/**
	 * Gets the debug text view
	 * @return debugText A text view that is used for debugging
	 */
	public TextView getDebugTextView()
	{
		return this.debugText;
	}

	/** Sets positions for the game graphics based on the relative screen size of the device **/
	public static void setPosition(FGLGraphic graphic, double x, double y)
	{
		int height = (int) (PlayingCardsActivity.height * y);
		int width = (int) (PlayingCardsActivity.width * x);
		graphic.setCurrentPosition( new Point( width, height));
	}

}
