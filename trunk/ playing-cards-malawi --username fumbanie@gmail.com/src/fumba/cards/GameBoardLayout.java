/**
 * 
 * Copyright (c) 2011 FUMBA GAME LAB. All rights reserved
 * 
 * Malawi Playing Cards: GameBoardLayout.java
 * This class organises the layout of the game elements
 * 
 * @author Fumbani Chibaka
 * @version 1.0
 * @since 0.0
 * 
 * 
 * /*******************************************************************************
 * Copyright (c) 1998, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution. 
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 ******************************************************************************
 *
 */
package fumba.cards;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.widget.Button;
import android.widget.RelativeLayout;

public class GameBoardLayout extends RelativeLayout {

	private Activity activity;

	private Context context;

	/**
	 * Uses relative layout to arrange game elements
	 * 
	 * @param context
	 */
	public GameBoardLayout(Activity activity) {
		super(activity.getApplication());
		this.context = activity.getApplication();
		this.activity = activity;
		Controller board = new Controller(this);
		this.addView(board);

		// add back button
		Button btn = new Button(context);
		btn.setId(555);
		btn.setBackgroundResource(R.drawable.back_button);
		this.addView(btn);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins((int) (ApplicationEntryActivity.width * 0.9),
				(int) (ApplicationEntryActivity.height * 0.1), 0, 0);
		btn.setLayoutParams(params);
		btn.setOnClickListener(new ButtonListeners(this.activity));
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
