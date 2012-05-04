package fumba.cards;

/**
 * This class houses all messages and instructions that are displayed to the
 * user throughout gameplay.
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

public class FGLMessage implements GeneralConstants, LanguageConstants {

	/**
	 * String message produced when user tries to make an invalid move
	 * 
	 * @param language
	 *            Selected user language
	 * @return String an instruction in the selected language
	 */
	public static String getMoveNotAllowedMsg(String language) {
		if (StringUtils.equals(CHICHEWA, language))
			return CH_NOT_ALLOWED;
		else if (StringUtils.equals(ENGLISH, language))
			return EN_NOT_ALLOWED;
		else if (StringUtils.equals(TUMBUKA, language))
			return TM_NOT_ALLOWED;
		return LN_UNKNOWN;
	}

	/**
	 * String message telling the player to touch the screen
	 * 
	 * @param language
	 *            Selected user language
	 * @return String an instruction in the selected language
	 */
	public static String getTouchScreenMsg(String language) {
		if (StringUtils.equals(CHICHEWA, language))
			return CH_TOUCH_SCREEN;
		else if (StringUtils.equals(ENGLISH, language))
			return EN_TOUCH_SCREEN;
		else if (StringUtils.equals(TUMBUKA, language))
			return TM_TOUCH_SCREEN;
		return LN_UNKNOWN;
	}
}
