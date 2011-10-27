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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;


public class Card  
{
	/** the unique name of the card (eg. 3D = three of diamonds) */
	private String name; 
	/** the graphical bitmap image of the card */
	private Bitmap img;
	/** the x- coordinate of the cards' current position. default = 0 */
	private int currentX = 0; 
	/** the y- coordinate of the cards' current position. default = 0 */
	private int currentY = 0;
	/** the x- coordinate of the cards original position after it was served */
	private int originalX; 
	/** the y- coordinate of the cards original position after it was served */
	private int originalY; 
	/** the width of the cards' bitmap image */
	private int width; 
	/** the height of the cards' bitmap image */
	private int height;

	/**
	 * Makes a new card object and defines the relevant image to represent the card
	 * @param context interface to global information about an application environment
	 * @param name the unique name of the card (eg. 3D = three of diamonds)
	 * @see R.drawable
	 */
	public Card(Context context, String name) {
		this.name = name;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		img = BitmapFactory.decodeResource(context.getResources(), R.drawable.card); 

		this.width = this.getBitmap().getWidth();
		this.height = this.getBitmap().getHeight();
	}

	/**
	 * Gets the cards' unique identifier
	 * @return the unique name of the card
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the x-coordinate for the cards' position.
	 * this method is used to move the card on the device screen.
	 * @param newX the new x-coordinate
	 */
	void setX(int newX) {
		currentX = newX;
	}

	public int getX() {
		return currentX;
	}

	void setY(int newValue) {
		currentY = newValue;
	}

	public int getY() {
		return currentY;
	}

	public Bitmap getBitmap() {
		return img;
	}

	/* Returns the card to its original position */
	public void resetPosition() 
	{
		this.setX(originalX);
		this.setY(originalY);
	}

	public void setCurrentPosition(Point point) {
		this.setX(point.x);
		this.setY(point.y);		
	}

	public void setDefaultPosition(Point point) {
		this.originalX = point.x;
		this.originalY = point.y;			
	}


	public String toString()
	{
		return this.name;
	}

	public Point getCenter() {
		Point center = new Point();
		int x = this.getBitmap().getWidth()/2;
		int y = this.getBitmap().getHeight()/2;
		center.x = x;
		center.y = y;
		return center;
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return this.width;
	}

	public void setToCenter(Point offset) {
		// TODO Auto-generated method stub
		this.currentX = this.currentX + offset.x;
		this.currentY = this.currentY + offset.y;
	}

	public int getHeight() {
		// TODO Auto-generated method stub
		return this.height;
	}

	public Point getPosition() {
		// TODO Auto-generated method stub
		return new Point(this.currentX, this.currentY);
	}



}
