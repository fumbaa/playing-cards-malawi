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
 * This is the entry point for the Malawi Playing Cards Android application.
 * Main menu items are displayed from which the user is directed to the chosen
 * activity on selection.
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
	 * Screen width of the device
	 */
	public static int width;

	/**
	 * Screen height of the device
	 */
	public static int height;

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
	 * Triggered when the start button on the main screen is clicked *
	 * 
	 * @param view
	 *            User interface component defined by res/layout/main.xml
	 */
	public void mainButtonOnClick(View view) {
		Intent gameTableProtocol = new Intent(view.getContext(),
				GameTableActivity.class);
		this.startActivityForResult(gameTableProtocol, Activity.RESULT_CANCELED);
	}

}