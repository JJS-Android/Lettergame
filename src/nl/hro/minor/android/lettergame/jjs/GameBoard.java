package nl.hro.minor.android.lettergame.jjs;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class GameBoard {
	
	public static Bitmap _bmp;
	private int _width;
	private int _height;
	
	public GameBoard(int width, int height)
	{
		_width = width;
		_height =height;
		
	}
	
	public void draw(Canvas canvas){
	
		Paint myPaint = new Paint();
		myPaint.setColor(Color.rgb(238, 87, 21));
		myPaint.setStrokeWidth(10);
		Rect lowerPlayfield = new Rect(0, _height-150, _width, _height);
		canvas.drawRect(lowerPlayfield, myPaint);
		canvas.drawBitmap(_bmp, new Rect(0, 0, 457, 55), new Rect(10, _height-135, 457, _height-80), null);
	}
	
	public int getHeight() {
		return _height;
	}

}
