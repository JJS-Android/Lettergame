package nl.hro.minor.android.lettergame.jjs.differences;

import nl.hro.minor.android.lettergame.jjs.R;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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
	private TextView _timeLeftView;
	private TextView _totalScoreView;
	
	private DBAdapter db;

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score_screen);
        
		// database
		db = new DBAdapter(this);
        
        // get score fields
        _scoreView = (TextView) findViewById(R.id.scoreTextView);
        _timeLeftView = (TextView) findViewById(R.id.timeleftTextView);
        _totalScoreView = (TextView) findViewById(R.id.totalScoreTextView);
        
        int timeLeft = 0;
        boolean isFinished = false;
        // get variables given
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            timeLeft = extras.getInt("timeLeft");
            isFinished = extras.getBoolean("isFinished");
        }
        _timeLeftView.setText(getString(R.string.timeLeftString) + " " + timeLeft);
        
        // get static final playtime
    	int playTime = Game1.playTime;
    	_score = _score + (playTime - timeLeft);
        _scoreView.setText(getString(R.string.yourScoreString) + " " + _score);

        // add score to the total
        Game1._totalScore += _score;
        
        // get static total score
        _totalScore = Game1._totalScore;
        _totalScoreView.setText(getString(R.string.totalScoreString) + " " + _totalScore);
        
        // get the buttons
        View submitScoreBtn = findViewById(R.id.submitScoreBtn);
        View submitScoreCancelBtn = findViewById(R.id.submitScoreCancelBtn);
        View nextImageBtn = findViewById(R.id.nextImageBtn);
    	View inputNameEditText = findViewById(R.id.inputNameEditText);
    	View youLostEditText = findViewById(R.id.youLostEditText);
        
        // show or hide buttons
        if (isFinished) {
        	if (this.isInHighscore(_totalScore)) {
	            submitScoreBtn.setOnClickListener(this);
	            submitScoreCancelBtn.setOnClickListener(this);
	            youLostEditText.setVisibility(View.GONE);
        	} else {
            	inputNameEditText.setVisibility(View.GONE);
                submitScoreBtn.setVisibility(View.GONE);
                submitScoreCancelBtn.setVisibility(View.GONE);
        	}
            nextImageBtn.setVisibility(View.GONE);
        } else {
        	inputNameEditText.setVisibility(View.GONE);
            submitScoreBtn.setVisibility(View.GONE);
            submitScoreCancelBtn.setVisibility(View.GONE);
            youLostEditText.setVisibility(View.GONE);
        }
    }

	//@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.submitScoreBtn :
				// get name input
				EditText nameEditText = (EditText)findViewById(R.id.inputNameEditText);
				String name = nameEditText.getText().toString();

				db.open();
				db.addScore(name, _totalScore);
				db.close();
				
				Intent i = new Intent(this, HighScoreList.class);
				startActivity(i);
			break;
			case R.id.nextImageBtn :
				// TODO: add next image function call
				finish();
			break;
			case R.id.submitScoreCancelBtn :
				finish();
			break;
		}
	}
	
	private boolean isInHighscore(int score) {
		int count = 0;
		boolean inHighscore = false;
		// open database
		db.open();
		Cursor c = db.getAllScores();
		if (c.moveToFirst())
		{
			do {
				count++;
				// if score is higher than current scores but not passed maximum scores
				if (score > c.getInt(2) && count <= _maxScores) {
					db.close();
					return true;
				}
			} while (c.moveToNext());
		}
		// inHighscore true if less scores than maximum scores
		if (c.getCount() < _maxScores) {
			inHighscore = true;
		}
		db.close();
		return inHighscore;
	}
}
