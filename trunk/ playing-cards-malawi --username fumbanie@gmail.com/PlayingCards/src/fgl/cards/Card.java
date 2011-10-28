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

	/**
	 * Gets the x-coordinate for the card's position.
	 * @return the x-coordinate for the cards' current position
	 */
	public int getX() {
		return currentX;
	}

	/**
	 * Sets the y-coordinate for the card's current position.
	 * This method is used to move the card on the device screen.
	 * @param newY the new y-coordinate.
	 */
	void setY(int newY) {
		currentY = newY;
	}
	
	/**
	 * Gets the y- coordinate for the card's current position
	 * @return the y-coordinate for the card's current position.
	 */
	public int getY() {
		return currentY;
	}

	/**
	 * Gets the bitmap image for the card object
	 * @return bitmap image
	 */
	public Bitmap getBitmap() {
		return img;
	}

	/**
	 * Resets the current position for the card to its original position.
	 * This method is used when the player drops the cards on screen areas which do not
	 * recognise the move.
	 */
	public void resetPosition() 
	{
		this.currentX = this.originalX;
		this.currentY = this.originalY;
	}

	/**
	 * Sets the current position of the card
	 * @param point contain x and y coordinates of the new position
	 */
	public void setCurrentPosition(Point point) {
		this.currentX = point.x;
		this.currentY = point.y;		
	}

	/**
	 * Sets the default position of the card
	 * @param point contain x and y coordinates for the default position
	 */
	public void setDefaultPosition(Point point) {
		this.originalX = point.x;
		this.originalY = point.y;			
	}

	/**
	 * Returns a string to represent the card object.
	 */
	public String toString()
	{
		return this.name;
	}

	/**
	 * Gets the center for the card
	 * @return a point representing the x and y coordinates for the cards center
	 */
	public Point getCenter() {
		int x = this.img.getWidth()/2;
		int y = this.img.getHeight()/2;
		return new Point(x,y);
	}

	/**
	 * Gets the width of the card's bitmap image
	 * @return an integer value representing the width of the card
	 */
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * Gets the height of the card
	 * @return the height of the card
	 */
	public int getHeight() {
		return this.height;
	}

	
	/**
	 * Calculates the offset position of the card once it is touched. Offset is used to center the card with the touch position.
	 * @param offset the offset position
	 * @see fgl.card.SetUpGameBoard.onTouchEvent
	 */
	public void setToCenter(Point offset) {
		this.currentX = this.currentX + offset.x;
		this.currentY = this.currentY + offset.y;
	}

	

}
