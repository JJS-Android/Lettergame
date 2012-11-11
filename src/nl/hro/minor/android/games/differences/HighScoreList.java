package nl.hro.minor.android.games.differences;


import java.util.ArrayList;

import nl.hro.minor.android.games.R;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HighScoreList extends Activity {
	private DBAdapter db;
	private int _maxScores = Score._maxScores;

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score_list);
        
        // get highscore listview
        ListView highScoreListView = (ListView) findViewById(R.id.highScoreListView);
        
		db = new DBAdapter(this);
		db.open();
		// get all scores
		Cursor c = db.getAllScores();
		int count = 0;
		ArrayList<String> scores = new ArrayList<String>();
		if (c.moveToFirst())
		{
			do {
				count++;
				if (count > _maxScores) {
				    // if more than maxscores, remove last
					db.deleteScore(c.getInt(0));
				} else {
				    // otherwise get score to add in listview
					String s = c.getString(1) + ": " + c.getInt(2);
					scores.add(s);
				}
			} while (c.moveToNext());
		}
		
        // set adapter
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, scores);
		highScoreListView.setAdapter(adapter);
		// close db
		db.close();
    }
}
