package fumba.cards;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class GameTableActivity extends Activity {

	/**
   * 
   */

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

		this.setContentView(new GameBoardLayout( this));
		
		
	}

}
