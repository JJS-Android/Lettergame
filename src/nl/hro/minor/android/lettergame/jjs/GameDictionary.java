package nl.hro.minor.android.lettergame.jjs;

import java.io.IOException;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GameDictionary {

	public GameDictionary() {
		super();
	}
	
	public static Boolean checkWord(String word){
		
		// Get main context
		ContextHolder ch = ContextHolder.getInstance();

		DbUtils db = null;
		SQLiteDatabase dbDictionary = null;
		try {
			db = new DbUtils(ch.getContext());
			//db.createdatabase();
			//db.opendatabase();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Cursor result = db.myDataBase.rawQuery("SELECT * FROM dictionary WHERE word='" + word + "'", null);

		if (result.moveToFirst())
		{
			do {
				String dbWord = result.getString(0);
				if( dbWord.equals(word) ){
					Log.w("DB", "Match!");
					db.myDataBase.close();
					return true;
				}
			} while (result.moveToNext());
		}
		db.myDataBase.close();
		return false;

	}
	
	public String randomWord(){
		
		// Return random word from database
		// Could be used as 'preset' - the letters of this word can be mixed and used on the dices
		
		return "";
		
	}

	
}
