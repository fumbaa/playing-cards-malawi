/**
 * 
 * Copyright (c) 2011 FUMBA GAME LAB. All rights reserved
 * 
 * Malawi Playing Cards: Tools.java
 * A Collection of methods that are used to complement class functioning and for debugging
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

import java.util.Hashtable;
import java.util.Iterator;
import android.util.Log;

public class Tools {

	/** tag used for Eclipse Android logcat **/
	private static String TAG = "FGL"; 

	/**
	 * Prints to the cat log in eclipse. A special tag, "FGL" is used to enable output cfilter.
	 * @param string The string to be printed to the log
	 */
	public static void catLog(String string)
	{
		Log.v(TAG, string);
	}


	/**
	 *Prints a hashmap. Used for debugging
	 */
	public static void printHashMap(Hashtable<String, Card> map) 
	{
		Iterator<String> iterator = map.keySet().iterator();  

		while (iterator.hasNext()) {  
			String key = iterator.next().toString();  
			String value = map.get(key).toString();  

			Log.v(TAG, "________________");
			Log.v(TAG, key + " " + value);  
			Log.v(TAG, "________________");
		} 
	}

}