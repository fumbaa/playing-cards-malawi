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
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameBoardLayout extends LinearLayout {
	
	/** text used for printing debugging messages to device screen **/
	private TextView debugText;

	/**
	 * Uses linear layout to arrange game elements
	 * @param context
	 */
	public GameBoardLayout(Context context)
	{
		super(context);
		this.setOrientation(LinearLayout.VERTICAL);

		//add debug text
		this.debugText = new TextView(context);
		debugText.setText("System initiated ....");
		this.addView(debugText);

		//add container for game elements (cards and buttons)
		Controller board = new Controller(context);
		this.addView(board);
	}


	/**
	 * Gets the debug text view
	 * @return debugText A text view that is used for debugging
	 */
	public TextView getDebugTextView()
	{
		return debugText;
	}

}
