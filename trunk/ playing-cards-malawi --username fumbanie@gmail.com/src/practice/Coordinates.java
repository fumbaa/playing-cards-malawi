package practice;

import android.graphics.Bitmap;

public class Coordinates {

	private int _x = 100;
	private int _y = 0;
	private Bitmap _bitmap;

	public Coordinates(Bitmap bitmap) {
		_bitmap = bitmap;
	}

	public int getX() {
		return _x + _bitmap.getWidth() / 2;
	}

	public void setX(int value) {
		_x = value - _bitmap.getWidth() / 2;
	}

	public int getY() {
		return _y + _bitmap.getHeight() / 2;
	}

	public void setY(int value) {
		_y = value - _bitmap.getHeight() / 2;
	}

	public String toString() {
		return "Coordinates: (" + _x + "/" + _y + ")";
	}
}