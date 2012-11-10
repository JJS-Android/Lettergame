package nl.hro.minor.android.lettergame.jjs.differences;

import nl.hro.minor.android.lettergame.jjs.R;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

public class ClickHandler {
	
private Bitmap _bitmap;
private contextHolder _ch =  contextHolder.getInstance();
private boolean returnValue = false;

private ArrayList<Point> _redPoints = new ArrayList<Point>();	
	
	public void setBitmap(Bitmap bitmap)
	{
		_bitmap = bitmap;
	}
	
	public boolean handleClick(MotionEvent event)
	{
    	ImageView iv = (ImageView) _ch.getContext().findViewById(R.id.clickAreaView);
		_bitmap = ((BitmapDrawable)iv.getDrawable()).getBitmap();
		
		int x = (int)event.getX();
        int y = (int)event.getY();
        
        if(checkIfRed(x, y) == true)
		{
        	Log.d("red", "red");
        	contextHolder ch = contextHolder.getInstance();
        	Toast.makeText(ch.getContext(), "Raak!", Toast.LENGTH_SHORT).show();
		 _redPoints.add(new Point(x, y));
		 loopThroughPoints();
		 Game1.clickedRightCount++;
		 if (Game1.clickedRightCount == 3)
		 {
			 returnValue = true;
			 return true;
		 }
		}
		else
		{
			Log.d("not red", "not red");
			//do stuff to punish for nog guessing correctly
		}
        
    	_ch.getContext().findViewById(R.id.clickAreaView).invalidate();
    	
    	returnValue = false;
    	return false;
		
	}
	
	public boolean getLastReturnValue(){
		return returnValue;
	}
	
    private void checkforRedpixel(int x, int y)
    {
    	
    	ArrayList<Point> points = new ArrayList<Point>();
    	
    	if (isPointInImage(x+1,y)) {
    		points.add(new Point(x+1,y));
    	}
    	if (isPointInImage(x-2,y)) {
    		points.add(new Point(x-2,y));
    	}
    	if (isPointInImage(x,y+1)) {
    		points.add(new Point(x,y+1));
    	}
    	if (isPointInImage(x,y-1)) {
    		points.add(new Point(x,y-1));
    	}
    	
    	for (Point point:points )
    	{
    		if(checkIfRed(point.x, point.y)==true)
    		{
    		 _redPoints.add(point);
    		 reColorPixel(point);
    		}
    	}
    }
    
    private boolean isPointInImage(int x, int y) {
    	if (x > 0 && x < _bitmap.getWidth() && y > 0 && y < _bitmap.getHeight()) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    private boolean checkIfRed(int x, int y)
    {
		if (isPointInImage(x,y)) {
			int pixel = _bitmap.getPixel(x,y);
			if(pixel== Color.RED){
	           return true;
	         }
			else
			{
				return false;
			}
		}
		return false;
    }
    
    private void loopThroughPoints()
    {
    	for(int i = 0; i < _redPoints.size(); i++ )
    	{
    		Point point = _redPoints.get(i);
    		checkforRedpixel(point.x, point.y);
    	}
    }
    
    private void reColorPixel(Point point)
    {
    	_bitmap.setPixel(point.x, point.y, Color.BLUE);
    }

    


}
