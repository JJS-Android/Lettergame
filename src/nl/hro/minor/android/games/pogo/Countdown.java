package nl.hro.minor.android.games.pogo;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;

public class Countdown {

	private int _gameTime = 90000;
	private Paint _p;
	private CountDownTimer _cdt;
	private ScoreDisplay _sd;
	
	public Countdown(ScoreDisplay sd) {
		
		_sd = sd;
		
		_p = new Paint();
		_p.setColor(Color.WHITE);
		_p.setAlpha(200);
		_p.setTextSize(20);
		//p.setStyle(style);
		
		_cdt = new CountDownTimer(_gameTime, 1000) {
	         public void onTick(long msUntilFinished){

	        	 _gameTime -= 1000;
	             
	         }
	         public void onFinish(){
	        	 // Do something when game is finished
	        	 // (Calculate score etc.)
	        	 _sd.showFinalScore();
	        	 
	         }
	      }.start();
	      
	}
	
	public void draw(Canvas c){
		c.drawText("Time left: " + (_gameTime/1000), 10, 25, _p);
	}
	
	public void pause(){
		_cdt.cancel();
	}
	
	public void resume(){
		_cdt.start();
	}

}
