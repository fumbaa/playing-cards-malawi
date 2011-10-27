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

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import android.graphics.Point;
import android.util.Log;

public class Tools {

	private static String TAG = "FGL"; //Tag used for Eclipse Android Logcat

	/**
	 * Prints to the cat log in eclipse. A special tag, "FGL" is used to enable output filter.
	 * @param string The string to be printed to the log   
	 * @author Fumba Chibaka
	 */
	public static void catLog(String string)
	{
		Log.v(TAG, string);
	}
	
	
	/**
	 * Returns an Image object that can then be painted on the screen. 
	 * The url argument must specify an absolute that draw the image will incrementally paint on the screen.
	 * 
	 * @param	name the location of the image, relative to the url argument
	 * @return	A random card from the card deck                 
	 * @see	Image
	 * @author Fumba Chibaka
	 */
	//check if a point is inside a rectangle
	public static List<Object> isInRectangle(Point touchPoint, Card card)
	{		
		Point cardCenter = card.getCenter();
		List<Object> result = new ArrayList<Object>();
		
		if( touchPoint.x >= card.getX() && touchPoint.x <= card.getX() + card.getWidth() )
		{
			if ( touchPoint.y >= card.getY() && touchPoint.y <= card.getY() + card.getHeight())
			{
				int x =  touchPoint.x - ( cardCenter.x + card.getX() ); 
				int y =  touchPoint.y - ( cardCenter.y + card.getY()) ; 				
				Point offset = new Point(x, y);
				
				//Add touch response
				result.add(true);
				//Add offset coordinates
				result.add(offset);
				return result;
			}
		}
		
		//set default values
		result.add(false);
		result.add(new Point());
		
		return result;
	}
	
	
	
	/**
	 * Returns an Image object that can then be painted on the screen. 
	 * The url argument must specify an absolute that draw the image will incrementally paint on the screen.
	 * 
	 * @param	name the location of the image, relative to the url argument
	 * @return	A random card from the card deck                 
	 * @see	Image
	 * @author Fumba Chibaka
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