package fumba.cards;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class ButtonListeners implements View.OnClickListener {

	// Activity that contains the UI
	private Activity activity;

	public ButtonListeners(Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
	}

	public void onClick(View view) {

		// Button 1 Actions
		if (view.getId() == 888) {
			// Create an Intent to start another activity.
			// 
			//Intent a2intentProtocol = new Intent(view.getContext(), Activity2.class);
			//Used for intent protocols that are designed to return a result
			//When called inside initial onCreate... window is not displayed until a result is returned when requestCode >= 0
		//	this.activity.startActivityForResult(a2intentProtocol, Activity.RESULT_CANCELED);
			//TODO startActivity(Intent) - activity is not launched as a sub activity
		}

		// Button 2 Actions
		if (view.getId() == 555) {
			this.activity.setResult(Activity.RESULT_OK, new Intent());
			//propagate the activity back to the one who called it using onActivityResult()
			this.activity.finish();
		}

	}

}
