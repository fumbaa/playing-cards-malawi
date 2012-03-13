package fumba.cards;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Series of events to happen when players are switching.
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

public class PlayerTransitionActivity extends Activity {

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

		String currentPlayerName = GameTableActivity.getCurrentPlayer()
				.getName();

		RelativeLayout testing = new RelativeLayout(getApplicationContext());
		RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		TextView tv = new TextView(this);
		tv.setId(8888);
		tv.setLayoutParams(lparams);
		tv.setText(currentPlayerName
				+ " , please press the button once you are ready to see your cards. ");

		testing.addView(tv);

		Button button = new Button(getApplicationContext());
		button.setText(currentPlayerName + ", Click to continue... ");
		RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params2.addRule(RelativeLayout.BELOW, tv.getId());
		button.setLayoutParams(params2);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				//Switch to next player and unlock the cards
				GameTableActivity.getController().switchToNextPlayer();
				GameTableActivity.getController().setLockCards(false);
				
				setResult(Activity.RESULT_OK, new Intent());
				finish();
			}
		});
		testing.addView(button);

		this.setContentView(testing);
	}

}
