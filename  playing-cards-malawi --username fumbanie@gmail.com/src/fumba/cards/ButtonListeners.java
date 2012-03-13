package fumba.cards;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

/**
 * Listeners for buttons that are dynamically added to the screen
 * 
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

public class ButtonListeners implements View.OnClickListener {

	/** Activity that contains the button **/
	private Activity activity;
	
	private GameBoardLayout layout;

	public ButtonListeners(GameBoardLayout gameBoardLayout, Activity activity) {
		this.activity = activity;
		this.layout = gameBoardLayout;
	}

	public void onClick(View view) {

		// Common Back Button
		if (view.getId() == ButtonConstants.COMMON_BACK) {
			this.activity.setResult(Activity.RESULT_OK, new Intent());
			this.activity.finish();
		}

		// Continue Button
		else if (view.getId() == ButtonConstants.CONTINUE) {
						
			// Intent starts GameTableActivity
			Intent gameTableProtocol = new Intent(view.getContext(),
					PlayerTransitionActivity.class);
			this.activity.startActivityForResult(gameTableProtocol,
					Activity.RESULT_CANCELED);
			
			this.layout.removeView(view);
		}

	}// end onClick

}
