package fgl.cards;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Point;



public class CustomButton extends FGLGraphic {


	CustomButton(Context context, Point point, String name)
	{
		super(context, point, name);
	}

	@Override
	protected void extractBitmap() {
		BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        this.img = BitmapFactory.decodeResource(context.getResources(), R.drawable.start_button); 
        this.width = this.getBitmap().getWidth();
        this.height = this.getBitmap().getHeight();
	}

}
