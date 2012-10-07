package nl.hro.minor.android.lettergame.jjs;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Display;
import android.view.View;

public class Dice extends View{
	
	public static Bitmap _bmp;
	private Rect _diceRect;
	private int _frames;
	private int _currentFrame;
	
	private int _speedX;
	private int _speedY;
	private int _posX;
	private int _posY;
	private boolean _moving;
	private ContextHolder _ch;
	private int _size;
	
	private Region _bounds = new Region();
	private Display _display;
	private int _displayWidth;
	private int _displayHeight;
	private int _square;

	char[] _alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	
	public Dice(Context context, int posX, int posY, int speedX, int speedY)
	{
		super(context);
		
		_ch = ContextHolder.getInstance();
	    _size = 55;
	    _diceRect = new Rect(0,0,_size,_size);
	    _frames = 26;
	    Random r = new Random();
	    _currentFrame = r.nextInt(_frames);
	    _square = -1;
	    
		_posX =posX;
		_posY =posY;
		_speedX = speedX;
		_speedY = speedY;
		
		// Set parent width/height (canvasview)
		_display = _ch.getContext().getWindowManager().getDefaultDisplay();	
		_displayWidth = _display.getWidth();
		_displayHeight = _display.getHeight();
		
	}


	public void startAnimation()
	{
		_moving = true;
	 new CountDownTimer(5000, 100) {

	     public void onTick(long millisUntilFinished) {
	    	 _currentFrame+=1;
	     }

	     public void onFinish() {
	    	 _moving = false;
	     }
	     
	  }.start();
	}
	
	
	public void updateFrame() {
		
		if(_currentFrame > _frames)
		{
			_currentFrame = 1;
		}
		
		_diceRect.top = _currentFrame * _size - _size; 
		_diceRect.bottom = _currentFrame * _size;
		
	}
	
	public void move()
	{
		
		if(_moving == true)
		{
		
			//move this somewhere else
			
			int nextPosX = _posX +=_speedX;
			int nextPosY = _posY +=_speedY;
			
			if(nextPosX > _displayWidth - _size || nextPosX < 0)
			{
				_speedX *= -1;
			}
			
			if(nextPosY > _displayHeight - _size - 150 || nextPosY < 0) // Jordi: Wat is die 130 hardcoded?
			{
				_speedY *= -1;
			}
			
			_posX = nextPosX;
			_posY = nextPosY;
			
			invalidate();
		}
	}
	
	public void draw(Canvas canvas){
		Rect location = new Rect(_posX, _posY, _posX+_size, _posY+_size);
		canvas.drawBitmap(_bmp, _diceRect, location, null);
		
		_bounds.set(location);
	}
	
	public String getLetter(){
		
		return Character.toString( _alphabet[_currentFrame - 1] );
	}
	
	public void setSquare(int square) {
		_square = square;
	}
	
	public int getSquare() {
		return _square;
	}
	
	public Region getBounds(){		
		return _bounds;
	}
	
	public void setPosition(int posX, int posY){
		// Make sure when setting the position to stop the auto-movements
		_moving = false;
		
		// Convert the 'draghandle' from the top left corner to the center of the dice
		int nextPosX = posX - (_size/ 2 );
		int nextPosY = posY - (_size / 2 );
		
		/*
		Log.w("pos", "----");
		Log.w("pos", _size+ ", " + _size);
		Log.w("pos", nextPosX + ", " + nextPosY);
		Log.w("pos", _displayWidth + ", " + _displayHeight);
		*/
		
		// Check if the position is within canvasview bounds
		if(nextPosX > _displayWidth){
			nextPosX = _displayWidth - this.getWidth();
		}
		
		if(nextPosY > _displayHeight){
			nextPosY = _displayHeight - this.getHeight();
		}
		
		// Set new X and Y
		_posX = nextPosX;
		_posY = nextPosY;
		
		invalidate();
	}
	
	public void setExactPosition(int posX, int posY) {
		_posX = posX;
		_posY = posY;
	}
}