package nl.hro.minor.android.games.differences;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	
	private static String dbPath = "";
	private static String dbName = "highscore";
	private static SQLiteDatabase db;
	private static DBHelper databaseHelper = null;
	private static String TAG;
	public final Context context;
	
	public DBHelper(Context context) {
	    super(context, dbName, null, 1);
	    this.context = context;
		TAG = getClass().getSimpleName();
	}
	public static DBHelper getInstance(Context context) {
	    if (databaseHelper == null) {
	        databaseHelper = new DBHelper(context);
	        databaseHelper.openDataBase();
	
	        if (db == null) {
	            try {
	                db = databaseHelper.getWritableDatabase();
	                databaseHelper.copyDataBase();
	            } 
	            catch (Exception e) {
	                Log.i(TAG, "Error in database creation");
	            }
	
	            databaseHelper.openDataBase();
	        }
	    }
	    return databaseHelper;
	}
	
	/** 
	 * To return the database. 
	 */
	public SQLiteDatabase getDatabase() {
	    return db;
	}
	
	/** 
	 * To open the database. 
	 */
	private boolean openDataBase() throws SQLException {
	
	    String path = dbPath + dbName;
	    try {
	
	        db = SQLiteDatabase.openDatabase(path, null,
	                SQLiteDatabase.OPEN_READWRITE);
	    } 
	    catch (SQLiteException e) {
	        Log.i(TAG, "Error in openDataBase method");
	        // TODO: handle exception
	    }
	    return db != null ? true : false;
	}
	private void copyDataBase() throws IOException {
	
	    String outFileName = dbPath + dbName;
	    OutputStream output = null;
	
	    try {
	        output = new FileOutputStream(outFileName);
	    } 
	    catch (IOException e) {
	        Log.i(TAG, "Error in copyDataBase method");
	    }
	
	    output.flush();
	    output.close();
	
	    /*SchemaCreator schemaCreator = new SchemaCreator(db);
	    try {
	
	        schemaCreator.createTables();
	
	    }
	    catch (SQLiteException e) {
	        Log.i(LOGTAG, "Error in createTables method");
	    }*/
	}
	@Override
	public synchronized void close() {
	    if (db != null)
	        db.close();
	    super.close();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
	
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	
    }
}