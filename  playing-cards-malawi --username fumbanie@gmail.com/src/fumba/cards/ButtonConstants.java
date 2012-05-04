package fumba.cards;

/**
 * Values for elements that are added dynamically to the application canvas
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

public interface ButtonConstants {

	/** Common Back Button **/
	public static final int COMMON_BACK = 555;

	/** Button that ends current player review **/
	public static final int CONTINUE = 111;
	
	/** Value 0 **/
	public static final int ZERO = 0;
	
	/** Back button x- margin positioning **/
	public static final int BACK_BTN_PSN_X = (int) (ApplicationEntryActivity.width * 0.9);
	
	/** Back button y-margin positioning **/
	public static final int BACK_BTN_PSN_Y = (int) (ApplicationEntryActivity.height * 0.1);

	/** Positions button on the middle of the screen **/
	public static final int CONT_BTN_PSN_X = (int) (ApplicationEntryActivity.width * 0.4);

	/** Continue button x-margin positioning **/
	public static final int CONT_BTN_PSN_Y = (int) (ApplicationEntryActivity.height * 0.5);

	/** Continue button y-margin positioning **/
	public static final int CONTINUE_BTN_HEIGHT = (int) (ApplicationEntryActivity.height * 0.1);

	/** Relative width for the continue button **/
	public static final int CONTINUE_BTN_WIDTH = (int) (ApplicationEntryActivity.width * 0.3);

}
