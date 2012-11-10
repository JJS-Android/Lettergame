package nl.hro.minor.android.lettergame.jjs.differences;

import nl.hro.minor.android.lettergame.jjs.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class Game1 extends Activity implements OnTouchListener {
	
	private ClickHandler _clikhandler = new ClickHandler();
	private contextHolder ch;
	private levelManager lvlmngr = new levelManager();
	private ImageView clickArea;
	private ImageView imageView1;
	private ImageView imageView2;
	private ProgressBar progessBar;
	private CountDownTimer countDown;
	
	public static int clickedRightCount;
	
	private int currentLevel = 1;
	
	private int _timeLeft;
	
	public static final int playTime = 60;
	public static int _totalScore = 0;
    
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playfield);
        
        // Set the current context (this) in the 'singleton' contextHolder.
        ch = contextHolder.getInstance();
        ch.setContext(this);
        /*
        lvlmngr.getNewLevel();
        */
        
        progessBar = (ProgressBar) findViewById(R.id.progress_bar);
        
        clickArea = (ImageView) findViewById(R.id.clickAreaView);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);

		startLevel();
		imageView1.setOnTouchListener(this);
		imageView2.setOnTouchListener(this);
        
    }

    private ProgressDialog dialog;
    
    public boolean onTouch(View v, MotionEvent event) {

        
        int action = event.getAction();
        
        if (action == MotionEvent.ACTION_DOWN)
        {
        	
        	// Show loading dialog
    		dialog = new ProgressDialog(this);
            dialog.setMessage("Controleren...");
            dialog.show();
            
            // Create handler
            final Handler h = new Handler(){
    		    public void handleMessage(Message msg) {
    		    	// If thread is finished a message will be send to this method
    		    	
    		    	if (_clikhandler.getLastReturnValue()) {
    	        		currentLevel++;
    	        		clickedRightCount = 0;
    	        		countDown.cancel();
    	        		Intent i = new Intent(ch.getContext(), Score.class);
    	        		i.putExtra("timeLeft", (_timeLeft/1000));
    	        		if (currentLevel > 3) {
    		        		i.putExtra("isFinished", true);
    		        		startActivity(i);
    	        		} else {
    		        		i.putExtra("isFinished", false);
    		        		startActivity(i);
    		        		startLevel();
    	        		}
    	        	}
    		    	
    		    	Log.w("PixelCheck", "Done, close loading message");
    		    	dialog.dismiss();
    		    	
    		    } 
    		};
    		
    		final MotionEvent fEvent = event;
    		
    		// Run clickHandler in a seperate thread
    		Thread t = new Thread() {
    	        public void run() {
    	          try {
    	        	  
    	        	//Looper.prepare(); // No idea why, just have to use it
    	        	Looper.prepareMainLooper();
    				
    	        	//pass event into clickhandler for checks
    	          	_clikhandler.handleClick(fEvent);
    				
    				// Send empty message to the handler
    				h.sendEmptyMessage(0);
    	
    	          }catch (Exception e) {
    	        	  Log.w("Clickhandler", "PixelCheck Failed: " + e.getMessage());
    	          }
    	        }
            };
    		t.start();
    		
        }
       
        
    return false;
    }
    
    private void startLevel()
    {
    	int playtime = playTime*1000;
    	countDown = new CountDownTimer(playtime, 1000) {

    	     public void onTick(long millisUntilFinished) {
    	    	 
    	    	 float playtime = playTime*1000;
    	    	 float time = (100/playtime) * (float) millisUntilFinished;
    	    	 _timeLeft = (int)millisUntilFinished;
    	    	 progessBar.setProgress((int)time);
    	     }

    	     public void onFinish() {
    	    	 finishGame();
    	     }
    	     public void cancelTimer() {
    	    	 this.cancel();
    	     }
    	  }.start();
    	 
    	  
		//int currentLevel = lvlmngr.getNewLevel();
		//lvlmngr.load(currentLevel); 
		
		int img1 = getResources().getIdentifier("zdv_0" + currentLevel + "_01","drawable","minigames.JJS.minor.hro");
		int img2 = getResources().getIdentifier("zdv_0" + currentLevel + "_02","drawable","minigames.JJS.minor.hro");
		
		bitmapCompare bmc = new bitmapCompare();
		//Bitmap diffMap = bmc.getDiffMap(R.drawable.zdv_01_01, R.drawable.zdv_01_02);
		Bitmap diffMap = bmc.getDiffMap(img1, img2);
			
		clickArea.setImageBitmap(diffMap);
		imageView1.setImageResource(img1);
		imageView2.setImageResource(img2);
    }
    
    private void finishGame() {
 		Intent i = new Intent(ch.getContext(), Score.class);
		i.putExtra("isFinished", true);
 		startActivity(i);
    }
}