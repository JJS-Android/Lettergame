package nl.hro.minor.android.games.pogo;

import nl.hro.minor.android.games.R;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class GamePogo extends Activity {

	private GameView _gv;
	private boolean _gamePaused = false;
	private boolean _activityOrientation = true;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Set the current context (this) in the 'singleton' contextHolder.
        ContextHolder ch = ContextHolder.getInstance();
        ch.setContext(this);
        
        _gv = new GameView(this);
        setContentView(_gv);
        
        // Flip orientation (to landscape if device is tablet
        if(isTablet(this)){
        	Log.w("Orientation", "Flipped, tablet detected");
        	flipOrientation();
        }
        
    }

	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		this.finish(); // Stops activity
		
	}

	@Override
	protected void onPause() {		
		// Pause everything on the gameview
		_gv.pause();
		
		_gamePaused = true;
		
		super.onPause();
	}

	@Override
	protected void onResume() {
		
		super.onResume();
		
		// Only resume if game was paused
		if(_gamePaused){
			// Start everything up again on the gameview
			_gv.resume();
		}
		
	}
	
	private void flipOrientation(){
		if(_activityOrientation){
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			_activityOrientation = false;
		} else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			_activityOrientation = true;
		}
	}

    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.gameoptions, menu);
    	return true;
    } 

    public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
		    case R.id.flipOrientation:
		    	this.flipOrientation();
		    return true;
	    }
	    return false;
    }
    
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
	
	
}
