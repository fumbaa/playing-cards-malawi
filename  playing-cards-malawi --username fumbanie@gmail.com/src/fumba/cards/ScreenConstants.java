package fumba.cards;

public interface ScreenConstants {

	/** Welcome screen **/
	public static final int START_SCREEN = 1;

	/** Options Screen **/
	public static final int OPTIONS_SCREEN = 3;

	/** Game Over Screen **/
	public static final int GAME_OVER = 4;

	/** Device handed to next player show cards in hand **/
	public static final int TRANSITION_SCREEN = 5;

	/** Player can move cards and make moves **/
	public static final int PLAY_SCREEN = 2;

	/** Continue Buttons shows to allow player to see their current card state **/
	public static final int REVIEW_SCREEN = 6;

	/** All Screens associated with a gameplay session **/
	public static final int[] GAMEPLAY_SCREENS = { TRANSITION_SCREEN,
			PLAY_SCREEN, REVIEW_SCREEN };
}
