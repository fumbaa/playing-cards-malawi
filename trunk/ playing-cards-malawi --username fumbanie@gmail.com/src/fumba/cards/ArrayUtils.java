package fumba.cards;

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
