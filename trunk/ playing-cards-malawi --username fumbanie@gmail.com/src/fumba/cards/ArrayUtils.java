package fumba.cards;

/**
 * This class contains general array functions.
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
	 * Checks if specified integer is available in the integer array collection
	 * 
	 * @param array
	 *            Collection of integers
	 * @param element
	 *            integer that needs to be found in the array collection
	 * @return boolean value
	 */
	public static boolean contains(int[] array, int element) {
		for (int arrayElement : array) {
			if (arrayElement == element)
				return true;
		}
		return false;
	}

	/**
	 * Checks if specified string is present in the string array collection.
	 * 
	 * @param array
	 * @param element
	 * @return boolean value
	 */
	public static boolean contains(String[] array, String element) {
		for (String arrayElement : array) {
			if (arrayElement.equalsIgnoreCase(element))
				return true;
		}
		return false;
	}

}
