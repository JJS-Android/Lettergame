package nl.hro.minor.android.lettergame.jjs;

import java.io.IOException;

import android.app.Activity;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;

public class Game extends Activity {

	private ContextHolder ch;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        loadStatics();
        setContentView(new CanvasView(this));
        ch = ContextHolder.getInstance();
        ch.setContext(this);
        
		DbHolder dbH = DbHolder.getInstance();
		dbH.setDb(new DbUtils(this));
        
    }
	
	public void loadStatics()
	{
		Resources res = getResources();
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inScaled = false;  
		Dice._bmp = BitmapFactory.decodeResource(res, R.drawable.dice, opts);
		GameBoard._bmp = BitmapFactory.decodeResource(res, R.drawable.board, opts);
	}

}
