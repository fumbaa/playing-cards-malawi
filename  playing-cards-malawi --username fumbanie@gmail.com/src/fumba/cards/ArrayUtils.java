package fumba.cards;

/**
 * General functions to traverse through collections.
 * 
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

public class ArrayUtils {

	/**
	 * 
	 * @param gameplayScreens
	 * @param currentScreen
	 * @return
	 */
	public static boolean contains(int[] gameplayScreens, int currentScreen) {
		for (int check : gameplayScreens) {
			if (check == currentScreen)
				return true;
		}
		return false;
	}

	/**
	 * 
	 * @param repeatCards
	 * @param name
	 * @return
	 */
	public static boolean contains(String[] repeatCards, String name) {
		for (String string : repeatCards) {
			if (string.equalsIgnoreCase(name))
				return true;
		}
		return false;
	}

}
