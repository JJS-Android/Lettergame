package nl.hro.minor.android.lettergame.jjs;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;


public class Game extends Activity {

	private ContextHolder ch;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(new CanvasView(this));
        ch = ContextHolder.getInstance();
        ch.setContext(this);
        loadStatics();
        
    }
	
	public void loadStatics()
	{
		Resources res = getResources();
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inScaled = false;  
		Dice._bmp = BitmapFactory.decodeResource(res, R.drawable.dice, opts);
	}

}
