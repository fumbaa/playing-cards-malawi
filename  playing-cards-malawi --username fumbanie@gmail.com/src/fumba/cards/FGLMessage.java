package fumba.cards;

import org.apache.commons.lang3.StringUtils;

public class FGLMessage {

	/**
	 * String message produced when user tries to make an invalid move
	 * @param language Selected user language
	 * @return String an instruction in the selected language
	 */
	public static String getMoveNotAllowed(String language) {
		if (StringUtils.equals(GeneralConstants.CHICHEWA, language)) {
			return MessageConstants.CH_NOT_ALLOWED;
		} else if (StringUtils.equals(GeneralConstants.ENGLISH, language)) {
			return MessageConstants.EN_NOT_ALLOWED;
		} else if (StringUtils.equals(GeneralConstants.TUMBUKA, language)) {
			return MessageConstants.TM_NOT_ALLOWED;
		}
		return GeneralConstants.LN_UNKNOWN;
	}

}
