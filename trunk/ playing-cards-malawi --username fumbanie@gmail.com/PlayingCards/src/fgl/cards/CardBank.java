package fgl.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;

/**
 * Collection of cards that make up the deck
 * <p><i>Copyright (c) 1998, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution.</i></p>
 * 
 * @author Fumbani Chibaka, 
 * @version 1.0, 10/28/2011
 * @see <a href="http:chibaka.com">Fumba Game Lab</a>
 */

public class CardBank {

	/** Collection of all sounds with their respective keys **/
	private List <Card> cardMap;

	/**   **/
	private Context context;

	/**
	 * Initiates sound bank and populates it with sounds from applications resources
	 * @param context Application-specific resources and classes
	 */
	CardBank(Context context)
	{
		this.context = context;
		this.cardMap = new ArrayList <Card>();
		this.initialise();
	}

	/**
	 * Makes 54 card objects that are to be reused throughout gameplay. 
	 */
	private void initialise() {
		this.cardMap.add( new Card(this.context, "AC", R.drawable.fglaclub) );
		this.cardMap.add( new Card(this.context, "AD", R.drawable.fglad) );
		this.cardMap.add( new Card(this.context, "AH", R.drawable.fglahearts) );
		this.cardMap.add( new Card(this.context, "2C", R.drawable.fgl2club) );
		this.cardMap.add( new Card(this.context, "2D", R.drawable.fgl2d) );
		this.cardMap.add( new Card(this.context, "2H", R.drawable.fgl2hearts) );
	}

	/**
	 * Picks a random card from the card deck. 
	 * @return	A random card from the card deck 
	 */
	public Card pickRandomCard() {
		return this.cardMap.remove( new Random().nextInt( this.cardMap.size()) );
	}

	/**
	 * Put card back in the Card bank- reuse
	 * @param card Card to be put into the bank for recycling
	 */
	public void putBackCard(Card card) {
		this.cardMap.add(card);
	}

	/** Count the number of cards currently in the card bank **/
	public int countCards() {
		return this.cardMap.size();
	}

}
