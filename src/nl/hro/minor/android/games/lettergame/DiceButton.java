package nl.hro.minor.android.games.lettergame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;

public class DiceButton {
	
	public static Bitmap _bmp;
	private int _height;
	private Region _bounds = new Region();
	private boolean _active = true;
	
	public int getHeight() {
		return _height;
	}
	
	public Region getBounds(){		
		return _bounds;
	}
	
	public void setActive(boolean status)
	{
		_active = status; 
	}
	
	public boolean isActive(){ return _active;}

	public DiceButton(int width, int height)
	{
		_height =height;
		
	}
		
	
	
	public void draw(Canvas canvas){
		
		Rect location = new Rect(10, _height-60, 40, _height-20); 
		_bounds.set(location);
		canvas.drawBitmap(_bmp, new Rect(0, 0, 40, 40), location, null);
	}
}
