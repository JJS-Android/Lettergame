package nl.hro.minor.android.games.lettergame;

import nl.hro.minor.android.games.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Game extends Activity {

	private ContextHolder ch;
	private ProgressDialog dialog;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        loadStatics();
        ch = ContextHolder.getInstance();
        ch.setContext(this);
		
        // Create handler for database creation thread
        final Handler h = new Handler(){
		    public void handleMessage(Message msg) {
		    	// If thread is finished a message will be send to this method
		    	Log.w("Database", "Creation and opening finished");
		    	dialog.dismiss();
		    	
		    	setContentView(new CanvasView(ch.getContext()));
		    } 
		};
		
		// Show loading dialog
		dialog = new ProgressDialog(this);
        dialog.setMessage("Spel laden...");
        dialog.show();
        
		// Create and open database in a thread
		Thread t = new Thread() {
	        public void run() {
	          try {
				
				DbHolder dbH = DbHolder.getInstance();
				dbH.setDb(new DbUtils(ch.getContext()));
				
				// Send empty message to the handler
				h.sendEmptyMessage(0);
	
	          }catch (Exception e) {
	        	  Log.w("Firing up database", "Failed: " + e.getMessage());
	          }
	        }
        };
		t.start();
        
    }
	
	public void loadStatics()
	{
		Resources res = getResources();
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inScaled = false;  
		Dice._bmp = BitmapFactory.decodeResource(res, R.drawable.dice, opts);
		GameBoard._bmp = BitmapFactory.decodeResource(res, R.drawable.board, opts);
		DiceButton._bmp = BitmapFactory.decodeResource(res, R.drawable.plus, opts);
	}

}
