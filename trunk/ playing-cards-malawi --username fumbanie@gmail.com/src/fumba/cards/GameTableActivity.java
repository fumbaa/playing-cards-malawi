package fumba.cards;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Makes adjustments to the game screen and initiates the main game layout.
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

public class GameTableActivity extends Activity {

	/**
	 * The current player - updated by the Controller class
	 */
	private static Player currentPlayer;
	private static Controller controller;
	
	@Override
	/**
	 * Entry point for game table activity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set hardware volume buttons to control this applications' volume
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		this.setContentView(new GameBoardLayout(this));
	}
	

	/**
	 * Gets the current player.
	 * 
	 * @return Current Player
	 */
	public static Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Sets the current player. Updated by the Controller class
	 * 
	 * @param player
	 *            Current Player
	 */
	public static void setCurrentPlayer(Player player) {
		currentPlayer = player;
	}


	public static Controller getController() {
		return controller;
	}
	
	public static void setController(Controller controll){
		controller = controll;
	}

}
