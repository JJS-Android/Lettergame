package nl.hro.minor.android.games.lettergame;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DbUtils extends SQLiteOpenHelper{
	private Context mycontext;

	private String DB_PATH = "/data/data/nl.hro.minor.android.games/lettergame/databases/";
	private static String DB_NAME = "gamedictionary.db";//the extension may be .sqlite or .db
	public SQLiteDatabase myDataBase;

	public DbUtils(Context context)  {
	    super(context,DB_NAME,null,1);
	    this.mycontext=context;
	    boolean dbexist = checkdatabase();
	    if(dbexist)
	    {
	        System.out.println("Database exists");
	        opendatabase(); 
	    }
	    else
	    {
	        System.out.println("Database doesn't exist");
	        createdatabase();
	    }

	}

	public void createdatabase() {
	    boolean dbexist = checkdatabase();
	    if(dbexist)
	    {
	        System.out.println("Database exists.");
	    }
	    else{
	    	System.out.println("Reading database.");
	        this.getReadableDatabase();
	    try{
	    	System.out.println("Try database copy");
	    		copydatabase();
	    		opendatabase();
	        }
	        catch(IOException e){
	            throw new Error("Error copying database");
	        }
	    }
	}
	private boolean checkdatabase() {
	    boolean checkdb = false;
	    try{
	        String myPath = DB_PATH + DB_NAME;
	        File dbfile = new File(myPath);
	        checkdb = dbfile.exists();
	    }
	    catch(SQLiteException e){
	        System.out.println("Database doesn't exist");
	    }

	    return checkdb;
	}
	private void copydatabase() throws IOException {

	    //Open your local db as the input stream
	    InputStream myinput = mycontext.getAssets().open(DB_NAME);

	    // Path to the just created empty db
	    String outfilename = DB_PATH + DB_NAME;

	    //Open the empty db as the output stream
	    OutputStream myoutput = new FileOutputStream(outfilename);

	    // transfer byte to inputfile to outputfile
	    byte[] buffer = new byte[1024];
	    int length;
	    while ((length = myinput.read(buffer))>0)
	    {
	        myoutput.write(buffer,0,length);
	    }

	    //Close the streams
	    myoutput.flush();
	    myoutput.close();
	    myinput.close();
	    System.out.println("Database copied.");
	}

	public void opendatabase() throws SQLException
	{
	    //Open the database
	    String mypath = DB_PATH + DB_NAME;
	    myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);

	}



	public synchronized void close(){
	    if(myDataBase != null){
	        myDataBase.close();
	    }
	    super.close();
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
}
