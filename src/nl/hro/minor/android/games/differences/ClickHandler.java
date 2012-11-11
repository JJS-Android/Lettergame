package nl.hro.minor.android.games.differences;

import java.util.ArrayList;

import nl.hro.minor.android.games.R;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;

public class ClickHandler {
    
    private Bitmap _bitmap;
    private contextHolder _ch =  contextHolder.getInstance();
    private boolean _returnValue = false;
    
    private ArrayList<Point> _redPoints = new ArrayList<Point>();
    
    public ClickHandler() {
        ImageView iv = (ImageView) _ch.getContext().findViewById(R.id.clickAreaView);
        _bitmap = ((BitmapDrawable)iv.getDrawable()).getBitmap();
    }
    
    public void setBitmap(Bitmap bitmap) {
        _bitmap = bitmap;
    }
    
    public boolean handleClick(int x, int y) {
        if (checkIfRed(x, y)) {
            _redPoints.add(new Point(x, y));
            loopThroughPoints();
            Game1.clickedRightCount++;
            if (Game1.clickedRightCount == 3) {
                _returnValue = true;
            }
            return true;
        } else {
            //TODO: stuff to punish for not guessing correctly
        }
        
        _returnValue = false;
        return false;
    }
    
    private boolean checkIfRed(int x, int y) {
        if (isPointInImage(x,y)) {
            int pixel = _bitmap.getPixel(x,y);
            if(pixel == Color.RED){
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    
    private void loopThroughPoints() {
        for(int i = 0; i < _redPoints.size(); i++ ) {
            Point point = _redPoints.get(i);
            checkforRedpixel(point.x, point.y);
        }
    }
    
    private void checkforRedpixel(int x, int y) {
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
        
        for (Point point:points ) {
            if (checkIfRed(point.x, point.y)) {
                //_redPoints.add(point); // this caused an infinite loop, what was its purpose?
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
    
    private void reColorPixel(Point point) {
        _bitmap.setPixel(point.x, point.y, Color.BLUE);
    }
    
    public boolean getLastReturnValue() {
        return _returnValue;
    }
}
