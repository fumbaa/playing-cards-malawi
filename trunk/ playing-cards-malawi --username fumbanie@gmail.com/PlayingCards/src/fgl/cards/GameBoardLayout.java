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
package fgl.cards;

import android.content.Context;
import android.graphics.Point;
import android.widget.RelativeLayout;

public class GameBoardLayout extends RelativeLayout {


	/**
	 * Uses relative layout to arrange game elements
	 * @param context
	 */
	public GameBoardLayout(Context context)
	{
		super(context);
		Controller board = new Controller(context, this);
		this.addView(board);
	}

	/** Sets positions for the game graphics based on the relative screen size of the device **/
	public void setPosition(FGLGraphic graphic, double x, double y)
	{
		int height = (int) (PlayingCardsActivity.height * y);
		int width = (int) (PlayingCardsActivity.width * x);
		graphic.setCurrentPosition( new Point( width, height));
	}

}
