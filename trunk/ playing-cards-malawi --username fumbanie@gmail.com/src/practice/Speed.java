package practice;

public class Speed {
	
	public static final int X_DIRECTION_RIGHT = 1;
	public static final int X_DIRECTION_LEFT = -1;
	public static final int Y_DIRECTION_DOWN = 1;
	public static final int Y_DIRECTION_UP = -1;

	private int _x = 1;
	private int _y = 1;

	private int _xDirection = X_DIRECTION_RIGHT;
	private int _yDirection = Y_DIRECTION_DOWN;

	/**
	 * @return the _xDirection
	 */
	public int getXDirection() {
		return _xDirection;
	}

	/**
	 * @param direction
	 *            the _xDirection to set
	 */
	public void setXDirection(int direction) {
		_xDirection = direction;
	}

	public void toggleXDirection() {
		if (_xDirection == X_DIRECTION_RIGHT) {
			_xDirection = X_DIRECTION_LEFT;
		} else {
			_xDirection = X_DIRECTION_RIGHT;
		}
	}

	/**
	 * @return the _yDirection
	 */
	public int getYDirection() {
		return _yDirection;
	}

	/**
	 * @param direction
	 *            the _yDirection to set
	 */
	public void setYDirection(int direction) {
		_yDirection = direction;
	}

	public void toggleYDirection() {
		if (_yDirection == Y_DIRECTION_DOWN) {
			_yDirection = Y_DIRECTION_UP;
		} else {
			_yDirection = Y_DIRECTION_DOWN;
		}
	}

	/**
	 * @return the _x
	 */
	public int getX() {
		return _x;
	}

	/**
	 * @param speed
	 *            the _x to set
	 */
	public void setX(int speed) {
		_x = speed;
	}

	/**
	 * @return the _y
	 */
	public int getY() {
		return _y;
	}

	/**
	 * @param speed
	 *            the _y to set
	 */
	public void setY(int speed) {
		_y = speed;
	}

	public String toString() {
		String xDirection;
		if (_xDirection == X_DIRECTION_RIGHT) {
			xDirection = "right";
		} else {
			xDirection = "left";
		}
		return "Speed: x: " + _x + " | y: " + _y + " | xDirection: "
				+ xDirection;
	}
}