package fumba.cards;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class GameTableActivity extends Activity {

	/**
	 * 
	 */
	
	private Controller board;

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

		Context context = this.getApplication();
		this.board = new Controller(context, this);

		this.setContentView(this.board);
	}

	/**
	 * Sets positions for the game graphics based on the relative screen size of
	 * the device
	 **/
	public void setPosition(FGLGraphic graphic, double x, double y) {
		int height = (int) (ApplicationEntryActivity.height * y);
		int width = (int) (ApplicationEntryActivity.width * x);
		graphic.setCurrentPosition(new Point(width, height));
	}

}
