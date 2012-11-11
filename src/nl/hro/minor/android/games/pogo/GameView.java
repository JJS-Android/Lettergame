package nl.hro.minor.android.games.pogo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

public class GameView extends View {

	private int _fieldSize = 7; // = 8 tiles (0-7)
	private Map<Integer, Tile> _tiles = new LinkedHashMap<Integer, Tile>();
	private Map<Integer, Player> _playerList = new LinkedHashMap<Integer, Player>();
	private List<Integer> _checkedTiles = new ArrayList<Integer>();
	
	private Countdown _cd;
	private ScoreDisplay _sd;
	
	private GameInput _gi;
	private PowerupManager _pum;
	
	public GameView(Context context) {
		super(context);
		
		// Build level map - rows are 10,20,30 etc
		for (int a = 0; a <= (_fieldSize*10); a+=10) {
			// columns are 1,2,3 etc
			for (int b = 0; b <= _fieldSize; b++) {
				// id is row(2) + column(4) = 24
				int id = a+b;
				_tiles.put(id, new Tile(id));
			}
		}
		
		// Build players
		
		// First player is controlled by the user
		_playerList.put(1, new Player(1, 1, _tiles, Color.BLUE));
		_playerList.get(1).setGameView(this);
		
		// Link the GameInput to the first player
        _gi = new GameInput(_playerList.get(1));
		
		// Other 3 are computer controlled
        AiPlayer aip1 = new AiPlayer(2, 1, _tiles, Color.GREEN);
        AiPlayer aip2 = new AiPlayer(3, 2, _tiles, Color.YELLOW);
        AiPlayer aip3 = new AiPlayer(4, 2, _tiles, Color.MAGENTA);
        
        // Add gameview to players
        aip1.setGameView(this);
        aip2.setGameView(this);
        aip3.setGameView(this);

        // Add to playerlist and pass the gameview
        _playerList.put(2, aip1);
        _playerList.put(3, aip2);
        _playerList.put(4, aip3);

        // Start Ai for the AiPlayers
        aip1.startAi();
        aip2.startAi();
        aip3.startAi();
        
        
        // Display user scores
        _sd = new ScoreDisplay(_playerList, context);
        
        // Show level countdown
        _cd = new Countdown(_sd);
        
        // Create PowerupManager - Generate random 'check-ins' (get points for tiles in your color) and powerups (optional)
        _pum = new PowerupManager(_tiles);
        _pum.start();
        
		
	}

	@Override
	protected void onDraw(Canvas canvas){
		// iterate through keys for drawing level map
		for (int id : _tiles.keySet()) {
	    	// draw Tile
			_tiles.get(id).draw(canvas);
		}
		// Iterate over players and draw them
		for (int id : _playerList.keySet()) {
	    	// draw player
			_playerList.get(id).draw(canvas);
		}
		
		// Draw countdown timer
		_cd.draw(canvas);
		// Draw ScoreDisplay
		_sd.draw(canvas);
		
		// Invalidate the view (redraw)
		invalidate();
	}

	
	/*
	 * Path finder finds most outside path of a specified color
	 */
	public void checkForFill(int color) {
		// loop through the tiles
		for (int id : _tiles.keySet()) {
			if (!_checkedTiles.contains(id)) {
//				Log.w("game","START check id: "+id);
				// get the right color
				int currentColor = _tiles.get(id).getColor(); 
				if (currentColor == color) {
					// List to keep tile history
					List<Integer> pathFound = new ArrayList<Integer>();
					List<Integer> tileHistory = new ArrayList<Integer>();
					// add first entry we check when we get it back, will be -2 if success
					pathFound.add(-1);
					// add the directions we will check in, first: top, right, bottom, left
					List<Integer> directions = new ArrayList<Integer>();
					directions.add(-10);
					directions.add(1);
					directions.add(+10);
					directions.add(-1);
					// set out the path finder and get back a path
					pathFound = _tiles.get(id).checkNeighbours(directions, _tiles, currentColor, pathFound, tileHistory);
					for (int id2 : tileHistory) {
						if (!_checkedTiles.contains(id2)){
							_checkedTiles.add(id2);
						}
					}
					// if first entry is -2, we have a path!
					if (pathFound.get(0) == -2) {
						fillIn(pathFound, color);
					}
				}
			}
		}
		_checkedTiles = new ArrayList<Integer>();
	}
	
	/*
	 * Get all values of one row,
	 * find the most outside values and fill in between
	 */
	private void fillIn(List<Integer> pathFound, int color) {
		// sort number low to high
		Collections.sort(pathFound);
		boolean fill = false;
		int tmpEndFill = -1;
		// loop through tiles
		for (int id : _tiles.keySet()) {
			if (pathFound.contains(id)) {
				if (id == tmpEndFill) {
					fill = false;
					tmpEndFill = -1;
				} else if (tmpEndFill == -1) {
					int min = (int) (Math.floor(id/10.0) * 10);
					int max = (int) (Math.ceil(id/10.0) * 10);
					if (max == id) {
						max = id+10;
					}
					for (int id2 : pathFound) {
						if (id != id2 && (id2 > min && id2 < max)) {
							tmpEndFill = id2;
						}
					}
					if (tmpEndFill != -1) {
						fill = true;
					}
				}
			}
			// if fill is true and the color is different: set the color
			int tileColor = _tiles.get(id).getColor();
			if (fill && tileColor != color && tileColor != Color.WHITE) {
				_checkedTiles.add(id);
				_tiles.get(id).setColor(color);
			}
		}
	}
	
	public void pause(){
		
		// Stop players moving
		Player player;
		for(Integer playerIndex : _playerList.keySet()){
			player = _playerList.get(playerIndex);
			player.pause(); // Pause
		}
		
		// Pause Gameinput
		_gi.pause();
		
		// Pause Countdown
		_cd.pause();
		
		// Pause PowerUpManger
		_pum.pause();

	}
	
	
	public void resume(){
		
		// Stop players moving
		Player player;
		for(Integer playerIndex : _playerList.keySet()){
			player = _playerList.get(playerIndex);
			player.resume(); // Resume
		}
		
		// Resume Gameinput
		_gi.resume();
		
		// Resume countdown
		_cd.resume();
		
		// Resume PowerUpManager
		_pum.resume();

	}
}
