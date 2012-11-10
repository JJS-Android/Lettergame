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
    
    private ClickHandler _clickhandler;
    private contextHolder _ch;
    private levelManager _lvlmngr = new levelManager();
    private ImageView _clickArea;
    private ImageView _imageView1;
    private ImageView _imageView2;
    private ProgressBar _progessBar;
    private CountDownTimer _countDown;
    private ProgressDialog dialog;
    
    public static int clickedRightCount;
    
    private int _currentLevel = 1;
    
    private int _timeLeft;
    
    public static final int playTime = 60;
    public static int _totalScore = 0;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playfield);
        
        // Set the current context (this) in the 'singleton' contextHolder.
        _ch = contextHolder.getInstance();
        _ch.setContext(this);
        /*
        lvlmngr.getNewLevel();
        */
        
        _progessBar = (ProgressBar) findViewById(R.id.progress_bar);
        _clickArea = (ImageView) findViewById(R.id.clickAreaView);
        _imageView1 = (ImageView) findViewById(R.id.imageView1);
        _imageView2 = (ImageView) findViewById(R.id.imageView2);

        loadImgs();
        _clickhandler = new ClickHandler();
        startLevel();
        _imageView1.setOnTouchListener(this);
        _imageView2.setOnTouchListener(this);
        
    }
    
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            // Show loading dialog
            dialog = new ProgressDialog(this);
            dialog.setMessage("Controleren...");
            dialog.show();
            
            // Create handler
            final Handler h = new Handler() {
                public void handleMessage(Message msg) {
                    // If thread is finished a message will be send to this method
                    if (_clickhandler.getLastReturnValue()) {
                        findViewById(R.id.clickAreaView).invalidate();
                        _currentLevel++;
                        clickedRightCount = 0;
                        _countDown.cancel();
                        Intent i = new Intent(_ch.getContext(), Score.class);
                        i.putExtra("timeLeft", (_timeLeft/1000));
                        if (_currentLevel > 3) {
                            i.putExtra("isFinished", true);
                            startActivity(i);
                        } else {
                            i.putExtra("isFinished", false);
                            startActivity(i);
                            loadImgs();
                            _clickhandler = new ClickHandler();
                            startLevel();
                        }
                    }
                    dialog.dismiss();
                }
            };

            //final MotionEvent fEvent = event;
            final int eventX = (int) event.getX();
            final int eventY = (int) event.getY();

            // Run clickHandler in a seperate thread
            Thread t = new Thread(new Runnable() {
                private int eX = eventX;
                private int eY = eventY;
                public void run() {
                    try {
                        //Looper.prepare(); // No idea why, just have to use it
                        Looper.prepareMainLooper();
                        
                        //pass event into clickhandler for checks
                        _clickhandler.handleClick(eX, eY);

                        // Send empty message to the handler
                        h.sendEmptyMessage(0);
                    } catch (Exception e) {
                        Log.e("Clickhandler", "Error in Thread handleClick: " + e.getMessage());
                    }
                }
            });
            t.start();
        }
        return false;
    }

    private void loadImgs() {
        //int currentLevel = lvlmngr.getNewLevel();
        //lvlmngr.load(currentLevel); 
        
        int img1 = getResources().getIdentifier("zdv_0" + _currentLevel + "_01","drawable","nl.hro.minor.android.lettergame.jjs");
        int img2 = getResources().getIdentifier("zdv_0" + _currentLevel + "_02","drawable","nl.hro.minor.android.lettergame.jjs");
        
        bitmapCompare bmc = new bitmapCompare();
        //Bitmap diffMap = bmc.getDiffMap(R.drawable.zdv_01_01, R.drawable.zdv_01_02);
        Bitmap diffMap = bmc.getDiffMap(img1, img2);
        	
        _clickArea.setImageBitmap(diffMap);
        _imageView1.setImageResource(img1);
        _imageView2.setImageResource(img2);
    }
    
    private void startLevel() {
        int playtime = playTime*1000;
        _countDown = new CountDownTimer(playtime, 1000) {
        
             public void onTick(long millisUntilFinished) {
                 float playtime = playTime*1000;
                 float time = (100/playtime) * (float) millisUntilFinished;
                 _timeLeft = (int)millisUntilFinished;
                 _progessBar.setProgress((int)time);
             }
        
             public void onFinish() {
            	 finishGame();
             }
             public void cancelTimer() {
            	 this.cancel();
             }
          }.start();
    }
    
    private void finishGame() {
        Intent i = new Intent(_ch.getContext(), Score.class);
        i.putExtra("isFinished", true);
        startActivity(i);
    }
}