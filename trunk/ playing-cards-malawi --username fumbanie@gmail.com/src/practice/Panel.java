package practice;

import java.util.ArrayList;

import fumba.cards.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Panel extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceViewThread _thread;
	private ArrayList<FumbaGraphic> _graphics = new ArrayList<FumbaGraphic>();
	private FumbaGraphic _currentGraphic = null;

	public Panel(Context context) {
		super(context);

		// TODO add the Panel to the SurfaceHolder for a callback
		getHolder().addCallback(this);
		_thread = new SurfaceViewThread(getHolder(), this);

		// enable touch events to be processed on the panel
		setFocusable(true);
	}

	@Override
	public void onDraw(Canvas canvas) {
		   canvas.drawColor(Color.BLACK);
		    Bitmap bitmap;
		    Coordinates coords;
		    for (FumbaGraphic graphic : _graphics) {
		        bitmap = graphic.getGraphic();
		        coords = graphic.getCoordinates();
		        canvas.drawBitmap(bitmap, coords.getX(), coords.getY(), null);
		    }
		    // draw current graphic at last...
		    if (_currentGraphic != null) {
		        bitmap = _currentGraphic.getGraphic();
		        coords = _currentGraphic.getCoordinates();
		        canvas.drawBitmap(bitmap, coords.getX(), coords.getY(), null);
		    }
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		synchronized (_thread.getSurfaceHolder()) {
			FumbaGraphic graphic = null;
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				graphic = new FumbaGraphic(BitmapFactory.decodeResource(
						getResources(), R.drawable.card));
				graphic.getCoordinates().setX(
						(int) event.getX() - graphic.getGraphic().getWidth()
								/ 2);
				graphic.getCoordinates().setY(
						(int) event.getY() - graphic.getGraphic().getHeight()
								/ 2);
				_currentGraphic = graphic;
			} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
				_currentGraphic.getCoordinates().setX(
						(int) event.getX()
								- _currentGraphic.getGraphic().getWidth() / 2);
				_currentGraphic.getCoordinates().setY(
						(int) event.getY()
								- _currentGraphic.getGraphic().getHeight() / 2);
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				_graphics.add(_currentGraphic);
				_currentGraphic = null;
			}
			return true;
		}
	}

	public void updatePhysics() {
		Coordinates coord;
		Speed speed;

		for (FumbaGraphic graphic : _graphics) {
			coord = graphic.getCoordinates();
			speed = graphic.getSpeed();

			// Direction
			if (speed.getXDirection() == Speed.X_DIRECTION_RIGHT) {
				coord.setX(coord.getX() + speed.getX());
			} else {
				coord.setX(coord.getX() - speed.getX());
			}
			if (speed.getYDirection() == Speed.Y_DIRECTION_DOWN) {
				coord.setY(coord.getY() + speed.getY());
			} else {
				coord.setY(coord.getY() - speed.getY());
			}

			// borders for x...
			if (coord.getX() < 0) {
				speed.toggleXDirection();
				coord.setX(-coord.getX());
			} else if (coord.getX() + graphic.getGraphic().getWidth() > getWidth()) {
				speed.toggleXDirection();
				coord.setX(coord.getX() + getWidth()
						- (coord.getX() + graphic.getGraphic().getWidth()));
			}

			// borders for y...
			if (coord.getY() < 0) {
				speed.toggleYDirection();
				coord.setY(-coord.getY());
			} else if (coord.getY() + graphic.getGraphic().getHeight() > getHeight()) {
				speed.toggleYDirection();
				coord.setY(coord.getY() + getHeight()
						- (coord.getY() + graphic.getGraphic().getHeight()));
			}
		}
	}

	public void surfaceCreated(SurfaceHolder holder) {
		_thread.setRunning(true);
		_thread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// simply copied from sample application LunarLander:
		// we have to tell thread to shut down & wait for it to finish, or else
		// it might touch the Surface after we return and explode
		boolean retry = true;
		_thread.setRunning(false);
		while (retry) {
			try {
				_thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// we will try it again and again...
			}
		}
	}

}