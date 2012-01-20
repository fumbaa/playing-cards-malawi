package fumba.cards;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Initiates the canvas and adds a layout view which contains all graphic game
 * elements.
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

public class ApplicationEntryActivity extends Activity {

	/**
	 * The screen width and height of the android device running this
	 * application.
	 */
	public static int width, height;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set hardware volume buttons to control this applications' volume
		this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

		// Determine the height and width of the mobile device screen
		Display display = this.getWindowManager().getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight();

		// Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// Show Landing Screen (main.xml)
		this.setContentView(R.layout.main);
	}

	/**
	 * Start Button opens an activity with the game elements ready for gameplay
	 * 
	 * @param view
	 *            Button View that triggers this method
	 */
	public void selfDestruct(View view) {
		// Intent starts GameTableActivity
		Intent gameTableProtocol = new Intent(view.getContext(),
				GameTableActivity.class);
		this.startActivityForResult(gameTableProtocol, Activity.RESULT_CANCELED);
	}
}