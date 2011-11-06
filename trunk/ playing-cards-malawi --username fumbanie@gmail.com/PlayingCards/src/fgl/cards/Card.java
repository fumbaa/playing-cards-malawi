/**
 * 
 * Copyright (c) 2011 FUMBA GAME LAB. All rights reserved
 * 
 * Malawi Playing Cards: Card.java
 * Represents the card object
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

public class Card extends FGLGraphic
{	
	/**
	 * Makes a new card object and defines the relevant image to represent the card
	 * @param context interface to global information about an application environment
	 * @param name the unique name of the card (eg. 3D = three of diamonds)
	 * @see R.drawable
	 */
	public Card(Context context, String name, int resourceID) {
		super(context, name, resourceID);
	}

	@Override
	protected void extractBitmap() {
		BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        this.img = BitmapFactory.decodeResource(context.getResources(), this.resourceID); 

        this.width = this.getBitmap().getWidth();
        this.height = this.getBitmap().getHeight();
	}

	/**
	 * Gets the card suite
	 * @return
	 */
	public char getSuite() {
		return this.name.charAt(1);
	}
	
	/**
	 * 
	 * @return
	 */
	public char getNumber(){
		return this.name.charAt(0);
	}
	
	/**
	 * 
	 * @return
	 */
	public Point getPosition() {
		return new Point(this.currentX, this.currentY);
	}
	

}
