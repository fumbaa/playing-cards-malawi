package fumba.cards;

import java.util.HashMap;

import android.content.Context;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

/**
 * The <code>ButtonBank</code> class represents a collection of dynamic
 * {@link Button Buttons} that are used in the game application.
 * 
 * <p>
 * <i>Copyright (c) 1998, 2011 Oracle. All rights reserved. This program and the
 * accompanying materials are made available under t he terms of the Eclipse
 * Public License v1.0 and Eclipse Distribution License v. 1.0 which accompanies
 * this distribution.</i>
 * </p>
 * 
 * @author Fumbani Chibaka
 * @version 1.0, 10/28/2011
 * @see <a href="http:chibaka.com">Fumba Game Lab</a>
 */

public class ButtonBank implements ButtonConstants {

	/** Collection of buttons available for this application **/
	private HashMap<Integer, Button> buttonMap = new HashMap<Integer, Button>();

	/** Application context **/
	private Context context;

	public ButtonBank(Context context) {
		this.context = context;
		this.createButtons();
	}

	/** Make buttons and store them in a hashmap **/
	private void createButtons() {

		//Back Button
		Button back_button = new Button(this.context);
		back_button.setId(COMMON_BACK);
		back_button.setBackgroundResource(R.drawable.back_button);
		RelativeLayout.LayoutParams back_button_params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		back_button_params.setMargins(BACK_BTN_PSN_X, BACK_BTN_PSN_Y, ZERO,
				ZERO);
		back_button.setLayoutParams(back_button_params);
		this.buttonMap.put(COMMON_BACK, back_button);

		// Continue Button
		Button continue_btn = new Button(this.context);
		continue_btn.setId(CONTINUE);
		continue_btn.setBackgroundResource(R.drawable.continue_button);
		RelativeLayout.LayoutParams continue_btn_params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		continue_btn_params.setMargins(CONT_BTN_PSN_X, CONT_BTN_PSN_Y, ZERO,
				ZERO);
		continue_btn_params.height = CONTINUE_BTN_HEIGHT;
		continue_btn_params.width = CONTINUE_BTN_WIDTH;
		continue_btn.setLayoutParams(continue_btn_params);
		this.buttonMap.put(CONTINUE, continue_btn);

	}

	/** Sets the buttonMap hashmap to the specified one **/
	public void setButtonMap(HashMap<Integer, Button> buttonMap) {
		this.buttonMap = buttonMap;
	}

	/** Gets the button hashmap collection of buttons **/
	public HashMap<Integer, Button> getButtonMap() {
		return buttonMap;
	}

}
