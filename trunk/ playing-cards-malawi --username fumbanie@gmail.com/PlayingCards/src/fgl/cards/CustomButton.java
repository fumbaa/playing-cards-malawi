package fgl.cards;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Customised menu buttons.
 * <p><i>Copyright (c) 1998, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution.</i></p>
 * 
 * @author Fumbani Chibaka, 
 * @version 1.0, 10/28/2011
 * @see <a href="http:chibaka.com">Fumba Game Lab</a>
 */


public class CustomButton extends FGLGraphic {


	CustomButton(Context context, Point point, String name, int resourceID)
	{
		super(context, point, name, resourceID);
	}

	@Override
	protected void extractBitmap() {
		BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        this.img = BitmapFactory.decodeResource(context.getResources(), resourceID); 
        this.width = this.getBitmap().getWidth();
        this.height = this.getBitmap().getHeight();
	}

}
