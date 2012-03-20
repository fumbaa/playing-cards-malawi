package fumba.cards;

import android.app.Activity;
import android.app.Application;
import android.graphics.Point;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * The Canvas on which the game table elements are added onto.
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

public class GamePanelLayout extends RelativeLayout implements ButtonConstants {

	/**
	 * Listeners for any buttons that are dynamically added to the
	 * GameTableLayout activity ONLY
	 */
	private ButtonListeners gameBoardListeners;

	/** Collections of dynamic buttons **/
	private ButtonBank buttonBank;

	/** game table controller **/
	private GamePanel controller;

	/** Textviews that are used to display text for this application **/
	private TextViewBank textViews;

	/**
	 * Adds framework game elements to the game table
	 * 
	 * @param activity
	 *            Focused activity(i.e. GameTableLayout)
	 */
	public GamePanelLayout(Activity activity) {
		super(activity.getApplication());
		Application context = activity.getApplication();
		this.buttonBank = new ButtonBank(context);

		this.setGameBoardListeners(new ButtonListeners(this, activity));

		// Add the controller board
		this.controller = new GamePanel(this, activity);
		this.addView(controller);
		GameTableActivity.setController(controller);
		

		this.textViews = new TextViewBank(context, this);

		// add common back button
		Button btn = this.buttonBank.getButtonMap().get(COMMON_BACK);
		btn.setOnClickListener(this.gameBoardListeners);
		this.addView(btn);

	}

	/**
	 * Sets relative positions for the game graphics based on the screen size of
	 * the device being used
	 * 
	 * @param graphic
	 *            Graphical element to be positioned
	 * @param x
	 *            Percentage value ( 0 - 1)
	 * @param y
	 *            Percentage value ( 0 - 1)
	 */
	public void setPosition(FGLGraphic graphic, double x, double y) {
		int height = (int) (ApplicationEntryActivity.height * y);
		int width = (int) (ApplicationEntryActivity.width * x);
		graphic.setCurrentPosition(new Point(width, height));
	}

	/**
	 * Get the click listeners for buttons added to the GameTableLayout activity
	 * 
	 * @param gameBoardListeners
	 */
	public void setGameBoardListeners(ButtonListeners gameBoardListeners) {
		this.gameBoardListeners = gameBoardListeners;
	}

	/**
	 * Sets the click listeners for buttons added to the GameTableLayout
	 * activity
	 * 
	 * @return ButtonListeners Object
	 */
	public ButtonListeners getGameBoardListeners() {
		return gameBoardListeners;
	}

	/**
	 * Gets the button bank
	 * 
	 * @return a collection of dynamic buttons
	 */
	public ButtonBank getButtonBank() {
		return this.buttonBank;
	}

	/**
	 * Used for debugging: Shows the current game status
	 * 
	 * @param canvas
	 */
	public void showGameStatus() {
		this.textViews.getCurrentPlayerTextView().setText(
				"Current Player : " + this.controller.getCurrentPlayer().getName());
		this.textViews.getHandStatusTextView().setText(
				"# of Cards in Hand : " + this.controller.getCurrentPlayer().countCardsInHands());
	}

}
