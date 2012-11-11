package nl.hro.minor.android.games.differences;

import nl.hro.minor.android.games.Main;
import nl.hro.minor.android.games.R;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Score extends Activity implements OnClickListener {
	
	public static final int _maxScores = 10;
	
	private int _score = 0;
	private int _totalScore;
	private TextView _scoreView;
	private TextView _totalScoreView;
	
	private DBAdapter _db;

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_screen);
        
        // get score fields
        _scoreView = (TextView) findViewById(R.id.scoreTextView);
        _totalScoreView = (TextView) findViewById(R.id.totalScoreTextView);
        
        int timeLeft = 0;
        boolean isFinished = false;
        // get variables given
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            timeLeft = extras.getInt("timeLeft");
            isFinished = extras.getBoolean("isFinished");
        }
        
    	_score += timeLeft;
        _scoreView.setText(getString(R.string.yourScoreString) + " " + _score);

        // add score to the total
        Game1._totalScore += _score;
        
        // get static total score
        _totalScore = Game1._totalScore;
        _totalScoreView.setText(getString(R.string.totalScoreString) + " " + _totalScore);
        
        // get the buttons
        View submitScoreBtn = findViewById(R.id.submitScoreBtn);
        View nextImageBtn = findViewById(R.id.nextImageBtn);
    	View inputNameEditText = findViewById(R.id.inputNameEditText);
    	View youLostEditText = findViewById(R.id.youLostEditText);
    	View exitBtn = findViewById(R.id.exitBtn);

    	// set listners
    	exitBtn.setOnClickListener(this);
    	nextImageBtn.setOnClickListener(this);
        
        // show or hide buttons
        if (isFinished) {
            // database
            _db = new DBAdapter(this);
            // if score is in highscore
        	if (this.isInHighscore(_totalScore)) {
	            submitScoreBtn.setOnClickListener(this);
	            youLostEditText.setVisibility(View.GONE);
        	} else {
            	inputNameEditText.setVisibility(View.GONE);
                submitScoreBtn.setVisibility(View.GONE);
        	}
            nextImageBtn.setVisibility(View.GONE);
        } else {
            exitBtn.setVisibility(View.GONE);
        	inputNameEditText.setVisibility(View.GONE);
            submitScoreBtn.setVisibility(View.GONE);
            youLostEditText.setVisibility(View.GONE);
        }
    }

	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.submitScoreBtn :
				// get name input
				EditText nameEditText = (EditText)findViewById(R.id.inputNameEditText);
				String name = nameEditText.getText().toString();

				_db.open();
				_db.addScore(name, _totalScore);
				_db.close();
				
				Intent i = new Intent(this, HighScoreList.class);
				startActivity(i);
			break;
			case R.id.nextImageBtn :
			    // close score screen
				this.finish();
			break;
			case R.id.exitBtn :
			    // restart mainmenu
			    Activity ch = contextHolder.getInstance().getContext();
			    Intent j = new Intent(ch, Main.class);
			    ch.startActivity(j);
			break;
		}
	}

	private boolean isInHighscore(int score) {
		int count = 0;
		boolean inHighscore = false;
		// open database
		_db.open();
		Cursor c = _db.getAllScores();
		if (c.moveToFirst())
		{
			do {
				count++;
				// if score is higher than current scores but not passed maximum scores
				if (score > c.getInt(2) && count <= _maxScores) {
					_db.close();
					return true;
				}
			} while (c.moveToNext());
		}
		// inHighscore true if less scores than maximum scores
		if (c.getCount() < _maxScores) {
			inHighscore = true;
		}
		_db.close();
		return inHighscore;
	}
}
