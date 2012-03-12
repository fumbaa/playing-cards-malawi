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

	public ButtonListeners(Activity activity) {
		this.activity = activity;
	}

	public void onClick(View view) {

		// Button 1 Actions
		if (view.getId() == 888) {
			// Create an Intent to start another activity.
			//
			// Intent a2intentProtocol = new Intent(view.getContext(),
			// Activity2.class);
			// Used for intent protocols that are designed to return a result
			// When called inside initial onCreate... window is not displayed
			// until a result is returned when requestCode >= 0
			// this.activity.startActivityForResult(a2intentProtocol,
			// Activity.RESULT_CANCELED);
			// TODO startActivity(Intent) - activity is not launched as a sub
			// activity
		}

		// Common Back Button
		if (view.getId() ==IDConstants.COMMON_BACK) {
			this.activity.setResult(Activity.RESULT_OK, new Intent());
			this.activity.finish();
		}

	}//end onClick

}
