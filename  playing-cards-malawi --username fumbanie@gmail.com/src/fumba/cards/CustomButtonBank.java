package fumba.cards;

import java.util.HashMap;
import android.content.Context;

/**
 * The <code>CustomButtonBank</code> class represents a collection of {@link CustomButton Custom Buttons} that are used in the game application. This
 * class extracts images from the {@link R.drawable Android R.drawable} class and assigns them to appropriate buttons.
 * <p>
 * The buttons are stored in a hashmap and appropriate accessor methods are provided. The {@link Controller Controller}
 * class collects necessary buttons from an instance this class in a fashion that promotes recyling of the button resources.
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

public class CustomButtonBank {

	/** The collection of buttons **/
	private HashMap<Integer, CustomButton> buttonMap = new HashMap<Integer, CustomButton>(); 

	/** A hashmap key for the button that starts the game. This buttons shows on the main welcome screen. **/
	public static int START_BUTTON = 1;

	/** A hashmap key for the button that takes users to the previous screen when they are navigating the game application. **/
	public static int BACK_BUTTON = 2;
	
	/** A hashmap key for the button that shows when the current player is ready to pass the android device to the next player during gameplay. **/
	private static final int CONTINUE_BUTTON = 3;

	/** Android interface that provides the global information of the application. **/
	private Context context;

	/** GameBoard layout **/
	private GameBoardLayout layout;
	
	/**
	 * Constructs a new collection of <code>Custom Buttons</code> 
	 * @param context applications global information
	 * @see <a href="http://developer.android.com/reference/android/content/Context.html">Context (Android API) </a>
	 */
	CustomButtonBank(Context context, GameBoardLayout layout)
	{
		this.context = context;
		this.layout = layout;
		this.initialise();
		
	}

	/**
	 * Initialises the creation of custom buttons. 
	 * @see R.drawable
	 */
	public void initialise()
	{
		//Start button
		CustomButton startBtn = new CustomButton(this.context, "start_button", R.drawable.start_button);
		this.layout.setPosition(startBtn, 0.5, 0.5);
		buttonMap.put(START_BUTTON, startBtn);

		//Back button
		CustomButton back_button = new CustomButton(this.context, "back_button", R.drawable.back_button);
		this.layout.setPosition(back_button, 0.9 , 0.1);
		buttonMap.put(BACK_BUTTON, back_button);
		
		//Continue button
		CustomButton continueBtn = new CustomButton(this.context, "cont_button", R.drawable.continue_button);
		this.layout.setPosition(continueBtn, 0.2, 0.5);
		buttonMap.put(CONTINUE_BUTTON, continueBtn);
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

	/** Retrieve the continue button **/
	public FGLGraphic getContinueButton() {
		return buttonMap.get(CONTINUE_BUTTON);
	}

}
