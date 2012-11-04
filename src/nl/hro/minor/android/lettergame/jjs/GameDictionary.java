package nl.hro.minor.android.lettergame.jjs;

import android.database.Cursor;
import android.util.Log;

public class GameDictionary {

	public GameDictionary() {
		super();
	}
	
	public static Boolean checkWord(String word)
	{
		// get db
		DbHolder dbH = DbHolder.getInstance();
		DbUtils db = dbH.getDb();
		
		Cursor result = db.myDataBase.rawQuery("SELECT * FROM dictionary WHERE word='" + word + "'", null);

		if (result.moveToFirst())
		{
			do {
				String dbWord = result.getString(0);
				if( dbWord.equals(word) ){
					Log.w("DB", "Match!");
					return true;
				}
			} while (result.moveToNext());
		}
		return false;

	}
	
	public String randomWord(){
		
		// Return random word from database
		// Could be used as 'preset' - the letters of this word can be mixed and used on the dices
		
		return "";
		
	}

	
}
