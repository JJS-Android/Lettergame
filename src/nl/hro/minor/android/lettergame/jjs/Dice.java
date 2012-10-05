package nl.hro.minor.android.lettergame.jjs;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.Display;


public class Dice {
	
	public static Bitmap _bmp;
	private Rect _diceRect;
	private int _frames;
	private int _currentFrame;
	private int _spriteHeight;
	private int _spriteWidth;
	
	private int _speedX;
	private int _speedY;
	private int _posX;
	private int _posY;
	private boolean _moving;
	private ContextHolder _ch;
	private int _size;
	
	public Dice(int posX, int posY, int speedX, int speedY)
	{
		_ch = ContextHolder.getInstance();
		
	    _diceRect = new Rect(0,0,55,55);
	    _currentFrame =1;
	    _frames = 11;
		
	    _size = 55;
		_posX =posX;
		_posY =posY;
		_speedX = speedX;
		_speedY = speedY;
		startAnimation();
		
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
		
		Display display = _ch.getContext().getWindowManager().getDefaultDisplay();	
		
		if(_moving == true)
		{
		int width = display.getWidth();
		int height = display.getHeight();
		int nextPosX = _posX +=_speedX;
		int nextPosY = _posY +=_speedY;
		if(nextPosX > width-_size || nextPosX<0)
		{
			_speedX*=-1;
		}
		if(nextPosY > height-_size || nextPosY<0)
		{
			_speedY*=-1;
		}
		_posX =nextPosX;
		_posY =nextPosY;
		}
	}
	
	public void draw(Canvas canvas){
	move();
	updateFrame();
	Rect location = new Rect(_posX, _posY, _posX+_size, _posY+_size);
	canvas.drawBitmap(_bmp, _diceRect, location, null);
	}
}