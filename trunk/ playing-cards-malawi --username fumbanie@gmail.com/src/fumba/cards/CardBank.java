package fumba.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.content.Context;

/**
 * The <code>CardBank</code> class represents a collection of {@link Card Cards} that are used in the game application. This
 * class extracts images from the {@link R.drawable Android R.drawable} class and assigns them to appropriate card.
 * <p>
 * The cards are stored in a hashmap and appropriate accessor methods are provided. The {@link Controller Controller}
 * class collects necessary buttons from an instance this class in a fashion that promotes recyling of the cards.
 * 
 * <p><i>Copyright (c) 1998, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under t he 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution.</i></p>
 * 
 * @author Fumbani Chibaka
 * @version 1.0, 10/28/2011
 * @see <a href="http:chibaka.com">Fumba Game Lab</a>
 */
public class CardBank {

	/** Collection of all sounds with their respective keys **/
	private List <Card> cardMap;

	/** Android interface that provides the global information of the application. **/
	private Context context;

	/** The controller to which this card belongs to. In this case, the contoller can be visualised as the person
	 * who is taking charge of distributing the cards and observing that all game rules are followed.
	 */
	private Controller controller;

	/**
	 * Initiates sound bank and populates it with sounds from applications resources
	 * @param context application-specific resources and classes
	 * @param controller can be visualiased as the person in control of the card deck
	 * @see <a href="http://developer.android.com/reference/android/content/Context.html">Context (Android API) </a>
	 */
	CardBank(Context context, Controller controller)
	{
		this.context = context;
		this.controller = controller;
		this.cardMap = new ArrayList <Card>();
		this.initialise();
	}

	/**
	 * Makes 54 card objects that are to be reused throughout gameplay. 
	 * @see R.drawable
	 */
	private void initialise() {
		//A
		this.cardMap.add( new Card(this.context, "AC", R.drawable.fglaclub,  this.controller)		);
		this.cardMap.add( new Card(this.context, "AD", R.drawable.fglad, this.controller)		);
		this.cardMap.add( new Card(this.context, "AH", R.drawable.fglahearts, this.controller)	);
		//this.cardMap.add( new Card(this.context, "AS", R.drawable.fglaspades)	);

		//2
		this.cardMap.add( new Card(this.context, "2C", R.drawable.fgl2club, this.controller) 	);
		this.cardMap.add( new Card(this.context, "2D", R.drawable.fgl2d, this.controller) 		);
		this.cardMap.add( new Card(this.context, "2H", R.drawable.fgl2hearts, this.controller) 	);
		//this.cardMap.add( new Card(this.context, "2S", R.drawable.fgl2spades)	);

		//3
		this.cardMap.add( new Card(this.context, "3C", R.drawable.fgl3club, this.controller)		);
		this.cardMap.add( new Card(this.context, "3D", R.drawable.fgl3d, this.controller)		);
		this.cardMap.add( new Card(this.context, "3H", R.drawable.fgl3hearts, this.controller)	);
		//this.cardMap.add( new Card(this.context, "3S", R.drawable.fgl3spades)	);

		//4
		this.cardMap.add( new Card(this.context, "4C", R.drawable.fgl4club, this.controller)		);
		this.cardMap.add( new Card(this.context, "4D", R.drawable.fgl4d, this.controller)		);
		this.cardMap.add( new Card(this.context, "4H", R.drawable.fgl4hearts, this.controller)	);
		//this.cardMap.add( new Card(this.context, "4S", R.drawable.fgl4spades)	);

		//5
		this.cardMap.add( new Card(this.context, "5C", R.drawable.fgl5club, this.controller)		);
		this.cardMap.add( new Card(this.context, "5D", R.drawable.fgl5d, this.controller)		);
		this.cardMap.add( new Card(this.context, "5H", R.drawable.fgl5hearts, this.controller)	);
		//this.cardMap.add( new Card(this.context, "5S", R.drawable.fgl5spades)	);

		//6 
		this.cardMap.add( new Card(this.context, "6C", R.drawable.fgl6club, this.controller)		);
		this.cardMap.add( new Card(this.context, "6D", R.drawable.fgl6d, this.controller)		);
		this.cardMap.add( new Card(this.context, "6H", R.drawable.fgl6hearts, this.controller)	);
		//this.cardMap.add( new Card(this.context, "6S", R.drawable.fgl6spades)	);

		//7
		this.cardMap.add( new Card(this.context, "7C", R.drawable.fgl7club,this.controller)		);
		this.cardMap.add( new Card(this.context, "7D", R.drawable.fgl7d, this.controller)		);
		this.cardMap.add( new Card(this.context, "7H", R.drawable.fgl7hearts,this.controller)	);
		//this.cardMap.add( new Card(this.context, "7S", R.drawable.fgl7spades)	);

		//8
		this.cardMap.add( new Card(this.context, "8C", R.drawable.fgl8club, this.controller)		);
		this.cardMap.add( new Card(this.context, "8D", R.drawable.fgl8d, this.controller)		);
		this.cardMap.add( new Card(this.context, "8H", R.drawable.fgl8hearts, this.controller)	);
		//this.cardMap.add( new Card(this.context, "8S", R.drawable.fgl8spades)	);

		//9
		this.cardMap.add( new Card(this.context, "9C", R.drawable.fgl9club, this.controller)		);
		this.cardMap.add( new Card(this.context, "9D", R.drawable.fgl9d, this.controller)		);
		this.cardMap.add( new Card(this.context, "9H", R.drawable.fgl9hearts, this.controller)	);
		//this.cardMap.add( new Card(this.context, "9S", R.drawable.fgl9spades)	);

		//10
		this.cardMap.add( new Card(this.context, "XC", R.drawable.fgl10club, this.controller)	);
		this.cardMap.add( new Card(this.context, "XD", R.drawable.fgl10d, this.controller)		);
		this.cardMap.add( new Card(this.context, "XH", R.drawable.fgl10hearts, this.controller)	);
		//this.cardMap.add( new Card(this.context, "XS", R.drawable.fgl10spades)	);

		//K
		this.cardMap.add( new Card(this.context, "KC", R.drawable.kclub, this.controller)		);
		this.cardMap.add( new Card(this.context, "KD", R.drawable.kd, this.controller)			);
		this.cardMap.add( new Card(this.context, "KH", R.drawable.khearts, this.controller)		);
		this.cardMap.add( new Card(this.context, "KS", R.drawable.kspades, this.controller)		);

		//J
		this.cardMap.add( new Card(this.context, "JC", R.drawable.jclub, this.controller )		);
		this.cardMap.add( new Card(this.context, "JD", R.drawable.jd, this.controller)			);
		this.cardMap.add( new Card(this.context, "JH", R.drawable.jhearts, this.controller)		);
		this.cardMap.add( new Card(this.context, "JS", R.drawable.jspades, this.controller)		);

		//Jokers
		//this.cardMap.add( new Card(this.context, "*B", R.drawable.fglahearts)	);
		//this.cardMap.add( new Card(this.context, "*C", R.drawable.fglahearts)	);

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
