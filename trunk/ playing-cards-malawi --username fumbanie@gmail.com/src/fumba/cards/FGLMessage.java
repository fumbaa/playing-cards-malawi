package fumba.cards;

public class FGLMessage implements GeneralConstants, LanguageConstants {

	/**
	 * String message produced when user tries to make an invalid move
	 * 
	 * @param language
	 *            Selected user language
	 * @return String an instruction in the selected language
	 */
	public static String getMoveNotAllowedMsg(String language) {
		if (StringUtils.equals(CHICHEWA, language)) {
			return CH_NOT_ALLOWED;
		} else if (StringUtils.equals(ENGLISH, language)) {
			return EN_NOT_ALLOWED;
		} else if (StringUtils.equals(TUMBUKA, language)) {
			return TM_NOT_ALLOWED;
		}
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
		if (StringUtils.equals(CHICHEWA, language)) {
			return CH_TOUCH_SCREEN;
		} else if (StringUtils.equals(ENGLISH, language)) {
			return EN_TOUCH_SCREEN;
		} else if (StringUtils.equals(TUMBUKA, language)) {
			return TM_TOUCH_SCREEN;
		}
		return LN_UNKNOWN;
	}
}
