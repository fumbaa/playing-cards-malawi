/**
 * 
 * Copyright (c) 2011 FUMBA GAME LAB. All rights reserved
 * 
 * Malawi Playing Cards: Card.java
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
	private String name; //card name = letter and number
	private String letter; //card_letter
	private String suite; //number value on the card

	private Bitmap img; // the image of the ball
	private int coordX = 0; // the x coordinate at the canvas
	private int coordY = 0; // the y coordinate at the canvas

	private int originalX; //Original x-coordinate
	private int originalY; //Original y-coordinate
	private int width; 
	private int height;


	public Card(Context context, int drawable, String letter, String suite) {
		this.letter = letter;
		this.suite = suite;
		name = letter + suite;
		
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		img = BitmapFactory.decodeResource(context.getResources(), drawable); 
		
		this.width = this.getBitmap().getWidth();
		this.height = this.getBitmap().getHeight();
	}

	public Card(Context context, int drawable, Point point, String letter, String suite) {
		this(context, drawable, letter, suite);
		originalX = coordX = point.x;
		originalY = coordY = point.y;
	}

	/* Gets the name of the card */
	public String getName()
	{
		return name;
	}

	

	void setX(int newValue) {
		coordX = newValue;
	}

	public int getX() {
		return coordX;
	}

	void setY(int newValue) {
		coordY = newValue;
	}

	public int getY() {
		return coordY;
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
		this.coordX = this.coordX + offset.x;
		this.coordY = this.coordY + offset.y;
	}

	public int getHeight() {
		// TODO Auto-generated method stub
		return this.height;
	}

	public Point getPosition() {
		// TODO Auto-generated method stub
		return new Point(this.coordX, this.coordY);
	}

	

}
