package nl.hro.minor.android.lettergame.jjs.differences;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	public static final String SCORE_ROWID = "_id";
	public static final String SCORE_NAME = "name";
	public static final String SCORE_SCORE = "score";
	private static String TAG;
	private static final String DATABASE_NAME = "zoekverschillen";
	private static final String DATABASE_TABLE = "highscore";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE =
		"create table highscore (_id integer primary key autoincrement, "
			+ "name text not null, score text not null);";
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	public DBAdapter(Context ctx)
	{
		this.context = ctx;
		TAG = getClass().getSimpleName();
		DBHelper = new DatabaseHelper(context);
	}
	
	public DBAdapter open() throws SQLException
	{
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	// closes the database
	public void close()
	{
		DBHelper.close();
	}
	
	// insert a reminder into the database
	public long addScore(String name, int score)
	{
		ContentValues initialValues = new ContentValues();
		initialValues.put(SCORE_NAME, name);
		initialValues.put(SCORE_SCORE, score);
		return db.insert(DATABASE_TABLE, null, initialValues);
	}
	
	// deletes a score
	public boolean deleteScore(long rowId)
	{
		Log.w(TAG, "Deleting row: " + rowId);
		return db.delete(DATABASE_TABLE, SCORE_ROWID + "=" + rowId, null) > 0;
	}
	
	// retrieves all scores
	public Cursor getAllScores()
	{
		return db.query(DATABASE_TABLE, new String[] {
		SCORE_ROWID,
		SCORE_NAME,
		SCORE_SCORE},
		null,
		null,
		null,
		null,
		SCORE_SCORE + " DESC");
	}

	// retrieves a specific score row
	public Cursor getScore(long rowId) throws SQLException
	{
		Cursor mCursor =
		db.query(true, DATABASE_TABLE, new String[] {
			SCORE_ROWID,
			SCORE_NAME,
			SCORE_SCORE
			},
			SCORE_ROWID + "=" + rowId,
			null,
			null,
			null,
			null,
			null);
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		return mCursor;
	}
	
	// updates a specific score row
	public boolean updateScore(long rowId, String name, String score)
	{
		ContentValues args = new ContentValues();
		args.put(SCORE_NAME, name);
		args.put(SCORE_SCORE, score);
		return db.update(DATABASE_TABLE, args,
		SCORE_ROWID + "=" + rowId, null) > 0;
	}
	
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			Log.e(TAG,"dbhelper created");
		}
		
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			db.execSQL(DATABASE_CREATE);
			Log.e(TAG,"db created");
		}
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			Log.w(TAG, "Upgrading database from version " + oldVersion
			+ " to "
			+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS GPS_coords");
			onCreate(db);
		}
	}
}
