package nl.hro.minor.android.games.pogo;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;

public class AiPlayer extends Player {

	private TimerTask moveTask;
	final Handler handler = new Handler();
	private Timer t = new Timer();
	private boolean _makeSquire = false;
	private int _path[] = new int[]{2,2,2,4,4,4,1,1,1,3,3,3};
	private int _pathPosition = 0;
	
	private int _minLimit = 1;
	private int _maxLimit = 4;
	
	private int _moveInternal = 300; // Movement delay in ms
	private int _delayBeforeFirstMove = _moveInternal*3; // in ms
	
	public AiPlayer(int id, int teamId, Map<Integer, Tile> map, Integer color) {
		super(id, teamId, map, color);
		
		// Set start corner for each player
		if(id == 2){
			this.moveTile(2,8); // 8 times to right (top right corner)
		}
		
		if(id == 3){
			this.moveTile(3,8); // 8 times down (bottom left corner)
		}
		
		if(id == 4){
			this.moveTile(2,8); // 8 times to right
			this.moveTile(3,8); // 8 times down (bottom right corner)
		}
	}
	
	public void startAi(){
		// Start players auto intelligence
		
		final AiPlayer self = this;
		
		moveTask = new TimerTask() {
	        public void run() {
			handler.post(new Runnable() {
					public void run() {
						if (1 + (int) (Math.random() * ((10 - 1) + 1))==10)_makeSquire=true;
						
						if(_makeSquire == true)
						{
							if (_pathPosition>=11)
							{
								_pathPosition = 0;
								_makeSquire = false;
								
							}
							self.move(_path[_pathPosition]);
							_pathPosition++;
						}
						else
						{
						int goTo = _minLimit + (int) (Math.random() * ((_maxLimit - _minLimit) + 1));
						self.move(goTo);
						}
						//Log.w("AiPlayer", "AiP " + self._id + " moved to: " + goTo);
					}
				});
	        }
	    };
	        
		t.scheduleAtFixedRate(moveTask, _delayBeforeFirstMove, _moveInternal);
		
	}

}
