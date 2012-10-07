package nl.hro.minor.android.lettergame.jjs;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Region;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

public class CanvasView extends View{
	private Context mContext;
	private Dice[] _dices; 
	private GameBoard _gameBoard;
	private int _width;
	private int _height;
	private ContextHolder _ch;
	
	private Region _checkBounds;
	private int _currentDiceDragging = -1; // -1 = none (yet) or stopped dragging
	
	public CanvasView(Context context) {
		super(context);
		mContext = context;
		_ch = ContextHolder.getInstance();
		//get screen dimensions
		Display display = _ch.getContext().getWindowManager().getDefaultDisplay();	
		_width = display.getWidth();
		_height = display.getHeight();
		//throw dices
		makeDiceArray();
		//make gameboard
		_gameBoard = new GameBoard(_width, _height);
		
		setFocusable(true);
		setFocusableInTouchMode(true);

	}
	
	public void makeDiceArray()
	{
		
		_dices = new Dice[8];
		
		for (int i = 0; i < _dices.length; i++) { 
			Random r = new Random();
			_dices[i] = new Dice(mContext, r.nextInt((_width-60)), 0, r.nextInt(3)+1, r.nextInt(5)+1);
	       
	    }
	}
	
	@Override
	protected void onDraw(Canvas canvas){
	
		for (int i = 0; i < _dices.length; i++) { 
			
		_dices[i].draw(canvas);
	       
	    }
		_gameBoard.draw(canvas);
		invalidate();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		// Switch over different touch states
		switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN :
				// Return true is required in some (lower) Android versions
				
				for (int i = 0; i < _dices.length; i++) { 
					
					_checkBounds = _dices[i].getBounds();
					
					if(_checkBounds.contains( (int) event.getRawX(), (int) event.getRawY())){
						_currentDiceDragging = i;
					}
					                                                             
			    }
				
				return true;
			case MotionEvent.ACTION_MOVE :
				
				// move dice (letter)
				if(_currentDiceDragging != -1){
					// Update position of _currentDiceDragging item
					_dices[_currentDiceDragging].setPosition((int) event.getRawX(), (int) event.getRawY());
					invalidate();
				}
				
				
			return true;
			case MotionEvent.ACTION_UP :
				
				if(_currentDiceDragging != -1){
					Log.w("Letter dragged", _dices[_currentDiceDragging].getLetter());
					_currentDiceDragging = -1; // Set to -1 (none) again
				}
				
			return true;
		}
		
		return true;
	
	}
	
}
