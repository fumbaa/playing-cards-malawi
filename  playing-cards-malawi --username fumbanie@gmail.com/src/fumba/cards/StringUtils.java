package fumba.cards;

public class StringUtils {

	/**
	 * 
	 * @param english
	 * @param language
	 * @return
	 */
	public static boolean equals(String string1, String string2) {
		if (string1.equalsIgnoreCase(string2))
			return true;
		return false;
	}

}
