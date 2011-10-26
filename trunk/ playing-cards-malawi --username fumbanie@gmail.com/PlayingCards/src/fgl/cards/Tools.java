/**
 * 
 * Copyright (c) 2011 FUMBA GAME LAB. All rights reserved
 * 
 * Malawi Playing Cards: Tools.java
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

	private static String TAG = "FGL";

	
	//check if a point is inside a rectangle
	public static List<Object> isInRectangle(Point touchPoint, Card card)
	{
		//XXX: Debug test point
		//Log.v(TAG, "touchPoint : " + touchPoint.x +", "+ touchPoint.y );
		//Log.v(TAG, "cardPosn   : " + card.getX() + ", "+ card.getY() );
		
		//Determine card center point
		Point cardCenter = new Point( card.getBitmap().getWidth()/2 , card.getBitmap().getHeight()/2);
		
		
		List<Object> result = new ArrayList<Object>() ;
		
		if( touchPoint.x > card.getX() && touchPoint.x < card.getX() + card.getBitmap().getWidth() )
		{
			if ( touchPoint.y > card.getY() && touchPoint.y < card.getY() + card.getBitmap().getHeight())
			{
				
				int x =  touchPoint.x - ( cardCenter.x + card.getBitmap().getWidth() ); 
				int y =  touchPoint.y - ( cardCenter.y + card.getBitmap().getHeight()) ; 
				
				x = Math.abs(x);
				y = Math.abs(y);
				
				Point offset = new Point(x, y);
				
				result.add(true);
				result.add(offset);
				return result;
			}
		}
		result.add(false);
		return result;
	}
	
	
	
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