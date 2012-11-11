package nl.hro.minor.android.games.differences;

import android.app.Activity;

public class contextHolder{

	private static contextHolder _ch = null;
	private Activity _c;
	
	private contextHolder(){
		
	}
	
	public static contextHolder getInstance(){
		if(_ch == null){
			_ch = new contextHolder();
		}
		
		return _ch;
	}
	
	public void setContext(Activity c){
		if(_c == null){
			_c = c;
		}
	}
	
	public Activity getContext(){
		return _c;
	}
}