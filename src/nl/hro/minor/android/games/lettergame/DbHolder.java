package nl.hro.minor.android.games.lettergame;

public class DbHolder{

	private static DbHolder _dbH = null;
	private DbUtils _db;
	
	private DbHolder(){
		
	}
	
	public static DbHolder getInstance(){
		if(_dbH == null){
			_dbH = new DbHolder();
		}
		
		return _dbH;
	}
	
	public void setDb(DbUtils db){
		if(_db == null){
			_db = db;
		}
	}
	
	public DbUtils getDb(){
		return _db;
	}
}