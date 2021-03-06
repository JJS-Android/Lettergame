package nl.hro.minor.android.games.lettergame;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Region;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

public class CanvasView extends View {
	private Context mContext;
	private Dice[] _dices; 
	private GameBoard _gameBoard;
	private DiceButton _diceButton;
	private int _width;
	private int _height;
	private ContextHolder _ch;
	private int _score;
	private ArrayList<String> _guessedWords = new ArrayList<String>();
	
	private int _destHeight;
	private int[] _squares = { -1,-1,-1,-1,-1,-1,-1,-1 };
	
	private Region _checkBounds;
	private int _currentDiceDragging = -1; // -1 = none (yet) or stopped dragging
	
	private int[] _lastPosition = new int[2];
	private Paint _paintRed;
	private Paint _paintWhite;
	private String _message = "";
	
	public CanvasView(Context context) {
		super(context);
		mContext = context;
		_ch = ContextHolder.getInstance();
		//get screen dimensions
		Display display = _ch.getContext().getWindowManager().getDefaultDisplay();	
		_width = display.getWidth();
		_height = display.getHeight();
		//make gameboard
		_gameBoard = new GameBoard(_width, _height);
		//make diceButton
		_diceButton = new DiceButton(_width, _height);
		_score = 0;
		//throw dices
		makeDiceArray();
		//set height where dice snap to the destination squares
		_destHeight = _gameBoard.getHeight() - 150;
		
		// set score paint
		_paintRed = new Paint();
		_paintRed.setColor(Color.rgb(238, 87, 21)); 
		_paintRed.setTextSize(30);
		// set message paint
		_paintWhite = new Paint();
		_paintWhite.setColor(Color.WHITE);
		_paintWhite.setTextSize(30);
		_paintWhite.setTextAlign(Align.CENTER);
		
		setFocusable(true);
		setFocusableInTouchMode(true);

	}
	
	private boolean checkBoxCollision(Dice dice1, Dice dice2) {
	    
		//if dice location not is null check if it hits another dice
		if(dice1.get_location()!=null && dice2.get_location()!=null)if(dice1.get_location().intersect(dice2.get_location())) return true;
		return false;
	}
	
	private void makeDiceArray()
	{
		
		_dices = new Dice[8];
		
		for (int i = 0; i < _dices.length; i++) { 
			Random r = new Random();
			_dices[i] = new Dice(mContext, r.nextInt((_width-60)), 100, r.nextInt(10)-r.nextInt(10), r.nextInt(12)-r.nextInt(12));
			_dices[i].startAnimation();
	    }
	}
	
	private void extraDice()
	{
		
		if(_diceButton.isActive())
		{
			int diceCount = _dices.length;
			Dice[] temp = new Dice[diceCount+1];
			for (int i = 0; i < _dices.length; i++) { 
				temp[i] = _dices[i];
				
		    }
			
			Random r = new Random();
			Log.d("test", ""+diceCount);
			temp[diceCount] = new Dice(mContext, r.nextInt((_width-60)), 100, r.nextInt(10)-r.nextInt(10), r.nextInt(12)-r.nextInt(12));
			temp[diceCount].startAnimation();
			
			_dices = temp;
			_score-= 200;
		}
		_diceButton.setActive(false);
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		// draw score text
		canvas.drawText("Score: "+_score, 15, 30, _paintRed);
		if(_score>=200) _diceButton.setActive(true);
		
		// draw gameboard (destination squares)
		_gameBoard.draw(canvas);
		//draw button to het extra dice
		if(_diceButton.isActive()) _diceButton.draw(canvas);
		
		// draw message
		canvas.drawText(_message, _width/2, _height-30, _paintWhite);
		for (int i = 0; i < _dices.length; i++) { 
			_dices[i].move();
			_dices[i].updateFrame();
			_dices[i].draw(canvas);

			for (int c = 0; c < _dices.length; c++) {
				if(i != c)if (checkBoxCollision(_dices[i], _dices[c]))_dices[i].collisionAcivity();

			}
			

	       
	    }
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
						_lastPosition[0] = (int) event.getRawX();
						_lastPosition[1] = (int) event.getRawY();
						
						return true; // prevent loop from checking other (remaining) dices
					}
					//check if player wants to buy extra letter
					if(_diceButton.getBounds().contains((int) event.getRawX(), (int) event.getRawY())) extraDice();
					
				
			    }
			return true;
			case MotionEvent.ACTION_MOVE :
				// move dice (letter)
				if(_currentDiceDragging != -1){
					// Update position of _currentDiceDragging item
					_dices[_currentDiceDragging].setPosition((int) event.getX(), (int) event.getY());
					invalidate();
				}
			return true;
			case MotionEvent.ACTION_UP :
				// snap dice to squares
				if (_currentDiceDragging != -1) {
					// get current square position
					int currentSquare = _dices[_currentDiceDragging].getSquare();
					// check if last position was on a square, if so reset that position
					if (currentSquare != -1) {
						_squares[currentSquare] = -1;
					}
					// if new position is below a certain height -> snap to square
					if (event.getRawY() > _destHeight) {
						// get the destination square to snap to
						int square = (int) Math.ceil((event.getRawX()-67) / 55);
						// make sure its always within 0-7 range (8 squares)
						square = (square < 0) ? 0 : square;
						square = (square > 7) ? 7 : square;
						_dices[_currentDiceDragging].setExactPosition(10+(square*55), (_destHeight+15));
						if (_squares[square] == -1) {
							// let the square remember his position
							_dices[_currentDiceDragging].setSquare(square);
							// set (new) square position in checkWord() array
							_squares[square] = _currentDiceDragging;
							checkWord();
						} else {
							// a dice is already at this square position
							_dices[_currentDiceDragging].setPosition(_lastPosition[0], _lastPosition[1]);
						}
					} else {
						// remove previous square position
						if (currentSquare != -1) {
							_dices[_currentDiceDragging].setSquare(-1);
							checkWord();
						}
					}
					_currentDiceDragging = -1; // Set to -1 (none) again
				}
			return true;
		}
		return true;
	} 
	
	private void checkWord() {
		String word = "";
		int countLetters = 0;
		// loop through square positions
		for (int i = 0; i < _squares.length; i++) {
			if (_squares[i] != -1) {
				countLetters++;
				// add letter to the word
				word += _dices[_squares[i]].getLetter();
			}
		}
		if (countLetters > 2) {
			if (GameDictionary.checkWord(word)) {
				if(_guessedWords.contains(word))
				{
					_message = "Woord is al geraden";
				}
				else
				{
					int score = word.length()*10;
					_message = word+" is goed! (+" + score + ")";
					_score += score;
					_guessedWords.add(word);
				}
			} else {
				_message = word + " is fout!";
			}
		}
	}
}
