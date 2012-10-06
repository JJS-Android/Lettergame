package nl.hro.minor.android.lettergame.jjs;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
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
	
	public CanvasView(Context context) {
		super(context);
		mContext = context;
		_ch = ContextHolder.getInstance();
		//get screen dementions
		Display display = _ch.getContext().getWindowManager().getDefaultDisplay();	
		_width = display.getWidth();
		_height = display.getHeight();
		//throw dices
		makeDiceArray();
		//make gameboard
		_gameBoard = new GameBoard(_width, _height);

	}
	
	public void makeDiceArray()
	{
		
		_dices = new Dice[8];
		
		for (int i = 0; i < _dices.length; i++) { 
			Random r = new Random();
			_dices[i] = new Dice(r.nextInt((_width-60)), 0,r.nextInt(3)+1,r.nextInt(5)+1);
	       
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
	
}
