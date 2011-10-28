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
import android.graphics.BitmapFactory;
import android.graphics.Point;

public class CustomButton extends FGLGraphic {


	CustomButton(Context context, Point point, String name)
	{
		super(context, point, name);
	}

	@Override
	protected void extractBitmap() {
		BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        this.img = BitmapFactory.decodeResource(context.getResources(), R.drawable.start_button); 
        this.width = this.getBitmap().getWidth();
        this.height = this.getBitmap().getHeight();
	}

}
