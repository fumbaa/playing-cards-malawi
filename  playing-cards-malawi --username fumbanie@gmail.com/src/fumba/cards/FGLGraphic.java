package fumba.cards;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;

/**
 * This superclass represents all custom graphic objects that are added to the
 * game screen.
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

public abstract class FGLGraphic {

	/** the resource ID assigned in the R class **/
	protected int resourceID;

	/** the graphical bitmap image of the custom view */
	protected Bitmap img;

	/** the unique id of the custom view */
	protected String name;

	/** graphic current position coordinate */
	protected int currentX, currentY;

	/** graphic original position coordinate */
	private int originalX, originalY;

	/** view dimension */
	protected int width, height;

	/** the context */
	protected Context context;

	/** a tag to check if the graphic is touch sensitive **/
	private boolean active;

	public FGLGraphic(Context context, String name, int resourceID) {
		this.active = false;
		this.name = name;
		this.context = context;
		this.resourceID = resourceID;
		this.extractBitmap();
	}

	FGLGraphic(Context context, Point point, String name, int resourceID) {
		this(context, name, resourceID);
		this.currentX = point.x;
		this.currentY = point.y;
	}

	/**
	 * Graphic object extracts the appropriate bitmap image from the resources
	 * folder
	 */
	protected abstract void extractBitmap();

	/**
	 * Gets the cards' unique identifier
	 * 
	 * @return the unique name of the card
	 */
	public String getName() {
		return this.name;
	}

	public Bitmap getBitmap() {
		return this.img;
	}

	public int getX() {
		return this.currentX;
	}

	public int getY() {
		return this.currentY;
	}

	/**
	 * Gets the center for the button
	 * 
	 * @return a point representing the x and y coordinates for the buttons
	 */
	public Point getCenter() {
		int x = this.img.getWidth() / 2;
		int y = this.img.getHeight() / 2;
		return new Point(x, y);
	}

	/**
	 * Resets the current position for the card to its original position. This
	 * method is used when the player drops the cards on screen areas which do
	 * not recognise the move.
	 */
	public void resetPosition() {
		this.currentX = this.originalX;
		this.currentY = this.originalY;
	}

	/**
	 * Sets the y-coordinate for the card's current position. This method is
	 * used to move the card on the device screen.
	 * 
	 * @param newY
	 *            the new y-coordinate.
	 */
	void setY(int newY) {
		currentY = newY;
	}

	/**
	 * Sets the x-coordinate for the cards' position. this method is used to
	 * move the card on the device screen.
	 * 
	 * @param newX
	 *            the new x-coordinate
	 */
	void setX(int newX) {
		currentX = newX;
	}

	/**
	 * Sets the default position of the card
	 * 
	 * @param point
	 *            contain x and y coordinates for the default position
	 */
	public void setDefaultPosition(Point point) {
		this.originalX = point.x;
		this.originalY = point.y;
	}

	/**
	 * Sets the current position of the card
	 * 
	 * @param point
	 *            contain x and y coordinates of the new position
	 */
	public void setCurrentPosition(Point point) {
		this.currentX = point.x;
		this.currentY = point.y;
	}

	/**
	 * Calculates the offset position of the card once it is touched. Offset is
	 * used to center the card with the touch position.
	 * 
	 * @param offset
	 *            the offset position
	 * @see fgl.Controller.SetUpGameBoard.onTouchEvent
	 */
	public void setToCenter(Point offset) {
		this.currentX = this.currentX + offset.x;
		this.currentY = this.currentY + offset.y;
	}

	/**
	 * Gets the width of the card's bitmap image
	 * 
	 * @return an integer value representing the width of the card
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Gets the height of the card
	 * 
	 * @return the height of the card
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Deactivates a button when it is not necessary for the current screen
	 * state. This method is necessary for CustomButtons.
	 */
	public void deactivate() {
		this.active = false;
	}

	/**
	 * Activates a graphic when it is necessary for it to be. This method is
	 * necessary for CustomButtons.
	 */
	public void activate() {
		this.active = true;
	}

	/**
	 * Checks to see if the graphic object was touched. Only currently active
	 * graphics can be touched.
	 * 
	 * @return the offset position
	 */
	public Point isTouched(Point touchPoint) {
		int adjust = 10;
		if (this.active)
			if (touchPoint.x >= (this.getX() - adjust)
					&& touchPoint.x <= (this.getX() + adjust) + this.getWidth())
				if (touchPoint.y >= (this.getY() - adjust)
						&& touchPoint.y <= (this.getY() + adjust)
								+ this.getHeight()) {
					int x = touchPoint.x - (this.getCenter().x + this.getX());
					int y = touchPoint.y - (this.getCenter().y + this.getY());
					// Log that this button was touched (for debugging purposes
					Tools.catLog(this.name);
					return new Point(x, y);
				}
		return null;
	}

}
