package nl.hro.minor.android.games.pogo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Tile {

	private int _id;
	private int _posX;
	private int _posY;
	private int _size = 50;
	private int _margin = 5;
	
	private int _color = Color.RED;
	private int _defaultColor = Color.RED;

	private Paint _paint = new Paint();
	
	private String _powerupType = "none";
	
	public Tile(int id) {
		// get row position first
		_posY = (int)Math.floor(id/10);
		// remove row number to get column number
		_posX = id-(_posY*10);
		_id = id;
	}
	
	public void draw(Canvas canvas) {
		// if pos is first x or y, don't use margin
		// otherwise add margin times the row/column number to add margins
		int posX = (_posX > 0) ? (_posX*_size)+(_margin*_posX) : _posX;
		int posY = (_posY > 0) ? (_posY*_size)+(_margin*_posY) : _posY;
		// make rectangle
		Rect r = new Rect(posX, posY, posX+_size, posY+_size);
		// set the color
		_paint.setColor(_color);
		// draw it
		canvas.drawRect(r, _paint);
	}
	
	public void setColor(int c) {
		// change color
		_color = c;
	}
	
	public int getColor(){
		return _color;
	}
	
	public int getDefaultColor(){
		return _defaultColor;
	}
	
	public int getX(){
		return _posX;
	}
	
	public int getY(){
		return _posY;
	}
	
	public void setPowerupType(String pu){
		_powerupType = pu;
	}
	
	public String getPowerupType(){
		return _powerupType;
	}
	public List<Integer> checkNeighbours(List<Integer> dirs, Map<Integer, Tile> tiles, int currentColor, List<Integer> currentPath, List<Integer> tileHistory) {
//		Log.w("game","start checkNeighbours ("+_id+")");
		// add tile to all tiles we have checked
		tileHistory.add(_id);
		// add tile to path, remove if dead end
		currentPath.add(_id);
		int lastTileInHistory = (currentPath.size() == 1) ? currentPath.size()-1 : currentPath.size()-2;
//		Log.w("game","currentPath "+currentPath);
		// for each directions
		for (int dir : dirs) {
			// check next tile in the direction
			int id = _id+dir;
			// check if tile exists and is not the last one we just came from
			if (tiles.containsKey(id) && id != currentPath.get(lastTileInHistory)) {
				// check the color
				if (tiles.get(id).getColor() == currentColor) {
					// if the id was somewhere else in the history: we have a path!
					if (currentPath.contains(id)) {
						if (currentPath.size() <= 5) {
							continue;
						}
						// record from the id we found again
						boolean record = false;
						// isolate the path
						List<Integer> isolatePath = new ArrayList<Integer>();
						// add victory number -2
						isolatePath.add(-2);
						for (int id2 : currentPath) {
							// start recording from that number
							if (id == id2) {
								record = true;
							}
							// save the isolated path ..
							if (record) {
								isolatePath.add(id2);
							}
						}
						// .. and return it if the path is longer than 5 (status + 4 points)
						if (isolatePath.size() > 5) {
							return isolatePath;
						}
					}
					// change directions so we always get the outside path
					// TODO: has to be optimized
					if (dir == 1) {
						dirs = new ArrayList<Integer>();
						dirs.add(-10);
						dirs.add(1);
						dirs.add(10);
						dirs.add(-1);
					} else if (dir == 10) {
						dirs = new ArrayList<Integer>();
						dirs.add(1);
						dirs.add(10);
						dirs.add(-1);
						dirs.add(-10);
					} else if (dir == -1) {
						dirs = new ArrayList<Integer>();
						dirs.add(10);
						dirs.add(-1);
						dirs.add(-10);
						dirs.add(1);
					} else if (dir == -10) {
						dirs = new ArrayList<Integer>();
						dirs.add(-1);
						dirs.add(-10);
						dirs.add(1);
						dirs.add(10);
					}
					// check other tiles around and return result
					List<Integer> tmpPathFound = tiles.get(id).checkNeighbours(dirs, tiles, currentColor, currentPath, tileHistory);
					// if we have not found a path: remove last tile
					if (tmpPathFound.get(0) == -1) {
//						Log.w("game","removed: "+tmpPathFound.get(currentPath.size()-1));
						tmpPathFound.remove(currentPath.size()-1);
					} else {
						return tmpPathFound;
					}
				}
			}
		}
		// return back the history to keep the chain
		return currentPath; 
	}
}
