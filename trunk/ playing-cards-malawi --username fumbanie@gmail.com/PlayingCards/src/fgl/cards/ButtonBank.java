package fgl.cards;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Point;

/**
 * Collection of all the buttons used in the game
 * <p><i>Copyright (c) 1998, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution.</i></p>
 * 
 * @author Fumbani Chibaka, 
 * @version 1.0, 10/28/2011
 * @see <a href="http:chibaka.com">Fumba Game Lab</a>
 */

public class ButtonBank {

	/** The collection of buttons **/
	private HashMap<Integer, CustomButton> buttonMap = new HashMap<Integer, CustomButton>(); 

	/** Start button **/
	public static int START_BUTTON = 1;

	/** Back button **/
	public static int BACK_BUTTON = 2;

	/**   **/
	private Context context;

	/** Button constructor **/
	ButtonBank(Context context)
	{
		this.context = context;
		this.initialise();
	}


	/** Start collecting the buttons **/
	public void initialise()
	{
		//Start button
		Point startPoint = new Point(0,0);
		CustomButton startBtn = new CustomButton(this.context, startPoint,"start_button", R.drawable.start_button);
		buttonMap.put(START_BUTTON, startBtn);

		//Back button
		CustomButton backBtn = new CustomButton(this.context, startPoint,"start_button", R.drawable.back_button);
		buttonMap.put(BACK_BUTTON, backBtn);
	}


	/** Retrieve the start button **/
	public CustomButton getStartButton()
	{
		return buttonMap.get(START_BUTTON);
	}

	/** Retrieve the back button **/
	public CustomButton getBackButton()
	{
		return buttonMap.get(BACK_BUTTON);
	}

}
