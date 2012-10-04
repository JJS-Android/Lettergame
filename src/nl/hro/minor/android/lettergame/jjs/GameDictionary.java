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
			db.createdatabase();
			db.opendatabase();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//*
		Cursor result = db.myDataBase.rawQuery("SELECT word FROM dictionary WHERE word='" + word + "'", null);
		//Cursor rs = 
		
		
		while(!result.isLast()){
			result.moveToNext();
			String dbWord = result.getString(1);
			if( dbWord == word ){
				Log.w("DB", "Match!");
			}
		}
		//*/
		return false;

	}

	
}
