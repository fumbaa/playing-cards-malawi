package fgl.cards;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
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
		
		//Set hardware volume buttons to control this applications volume
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);  
		
		Display display = this.getWindowManager().getDefaultDisplay(); 
		width = display.getWidth();
		height = display.getHeight();
		
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		Context context = this.getApplication();
		elements = new GameBoardLayout(context);
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
	
	/**
	 * Used to print debugging text on the android device screen. This method is called by  {@link fgl.cards.Tools.printDebug}
	 * @param  text The text to be printed on the android device screen
	 * @see fgl.cards.Tools.printDebug
	 */
	public static void updateCurrPlayerName(String text)
	{
		if (elements.getPlayerNameView().getText() != text)
			elements.getPlayerNameView().setText("Current Player : " + text);
	}
	
	/**
	 * Used to print debugging text on the android device screen. This method is called by  {@link fgl.cards.Tools.printDebug}
	 * @param  text The text to be printed on the android device screen
	 * @see fgl.cards.Tools.printDebug
	 */
	public static void updateDeckStatus(String text)
	{
		if (elements.getDeckView().getText() != text)
			elements.getDeckView().setText("Remaining : " + text);
	}

	/**
	 * Resets the notification text to the initial text views
	 */
	public static void reset() {
		elements.getDeckView().setText("Card Deck Status");
		elements.getPlayerNameView().setText("--");
		elements.getDebugTextView().setText("Fumba Game Lab");
	}
}