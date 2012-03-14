package practice;

import android.graphics.Bitmap;

class FumbaGraphic {
	
	private Bitmap _bitmap;
	private Coordinates _coordinates;
	private Speed _speed;

	public FumbaGraphic(Bitmap bitmap) {
		_bitmap = bitmap;
		_coordinates = new Coordinates(_bitmap);
		_speed = new Speed();
	}

	public Bitmap getGraphic() {
		return _bitmap;
	}

	public Coordinates getCoordinates() {
		return _coordinates;
	}
	
	public Speed getSpeed() {
	    return _speed;
	}
	
}