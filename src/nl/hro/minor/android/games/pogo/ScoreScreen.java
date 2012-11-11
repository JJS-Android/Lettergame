package nl.hro.minor.android.games.pogo;

import nl.hro.minor.android.games.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ScoreScreen extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Set the correct screen orientation
		setOrientation();
		
		setContentView(R.layout.finalscorescreen);
		
		Intent intent = getIntent();
		String scorep1 = intent.getStringExtra("score1");
		String scorep2 = intent.getStringExtra("score2");
		String scorep3 = intent.getStringExtra("score3");
		String scorep4 = intent.getStringExtra("score4");
		
		String finalScores = "Scores: \n\n";
		
		if(scorep1 != null){
			finalScores += "Player 1: " + scorep1 + "\n";
		}
		if(scorep2 != null){
			finalScores += "Player 2: " + scorep2 + "\n";
		}
		if(scorep3 != null){
			finalScores += "Player 3: " + scorep3 + "\n";
		}
		if(scorep4 != null){
			finalScores += "Player 4: " + scorep4 + "\n";
		}
		
		TextView tv = (TextView) findViewById(R.id.scoreTextView);
    	tv.setText(finalScores);
    	
    	Button btnPlayAgain = (Button) findViewById(R.id.btnPlayAgain);
    	btnPlayAgain.setOnClickListener(this);
		
	}

	public void onClick(View v) {
		
		switch (v.getId()) {
			case R.id.btnPlayAgain:
				// Restart the game (starting GamePogo class - which was finished when the game ended)
				Intent i = new Intent(this, GamePogo.class);
				this.finish(); // Quit this activity (so it won't break the back button while you're ingame)
				startActivity(i);
			break;
		}
	}
	
	private void setOrientation(){
		
		// Get the GamePogo instance
		GamePogo c = (GamePogo) ContextHolder.getInstance().getContext();
		
		// Determine which orientation is the current orientation and set it that way
		if(c.getOrientation().equals("portrait")){
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		} else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
	}

}
