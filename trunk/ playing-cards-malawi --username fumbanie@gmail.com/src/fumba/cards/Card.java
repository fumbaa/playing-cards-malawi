package fumba.cards;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * The <code>Card</code> class represents a card object with an assigned suite
 * and letter or number. This game application uses 54 cards that are all
 * unique.
 * <p>
 * A card can belong to one of the following suites;
 * <ul>
 * <li>Clubs <i>(Represented by letter C)</i></li>
 * <li>Diamonds <i>(Represented by letter D)</i></li>
 * <li>Spades <i>(Represented by letter S)</i></li>
 * <li>Hearts <i>(Represented by letter H)</i></li>
 * </ul>
 * <br>
 * 
 * It can either be a King (K), Queen (Q), * (Joker), Ace (A) or any number
 * between 2 - 10 inclusive.
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

public class Card extends FGLGraphic {
	/**
	 * The GamePanel to which this card belongs to. In this case, the GamePanel
	 * can be visualized as the person who is taking charge of distributing the
	 * cards and observing that all game rules are followed.
	 */
	private GamePanel GamePanel;

	/**
	 * Makes a new card object with a name and decodes the
	 * <code>resourceID</code> to figure out which bitmap image should be used
	 * for the card object. The card is then placed in the control of the
	 * GamePanel.
	 * 
	 * @param context
	 *            interface to global information about an application
	 *            environment
	 * @param name
	 *            the unique name of the card (eg. 3D = three of diamonds)
	 * @param resourceID
	 *            an integer that maps to the appropriate bitmap image of the
	 *            card
	 * @param GamePanel
	 *            can be visualized as the person controlling the game
	 * @see R.drawable
	 */
	public Card(Context context, String name, int resourceID,
			GamePanel GamePanel) {
		super(context, name, resourceID);
		this.GamePanel = GamePanel;
	}

	/**
	 * Extracts the bitmap from the image stored in the /res/drawable folder.
	 */
	@Override
	protected void extractBitmap() {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		this.img = BitmapFactory.decodeResource(context.getResources(),
				this.resourceID);
		this.width = this.getBitmap().getWidth();
		this.height = this.getBitmap().getHeight();
	}

	/** Magnify the card image **/
	public void magnify() {
		// TODO: Implement this method
	}

	/**
	 * Restores the scale of the bitmap image
	 */
	public void rescale() {
		// TODO: Implement this method
	}

	/**
	 * Gets the card suite. This can be either Clubs, Diamonds, Hearts or Spades
	 * (C,D,H,S)
	 * 
	 * @return char representing the suite
	 */
	public char getSuite() {
		return this.name.charAt(1);
	}

	/**
	 * Gets the letter or number of the card. This can be K,Q,A,* or 2-10.
	 * 
	 * @return char representing the card number
	 */
	public char getNumber() {
		return this.name.charAt(0);
	}

	/**
	 * Gets the current position of the card
	 * 
	 * @return Point representing the x and y coordinates of the position
	 */
	public Point getPosition() {
		return new Point(this.currentX, this.currentY);
	}

	/**
	 * Retrieves the GamePanel for the card
	 * 
	 * @return GamePanel Object
	 */
	public GamePanel getGamePanel() {
		return this.GamePanel;
	}

	/**
	 * String representation of a card object
	 * 
	 * @return String representing the card name and the player who is currently
	 *         holding the card
	 */
	public String toString() {
		return "Card: " + this.name + " , Owner: "
				+ this.GamePanel.getCurrentPlayer();
	}

}
