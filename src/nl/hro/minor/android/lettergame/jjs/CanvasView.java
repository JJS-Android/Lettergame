package nl.hro.minor.android.lettergame.jjs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CanvasView extends View{
	Context mContext;
	Dice dice = new Dice(100,100,2,4);
	
	public CanvasView(Context context) {
		super(context);
		mContext = context;
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		dice.draw(canvas);		
		invalidate();
	}
	
}
