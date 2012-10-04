package nl.hro.minor.android.lettergame.jjs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CanvasView extends View{
	Context mContext;
	int counter;
	
	int ballPosX;
	int ballPosY;
	int ballSize;
	
	int boxPosX = 100;
	int boxPosY = 100;
	
	int lastBoxGotoX = 0;
	int lastBoxGotoY = 0;
	
	public CanvasView(Context context) {
		super(context);
		mContext = context;
		
		setFocusable(true);
		setFocusableInTouchMode(true);
				
	}
	
	@Override

	protected void onDraw(Canvas canvas){
		Paint myPaint = new Paint();
		myPaint.setStrokeWidth(3);
		myPaint.setColor(0xFF097286);
		canvas.drawCircle(ballPosX, ballPosY, 30 + (ballSize * 15), myPaint);
		
		updateBoxPosition(canvas);
		canvas.drawRect(new Rect(boxPosX, boxPosY, boxPosX+30, boxPosY+30), myPaint);
		
		invalidate();
	}
	
	private void updateBoxPosition(Canvas cv){
		
		boxPosX -= lastBoxGotoX;
        boxPosY += lastBoxGotoY;
		
		if(boxPosX >= cv.getWidth()-30) boxPosX = cv.getWidth() - 30;
		if(boxPosY >= cv.getHeight()-110) boxPosY = cv.getHeight() - 110;
		
		if(boxPosX <= 0) boxPosX = 1;
		if(boxPosY <= 0) boxPosY = 1;
		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN :
				 // Moet gewoon...
				return true;
			case MotionEvent.ACTION_MOVE :
				ballPosX = (int) event.getRawX();
				ballPosY = (int) event.getRawY();
				ballSize = (int) event.getPressure();
				invalidate();
			return true;
			case MotionEvent.ACTION_UP :
				
			return true;
		}
		
		return true;
	}
	
}
