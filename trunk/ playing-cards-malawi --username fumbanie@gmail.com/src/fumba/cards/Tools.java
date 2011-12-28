package fumba.cards;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;

import android.util.Log;

/**
 * Contains developer tools that are used to debug the application.
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

public class Tools {

	/** tag used for Eclipse Android logcat **/
	private static String TAG = "FGL";

	/**
	 * Prints to the cat log in eclipse. A special tag, "FGL" is used to enable
	 * output cfilter.
	 * 
	 * @param string
	 *            The string to be printed to the log
	 */
	public static void catLog(String string) {
		Log.v(TAG, string);
	}

	/**
	 * Prints a hashmap. Used for debugging
	 */
	public static void printHashMap(Hashtable<String, Card> map) {
		Iterator<String> iterator = map.keySet().iterator();

		while (iterator.hasNext()) {
			String key = iterator.next().toString();
			String value = map.get(key).toString();

			Log.v(TAG, "________________");
			Log.v(TAG, key + " " + value);
			Log.v(TAG, "________________");
		}
	}

	/**
	 * Debugging purposes only
	 * 
	 * @param textViews
	 * @param cards
	 * @param totalNumCards
	 * @param playedCards
	 * @param cardsAllPlayers
	 */
	public static void debug(String string, TextViewBank textViews,
			CardBank cards, int totalNumCards, ArrayList<Card> playedCards,
			int cardsAllPlayers, Player player) {

		if (StringUtils.equals(string, GeneralConstants.USED_CARDS_SUMMARY))
			textViews.getDeckStatusTextView().setText(
					"Used : " + cards.countCards() + " / " + totalNumCards
							+ " c[" + playedCards.size() + "] a["
							+ cardsAllPlayers + "]");

		else if (StringUtils.equals(string,
				GeneralConstants.CURRENT_PLAYER_SUMMARY)) {
			textViews.getCurrentPlayerTextView().setText(
					"Current Player : " + player.getName());
			textViews.getHandStatusTextView().setText(
					"# of Cards in Hand : " + player.countCardsInHands());
		}

		else if (StringUtils.equals(string,
				GeneralConstants.CURRENT_MOVE_UPDATE)) {
			textViews.getHandStatusTextView().setText(
					"# of Cards in Hand : " + player.countCardsInHands());
		}

	}

}