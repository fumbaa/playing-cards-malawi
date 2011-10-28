package fgl.cards;
import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

/**
 * Initiates the canvas and adds a layout view which contains all graphic game elements.
 * <p><i>Copyright (c) 1998, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution.</i></p>
 * 
 * @author Fumbani Chibaka
 * @version 1.0, 10/28/2011
 * @see <a href="http:chibaka.com">Fumba Game Lab</a>
 */

public class PlayingCardsActivity extends Activity {

	/** Organises all the game elements. */
	private static GameBoardLayout elements; 
	
	/** The screen width and height of the android device running this application. */
	public static int width, height;

	/** Called when the activity is first created. */
	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Display display = this.getWindowManager().getDefaultDisplay(); 
		width = display.getWidth();
		height = display.getHeight();
		
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		elements = new GameBoardLayout(this);
		this.setContentView(elements);
	}

	/**
	 * Used to print debugging text on the android device screen. This method is called by  {@link fgl.cards.Tools.printDebug}
	 * @param  text The text to be printed on the android device screen
	 * @see fgl.cards.Tools.printDebug
	 */
	public static void printDebug(String text)
	{
		if (elements.getDebugTextView().getText() != text)
			elements.getDebugTextView().setText(text);
	}
}