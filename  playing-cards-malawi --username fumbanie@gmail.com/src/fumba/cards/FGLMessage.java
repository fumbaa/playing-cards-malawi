package fumba.cards;

import org.apache.commons.lang3.StringUtils;

public class FGLMessage {

	
	public static String getMoveNotAllowed(String language) {
		if ( StringUtils.equals("Hie", "Hallo"))
		return "hallo";
		return language;
	}

}
